package com.example.todok.ui.addTodo

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.todok.R
import com.example.todok.domain.CoroutineDispatcherProvider
import com.example.todok.domain.category.GetCategoriesUseCase
import com.example.todok.domain.todo.AddTodoUseCase
import com.example.todok.domain.todo.TodoEntity
import com.example.todok.ui.utils.NativeText
import com.example.todok.ui.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AddTodoViewModel @Inject constructor(
    private val addTodoUseCase: AddTodoUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider,
) : ViewModel() {

    private val isAddingTotoInDatabaseMutableStateFlow = MutableStateFlow(false)
    private val categoryIdMutableStateFlow = MutableStateFlow<Long?>(null)
    private val descriptionMutableStateFlow = MutableStateFlow<String?>(null)
    private val isDoneMutableStateFlow = MutableStateFlow<Boolean?>(null)
    private val hasSaveButtonBeenClickedMutableStateFlow = MutableStateFlow(false)

    val singleLiveEvent = SingleLiveEvent<AddTodoEvent>()

    val viewStateLiveData: LiveData<AddTodoViewState> = liveData {
        combine(
            getCategoriesUseCase.invoke(),
            isAddingTotoInDatabaseMutableStateFlow,
        ) { categories, isAddingTodoInDatabase ->
            emit(
                AddTodoViewState(
                    items = categories.map { categoryEntity ->
                        AddTodoViewStateItem(
                            categoryId = categoryEntity.id,
                            categoryColor = categoryEntity.colorInt,
                            categoryName = categoryEntity.name,
                        )
                    },
                    isProgressBarVisible = isAddingTodoInDatabase,
                    isSaveButtonEnabled = !isAddingTodoInDatabase,
                    isDone = false,
                )
            )
        }.collect()
    }

    fun onCategorySelected(categoryId: Long) {
        categoryIdMutableStateFlow.value = categoryId
    }

    fun onDescriptionChanged(description: String?) {
        descriptionMutableStateFlow.value = description
    }

    fun onSaveButtonClicked() {
        val capturedCategoryId = categoryIdMutableStateFlow.value  // is it ok? (// MutableLD)
        val capturedDescription = descriptionMutableStateFlow.value
        val capturedIsDone = isDoneMutableStateFlow.value

        if (capturedCategoryId != null && !capturedDescription.isNullOrBlank()) {
            isAddingTotoInDatabaseMutableStateFlow.value = true

            viewModelScope.launch(coroutineDispatcherProvider.io) {
                val success = addTodoUseCase.invoke(
                    TodoEntity(
                        categoryId = capturedCategoryId,
                        description = capturedDescription,
                        isDone = capturedIsDone ?: false
                    )
                )

                isAddingTotoInDatabaseMutableStateFlow.value = false // adding to db: done!

                withContext(coroutineDispatcherProvider.main) {
                    singleLiveEvent.value = if (success) {
                        AddTodoEvent.Dismiss
                    } else {
                        AddTodoEvent.Toast(text = NativeText.Resource(R.string.cant_insert_todo))
                    }
                }
            }
        } else {
            singleLiveEvent.value = AddTodoEvent.Toast(text = NativeText.Resource(R.string.error_inserting_todo))
        }

        hasSaveButtonBeenClickedMutableStateFlow.value = true
    }

}