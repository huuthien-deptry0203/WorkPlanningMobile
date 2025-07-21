package com.example.workplanning.viewmodel

import androidx.lifecycle.ViewModel
import com.example.workplanning.Task
import com.example.workplanning.data.ListTask
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.UUID

class TaskViewModel : ViewModel() {
    private val _tasks = MutableStateFlow(ListTask.listTask.toList())

    val todayTasks = _tasks.asStateFlow()

    fun getTaskById(taskId: String): Task? = _tasks.value.find { it.id == taskId }

//    fun addTask(title: String, date: String, description: String) {
//        val newTask = Task(UUID.randomUUID().toString(), title, date, description)
//        _tasks.value = _tasks.value + newTask
//    }

    fun addTask(title: String, date: String, description: String) {
        val newTask = Task(
            id = UUID.randomUUID().toString(),
            title = title,
            date = date,
            description = description
        )

        // Cập nhật _tasks (UI State)
        _tasks.value = _tasks.value + newTask

        // Cập nhật ListTask (danh sách gốc)
        ListTask.listTask.add(newTask)
    }

    fun updateTask(id: String, title: String, date: String, description: String, isDone: Boolean) {
        _tasks.value = _tasks.value.map {
            if (it.id == id) it.copy(title = title, date = date, description = description, isDone = isDone) else it
        }
    }

    fun deleteTask(id: String) {
        _tasks.value = _tasks.value.filter { it.id != id }
    }
}
