package com.example.workplanning.ui.theme


import android.content.Context
import com.example.workplanning.data.User
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File

object UserStorage {
    private const val FILE_NAME = "users.json"
    private val gson = Gson()

    fun saveUsers(context: Context, users: List<User>) {
        val json = gson.toJson(users)
        File(context.filesDir, FILE_NAME).writeText(json)
    }

    fun loadUsers(context: Context): List<User> {
        val file = File(context.filesDir, FILE_NAME)
        if (!file.exists()) return emptyList()
        val json = file.readText()
        val type = object : TypeToken<List<User>>() {}.type
        return gson.fromJson(json, type)
    }
}
