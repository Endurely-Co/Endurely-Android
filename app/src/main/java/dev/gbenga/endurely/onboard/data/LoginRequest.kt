package dev.gbenga.endurely.onboard.data

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    val username: String,
    val password: String
    )


data class LoginResponse(
    val data: Data,
)

data class Data(
    @SerializedName("user_id")
    val userId: Int,
    val username: String,
    @SerializedName("first_name")
    val firstName: String,
    @SerializedName("last_name")
    val lastName: String,
    val email: String,
    val token: String,
    @SerializedName("refresh_token")
    val refreshToken: String,
)