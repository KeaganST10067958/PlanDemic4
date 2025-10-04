package com.keagan.plandemic.ui.todo

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.keagan.plandemic.R
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class TodoFragment : Fragment(R.layout.fragment_todo) {

    private val vm: TodoViewModel by viewModels()
    private lateinit var adapter: TodoAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val input = view.findViewById<EditText>(R.id.inputTitle)
        val btnAdd = view.findViewById<Button>(R.id.btnAdd)
        val list = view.findViewById<RecyclerView>(R.id.list)

        adapter = TodoAdapter(
            onToggle = { t, done -> vm.toggle(t, done) },
            onDelete = { t -> vm.delete(t) }
        )
        list.layoutManager = LinearLayoutManager(requireContext())
        list.adapter = adapter

        // Swipe to delete (left/right)
        val ith = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(rv: RecyclerView, vh: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder) = false
            override fun onSwiped(vh: RecyclerView.ViewHolder, dir: Int) {
                val item = adapter.currentList[vh.bindingAdapterPosition]
                vm.delete(item)
            }
        })
        ith.attachToRecyclerView(list)

        btnAdd.setOnClickListener {
            vm.add(input.text.toString())
            input.text = null
            input.clearFocus()
        }
        input.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                btnAdd.performClick()
                true
            } else false
        }

        viewLifecycleOwner.lifecycleScope.launch {
            vm.todos.collectLatest { items -> adapter.submitList(items) }
        }
    }
}
