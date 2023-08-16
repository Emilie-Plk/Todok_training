package com.example.todok.ui.todos

sealed class TodosEvent {
    object NavigateToAddTodo : TodosEvent()
}
