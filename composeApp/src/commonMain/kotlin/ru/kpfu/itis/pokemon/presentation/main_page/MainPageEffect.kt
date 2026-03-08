package ru.kpfu.itis.pokemon.presentation.main_page

interface MainPageEffect {
    data class OpenPokemonDetails(val id: Int) : MainPageEffect
}