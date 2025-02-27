package dev.gbenga.endurely.dashboard

import android.util.Log
import dev.gbenga.endurely.core.data.UserDataStore
import dev.gbenga.endurely.onboard.data.repoContext
import kotlinx.coroutines.flow.first
import kotlin.coroutines.CoroutineContext

class DashboardRepository(private val userDataStore: UserDataStore,
                          private val ioContext: CoroutineContext) {

    suspend fun getUser() = userDataStore.login
}