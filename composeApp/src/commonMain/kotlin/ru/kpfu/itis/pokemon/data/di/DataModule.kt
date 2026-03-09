package ru.kpfu.itis.pokemon.data.di

import co.pokeapi.pokekotlin.PokeApi
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.kpfu.itis.pokemon.PokemonDataBase
import ru.kpfu.itis.pokemon.data.dao.PokemonCacheDao
import ru.kpfu.itis.pokemon.data.datasource.PokemonPagingSource
import ru.kpfu.itis.pokemon.data.repository.PokemonCacheRepositoryImpl
import ru.kpfu.itis.pokemon.data.repository.PokemonRepositoryImpl
import ru.kpfu.itis.pokemon.domain.repository.PokemonCacheRepository
import ru.kpfu.itis.pokemon.domain.repository.PokemonRepository

internal val dataModule = module {
    factory {
        PokemonCacheDao(queries = get<PokemonDataBase>().pokemon_crudsQueries)
    }

    factory {
        PokemonPagingSource.Factory()
    }

    factory {
        PokemonRepositoryImpl(
            pokeApi = PokeApi,
            dao = get(),
            pokemonPagingSource = PokemonPagingSource.Factory()
        )
    } bind PokemonRepository::class

    factory {
        PokemonCacheRepositoryImpl(dao = get())
    } bind PokemonCacheRepository::class
}