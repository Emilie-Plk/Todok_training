package com.example.todok.ui.todos

import android.text.SpannableString
import android.text.Spanned
import android.text.style.StrikethroughSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todok.databinding.TodoEmptyStateBinding
import com.example.todok.databinding.TodoHeaderItemBinding
import com.example.todok.databinding.TodoItemBinding

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

        class Todo(private val binding: TodoItemBinding) : TodosViewHolder(binding.root) {
            companion object {
                fun create(parent: ViewGroup) = Todo(
                    binding = TodoItemBinding.inflate(
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
                binding.root.setOnLongClickListener {
                    item.onLongClickEvent.invoke()
                    true
                }
                if (item.isDone) {
                    val strikethroughText = SpannableString(item.description)
                    strikethroughText.setSpan(
                        StrikethroughSpan(),
                        0,
                        item.description.length,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                    binding.noteItemTextViewTitle.text = strikethroughText
                } else {
                    binding.noteItemTextViewTitle.text = item.description
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