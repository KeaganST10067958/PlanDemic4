package com.keagan.plandemic.ui.todo

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.keagan.plandemic.data.local.TodoEntity
import com.keagan.plandemic.databinding.ItemTodoBinding

class TodoAdapter(
    private val onToggle: (TodoEntity) -> Unit
) : ListAdapter<TodoEntity, TodoAdapter.VH>(Diff) {

    object Diff : DiffUtil.ItemCallback<TodoEntity>() {
        override fun areItemsTheSame(a: TodoEntity, b: TodoEntity) = a.id == b.id
        override fun areContentsTheSame(a: TodoEntity, b: TodoEntity) = a == b
    }

    inner class VH(private val b: ItemTodoBinding) : RecyclerView.ViewHolder(b.root) {
        private val listener = CompoundButton.OnCheckedChangeListener { _, _ ->
            val item = currentList[bindingAdapterPosition]
            onToggle(item)
        }
        fun bind(item: TodoEntity) {
            b.checkBox.setOnCheckedChangeListener(null)
            b.checkBox.isChecked = item.isDone
            b.title.text = item.text
            b.checkBox.setOnCheckedChangeListener(listener)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val inflater = LayoutInflater.from(parent.context)
        return VH(ItemTodoBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(getItem(position))
    }
}
