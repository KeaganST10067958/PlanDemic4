package com.keagan.plandemic.ui.todo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.keagan.plandemic.R

class TodoFragment : Fragment() {

    private val vm: TodoViewModel by viewModels()
    private lateinit var adapter: TodoAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_todo, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val input = view.findViewById<EditText>(R.id.inputTodo)
        val btnAdd = view.findViewById<Button>(R.id.btnAdd)
        val list = view.findViewById<RecyclerView>(R.id.list)

        adapter = TodoAdapter(
            onToggle = { id -> vm.toggle(id) },
            onDelete = { id -> vm.delete(id) }
        )
        list.adapter = adapter
        list.layoutManager = LinearLayoutManager(requireContext())
        list.addItemDecoration(
            DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL)
        )
        list.setHasFixedSize(true)

        btnAdd.setOnClickListener {
            val text = input.text?.toString()?.trim().orEmpty()
            if (text.isNotEmpty()) {
                vm.add(text)
                input.setText("")
            }
        }

        vm.items.observe(viewLifecycleOwner) { items ->
            adapter.submitList(items)
        }
    }
}
