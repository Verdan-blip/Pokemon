package ru.kpfu.itis.pokemon.presentation.pokemon_list

import ru.kpfu.itis.pokemon.domain.entity.PokemonInfo

interface PokemonListUiEvent {
    data class PokemonClick(val id: Int) : PokemonListUiEvent
    data class AddToFavouritesClick(val pokemon: PokemonInfo) : PokemonListUiEvent
    data class RemoveFromFavouritesClick(val pokemon: PokemonInfo) : PokemonListUiEvent
}