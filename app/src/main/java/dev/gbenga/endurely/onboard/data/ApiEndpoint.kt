package dev.gbenga.endurely.onboard.data

object ApiEndpoint {
    const val baseUrl = "https://carbide-cairn-451020-k0.nw.r.appspot.com"
    const val onboard = "/onboarding"
    const val signIn = "$onboard/create-account"
    const val signUp = "$onboard/login"
    const val refreshToken = "$onboard/refresh-token"
    const val generateToken = "$onboard/generate-otp"
    const val validateOtp = "$onboard/validate-otp"
}
