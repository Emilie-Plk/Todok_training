package com.example.todok.ui.addTodo

import com.example.todok.ui.utils.NativeText

sealed class AddTodoEvent {
    object Dismiss : AddTodoEvent()
    data class Toast(val text: NativeText) : AddTodoEvent()
}