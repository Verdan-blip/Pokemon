package ru.kpfu.itis.pokemon.data.mapper

import co.pokeapi.pokekotlin.model.Handle
import kotlinx.coroutines.flow.Flow
import ru.kpfu.itis.pokemon.domain.entity.Pokemon
import ru.kpfu.itis.pokemon.domain.entity.PokemonInfo
import ru.kpfu.itis.pokemon.domain.entity.PokemonSprites
import ru.kpfu.itis.pokemon.domain.entity.PokemonStat

typealias ApiPokemonSprites = co.pokeapi.pokekotlin.model.PokemonSprites
typealias ApiPokemonStat = co.pokeapi.pokekotlin.model.PokemonStat
typealias ApiPokemonVariety = co.pokeapi.pokekotlin.model.PokemonVariety

internal fun Handle.Named<ApiPokemonVariety>.toPokemon(
    pokemonInfo: Flow<PokemonInfo>
) = Pokemon(
    name = name,
    info = pokemonInfo
)

internal fun ApiPokemonVariety.toPokemonInfo() = PokemonInfo(
    id = id,
    name = name,
    abilities = abilities
        .mapNotNull { it.ability }
        .map { it.name },
    moves = moves.map { it.move.name },
    stats = stats.map { it.toPokemonStat() },
    sprites = sprites.toPokemonSprites(),
    types = types.map { it.type.name },
    weight = weight,
    height = height
)

internal fun ApiPokemonSprites.toPokemonSprites() = PokemonSprites(
    frontUrl = frontDefault,
    backUrl = backDefault
)

internal fun ApiPokemonStat.toPokemonStat() = PokemonStat(
    name = stat.name,
    value = baseStat
)