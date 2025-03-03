package dev.gbenga.endurely.core.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingsDatastore(private val dataStore: DataStore<Preferences>, ) {


    private val themeKey = booleanPreferencesKey("theme_key")

    suspend fun changeTheme(isDark: Boolean){
        dataStore.edit { prefs ->
            prefs[themeKey] = isDark
        }
    }

    val theme : Flow<Boolean?> = dataStore.data.map { prefs ->
        prefs[themeKey]
    }


}