package ru.kpfu.itis.pokemon.presentation.features

import ru.kpfu.itis.pokemon.domain.entity.PokemonInfo

sealed interface FavouritePokemonsUiEvent {
    data object Init : FavouritePokemonsUiEvent
    data class Click(val pokemon: PokemonInfo) : FavouritePokemonsUiEvent
    data class Remove(val pokemon: PokemonInfo) : FavouritePokemonsUiEvent
}