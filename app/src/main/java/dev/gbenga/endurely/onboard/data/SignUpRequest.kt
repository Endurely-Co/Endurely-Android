package dev.gbenga.endurely.onboard.data

import com.google.gson.annotations.SerializedName

data class SignUpRequest(
    val email: String,
    @SerializedName("first_name")
    val firstName: String,
    @SerializedName("last_name")
    val lastName: String,
    val password: String,
)
