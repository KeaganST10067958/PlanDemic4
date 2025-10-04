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
import com.keagan.plandemic.data.local.Todo

class TodoAdapter(
    private val onToggle: (Todo, Boolean) -> Unit,
    private val onDelete: (Todo) -> Unit
) : ListAdapter<Todo, TodoAdapter.VH>(Diff) {

    object Diff : DiffUtil.ItemCallback<Todo>() {
        override fun areItemsTheSame(old: Todo, new: Todo) = old.id == new.id
        override fun areContentsTheSame(old: Todo, new: Todo) = old == new
    }

    inner class VH(v: View) : RecyclerView.ViewHolder(v) {
        private val check: CheckBox = v.findViewById(R.id.check)
        private val title: TextView = v.findViewById(R.id.title)
        private val btnDelete: ImageButton = v.findViewById(R.id.btnDelete)

        fun bind(item: Todo) {
            title.text = item.title
            check.setOnCheckedChangeListener(null)
            check.isChecked = item.done
            check.setOnCheckedChangeListener { _, isChecked ->
                onToggle(item, isChecked)
            }
            btnDelete.setOnClickListener { onDelete(item) }
            title.alpha = if (item.done) 0.5f else 1f
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_todo, parent, false)
        return VH(v)
    }

    override fun onBindViewHolder(holder: VH, position: Int) = holder.bind(getItem(position))
}
