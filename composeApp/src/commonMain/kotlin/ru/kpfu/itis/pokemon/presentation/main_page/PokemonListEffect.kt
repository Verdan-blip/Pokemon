package ru.kpfu.itis.pokemon.presentation.main_page

interface PokemonListEffect {
    data class OpenPokemonDetails(val id: Int) : PokemonListEffect
}