package com.keagan.plandemic.ui.todo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

data class TodoItem(
    val id: Long,
    val text: String,
    val done: Boolean
)

class TodoViewModel : ViewModel() {

    private val _items = MutableLiveData<List<TodoItem>>(emptyList())
    val items: LiveData<List<TodoItem>> = _items

    private var nextId = 1L

    fun add(text: String) {
        val newItem = TodoItem(nextId++, text, false)
        _items.value = (_items.value ?: emptyList()) + newItem
    }

    fun toggle(id: Long) {
        _items.value = _items.value?.map { item ->
            if (item.id == id) item.copy(done = !item.done) else item
        }
    }

    fun delete(id: Long) {
        _items.value = _items.value?.filterNot { it.id == id }
    }
}
