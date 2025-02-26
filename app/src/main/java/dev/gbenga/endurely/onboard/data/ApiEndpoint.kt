package dev.gbenga.endurely.onboard.data

object ApiEndpoint {
    const val baseUrl = "http://127.0.0.1:8000"
    const val onboard = "/onboarding/"
    const val signIn = "$onboard/create-account"
    const val signUp = "$onboard/login"
    const val refreshToken = "$onboard/refresh-token"
    const val generateToken = "$onboard/generate-otp"
    const val validateOtp = "$onboard/validate-otp"
}