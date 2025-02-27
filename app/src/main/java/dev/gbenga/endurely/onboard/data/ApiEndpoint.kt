package dev.gbenga.endurely.onboard.data

object ApiEndpoint {
    const val baseUrl = "https://carbide-cairn-451020-k0.nw.r.appspot.com"
    const val onboard = "/onboarding"
    const val signIn = "$onboard/login"
    const val signUp = "$onboard/create-account"
    const val refreshToken = "$onboard/refresh-token"
    const val generateToken = "$onboard/generate-otp"
    const val validateOtp = "$onboard/validate-otp"
}
