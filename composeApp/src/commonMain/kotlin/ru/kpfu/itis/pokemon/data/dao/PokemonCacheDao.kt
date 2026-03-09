package ru.kpfu.itis.pokemon.data.dao

import ru.kpfu.itis.pokemon.Pokemon_crudsQueries
import ru.kpfu.itis.pokemon.domain.entity.PokemonInfo
import ru.kpfu.itis.pokemon.domain.entity.PokemonSprites
import ru.kpfu.itis.pokemon.domain.entity.PokemonStat

class PokemonCacheDao(
    private val queries: Pokemon_crudsQueries
) {
    fun getPokemon(id: Int): PokemonInfo? {
        val pokemon = queries
            .selectPokemonById(id.toLong())
            .executeAsOneOrNull()
            ?: return null

        val stats = queries
            .selectStatsByPokemonId(id.toLong())
            .executeAsList()

        val moves = queries
            .selectMovesByPokemonId(id.toLong())
            .executeAsList()

        val abilities = queries
            .selectMovesByPokemonId(id.toLong())
            .executeAsList()

        val types = queries
            .selectTypesByPokemonId(id.toLong())
            .executeAsList()

        val isAddedToFavourites = queries
            .isFavorite(id.toLong())
            .executeAsOneOrNull()

        return PokemonInfo(
            id = pokemon.id.toInt(),
            name = pokemon.name.orEmpty(),
            abilities = abilities.mapNotNull { it.name },
            moves = moves.mapNotNull { it.name },
            stats = stats.map {
                PokemonStat(
                    name = it.name.orEmpty(),
                    value = it.value_?.toInt() ?: 0
                )
            },
            types = types.map { it.name.orEmpty() },
            sprites = PokemonSprites(
                frontUrl = pokemon.front_img,
                backUrl = pokemon.back_img
            ),
            weight = pokemon.weigth?.toInt() ?: 0,
            height = pokemon.height?.toInt() ?: 0,
            isFavourite = isAddedToFavourites ?: false
        )
    }

    fun insertPokemon(pokemon: PokemonInfo) {
        val dbPokemon = queries
            .selectPokemonById(pokemon.id.toLong())
            .executeAsOneOrNull()

        if (dbPokemon == null) {
            val id = pokemon.id.toLong()

            queries.insertPokemon(
                id = id,
                name = pokemon.name,
                weigth = pokemon.weight.toLong(),
                height = pokemon.height.toLong(),
                back_img = pokemon.sprites.backUrl,
                front_img = pokemon.sprites.frontUrl
            )

            pokemon.abilities.forEach { queries.insertAbility(it) }
            pokemon.stats.forEach {
                queries.insertStat(
                    pokemon_id = id,
                    name = it.name,
                    value_ = it.value.toLong()
                )
            }
            pokemon.moves.forEach { queries.insertMove(it) }
            pokemon.types.forEach { queries.insertType(it) }
        }
    }

    fun addToFeatures(id: Int) {
        queries.insertFavorite(pokemon_id = id.toLong())
    }

    fun removeFromFeatures(id: Int) {
        queries.removeFavorite(pokemon_id = id.toLong())
    }

    fun getAllFavourites(): List<PokemonInfo> {
        return queries
            .getFavoritePokemons()
            .executeAsList()
            .mapNotNull { getPokemon(it.id.toInt()) }
    }
}