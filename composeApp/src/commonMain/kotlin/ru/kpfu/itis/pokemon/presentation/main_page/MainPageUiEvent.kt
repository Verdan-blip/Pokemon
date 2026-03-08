package ru.kpfu.itis.pokemon.presentation.main_page

interface MainPageUiEvent {
    data object Init : MainPageUiEvent
    data object SearchTextChange : MainPageUiEvent
    data class PokemonClick(val id: Int) : MainPageUiEvent
}