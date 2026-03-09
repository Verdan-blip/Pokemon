package ru.kpfu.itis.pokemon.presentation.entity

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

sealed interface DataState<out T> {
    data object Failure : DataState<Nothing>
    data object Loading : DataState<Nothing>
    data class Done <T> (val data: T) : DataState<T>
}

inline fun <T> MutableStateFlow<DataState<T>>.updateStateIfDone(
    update: (T) -> T
) {
    update { dataState ->
        if (dataState is DataState.Done) {
            DataState.Done(update(dataState.data))
        } else {
            dataState
        }
    }
}