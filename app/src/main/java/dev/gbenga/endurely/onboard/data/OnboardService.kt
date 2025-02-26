package dev.gbenga.endurely.onboard.data

import retrofit2.http.Body
import retrofit2.http.POST

interface OnboardService {

    @POST(ApiEndpoint.signIn)
    suspend fun login(@Body request: LoginRequest)
}