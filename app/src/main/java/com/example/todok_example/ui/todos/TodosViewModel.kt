package com.example.todok_example.ui.todos

import androidx.lifecycle.ViewModel
import com.example.todok_example.domain.todo.DeleteTodoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TodosViewModel @Inject constructor(
    private val deleteTodoUseCase: DeleteTodoUseCase,
) : ViewModel() {
    fun onAddButtonClicked() {
        TODO("Not yet implemented")
    }

    //  val viewStateLiveData: LiveData<List<TodosViewStateItem>>
}