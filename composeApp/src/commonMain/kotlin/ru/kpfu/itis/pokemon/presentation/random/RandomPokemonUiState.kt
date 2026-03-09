package ru.kpfu.itis.pokemon.presentation.random

import ru.kpfu.itis.pokemon.domain.entity.PokemonInfo

data class RandomPokemonUiState(
    val pokemons: List<PokemonInfo>,
    val currentPokemon: PokemonInfo? = null,
    val isSpinning: Boolean
)