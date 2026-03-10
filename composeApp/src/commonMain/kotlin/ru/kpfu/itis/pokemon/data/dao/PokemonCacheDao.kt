package ru.kpfu.itis.pokemon.data.dao

import ru.kpfu.itis.pokemon.Pokemon_crudsQueries
import ru.kpfu.itis.pokemon.domain.entity.Pokemon
import ru.kpfu.itis.pokemon.domain.entity.PokemonInfo
import ru.kpfu.itis.pokemon.domain.entity.PokemonSprites
import ru.kpfu.itis.pokemon.domain.entity.PokemonStat

class PokemonCacheDao(
    private val queries: Pokemon_crudsQueries
) {
    fun getPokemon(id: Int): PokemonInfo? {
        val rows = queries
            .getFullPokemonDataById(id = id.toLong())
            .executeAsList()

        if (rows.isEmpty()) return null

        val first = rows.first()

        return PokemonInfo(
            id = first.id.toInt(),
            name = first.name.orEmpty(),
            weight = first.weigth?.toInt() ?: 0,
            height = first.height?.toInt() ?: 0,
            sprites = PokemonSprites(frontUrl = first.front_img, backUrl = first.back_img),
            types = rows.mapNotNull { it.type_name }.distinct(),
            abilities = rows.mapNotNull { it.ability_name }.distinct(),
            moves = rows.mapNotNull { it.move_name }.distinct(),
            stats = rows.mapNotNull { row ->
                if (row.stat_name != null) {
                    PokemonStat(row.stat_name, row.stat_value?.toInt() ?: 0)
                } else null
            }.distinctBy { it.name },
            isFavourite = first.is_favorite
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

            pokemon.abilities.forEach {
                queries.insertAbility(pokemon_id = id, name = it)
            }

            pokemon.stats.forEach {
                queries.insertStat(
                    pokemon_id = id,
                    name = it.name,
                    value_ = it.value.toLong()
                )
            }

            pokemon.moves.forEach {
                queries.insertMove(pokemon_id = id, name = it)
            }

            pokemon.types.forEach {
                queries.insertType(pokemon_id = id, name = it)
            }
        }
    }

    fun addToFeatures(pokemon: PokemonInfo) {
        queries.insertFavorite(pokemon_id = pokemon.id.toLong())
    }

    fun removeFromFeatures(pokemon: PokemonInfo) {
        queries.removeFavorite(pokemon_id = pokemon.id.toLong())
    }

    fun getAllFavourites(): List<PokemonInfo> {
        val rows = queries
            .getFavoriteFullPokemons()
            .executeAsList()

        return rows.groupBy { it.id }.map { (id, pokemonRows) ->
            val first = pokemonRows.first()

            PokemonInfo(
                id = id.toInt(),
                name = first.name.orEmpty(),
                weight = first.weigth?.toInt() ?: 0,
                height = first.height?.toInt() ?: 0,
                sprites = PokemonSprites(frontUrl = first.front_img, backUrl = first.back_img),
                types = pokemonRows.mapNotNull { it.type_name }.distinct(),
                abilities = pokemonRows.mapNotNull { it.ability_name }.distinct(),
                moves = pokemonRows.mapNotNull { it.move_name }.distinct(),
                stats = pokemonRows.mapNotNull { row ->
                    if (row.stat_name != null) {
                        PokemonStat(name = row.stat_name, value = row.stat_value?.toInt() ?: 0)
                    } else {
                        null
                    }
                }.distinctBy { it.name },
                isFavourite = true
            )
        }
    }
}