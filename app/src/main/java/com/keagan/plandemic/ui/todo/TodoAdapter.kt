package com.keagan.plandemic.ui.todo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.keagan.plandemic.R

class TodoAdapter(
    private val onToggle: (Long) -> Unit,
    private val onDelete: (Long) -> Unit
) : ListAdapter<TodoItem, TodoAdapter.VH>(DIFF) {

    companion object {
        private val DIFF = object : DiffUtil.ItemCallback<TodoItem>() {
            override fun areItemsTheSame(oldItem: TodoItem, newItem: TodoItem) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: TodoItem, newItem: TodoItem) =
                oldItem == newItem
        }
    }

    inner class VH(view: View) : RecyclerView.ViewHolder(view) {
        val check: CheckBox = view.findViewById(R.id.chkDone)
        val text: TextView = view.findViewById(R.id.txtTask)
        val delete: ImageButton = view.findViewById(R.id.btnDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_todo, parent, false)
        return VH(v)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = getItem(position)
        holder.text.text = item.text
        holder.check.isChecked = item.done

        holder.check.setOnClickListener { onToggle(item.id) }
        holder.delete.setOnClickListener { onDelete(item.id) }
    }
}
