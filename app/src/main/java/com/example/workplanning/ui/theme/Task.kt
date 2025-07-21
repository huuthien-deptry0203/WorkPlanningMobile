package com.example.workplanning

import kotlinx.serialization.Serializable

@Serializable
data class Task(
    val id: String,
    val title: String,
    val date: String,
    val description: String = "",
    val isDone: Boolean = false
)

@Serializable
data class User(
    val username: String,
    val password: String
)
