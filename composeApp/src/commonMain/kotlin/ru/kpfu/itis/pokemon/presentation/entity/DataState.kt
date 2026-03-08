package ru.kpfu.itis.pokemon.presentation.entity

sealed interface DataState<out T> {
    data object Failure : DataState<Nothing>
    data object Loading : DataState<Nothing>
    data class Done <T> (val data: T) : DataState<T>
}