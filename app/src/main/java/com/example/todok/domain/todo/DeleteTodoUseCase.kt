package com.example.todok.domain.todo

import javax.inject.Inject

class DeleteTodoUseCase @Inject constructor(
    private val todoRepository: TodoRepository,
) {
    suspend fun invoke(id: Long)  {
        todoRepository.delete(id)
    }
}
