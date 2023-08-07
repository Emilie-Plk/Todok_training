package com.example.todok_example.ui.todos

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.todok_example.R
import com.example.todok_example.databinding.TodosFragmentBinding
import com.example.todok_example.ui.utils.NavigationListener
import com.example.todok_example.ui.utils.viewBinding
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
        binding.addTodoFab.setOnClickListener { viewModel.onAddButtonClicked() }
        viewModel.viewStateLiveData.observe(viewLifecycleOwner) { todosViewStateItems ->
            adapter.submitList(todosViewStateItems)
        }
    }
}