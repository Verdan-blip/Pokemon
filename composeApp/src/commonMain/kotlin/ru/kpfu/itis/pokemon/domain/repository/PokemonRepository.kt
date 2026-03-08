package ru.kpfu.itis.pokemon.domain.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.kpfu.itis.pokemon.domain.entity.PokemonInfo

interface PokemonRepository {
    fun getPokemonList(): Flow<PagingData<PokemonInfo>>
    fun getPokemon(id: Int): Flow<PokemonInfo>
}