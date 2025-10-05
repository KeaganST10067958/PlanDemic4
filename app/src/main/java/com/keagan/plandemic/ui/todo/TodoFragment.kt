package com.keagan.plandemic.ui.todo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.keagan.plandemic.data.local.TodoEntity
import com.keagan.plandemic.databinding.FragmentTodoBinding

class TodoFragment : Fragment() {

    private var _b: FragmentTodoBinding? = null
    private val b get() = _b!!

    private val vm: TodoViewModel by viewModels()
    private lateinit var adapter: TodoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _b = FragmentTodoBinding.inflate(inflater, container, false)
        return b.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = TodoAdapter(onToggle = { vm.toggle(it) })

        b.rvTodos.layoutManager = LinearLayoutManager(requireContext())
        b.rvTodos.adapter = adapter

        // Swipe to delete
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(rv: RecyclerView, vh: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder) = false
            override fun onSwiped(vh: RecyclerView.ViewHolder, dir: Int) {
                val item = adapter.currentList[vh.bindingAdapterPosition]
                vm.delete(item.id)
                Snackbar.make(b.root, "Deleted", Snackbar.LENGTH_LONG)
                    .setAction("Undo") { vm.add(item.text) }
                    .show()
            }
        }).attachToRecyclerView(b.rvTodos)

        // Observe data
        vm.items.observe(viewLifecycleOwner) { adapter.submitList(it) }

        // Add actions
        b.btnAdd.setOnClickListener { submit() }
        b.inputTodo.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) { submit(); true } else false
        }

        b.btnClearDone.setOnClickListener { vm.clearDone() }
    }

    private fun submit() {
        val text = b.inputTodo.text?.toString().orEmpty()
        vm.add(text)
        b.inputTodo.text?.clear()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _b = null
    }
}
