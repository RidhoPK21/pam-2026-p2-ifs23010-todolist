package org.delcom.services

import org.delcom.entities.Todo
import org.delcom.repositories.ITodoRepository

class TodoService(private val todoRepository: ITodoRepository) : ITodoService {
    override fun getAllTodos(): List<Todo> {
        val todos = todoRepository.getAll()
        return todos
    }

    override fun getTodoById(id: String): Todo? {
        return todoRepository.getById(id)
    }

    override fun createTodo(title: String, description: String): String {
        return todoRepository.create(title, description)
    }

    override fun updateTodo(id: String, title: String, description: String, isDone: Boolean): Boolean {
        return todoRepository.update(id, title, description, isDone)
    }

    override fun removeTodo(id: String): Boolean {
        return todoRepository.delete(id)
    }
}