package com.example.workplanning.ui.theme

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay

class UserViewModel(private val context: Context) : ViewModel() {
    private var users: MutableList<User> = UserStorage.loadUsers(context).toMutableList()
    private val prefs = UserPreferences(context)

    var currentUsername by mutableStateOf<String?>(null)
        private set

    var isInitialized by mutableStateOf(false)
        private set

    init {
        viewModelScope.launch {
            val saved = prefs.username.first()
            currentUsername = saved
            delay(1000)
            isInitialized = true
        }
    }

    fun register(username: String, password: String): Boolean {
        if (users.any { it.username == username }) return false
        val newUser = User(username, password)
        users.add(newUser)
        UserStorage.saveUsers(context, users)
        return true
    }

    fun login(username: String, password: String): Boolean {
        val userExists = users.any { it.username == username && it.password == password }
        if (userExists) {
            currentUsername = username
            viewModelScope.launch {
                prefs.saveUsername(username)
            }
        }
        return userExists
    }

    fun logout() {
        currentUsername = null
        viewModelScope.launch {
            prefs.clearUsername()
        }
    }
}
