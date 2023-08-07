package com.example.todok_example.ui.todos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.todok_example.R
import com.example.todok_example.databinding.TodosFragmentBinding
import com.example.todok_example.ui.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TodosFragment : Fragment(R.layout.todos_fragment) {

    companion object {
        fun newInstance() = TodosFragment()
    }

    private val binding by viewBinding { TodosFragmentBinding.bind(it) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        }
    }