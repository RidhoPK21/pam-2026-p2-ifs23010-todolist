package org.delcom.entities


import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.util.*
import kotlin.time.Clock
import kotlin.time.Instant

@Serializable
data class Todo(
    val id: String = UUID.randomUUID().toString(),
    var title: String,
    var description: String,
    var isDone: Boolean = false,

    @Contextual
    val createdAt: Instant = Clock.System.now(),
    @Contextual
    var updatedAt: Instant = Clock.System.now(),
)