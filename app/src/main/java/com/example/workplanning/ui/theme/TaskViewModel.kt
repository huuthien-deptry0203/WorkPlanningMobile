package com.example.workplanning.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workplanning.Task
import com.example.workplanning.ui.theme.TaskStorage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID

class TaskViewModel(private val context: Context) : ViewModel() {

    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val todayTasks = _tasks.asStateFlow()

    init {
        // Load dữ liệu từ file khi ViewModel khởi tạo
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

    fun addTask(title: String, date: String, description: String) {
        val newTask = Task(
            id = UUID.randomUUID().toString(),
            title = title,
            date = date,
            description = description
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
