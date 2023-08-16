package com.example.todok.domain.todo

import javax.inject.Inject

class AddTodoUseCase @Inject constructor(
    private val todoRepository: TodoRepository,
) {

    suspend fun invoke(todo: TodoEntity) {
        todoRepository.add(todo)
    }
}

