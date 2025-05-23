package dev.gbenga.endurely.di

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import dev.gbenga.endurely.core.data.SettingsDatastore
import dev.gbenga.endurely.dashboard.DashboardRepository
import dev.gbenga.endurely.dashboard.DashboardViewModel
import dev.gbenga.endurely.dashboard.Greeting
import dev.gbenga.endurely.dashboard.SettingsRepository
import dev.gbenga.endurely.dashboard.SettingsViewModel
import dev.gbenga.endurely.main.EndurelyBottomBarViewModel
import dev.gbenga.endurely.meal.MealPlanDetailsViewModel
import dev.gbenga.endurely.meal.MealPlanRepository
import dev.gbenga.endurely.meal.MealPlanViewModel
import dev.gbenga.endurely.meal.MealService
import dev.gbenga.endurely.meal.data.MessageSharedPref
import dev.gbenga.endurely.onboard.OnboardRepository
import dev.gbenga.endurely.onboard.data.OnboardService
import dev.gbenga.endurely.onboard.login.LoginViewModel
import dev.gbenga.endurely.onboard.signup.SignUpViewModel
import dev.gbenga.endurely.onboard.welcome.WelcomeViewModel
import dev.gbenga.endurely.routines.AddNewRoutineViewModel
import dev.gbenga.endurely.routines.EditRoutineViewModel
import dev.gbenga.endurely.routines.ExerciseSuggestionsViewModel
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
    single<MealService> { get<Retrofit>().create(MealService::class.java)  }
    single { Greeting() }

    single { SettingsDatastore(get()) }
    single<CoroutineContext> { Dispatchers.IO }
    single { MealPlanRepository(get(), get()) }
    single { OnboardRepository(get(), get(), get()) }
    single { DashboardRepository(get(), get()) }
    single { SettingsRepository(get(), get(), get()) }
    single { get<Context>().getSharedPreferences("dev.gbenga.endurely.di.Endurely.Co", Context.MODE_PRIVATE)}
    single { MessageSharedPref(get()) }
    viewModel { (handle: SavedStateHandle) -> WelcomeViewModel(handle, get()) }
    viewModel { (savedState: SavedStateHandle) -> LoginViewModel(savedState, get()) }
    viewModel { SignUpViewModel(get()) }
    viewModel { (handle: SavedStateHandle) ->  DashboardViewModel(get(), get(), get(), get(), get(), handle) }
    viewModel { EndurelyBottomBarViewModel() }
    viewModel { SettingsViewModel(get()) }
    single { RoutineRepository(get(), get(), get()) }
    viewModel { (handle: SavedStateHandle) -> RoutinesViewModel(get(), get(), get(), handle) }
    viewModel { RoutineDetailViewModel(get()) }
    viewModel{ AddNewRoutineViewModel(get(), get(), get()) }
    viewModel { ExerciseSuggestionsViewModel(get()) }
    viewModel { (handle: SavedStateHandle) -> MealPlanViewModel(get(), get(), get(), get(), handle) }
    viewModel { MealPlanDetailsViewModel(get(), get()) }
    viewModel { EditRoutineViewModel(get(), get()) }
}