package org.delcom.repositories

import org.delcom.entities.Todo
import kotlin.time.Clock

class TodoRepository : ITodoRepository {
    private val data = mutableListOf<Todo>()

    override fun getAll(): List<Todo> {
        return data
    }

    override fun getById(id: String): Todo? {
        return data
            .find { it.id == id }
    }

    override fun create(title: String, description: String) : String {
        val newTodo = Todo(title = title, description = description)
        data.add(newTodo)
        return newTodo.id
    }

    override fun delete(id: String): Boolean {
        val targetTodo = getById(id) ?: return false
        data.remove(targetTodo)
        return true
    }

    override fun update(
        id: String,
        title: String,
        description: String,
        isDone: Boolean
    ): Boolean {
        val targetTodo = getById(id) ?: return false
        targetTodo.title = title
        targetTodo.description = description
        targetTodo.isDone = isDone
        targetTodo.updatedAt = Clock.System.now()
        return true
    }
}