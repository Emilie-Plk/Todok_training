package com.example.todok.data

import com.example.todok.data.category.CategoryRepositoryRoom
import com.example.todok.data.todo.TodoRepositoryRoom
import com.example.todok.domain.category.CategoryRepository
import com.example.todok.domain.todo.TodoRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataBindingModule {

    @Singleton
    @Binds
    abstract fun bindTodoRepository(implementation: TodoRepositoryRoom): TodoRepository

    @Singleton
    @Binds
    abstract fun bindCategoryRepository(implementation: CategoryRepositoryRoom): CategoryRepository
}