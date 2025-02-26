package dev.gbenga.endurely.di

import dev.gbenga.endurely.onboard.data.ApiEndpoint
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    single {
        Retrofit.Builder()
            .baseUrl(ApiEndpoint.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}