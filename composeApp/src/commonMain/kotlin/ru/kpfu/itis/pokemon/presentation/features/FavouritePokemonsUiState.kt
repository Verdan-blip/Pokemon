package ru.kpfu.itis.pokemon.presentation.features

import ru.kpfu.itis.pokemon.domain.entity.PokemonInfo

data class FavouritePokemonsUiState(
    val pokemons: List<PokemonInfo>
)