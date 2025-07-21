package com.example.workplanning.ui.theme

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.workplanning.viewmodel.TaskViewModel

class TaskViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TaskViewModel::class.java)) {
            return TaskViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}