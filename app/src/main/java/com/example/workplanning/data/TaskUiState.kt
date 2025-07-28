package com.example.workplanning.data

data class TaskUiState(
    val title: String = "",
    val date: String = "",
    val description: String = "",
    val isDone: Boolean = false,
    val error: String? = null
)


