package com.nassrallah.vetfarmvet.feature_auth.data.data_source.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val TOKEN_KEY = stringPreferencesKey("token")
private val ID_KEY = intPreferencesKey("id")
private val ORDER_ID_KEY = intPreferencesKey("orderId")

class AppDataStore(
    private val dataStore: DataStore<Preferences>
) {

    val token: Flow<String> = dataStore.data.map { preferences ->
        preferences[TOKEN_KEY].orEmpty()
    }

    val id: Flow<Int> = dataStore.data.map { preferences ->
        preferences[ID_KEY] ?: 0
    }

    val orderId: Flow<Int> = dataStore.data.map { preferences ->
        preferences[ORDER_ID_KEY] ?: 0
    }

    suspend fun saveToken(token: String) {
        dataStore.edit { preferences ->
            preferences[TOKEN_KEY] = token
        }
    }

    suspend fun saveId(id: Int) {
        dataStore.edit { preferences ->
            preferences[ID_KEY] = id
        }
    }

    suspend fun saveOrderId(value: Int) {
        dataStore.edit { preferences ->
            preferences[ORDER_ID_KEY] = value
        }
    }

    suspend fun clearData() {
        dataStore.edit { preferences ->
            preferences[ID_KEY] = 0
            preferences[TOKEN_KEY] = ""
        }
    }


}