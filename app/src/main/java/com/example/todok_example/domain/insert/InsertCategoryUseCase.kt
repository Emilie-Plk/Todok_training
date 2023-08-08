package com.example.todok_example.domain.insert

import com.example.todok_example.data.dao.CategoryDao
import com.example.todok_example.domain.category.CategoryEntity
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
