package ru.kpfu.itis.pokemon.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.kpfu.itis.pokemon.domain.entity.PokemonInfo

interface PokemonCacheRepository {
    fun addToFeatures(pokemon: PokemonInfo): Flow<Unit>
    fun removeFromFavourites(pokemon: PokemonInfo): Flow<Unit>
    fun getPokemon(id: Int): Flow<PokemonInfo?>
    fun insertPokemon(pokemon: PokemonInfo): Flow<Unit>
    fun getAllFavourites(): Flow<List<PokemonInfo>>
}