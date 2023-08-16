package com.example.todok.ui.todos

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.todok.R
import com.example.todok.databinding.TodosFragmentBinding
import com.example.todok.ui.utils.NavigationListener
import com.example.todok.ui.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TodosFragment : Fragment(R.layout.todos_fragment) {

    companion object {
        fun newInstance() = TodosFragment()
    }

    private val binding by viewBinding { TodosFragmentBinding.bind(it) }
    private val viewModel by viewModels<TodosViewModel>()

    private lateinit var navigationListener: NavigationListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        navigationListener = context as NavigationListener
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = TodosListAdapter()
        binding.todosRv.adapter = adapter
        binding.addTodoFab.setOnClickListener { viewModel.onAddTodoClicked() }

        viewModel.viewStateLiveData.observe(viewLifecycleOwner) { todosViewStateItems ->
            adapter.submitList(todosViewStateItems)
        }

        viewModel.singleLiveEvent.observe(viewLifecycleOwner) { todosEvent ->
            when (todosEvent) {
                TodosEvent.NavigateToAddTodo -> navigationListener.displayAddTaskDialog()
            }
        }

        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.todo_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean = when (menuItem.itemId) {
                R.id.sort_todo -> {
                    viewModel.onSortButtonClicked()
                    true
                }

                else -> false
            }
        })
    }
}