package com.example.todok.domain.category

import com.example.todok.domain.category_with_todos.CategoriesWithTodosEntity
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    suspend fun add(categoryEntity: CategoryEntity)
    fun getCategoriesAsFlow(): Flow<List<CategoryEntity>>
    fun getCategoriesWithTodosAsFlow(): Flow<List<CategoriesWithTodosEntity>>
}