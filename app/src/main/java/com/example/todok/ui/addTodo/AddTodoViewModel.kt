package com.example.todok.ui.addTodo

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.todok.domain.CoroutineDispatcherProvider
import com.example.todok.domain.category.GetCategoriesUseCase
import com.example.todok.domain.todo.AddTodoUseCase
import com.example.todok.ui.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
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
    private val hasSubmitButtonBeenClickedMutableStateFlow = MutableStateFlow(false)

    private val singleLiveEvent = SingleLiveEvent<AddTodoEvent>()

    val viewStateLiveData: LiveData<AddTodoViewState> = liveData {
        combine(
            getCategoriesUseCase.invoke(),
            isAddingTotoInDatabaseMutableStateFlow,
        ) {
            categories, isAddingTodoInDatabase ->
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
                    isSubmitButtonEnabled = !isAddingTodoInDatabase
                )
            )
        }.collect()
    }


    fun onCategorySelected(categoryId: Long) {
        categoryIdMutableStateFlow.value = categoryId
    }

    fun onDescriptionChanged(description: String) {
        descriptionMutableStateFlow.value = description
    }

    fun onDoneChanged(isDone: Boolean) {
        isDoneMutableStateFlow.value = isDone
    }

    fun onSubmitButtonClicked() {
        hasSubmitButtonBeenClickedMutableStateFlow.value = true
    }

}