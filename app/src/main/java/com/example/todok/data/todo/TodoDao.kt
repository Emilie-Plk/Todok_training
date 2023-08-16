package com.example.todok.data.todo

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.todok.domain.todo.TodoEntity

@Dao
interface TodoDao {

    @Insert
    suspend fun insert(todoEntity: TodoEntity)

    @Query("DELETE FROM todo WHERE id = :taskId")
    suspend fun delete(taskId: Long): Int
}