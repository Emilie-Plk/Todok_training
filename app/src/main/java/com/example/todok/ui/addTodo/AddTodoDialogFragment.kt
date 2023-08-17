package com.example.todok.ui.addTodo

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.example.todok.R
import com.example.todok.databinding.AddTodoFragmentBinding
import com.example.todok.ui.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddTodoDialogFragment : DialogFragment(R.layout.add_todo_fragment) {

    companion object {
        fun newInstance() = AddTodoDialogFragment()
    }


    private val binding by viewBinding { AddTodoFragmentBinding.bind(it) }
    private val viewModel by viewModels<AddTodoViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = AddTodoProjectSpinnerAdapter()
        binding.addTodoActvCategory.setAdapter(adapter)
        binding.addTodoActvCategory.setOnItemClickListener { _, _, position, _ ->
            adapter.getItem(position)?.let {
                viewModel.onCategorySelected(it.categoryId)
            }
        }

        binding.addTodoTextInputEditText.doAfterTextChanged { viewModel.onDescriptionChanged(it?.toString()) }

        binding.addTodoBtnCancel.setOnClickListener { dismiss() }

        binding.addTodoBtnSave.setOnClickListener { viewModel.onSaveButtonClicked() }

        viewModel.viewStateLiveData.observe(this) { addTodoViewState ->
            adapter.setData(addTodoViewState.items)
            binding.addTodoBtnSave.isEnabled = addTodoViewState.isSaveButtonEnabled
            binding.addTodoProgressbar.visibility =
                if (addTodoViewState.isProgressBarVisible) View.VISIBLE else View.INVISIBLE
        }

        viewModel.singleLiveEvent.observe(this) { addTodoEvent: AddTodoEvent ->
            when (addTodoEvent) {
                is AddTodoEvent.Dismiss -> dismiss()
                is AddTodoEvent.Toast -> Toast.makeText(
                    requireContext(),
                    addTodoEvent.text.toCharSequence(requireContext()),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}