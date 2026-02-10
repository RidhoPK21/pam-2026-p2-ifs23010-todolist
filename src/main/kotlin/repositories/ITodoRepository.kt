package org.delcom.repositories

import org.delcom.entities.Todo

interface ITodoRepository {
    fun getAll(): List<Todo>
    fun getById(id: String): Todo?
    fun create(title: String, description: String) : String
    fun delete(id: String): Boolean
    fun update(id: String, title: String, description: String, isDone: Boolean): Boolean
}