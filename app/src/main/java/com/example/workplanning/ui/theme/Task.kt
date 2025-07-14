package com.example.workplanning

data class Task(
    val id: String,
    val title: String,
    val date: String,
    val description: String = "",
    val isDone: Boolean = false
)


data class User(
    val username: String,
    val password: String
)
