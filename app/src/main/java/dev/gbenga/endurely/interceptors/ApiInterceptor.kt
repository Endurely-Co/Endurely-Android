package dev.gbenga.endurely.interceptors

import android.util.Log
import com.google.gson.Gson
import dev.gbenga.endurely.onboard.data.ApiError
import dev.gbenga.endurely.onboard.data.ApiResponse
import dev.gbenga.endurely.onboard.data.ApiSuccess
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody

class ApiInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        val response = chain.proceed(request)
        val body = response.peekBody(Long.MAX_VALUE).string()

        val gson = Gson()
        val newResponse = gson.fromJson(body, Map::class.java)

        newResponse["message"]?.let {
            val apiError = ApiError(message = newResponse["message"] as String, code = response.code )
            return response.newBuilder().message(apiError.message).build()
        }

        return response.newBuilder().build()
    }
}