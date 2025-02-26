package dev.gbenga.endurely.onboard

import androidx.lifecycle.SavedStateHandle
import dev.gbenga.endurely.onboard.data.OnboardService
import dev.gbenga.endurely.onboard.login.LoginViewModel
import dev.gbenga.endurely.onboard.welcome.WelcomeViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val onboardModule = module {
   single { get<Retrofit>().create(OnboardService::class.java) }
    single { OnboardRepository() }
    viewModel { (handle : SavedStateHandle) ->  WelcomeViewModel(handle, get()) }
    viewModel { (savedState: SavedStateHandle) -> LoginViewModel(savedState, get()) }
}