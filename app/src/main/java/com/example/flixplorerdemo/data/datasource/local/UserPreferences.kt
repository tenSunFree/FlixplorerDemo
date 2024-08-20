package com.example.flixplorerdemo.data.datasource.local

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/** A calm reminder. This class is injected. Do not instantiate it directly.*/
class UserPreferences(private val context: Context) {
    companion object {
        val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "preferences")
        var abcValue: String = "123"
        var Context.abc: String get() {
            // Log.d("UserPreferences", "UserPreferences, get, abc: ${abc}")
            return abcValue
        }
            set(value) {
                Log.d("UserPreferences", "UserPreferences, set, abc: ${abc}")
                abcValue = value
            }
        val USE_MATERIAL3 = booleanPreferencesKey("use_material3")
        val USE_DARK_MODE = stringPreferencesKey("use_dark_mode")
    }

    val useMaterial3: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[USE_MATERIAL3] ?: true
    }
    val useDarkMode: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[USE_DARK_MODE] ?: AppTheme.SYSTEM_DEFAULT.string
    }

    suspend fun updateUseMaterial3(useM3: Boolean) {
        Log.d("UserPreferences", "UserPreferences, updateUseMaterial3, abc: ${context.abc}")
        context.dataStore.edit { preferences ->
            preferences[USE_MATERIAL3] = useM3
        }
    }

    suspend fun updateUseDarkMode(useDarkMode: String) {
        Log.d("UserPreferences", "UserPreferences, updateUseDarkMode, abc: ${context.abc}")
        context.dataStore.edit { preferences ->
            preferences[USE_DARK_MODE] = useDarkMode
        }
    }
}
