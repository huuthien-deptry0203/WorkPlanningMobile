package com.example.workplanning.viewmodel

import androidx.lifecycle.ViewModel
import com.example.workplanning.Task
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.UUID

class TaskViewModel : ViewModel() {
    private val _tasks = MutableStateFlow(
        listOf(
            Task("1", "Thiết kế giao diện", "2025-07-05 19:00", "Thiết kế giao diện ứng dụng với Compose"),
            Task("2", "Họp nhóm tuần", "2025-07-06 22:00", "Họp cập nhật tiến độ và phân chia công việc"),
            Task("3", "Triển khai backend", "2025-07-07 23:00", "Cài đặt API, kết nối Firebase")
        )
    )
    val todayTasks = _tasks.asStateFlow()

    fun getTaskById(taskId: String): Task? = _tasks.value.find { it.id == taskId }

    fun addTask(title: String, date: String, description: String) {
        val newTask = Task(UUID.randomUUID().toString(), title, date, description)
        _tasks.value = _tasks.value + newTask
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
