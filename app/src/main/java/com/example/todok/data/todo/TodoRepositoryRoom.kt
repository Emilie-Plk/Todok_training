package com.example.todok.data.todo

import android.database.sqlite.SQLiteException
import com.example.todok.domain.CoroutineDispatcherProvider
import com.example.todok.domain.todo.TodoEntity
import com.example.todok.domain.todo.TodoRepository
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TodoRepositoryRoom @Inject constructor(
    private val todoDao: TodoDao,
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider,
) : TodoRepository {
    override suspend fun add(todoEntity: TodoEntity): Boolean = withContext(coroutineDispatcherProvider.io) {
        try {
            todoDao.insert(todoEntity)
            true
        } catch (e: SQLiteException) {
            e.printStackTrace()
            false
        }
    }

    override suspend fun delete(todoId: Long): Boolean = withContext(coroutineDispatcherProvider.io) {
        todoDao.delete(todoId) == 1 // one row deleted
    }
}