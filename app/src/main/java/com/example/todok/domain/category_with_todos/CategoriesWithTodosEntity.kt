package com.example.todok.domain.category_with_todos

import androidx.room.Embedded
import androidx.room.Relation
import com.example.todok.domain.category.CategoryEntity
import com.example.todok.domain.todo.TodoEntity

data class CategoriesWithTodosEntity(
    @Embedded
    val categoryEntity: CategoryEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "categoryId"
    )
    val todoEntities: List<TodoEntity>,
)
