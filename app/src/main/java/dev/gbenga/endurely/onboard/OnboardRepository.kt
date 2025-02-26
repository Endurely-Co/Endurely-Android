package dev.gbenga.endurely.onboard

import dev.gbenga.endurely.onboard.data.LoginRequest
import dev.gbenga.endurely.onboard.data.OnboardService
import dev.gbenga.endurely.onboard.data.RepoState
import dev.gbenga.endurely.onboard.data.SignUpRequest
import dev.gbenga.endurely.onboard.data.repoContext
import kotlin.coroutines.CoroutineContext

class OnboardRepository(private val onboardService: OnboardService,
                        private val ioContext: CoroutineContext) {

    suspend fun logIn(email: String, password: String) = repoContext(ioContext){
        onboardService.login(LoginRequest(email, password))
    }

    suspend fun signUp(signUpRequest: SignUpRequest) = repoContext(ioContext) {
        onboardService.signUp(signUpRequest)
    }
}



