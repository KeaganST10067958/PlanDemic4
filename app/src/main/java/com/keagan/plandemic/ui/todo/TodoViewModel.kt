package com.keagan.plandemic.ui.todo

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.keagan.plandemic.data.TodoRepository
import com.keagan.plandemic.data.local.Todo
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TodoViewModel(app: Application) : AndroidViewModel(app) {
    private val repo = TodoRepository(app)

    val todos: StateFlow<List<Todo>> =
        repo.observeAll().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun add(title: String) = viewModelScope.launch { if (title.isNotBlank()) repo.add(title.trim()) }
    fun toggle(todo: Todo, done: Boolean) = viewModelScope.launch { repo.toggle(todo.id, done) }
    fun delete(todo: Todo) = viewModelScope.launch { repo.delete(todo) }
}
