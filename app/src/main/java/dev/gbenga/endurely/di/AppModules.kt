package dev.gbenga.endurely.di

import dev.gbenga.endurely.interceptors.ApiInterceptor
import dev.gbenga.endurely.onboard.data.ApiEndpoint
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


val appModule = module {
    single {
        val client: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(ApiInterceptor())
            .build()
        Retrofit.Builder()
            .baseUrl(ApiEndpoint.baseUrl)
            .client(client)

            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}