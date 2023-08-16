package com.example.todok.ui.todos

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.todok.domain.CoroutineDispatcherProvider
import com.example.todok.domain.category.CategoryEntity
import com.example.todok.domain.category_with_todos.GetCategoriesWithTodosUseCase
import com.example.todok.domain.todo.DeleteTodoUseCase
import com.example.todok.domain.todo.TodoEntity
import com.example.todok.ui.utils.EquatableCallback
import com.example.todok.ui.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodosViewModel @Inject constructor(
    private val getCategoriesWithTodosUseCase: GetCategoriesWithTodosUseCase,
    private val deleteTodoUseCase: DeleteTodoUseCase,
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider,
) : ViewModel() {

    private val todoSortingMutableStateFlow = MutableStateFlow(TodosSortingType.TODO_CHRONOLOGICAL)

    val singleLiveEvent = SingleLiveEvent<TodosEvent>()

    val viewStateLiveData: LiveData<List<TodosViewStateItem>> = liveData {
        combine(
            getCategoriesWithTodosUseCase.invoke(),
            todoSortingMutableStateFlow
        ) { categoriesWithTodos, todoSorting ->
            emit(
                when (todoSorting) {
                    TodosSortingType.TODO_CHRONOLOGICAL -> categoriesWithTodos
                        .asSequence()
                        .map { categoryWithTodosEntity ->
                            categoryWithTodosEntity.todoEntities.map { todoEntity ->
                                mapItem(categoryWithTodosEntity.categoryEntity, todoEntity)
                            }
                        }
                        .flatten()//  merging these inner lists into a single list
                        .sortedBy { it.id }
                        .toList()

                    TodosSortingType.CATEGORY_ALPHABETICAL -> buildList<TodosViewStateItem> {
                        categoriesWithTodos.forEach { categoryWithTodosEntity ->
                            if (categoryWithTodosEntity.todoEntities.isNotEmpty()) {
                                add(TodosViewStateItem.Header(categoryWithTodosEntity.categoryEntity.name))

                                categoryWithTodosEntity.todoEntities.forEach { todoEntity ->
                                    add(mapItem(categoryWithTodosEntity.categoryEntity, todoEntity))
                                }
                            }
                        }
                    }
                }.takeIf { it.isNotEmpty() } ?: listOf(TodosViewStateItem.EmptyState)
            )

        }.collect()
    }

    fun onAddTodoClicked() {
        singleLiveEvent.value = TodosEvent.NavigateToAddTodo
    }

    fun onSortButtonClicked() {
        todoSortingMutableStateFlow.update {
            TodosSortingType.values()[(it.ordinal + 1) % TodosSortingType.values().size]
        }
    }


    private fun mapItem(categoryEntity: CategoryEntity, todoEntity: TodoEntity) = TodosViewStateItem.Todo(
        id = todoEntity.id,
        typeColor = categoryEntity.colorInt,
        description = todoEntity.description,
        isDone = todoEntity.isDone,
        onDeleteEvent = EquatableCallback {
            viewModelScope.launch(coroutineDispatcherProvider.io) {
                deleteTodoUseCase.invoke(todoEntity.id)
            }
        }
    )
}