package dev.gbenga.endurely.core.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.google.gson.Gson
import dev.gbenga.endurely.onboard.data.LoginResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// At the top level of your kotlin file:

class UserDataStore(private val dataStore: DataStore<Preferences>, private val gson: Gson) {

    private val userLoginKey = stringPreferencesKey("login_data")

    suspend fun setLogin(loginResponse: LoginResponse){
        dataStore.edit { prefs ->
            prefs[userLoginKey] = gson.toJson(loginResponse)
        }
    }

    val login : Flow<LoginResponse> = dataStore.data.map { prefs ->
        val data = prefs[userLoginKey]
        data?.let { gson.fromJson(data, LoginResponse::class.java) } ?: throw Exception("User is yet to log in")
    }
}