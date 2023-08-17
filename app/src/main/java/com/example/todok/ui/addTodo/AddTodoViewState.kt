package com.example.todok.ui.addTodo

data class AddTodoViewState(
    val items: List<AddTodoViewStateItem>,
    val isProgressBarVisible: Boolean,
    val isSaveButtonEnabled: Boolean,
    val isDone: Boolean,
)