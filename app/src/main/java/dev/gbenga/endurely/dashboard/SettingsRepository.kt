package dev.gbenga.endurely.dashboard

import dev.gbenga.endurely.core.Repository
import dev.gbenga.endurely.core.data.SettingsDatastore
import kotlin.coroutines.CoroutineContext

class SettingsRepository (private val settings: SettingsDatastore, ioContext: CoroutineContext) : Repository(ioContext){

    suspend fun changeTheme(isDark: Boolean) = repoContext{
        settings.changeTheme(isDark)
    }

    fun theme() = settings.theme
}