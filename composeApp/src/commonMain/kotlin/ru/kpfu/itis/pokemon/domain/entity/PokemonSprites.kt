package ru.kpfu.itis.pokemon.domain.entity

class PokemonSprites(
    val frontUrl: String?,
    val backUrl: String?
) {
    companion object {
        fun mockEmpty() = PokemonSprites(
            frontUrl = null,
            backUrl = null
        )
    }
}