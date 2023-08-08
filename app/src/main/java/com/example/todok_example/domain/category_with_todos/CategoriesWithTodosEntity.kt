package com.example.todok_example.domain.category_with_todos

import androidx.room.Embedded
import androidx.room.Relation
import com.example.todok_example.domain.category.CategoryEntity
import com.example.todok_example.domain.todo.TodoEntity

data class CategoriesWithTodosEntity(
    @Embedded
    val categoryEntity: CategoryEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "categoryId"
    )
    val todoEntities: List<TodoEntity>,
)
