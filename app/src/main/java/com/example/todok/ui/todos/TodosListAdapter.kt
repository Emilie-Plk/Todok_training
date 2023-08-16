package com.example.todok.ui.todos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todok.databinding.NoteItemBinding
import com.example.todok.databinding.TodoEmptyStateBinding
import com.example.todok.databinding.TodoHeaderItemBinding

class TodosListAdapter :
    ListAdapter<TodosViewStateItem, TodosListAdapter.TodosViewHolder>(TodosDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodosViewHolder =
        when (TodosViewStateItem.Type.values()[viewType]) {
            TodosViewStateItem.Type.EMPTY_STATE -> TodosViewHolder.EmptyState.create(parent)
            TodosViewStateItem.Type.HEADER -> TodosViewHolder.Header.create(parent)
            TodosViewStateItem.Type.TODO -> TodosViewHolder.Todo.create(parent)
        }

    override fun onBindViewHolder(holder: TodosViewHolder, position: Int) {
        when (holder) {
            is TodosViewHolder.EmptyState -> Unit
            is TodosViewHolder.Header -> holder.bind(item = getItem(position) as TodosViewStateItem.Header)
            is TodosViewHolder.Todo -> holder.bind(item = getItem(position) as TodosViewStateItem.Todo)
        }
    }

    override fun getItemViewType(position: Int): Int = getItem(position).type.ordinal

    sealed class TodosViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        class EmptyState(private val binding: TodoEmptyStateBinding) :
            TodosViewHolder(binding.root) {
            companion object {
                fun create(parent: ViewGroup) = EmptyState(
                    binding = TodoEmptyStateBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
        }

        class Header(private val binding: TodoHeaderItemBinding) : TodosViewHolder(binding.root) {
            companion object {
                fun create(parent: ViewGroup) = Header(
                    binding = TodoHeaderItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }

            fun bind(item: TodosViewStateItem.Header) {
                binding.todoHeaderItemTvTitle.text = item.title
            }
        }

        class Todo(private val binding: NoteItemBinding) : TodosViewHolder(binding.root) {
            companion object {
                fun create(parent: ViewGroup) = Todo(
                    binding = NoteItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }

            fun bind(item: TodosViewStateItem.Todo) {
                binding.noteItemImageViewColor.setColorFilter(item.typeColor)
                binding.noteItemTextViewTitle.text = item.description
                binding.taskItemImageViewDelete.setOnClickListener {
                    item.onDeleteEvent.invoke()
                }
            }
        }
    }

    object TodosDiffCallback : DiffUtil.ItemCallback<TodosViewStateItem>() {
        override fun areItemsTheSame(
            oldItem: TodosViewStateItem,
            newItem: TodosViewStateItem
        ): Boolean = when {
            oldItem is TodosViewStateItem.Header && newItem is TodosViewStateItem.Header -> oldItem.title == newItem.title
            oldItem is TodosViewStateItem.Todo && newItem is TodosViewStateItem.Todo -> oldItem.id == newItem.id
            oldItem is TodosViewStateItem.EmptyState && newItem is TodosViewStateItem.EmptyState -> true
            else -> false
        }

        override fun areContentsTheSame(
            oldItem: TodosViewStateItem,
            newItem: TodosViewStateItem
        ): Boolean = oldItem == newItem
    }
}