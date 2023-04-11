package com.example.crosses_zeros.functionality.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AppDataStore @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    private object DataStoreKeys {
        const val NAME_KEY = "name_key"
        val nameKey = stringPreferencesKey(NAME_KEY)
    }

    suspend fun saveName(name: String) = dataStore.edit { it[DataStoreKeys.nameKey] = name }
    val readName: Flow<String> = dataStore.data.map { it[DataStoreKeys.nameKey] ?: "https://dok.ua" }
}