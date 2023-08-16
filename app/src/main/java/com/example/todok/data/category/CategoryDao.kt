package com.example.todok.data.category

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.todok.domain.category.CategoryEntity
import com.example.todok.domain.category_with_todos.CategoriesWithTodosEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {

    @Insert
    suspend fun insert(categoryEntity: CategoryEntity)

    @Query("SELECT * FROM category")
    fun getAllAsFlow(): Flow<List<CategoryEntity>>

    @Query("SELECT * from category")
    @Transaction
    fun getAllTodosWithCategoriesAsFlow(): Flow<List<CategoriesWithTodosEntity>>
}