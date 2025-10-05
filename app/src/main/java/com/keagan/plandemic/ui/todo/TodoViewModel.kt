package com.keagan.plandemic.ui.todo

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.keagan.plandemic.data.TodoRepository
import com.keagan.plandemic.data.local.AppDatabase
import com.keagan.plandemic.data.local.TodoEntity
import kotlinx.coroutines.launch

class TodoViewModel(app: Application) : AndroidViewModel(app) {
    private val repo = TodoRepository(AppDatabase.get(app).todoDao())

    val items = repo.items.asLiveData()

    fun add(text: String) = viewModelScope.launch { repo.add(text) }

    fun toggle(item: TodoEntity) = viewModelScope.launch {
        repo.toggle(item.id, item.isDone, item.text)
    }

    fun delete(id: Long) = viewModelScope.launch { repo.delete(id) }

    fun clearDone() = viewModelScope.launch { repo.deleteDone() }
}
