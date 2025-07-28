package com.example.workplanning.ui.theme

import android.content.Context
import androidx.datastore.preferences.core.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import androidx.datastore.preferences.preferencesDataStore

private val Context.dataStore by preferencesDataStore(name = "user_prefs")

class UserPreferences(private val context: Context) {

  companion object {
    val USERNAME_KEY = stringPreferencesKey("username")
  }

  val username: Flow<String?> = context.dataStore.data
    .map { it[USERNAME_KEY] }

  suspend fun saveUsername(name: String) {
    context.dataStore.edit { it[USERNAME_KEY] = name }
  }

  suspend fun clearUsername() {
    context.dataStore.edit { it.remove(USERNAME_KEY) }
  }
}

