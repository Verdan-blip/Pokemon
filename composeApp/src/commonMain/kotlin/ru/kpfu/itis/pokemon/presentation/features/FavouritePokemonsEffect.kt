package ru.kpfu.itis.pokemon.presentation.features

sealed interface FavouritePokemonsEffect {
    data class OpenPokemonDetails(val id: Int) : FavouritePokemonsEffect
}