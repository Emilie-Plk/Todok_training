package com.example.todok.domain.todo

interface TodoRepository {
    suspend fun add(todoEntity: TodoEntity): Boolean
    suspend fun  delete(todoId: Long): Boolean
    suspend fun update(todoEntity: TodoEntity): Boolean
}