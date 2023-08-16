package com.example.todok.domain.category

import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository,
) {
    fun invoke() = categoryRepository.getCategoriesAsFlow()
}