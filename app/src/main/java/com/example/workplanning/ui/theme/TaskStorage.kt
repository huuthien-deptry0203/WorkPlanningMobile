package com.example.workplanning.ui.theme

import android.content.Context
import com.example.workplanning.data.Task
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File

object TaskStorage {
    private const val FILE_NAME = "tasks.json"
    private val gson = Gson()

    fun saveTasks(context: Context, tasks: List<Task>) {
        val json = gson.toJson(tasks)
        File(context.filesDir, FILE_NAME).writeText(json)
    }

    fun loadTasks(context: Context): List<Task> {
        val file = File(context.filesDir, FILE_NAME)
        if (!file.exists()) return emptyList()
        val json = file.readText()
        val type = object : TypeToken<List<Task>>() {}.type
        return gson.fromJson(json, type)
    }
}
