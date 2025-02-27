package dev.gbenga.endurely.onboard

import androidx.lifecycle.SavedStateHandle
import dev.gbenga.endurely.dashboard.DashboardRepository
import dev.gbenga.endurely.dashboard.DashboardViewModel
import dev.gbenga.endurely.onboard.data.OnboardService
import dev.gbenga.endurely.onboard.login.LoginViewModel
import dev.gbenga.endurely.onboard.signup.SignUpViewModel
import dev.gbenga.endurely.onboard.welcome.WelcomeViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val onboardModule = module {
   single { get<Retrofit>().create(OnboardService::class.java) }
    single { DashboardRepository() }
    single { OnboardRepository(get(), ioContext = Dispatchers.IO) }
    viewModel { (handle : SavedStateHandle) ->  WelcomeViewModel(handle, get()) }
    viewModel { (savedState: SavedStateHandle) -> LoginViewModel(savedState, get()) }
    viewModel { SignUpViewModel(get()) }
    viewModel { DashboardViewModel(get()) }
}