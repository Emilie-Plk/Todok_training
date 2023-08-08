package com.example.todok_example.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.todok_example.domain.category.CategoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {

    @Insert
    suspend fun insert(categoryEntity: CategoryEntity)

    @Query("SELECT * FROM category")
    fun getAll(): Flow<List<CategoryEntity>>
}