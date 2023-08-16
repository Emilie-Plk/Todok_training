package com.example.todok.domain.category_with_todos

import com.example.todok.domain.category.CategoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetCategoriesWithTodosUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository,
) {
    fun invoke(): Flow<List<CategoriesWithTodosEntity>> = categoryRepository.getCategoriesWithTodosAsFlow()
}
