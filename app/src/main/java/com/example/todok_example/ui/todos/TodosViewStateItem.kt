package com.example.todok_example.ui.todos

import androidx.annotation.ColorInt

sealed class TodosViewStateItem(val type: Type) {

    enum class Type {
        HEADER,
        TODO,
        EMPTY_STATE
    }

    data class Header(
        val title: String
    ) : TodosViewStateItem(Type.HEADER)

    data class Todo(
        val id: Long,
        @get:ColorInt @param:ColorInt
        val type: Int,
        val description: String
    )
}
