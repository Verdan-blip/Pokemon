package ru.kpfu.itis.pokemon.presentation.pokemon_list

interface PokemonListEffect {
    data class OpenPokemonDetails(val id: Int) : PokemonListEffect
}