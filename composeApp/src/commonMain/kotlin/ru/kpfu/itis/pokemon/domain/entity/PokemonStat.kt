package ru.kpfu.itis.pokemon.domain.entity

class PokemonStat(
    val name: String,
    val value: Int
) {
    companion object {
        fun mock() = PokemonStat(
            name = "speed",
            value = 48
        )
    }
}