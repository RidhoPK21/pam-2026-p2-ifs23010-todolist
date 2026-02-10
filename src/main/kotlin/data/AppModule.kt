package org.delcom.data

import org.delcom.controllers.TodoController
import org.delcom.repositories.ITodoRepository
import org.delcom.repositories.TodoRepository
import org.delcom.services.ITodoService
import org.delcom.services.TodoService
import org.koin.dsl.module

val todoModule = module {
    // Repository
    single<ITodoRepository> {
        TodoRepository()
    }

    // Service
    single<ITodoService> {
        TodoService(get())
    }

    // Controller
    single {
        TodoController(get())
    }
}