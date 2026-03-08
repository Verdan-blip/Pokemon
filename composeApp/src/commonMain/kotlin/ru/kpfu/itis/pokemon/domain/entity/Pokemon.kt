package ru.kpfu.itis.pokemon.domain.entity

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

data class Pokemon(
    val name: String,
    val info: Flow<PokemonInfo>
) {
    companion object {
        fun mockEmpty() = Pokemon(
            name = "pokeball",
            info = flowOf()
        )

        fun mock() = Pokemon(
            name = "pokeball",
            info = flowOf()
        )
    }
}