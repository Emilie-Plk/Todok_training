package com.example.todok.ui.addTodo

import androidx.annotation.ColorInt

data class AddTodoViewStateItem(
    val categoryId: Long,
    @get:ColorInt @param:ColorInt
    val categoryColor: Int,
    val categoryName: String,
)
