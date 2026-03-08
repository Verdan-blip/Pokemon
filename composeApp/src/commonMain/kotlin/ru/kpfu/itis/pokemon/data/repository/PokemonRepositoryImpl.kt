package ru.kpfu.itis.pokemon.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import co.pokeapi.pokekotlin.PokeApi
import kotlinx.coroutines.flow.flow
import ru.kpfu.itis.pokemon.data.datasource.PokemonPagingSource
import ru.kpfu.itis.pokemon.data.mapper.toPokemonInfo
import ru.kpfu.itis.pokemon.domain.repository.PokemonRepository

class PokemonRepositoryImpl(
    private val pokeApi: PokeApi,
    private val pokemonPagingSource: PokemonPagingSource.Factory
) : PokemonRepository {

    override fun getPokemonList() = Pager(
        config = PagingConfig(
            pageSize = PAGE_SIZE,
            enablePlaceholders = true,
            initialLoadSize = PAGE_SIZE,
            prefetchDistance = 0
        )
    ) {
        pokemonPagingSource.create(api = PokeApi, pageSize = PAGE_SIZE)
    }.flow

    override fun getPokemon(id: Int) = flow {
        val pokemonInfo = pokeApi
            .getPokemonVariety(id)
            .toPokemonInfo()

        emit(pokemonInfo)
    }

    companion object {
        private const val PAGE_SIZE = 10
    }
}