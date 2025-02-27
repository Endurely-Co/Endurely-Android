package dev.gbenga.endurely.core

import kotlin.random.Random

sealed interface UiState<T> {
    data class Success<T>(val data: T): UiState<T>
    data class Loading<T>(val message: String = "Loading"): UiState<T>
    data class Failure<T>(val message: String): UiState<T>
    class Idle<T> : UiState<T>
}