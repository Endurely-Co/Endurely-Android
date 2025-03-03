package dev.gbenga.endurely.onboard.data

object ApiEndpoint {
    const val blocalBaseUrl = "http://127.0.0.1:8000"
    const val BASE_URL = "https://carbide-cairn-451020-k0.nw.r.appspot.com"
    const val ROUTINES = "routines"
    const val USER_ROUTINES = "$ROUTINES/user"
    const val EXERCISES = "$ROUTINES/exercises"
    const val EXERCISES_CATEGORIES = "$ROUTINES/categories"
    const val ROUTINES_ADD = "$ROUTINES/add"
    const val ROUTINES_UPDATE = "$ROUTINES/update"
    const val ROUTINES_DELETE = "$ROUTINES/delete"
    const val ROUTINES_NUTRIENTS = "$ROUTINES/food/nutrients"
    const val EXERCISES_CATEGORY_BY_ID = "$ROUTINES/exercises/category"
    const val ONBOARD = "/onboarding"
    const val signIn = "$ONBOARD/login"
    const val signUp = "$ONBOARD/create-account"
    const val refreshToken = "$ONBOARD/refresh-token"
    const val generateToken = "$ONBOARD/generate-otp"
    const val validateOtp = "$ONBOARD/validate-otp"
}
//http://127.0.0.1:8000/onboarding/login