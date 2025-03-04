package dev.gbenga.endurely.di

import androidx.lifecycle.SavedStateHandle
import dev.gbenga.endurely.core.data.SettingsDatastore
import dev.gbenga.endurely.dashboard.DashboardRepository
import dev.gbenga.endurely.dashboard.DashboardViewModel
import dev.gbenga.endurely.dashboard.Greeting
import dev.gbenga.endurely.dashboard.SettingsRepository
import dev.gbenga.endurely.dashboard.SettingsViewModel
import dev.gbenga.endurely.main.EndurelyBottomBarViewModel
import dev.gbenga.endurely.onboard.OnboardRepository
import dev.gbenga.endurely.onboard.data.OnboardService
import dev.gbenga.endurely.onboard.login.LoginViewModel
import dev.gbenga.endurely.onboard.signup.SignUpViewModel
import dev.gbenga.endurely.onboard.welcome.WelcomeViewModel
import dev.gbenga.endurely.routines.RoutineDetailViewModel
import dev.gbenga.endurely.routines.RoutineRepository
import dev.gbenga.endurely.routines.RoutinesService
import dev.gbenga.endurely.routines.RoutinesViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import kotlin.coroutines.CoroutineContext

val onboardModule = module {
    single { get<Retrofit>().create(OnboardService::class.java) }
    single { get<Retrofit>().create(RoutinesService::class.java) }
    single { Greeting() }

    single { SettingsDatastore(get()) }
    single<CoroutineContext> { Dispatchers.IO }
    single { OnboardRepository(get(), get(), get()) }
    single { DashboardRepository(get(), get()) }
    single { SettingsRepository(get(), get(), get()) }
    viewModel { (handle: SavedStateHandle) -> WelcomeViewModel(handle, get()) }
    viewModel { (savedState: SavedStateHandle) -> LoginViewModel(savedState, get()) }
    viewModel { SignUpViewModel(get()) }
    viewModel { DashboardViewModel(get(), get()) }
    viewModel { EndurelyBottomBarViewModel() }
    viewModel { SettingsViewModel(get()) }
    single { RoutineRepository(get(), get(), get()) }
    viewModel { RoutinesViewModel(get(), get()) }
    viewModel { RoutineDetailViewModel() }
}