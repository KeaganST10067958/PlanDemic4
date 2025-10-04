package com.keagan.plandemic.data

import android.content.Context
import com.keagan.plandemic.data.local.AppDatabase
import com.keagan.plandemic.data.local.Todo
import kotlinx.coroutines.flow.Flow

class TodoRepository(context: Context) {
    private val dao = AppDatabase.get(context).todoDao()

    fun observeAll(): Flow<List<Todo>> = dao.observeAll()
    suspend fun add(title: String) = dao.insert(Todo(title = title))
    suspend fun toggle(id: Long, done: Boolean) = dao.setDone(id, done)
    suspend fun delete(todo: Todo) = dao.delete(todo)
}
