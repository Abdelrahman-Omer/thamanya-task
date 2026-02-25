package com.thamanya.data.dataStore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import kotlinx.coroutines.flow.first

class AppDataStore(private val dataStore: DataStore<Preferences>) : PreferencesDataStore {

    override suspend fun getString(key: String, defaultValue: String): String {
        val dataStoreKey = stringPreferencesKey(key)
        return dataStore.data.first()[dataStoreKey] ?: defaultValue
    }

    override suspend fun putString(key: String, value: String) {
        val dataStoreKey = stringPreferencesKey(key)
        dataStore.edit { preferences ->
            preferences[dataStoreKey] = value
        }
    }

    override suspend fun getInt(key: String, defaultValue: Int): Int {
        val dataStoreKey = intPreferencesKey(key)
        return dataStore.data.first()[dataStoreKey] ?: defaultValue
    }

    override suspend fun putInt(key: String, value: Int) {
        val dataStoreKey = intPreferencesKey(key)
        dataStore.edit { preferences ->
            preferences[dataStoreKey] = value
        }
    }

    override suspend fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        val dataStoreKey = booleanPreferencesKey(key)
        return dataStore.data.first()[dataStoreKey] ?: defaultValue
    }

    override suspend fun putBoolean(key: String, value: Boolean) {
        val dataStoreKey = booleanPreferencesKey(key)
        dataStore.edit { preferences ->
            preferences[dataStoreKey] = value
        }
    }


    companion object {
        const val ACCESS_TOKEN = "access_token"
        const val REFRESH_TOKEN = "refresh_token"
        const val CUSTOMER_ID = "customer_id"
        const val CUSTOMER_PHONE_NUMBER = "customer_phone_number"
    }
}