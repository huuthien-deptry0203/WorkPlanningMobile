package com.example.workplanning.ui.theme

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.workplanning.User

class UserViewModel(private val context: Context) : ViewModel() {
    private var users: MutableList<User> = UserStorage.loadUsers(context).toMutableList()

    fun register(username: String, password: String): Boolean {
        if (users.any { it.username == username }) return false
        val newUser = User(username, password)
        users.add(newUser)
        UserStorage.saveUsers(context, users)
        return true
    }

    fun login(username: String, password: String): Boolean {
        return users.any { it.username == username && it.password == password }
    }
}
