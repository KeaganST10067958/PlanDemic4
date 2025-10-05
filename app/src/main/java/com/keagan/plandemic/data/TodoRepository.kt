package com.keagan.plandemic.data

import com.keagan.plandemic.data.local.TodoDao
import com.keagan.plandemic.data.local.TodoEntity
import kotlinx.coroutines.flow.Flow

class TodoRepository(private val dao: TodoDao) {
    val items: Flow<List<TodoEntity>> = dao.observeAll()

    suspend fun add(text: String) {
        if (text.isBlank()) return
        dao.insert(TodoEntity(text = text.trim()))
    }

    suspend fun toggle(id: Long, current: Boolean, text: String) {
        dao.update(TodoEntity(id = id, isDone = !current, text = text))
    }

    suspend fun delete(id: Long) = dao.deleteById(id)

    suspend fun deleteDone() = dao.deleteAllDone()
}
