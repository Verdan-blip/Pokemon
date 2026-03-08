package ru.kpfu.itis.pokemon.data.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import co.pokeapi.pokekotlin.PokeApi
import ru.kpfu.itis.pokemon.data.mapper.toPokemonInfo
import ru.kpfu.itis.pokemon.domain.entity.PokemonInfo

class PokemonPagingSource private constructor(
    private val pokeApi: PokeApi,
    private val pageSize: Int
) : PagingSource<Int, PokemonInfo>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PokemonInfo> {
        return try {
            val page = params.key ?: 0

            val shortPokemons = pokeApi
                .getPokemonVarietyList(offset = page * pageSize, limit = pageSize)
                .results

            val pokemons = shortPokemons
                .map {
                    pokeApi
                        .getPokemonVariety(id = it.id)
                        .toPokemonInfo()
                }

            LoadResult.Page(
                data = pokemons,
                prevKey = null,
                nextKey = page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, PokemonInfo>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    class Factory {
        fun create(api: PokeApi, pageSize: Int) = PokemonPagingSource(api, pageSize)
    }
}