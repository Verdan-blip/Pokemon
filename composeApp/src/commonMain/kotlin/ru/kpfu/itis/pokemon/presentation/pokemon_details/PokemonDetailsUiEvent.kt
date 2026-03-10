package ru.kpfu.itis.pokemon.presentation.pokemon_details

sealed interface PokemonDetailsUiEvent {
    data object Init : PokemonDetailsUiEvent
    data object GoBackClick : PokemonDetailsUiEvent
}