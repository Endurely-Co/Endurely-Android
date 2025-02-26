package dev.gbenga.endurely.onboard.data

data class ApiResponse <T>(val error: ApiError? =null, val apiSuccess: ApiSuccess<T>? =null)

data class ApiError(
    val code: Int,
    val message: String,
)

data class ApiSuccess<T>(val data: T)