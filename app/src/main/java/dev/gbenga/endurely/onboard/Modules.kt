package dev.gbenga.endurely.onboard

import androidx.lifecycle.SavedStateHandle
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val onboardModule = module {
    viewModel { (handle : SavedStateHandle) ->  WelcomeViewModel(handle, get())}
}