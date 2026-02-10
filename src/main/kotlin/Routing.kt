package org.delcom

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.routing.*
import io.ktor.server.response.*
import org.delcom.controllers.TodoController
import org.delcom.data.ErrorResponse
import org.delcom.data.AppException
import org.delcom.helpers.parseMessageToMap
import org.koin.ktor.ext.inject

fun Application.configureRouting() {
    val todoController: TodoController by inject()

    install(StatusPages) {
        // Menangkap AppException untuk error yang disengaja (400, 404, dll)
        exception<AppException> { call, cause ->
            val dataMap: Map<String, List<String>> = parseMessageToMap(cause.message)

            call.respond(
                status = HttpStatusCode.fromValue(cause.code),
                message = ErrorResponse(
                    status = "fail",
                    // Mengembalikan "Data yang dikirimkan tidak valid!" jika ada detail field error
                    message = if (dataMap.isNotEmpty()) "Data yang dikirimkan tidak valid!" else cause.message,
                    data = dataMap
                )
            )
        }

        // Menangkap semua error sistem lainnya (500)
        exception<Throwable> { call, cause ->
            call.respond(
                status = HttpStatusCode.InternalServerError,
                message = ErrorResponse(
                    status = "error",
                    message = cause.message ?: "Unknown error",
                )
            )
        }
    }

    routing {
        get("/") {
            call.respondText("11S23010 - Ridho Alexander Pakpahan")
        }

        route("/todos") {
            get {
                todoController.getAllTodos(call)
            }
            post {
                todoController.createTodo(call)
            }
            get("/{id}") {
                todoController.getTodoById(call)
            }
            put("/{id}") {
                todoController.updateTodo(call)
            }
            delete("/{id}") {
                todoController.deleteTodo(call)
            }
        }
    }
}