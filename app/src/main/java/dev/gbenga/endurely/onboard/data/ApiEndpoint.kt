package dev.gbenga.endurely.onboard.data

object ApiEndpoint {
    const val localBaseUrl = "http://127.0.0.1:8000"
    const val BASE_URL = "https://carbide-cairn-451020-k0.nw.r.appspot.com"
    const val ROUTINES = "routines"
    const val MEAL = "meal"
    const val USER_ROUTINES = "$ROUTINES/user"
    const val EXERCISES = "$ROUTINES/exercises"
    const val ADD_ROUTINES = "$ROUTINES/add"
    const val MEAL_PLAN = "$MEAL/plan"
    const val MEAL_NUTRIENT= "$MEAL/nutrient"
    const val ONBOARD = "/onboarding"
    const val signIn = "$ONBOARD/login"
    const val signUp = "$ONBOARD/create-account"
}