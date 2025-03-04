package dev.gbenga.endurely.dashboard

import dev.gbenga.endurely.core.Repository
import dev.gbenga.endurely.core.data.UserDataStore
import dev.gbenga.endurely.onboard.data.repoContext
import kotlinx.coroutines.flow.first
import kotlin.coroutines.CoroutineContext

class DashboardRepository(private val userDataStore: UserDataStore,
                          private val ioContext: CoroutineContext) : Repository(ioContext){

    suspend fun getUser() = repoContext{
        userDataStore.login.first()
    }

}