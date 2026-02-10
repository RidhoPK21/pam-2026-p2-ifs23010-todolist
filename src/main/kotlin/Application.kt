package org.delcom

import io.ktor.server.application.*
import org.delcom.data.todoModule
import org.koin.ktor.plugin.Koin
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.plugins.cors.routing.CORS
import kotlinx.serialization.json.Json
import io.github.cdimascio.dotenv.dotenv
import io.ktor.server.netty.EngineMain
import io.ktor.http.* // Tambahkan import ini

fun main(args: Array<String>) {
    val dotenv = dotenv {
        directory = "."
        ignoreIfMissing = false
    }

    dotenv.entries().forEach {
        System.setProperty(it.key, it.value)
    }

    EngineMain.main(args)
}

fun Application.module() {

    install(CORS) {
        // Mengizinkan koneksi dari host mana pun
        anyHost()

        // Izinkan Header Content-Type yang sering digunakan oleh Axios/Fetch
        allowHeader(HttpHeaders.ContentType)
        allowHeader(HttpHeaders.Authorization)

        // Izinkan Metode HTTP yang diperlukan untuk operasi CRUD
        allowMethod(HttpMethod.Options)
        allowMethod(HttpMethod.Put)
        allowMethod(HttpMethod.Patch)
        allowMethod(HttpMethod.Delete)

        // Izinkan kredensial jika diperlukan di masa depan
        allowCredentials = true

        // Ekspos header tertentu jika diperlukan oleh client
        exposeHeader(HttpHeaders.AccessControlAllowOrigin)
    }

    install(ContentNegotiation) {
        json(
            Json {
                prettyPrint = true
                ignoreUnknownKeys = true
            }
        )
    }

    install(Koin) {
        modules(todoModule)
    }

    configureRouting()
}