package org.delcom.data

import kotlinx.serialization.Serializable

@Serializable
data class TodoRequest(
    val title: String? = null,
    val description: String? = null,
    val isDone: Boolean = false
)