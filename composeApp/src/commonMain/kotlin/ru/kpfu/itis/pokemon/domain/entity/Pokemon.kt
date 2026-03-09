package ru.kpfu.itis.pokemon.domain.entity

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

data class Pokemon(
    val name: String,
    val info: Flow<PokemonInfo>
)