package dev.gbenga.endurely.dashboard

import dev.gbenga.endurely.core.Repository
import dev.gbenga.endurely.core.data.SettingsDatastore
import dev.gbenga.endurely.core.data.UserDataStore
import kotlin.coroutines.CoroutineContext

class SettingsRepository (private val settings: SettingsDatastore,
                          private val userDataStore: UserDataStore, ioContext: CoroutineContext) : Repository(ioContext){

    suspend fun changeTheme(isDark: Boolean) = repoContext{
        settings.changeTheme(isDark)
    }

    fun theme() = settings.theme


    suspend fun logout() = repoContext {
        // Sign out api
        userDataStore.clearLogin()
    }
}