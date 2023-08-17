package com.example.todok.data.todo

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.todok.domain.todo.TodoEntity

@Dao
interface TodoDao {

    @Insert
    suspend fun insert(todoEntity: TodoEntity)

    @Update
    suspend fun update(todoEntity: TodoEntity): Int

    @Query("DELETE FROM todo WHERE id = :taskId")
    suspend fun delete(taskId: Long): Int
}