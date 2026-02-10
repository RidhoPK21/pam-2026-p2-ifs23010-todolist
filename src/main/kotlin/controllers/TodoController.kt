package org.delcom.controllers

import io.ktor.server.application.ApplicationCall
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import org.delcom.data.TodoRequest
import org.delcom.data.AppException
import org.delcom.services.ITodoService
import org.delcom.data.DataResponse
import org.delcom.helpers.ValidatorHelper

class TodoController(private val todoService: ITodoService) {
    suspend fun getAllTodos(call: ApplicationCall) {
        val todos = todoService.getAllTodos()

        val response = DataResponse(
            "success",
            "Berhasil mengambil daftar todo",
            mapOf(Pair("todos", todos))
        )
        call.respond(response)
    }

    suspend fun getTodoById(call: ApplicationCall) {
        val id = call.parameters["id"]
            ?: throw AppException(400, "ID todo tidak boleh kosong!")

        val todo = todoService.getTodoById(id) ?: throw AppException(404, "Data todo tidak tersedia!")

        val response = DataResponse(
            "success",
            "Berhasil mengambil data todo",
            mapOf(Pair("todo", todo))
        )
        call.respond(response)
    }

    suspend fun createTodo(call: ApplicationCall) {
        val request = call.receive<TodoRequest>()

        val requestData = mapOf(
            "title" to request.title,
            "description" to request.description
        )
        val validatorHelper = ValidatorHelper(requestData)
        validatorHelper.required("title", "Judul tidak boleh kosong")
        validatorHelper.required("description", "Deskripsi tidak boleh kosong")
        validatorHelper.validate()

        val todoId = todoService.createTodo(request.title!!, request.description!!)

        val response = DataResponse(
            "success",
            "Berhasil menambahkan data todo",
            mapOf(Pair("todoId", todoId))
        )
        call.respond(response)
    }

    suspend fun updateTodo(call: ApplicationCall) {
        val id = call.parameters["id"]
            ?: throw AppException(400, "ID todo tidak boleh kosong!")
        val request = call.receive<TodoRequest>()

        val requestData = mapOf(
            "title" to request.title,
            "description" to request.description
        )
        val validatorHelper = ValidatorHelper(requestData)
        validatorHelper.required("title", "Judul tidak boleh kosong")
        validatorHelper.required("description", "Deskripsi tidak boleh kosong")
        validatorHelper.validate()

        val isUpdated = todoService.updateTodo(id, request.title!!, request.description!!, request.isDone)
        if (!isUpdated) {
            throw AppException(404, "Data todo tidak tersedia!")
        }

        val response = DataResponse(
            "success",
            "Berhasil mengubah data todo",
            null
        )
        call.respond(response)
    }

    suspend fun deleteTodo(call: ApplicationCall) {
        val id = call.parameters["id"]
            ?: throw AppException(400, "ID todo tidak boleh kosong!")

        val isDeleted = todoService.removeTodo(id)
        if (!isDeleted) {
            throw AppException(404, "Data todo tidak tersedia!")
        }

        val response = DataResponse(
            "success",
            "Berhasil menghapus data todo",
            null
        )
        call.respond(response)
    }
}