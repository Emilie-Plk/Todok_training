package com.example.todok.domain.todo

import javax.inject.Inject

class UpdateTodoUseCase @Inject constructor(
    private val todoRepository: TodoRepository,
) {

    suspend fun invoke(todo: TodoEntity) : Boolean =
        todoRepository.update(todo)
}
