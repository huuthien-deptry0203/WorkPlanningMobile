package com.example.workplanning.ui.theme

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.workplanning.data.Task
import com.example.workplanning.data.TaskUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.UUID

class TaskViewModel(private val context: Context) : ViewModel() {

    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val todayTasks = _tasks.asStateFlow()

    private val _uiState = MutableStateFlow(TaskUiState())
    val uiState = _uiState.asStateFlow()

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

    // --- UI State Handlers ---
    fun onTitleChange(newTitle: String) {
        _uiState.value = _uiState.value.copy(title = newTitle)
    }

    fun onDateChange(newDate: String) {
        _uiState.value = _uiState.value.copy(date = newDate)
    }

    fun onDescriptionChange(newDesc: String) {
        _uiState.value = _uiState.value.copy(description = newDesc)
    }

    fun onDoneChange(done: Boolean) {
        _uiState.value = _uiState.value.copy(isDone = done)
    }

    fun setError(message: String) {
        _uiState.value = _uiState.value.copy(error = message)
    }

    fun clearUiState() {
        _uiState.value = TaskUiState()
    }

    // --- Add Task từ UiState ---
    fun addTask(username: String) {
        val task = Task(
            id = UUID.randomUUID().toString(),
            title = _uiState.value.title,
            date = _uiState.value.date,
            description = _uiState.value.description,
            username = username
        )
        _tasks.value = _tasks.value + task
        saveTasks()
        clearUiState() // reset input sau khi thêm
    }

    fun updateTask(id: String, title: String, date: String, description: String, isDone: Boolean) {
        _tasks.value = _tasks.value.map {
            if (it.id == id) it.copy(title = title, date = date, description = description, isDone = isDone)
            else it
        }
        saveTasks()
    }

    fun deleteTask(id: String) {
        _tasks.value = _tasks.value.filter { it.id != id }
        saveTasks()
    }
}

