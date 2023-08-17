package com.example.todok.domain.category

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository,
) {
    fun invoke(): Flow<List<CategoryEntity>> = categoryRepository.getCategoriesAsFlow()
}