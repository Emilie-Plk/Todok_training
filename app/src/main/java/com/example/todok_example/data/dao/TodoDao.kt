package com.example.todok_example.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.todok_example.domain.category_with_todos.CategoriesWithTodosEntity
import com.example.todok_example.domain.todo.TodoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {

    @Insert
    suspend fun insert(todoEntity: TodoEntity)

    @Query("SELECT * from todo")
    @Transaction
    fun getAllTodosWithCategories(): Flow<List<CategoriesWithTodosEntity>>
}