package ru.kpfu.itis.pokemon.presentation.main_page

interface PokemonListUiEvent {
    data object SearchTextChange : PokemonListUiEvent
    data class PokemonClick(val id: Int) : PokemonListUiEvent
}