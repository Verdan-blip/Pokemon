package ru.kpfu.itis.pokemon.domain.entity

data class PokemonInfo(
    val id: Int,
    val name: String,
    val abilities: List<String>,
    val moves: List<String>,
    val types: List<String>,
    val stats: List<PokemonStat>,
    val sprites: PokemonSprites,
    val weight: Int,
    val height: Int,
    val isFavourite: Boolean = false
) {
    companion object {
        fun mock() = PokemonInfo(
            id = 0,
            name = "pikachu",
            abilities = listOf("jump attack", "sonic speed"),
            moves = listOf("transform", "fly"),
            types = listOf("normal"),
            stats = listOf(PokemonStat.mock()),
            sprites = PokemonSprites.mockEmpty(),
            weight = 50,
            height = 100
        )
    }
}