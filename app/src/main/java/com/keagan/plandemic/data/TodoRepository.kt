package com.keagan.plandemic.data

import android.content.Context
import com.keagan.plandemic.data.local.AppDatabase
import com.keagan.plandemic.data.local.Todo
import kotlinx.coroutines.flow.Flow

class TodoRepository(context: Context) {
    private val dao = AppDatabase.get(context).todoDao()

    val todos: Flow<List<Todo>> = dao.observeAll()

    suspend fun add(title: String) {
        if (title.isNotBlank()) dao.insert(Todo(title = title.trim()))
    }

    suspend fun toggle(todo: Todo) {
        dao.update(todo.copy(done = !todo.done))
    }

    suspend fun remove(todo: Todo) {
        dao.delete(todo)
    }
}
