package ru.kpfu.itis.pokemon.presentation.random

sealed interface RandomPokemonUiEvent {
    data object SpinClick : RandomPokemonUiEvent
}