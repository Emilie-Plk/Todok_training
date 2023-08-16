package com.example.todok.domain.insert

import com.example.todok.data.category.CategoryDao
import com.example.todok.domain.category.CategoryEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton // why?
class InsertCategoryUseCase @Inject constructor(
    private val categoryDao: CategoryDao,
) {
    suspend fun invoke(categoryEntity: CategoryEntity) {
        categoryDao.insert(categoryEntity)
    }
}
