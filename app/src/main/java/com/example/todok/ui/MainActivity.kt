package com.example.todok.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commitNow
import com.example.todok.databinding.MainActivityBinding
import com.example.todok.ui.addTodo.AddTodoDialogFragment
import com.example.todok.ui.todos.TodosFragment
import com.example.todok.ui.utils.NavigationListener
import com.example.todok.ui.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), NavigationListener {

    private val binding by viewBinding { MainActivityBinding.inflate(it) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.commitNow {
                replace(binding.mainFrameLayout.id, TodosFragment.newInstance())
            }
        }
    }

    override fun displayAddTaskDialog() {
        AddTodoDialogFragment.newInstance().show(supportFragmentManager, null)
    }
}