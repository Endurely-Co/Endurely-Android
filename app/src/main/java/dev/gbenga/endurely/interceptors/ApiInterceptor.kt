package dev.gbenga.endurely.interceptors

import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import dev.gbenga.endurely.onboard.data.ApiError
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class ApiInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        val response = chain.proceed(request)
        val body = response.peekBody(Long.MAX_VALUE).string()

        val gson = Gson()
        Log.d("body_body", body)
        try {
            val newResponse = gson.fromJson(body, Map::class.java)

            newResponse["message"]?.let {
                if (newResponse["message"] is Map<*, *>){
                    val errors = newResponse["message"] as Map<String, Any>
                    Log.d("newResponse", "${newResponse["message"] }")
                    return response.newBuilder().message("${errors.values}").build()

                }else{
                    val apiError =
                        ApiError(message = newResponse["message"] as String, code = response.code)
                    Log.d("newResponse", "${newResponse["message"] }")
                    return response.newBuilder().message(apiError.message).build()
                }
            }

            return response.newBuilder().build()
        }catch (je: JsonSyntaxException){

          //  ApiError(message = "Could not reach url. Possibly wrong endpoint", code = response.code)
            return response.newBuilder().message("Could not reach url. Possibly wrong endpoint").build()
        }
    }
}