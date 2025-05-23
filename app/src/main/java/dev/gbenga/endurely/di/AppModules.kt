package dev.gbenga.endurely.di

import dev.gbenga.endurely.interceptors.ApiInterceptor
import dev.gbenga.endurely.onboard.data.ApiEndpoint
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import dev.gbenga.endurely.MainActivityViewModel
import dev.gbenga.endurely.core.DateTimeUtils
import dev.gbenga.endurely.core.DateUtils
import dev.gbenga.endurely.core.DateUtilsNames
import dev.gbenga.endurely.core.data.UserDataStore

// At the top level of your kotlin file:
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")


val appModule = module {
    single {
        val client: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(ApiInterceptor())
            .build()
        Retrofit.Builder()
            .baseUrl(ApiEndpoint.BASE_URL)
            .client(client)

            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single { Gson() }
    single<DataStore<Preferences>> { get<Context>().dataStore }

    single {
        UserDataStore(get(), get())
    }

    single { MainActivityViewModel(get(), get()) }
    single { DateTimeUtils() }
    single { DateUtils() }
    single { DateUtilsNames(get()) }
}