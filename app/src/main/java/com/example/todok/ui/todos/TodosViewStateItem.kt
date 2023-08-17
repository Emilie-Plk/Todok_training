package com.example.todok.ui.todos

import androidx.annotation.ColorInt
import com.example.todok.ui.utils.EquatableCallback

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
        val typeColor: Int,
        val description: String,
        val isDone: Boolean,
        val onDeleteEvent: EquatableCallback,
        val onLongClickEvent: EquatableCallback,
    ) : TodosViewStateItem(Type.TODO)

    object EmptyState : TodosViewStateItem(Type.EMPTY_STATE)
}
