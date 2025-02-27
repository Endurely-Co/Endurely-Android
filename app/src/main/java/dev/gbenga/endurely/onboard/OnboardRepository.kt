package dev.gbenga.endurely.onboard

import android.util.Log
import com.google.gson.Gson
import dev.gbenga.endurely.core.Tokens
import dev.gbenga.endurely.core.data.UserDataStore
import dev.gbenga.endurely.onboard.data.LoginRequest
import dev.gbenga.endurely.onboard.data.OnboardService
import dev.gbenga.endurely.onboard.data.RepoState
import dev.gbenga.endurely.onboard.data.SignUpRequest
import dev.gbenga.endurely.onboard.data.repoContext
import kotlinx.coroutines.flow.first
import kotlin.coroutines.CoroutineContext

class OnboardRepository(private val onboardService: OnboardService,
                        private val userDataStore: UserDataStore,
                        private val ioContext: CoroutineContext) {

    suspend fun checkLogin() = repoContext(ioContext){
        userDataStore.login.first()
    }

    suspend fun logIn(username: String, password: String) = repoContext(ioContext){
        onboardService.login(LoginRequest(username, password)).also {
            userDataStore.setLogin(it)
        }.let { Tokens.loginSuccessful }

    }

    suspend fun signUp(signUpRequest: SignUpRequest) = repoContext(ioContext) {
        Log.d("OnboardRepository", "---> ${Gson().toJson(signUpRequest)}")
        onboardService.signUp(signUpRequest)
    }
}



