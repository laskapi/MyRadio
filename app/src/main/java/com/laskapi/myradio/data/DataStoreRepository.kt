package com.laskapi.myradio.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject


private const val SETTINGS = "settings"

class DataStoreRepository @Inject constructor(@ApplicationContext private val context: Context) {

    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = SETTINGS)

    suspend fun putInt(key: String, value: Int) {
        val preferencesKey = intPreferencesKey(key)
        context.dataStore.edit { preferences ->
            preferences[preferencesKey] = value
        }
    }

    suspend fun getInt(key: String): Flow<Int?> =
        withContext(Dispatchers.IO) {
            context.dataStore.data.catch {
                emit(emptyPreferences())
            }.map { preferences ->
                val preferencesKey = intPreferencesKey(key)
                preferences[preferencesKey]
            }
        }
}

