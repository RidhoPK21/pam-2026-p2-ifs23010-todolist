package org.delcom.services

import org.delcom.entities.Todo

interface ITodoService {
    fun getAllTodos(): List<Todo>
    fun getTodoById(id: String): Todo?
    fun createTodo(title: String, description: String): String
    fun updateTodo(id: String, title: String, description: String, isDone: Boolean): Boolean
    fun removeTodo(id: String): Boolean
}