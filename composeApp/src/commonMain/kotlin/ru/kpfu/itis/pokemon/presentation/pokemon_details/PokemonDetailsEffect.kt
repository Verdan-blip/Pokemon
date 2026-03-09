package ru.kpfu.itis.pokemon.presentation.pokemon_details

sealed interface PokemonDetailsEffect {
    data object GoBack : PokemonDetailsEffect
}