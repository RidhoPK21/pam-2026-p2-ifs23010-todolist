package org.delcom.entities

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer
import java.util.*
import kotlin.time.Instant
import kotlin.time.Clock

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