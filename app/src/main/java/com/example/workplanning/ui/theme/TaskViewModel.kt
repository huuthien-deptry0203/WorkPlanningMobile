package com.example.workplanning.ui.theme

import android.content.Context
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.UUID

class TaskViewModel(private val context: Context) : ViewModel() {

    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val todayTasks = _tasks.asStateFlow()

    init {
        loadTasks()
    }

    private fun loadTasks() {
        val loaded = TaskStorage.loadTasks(context)
        _tasks.value = loaded
    }

    private fun saveTasks() {
        TaskStorage.saveTasks(context, _tasks.value)
    }

    fun getTaskById(taskId: String): Task? = _tasks.value.find { it.id == taskId }

    fun addTask(title: String, date: String, description: String, username: String) {
        val newTask = Task(
            id = UUID.randomUUID().toString(),
            title = title,
            date = date,
            description = description,
            username = username
        )
        _tasks.value = _tasks.value + newTask
        saveTasks()
    }

    fun updateTask(id: String, title: String, date: String, description: String, isDone: Boolean) {
        _tasks.value = _tasks.value.map {
            if (it.id == id) it.copy(title = title, date = date, description = description, isDone = isDone) else it
        }
        saveTasks()
    }

    fun deleteTask(id: String) {
        _tasks.value = _tasks.value.filter { it.id != id }
        saveTasks()
    }
}
