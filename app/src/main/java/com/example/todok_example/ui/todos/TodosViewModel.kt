package com.example.todok_example.ui.todos

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TodosViewModel @Inject constructor() : ViewModel() {
    fun onAddButtonClicked() {
        TODO("Not yet implemented")
    }

    val viewStateLiveData: LiveData<List<TodosViewStateItem>>
}