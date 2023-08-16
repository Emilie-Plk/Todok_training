package com.example.todok.data.category

import com.example.todok.domain.CoroutineDispatcherProvider
import com.example.todok.domain.category.CategoryEntity
import com.example.todok.domain.category.CategoryRepository
import com.example.todok.domain.category_with_todos.CategoriesWithTodosEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CategoryRepositoryRoom @Inject constructor(
    private val categoryDao: CategoryDao,
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider,
) : CategoryRepository {

    override suspend fun add(categoryEntity: CategoryEntity) = withContext(coroutineDispatcherProvider.io) {
        categoryDao.insert(categoryEntity)
    }

    override fun getCategoriesAsFlow(): Flow<List<CategoryEntity>> = categoryDao
        .getAllAsFlow()
        .flowOn(coroutineDispatcherProvider.io)

    override fun getCategoriesWithTodosAsFlow(): Flow<List<CategoriesWithTodosEntity>> = categoryDao
        .getAllTodosWithCategoriesAsFlow()
        .flowOn(coroutineDispatcherProvider.io)
}