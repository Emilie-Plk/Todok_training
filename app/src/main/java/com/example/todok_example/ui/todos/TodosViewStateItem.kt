package com.example.todok_example.ui.todos

import androidx.annotation.ColorInt
import com.example.todok_example.ui.utils.EquatableCallback

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
        val onDeleteEvent: EquatableCallback,
    ) : TodosViewStateItem(Type.TODO)

    object EmptyState : TodosViewStateItem(Type.EMPTY_STATE)
}
