package com.example.todok_example.ui.todos

sealed class TasksEvent {
    // object: singleton instance of a class
    object NavigateToAddTask : TasksEvent()
}
