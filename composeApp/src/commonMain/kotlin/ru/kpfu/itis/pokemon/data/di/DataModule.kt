package ru.kpfu.itis.pokemon.data.di

import co.pokeapi.pokekotlin.PokeApi
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.kpfu.itis.pokemon.data.datasource.PokemonPagingSource
import ru.kpfu.itis.pokemon.data.repository.PokemonRepositoryImpl
import ru.kpfu.itis.pokemon.domain.repository.PokemonRepository

internal val dataModule = module {
    factory {
        PokemonRepositoryImpl(
            pokeApi = PokeApi,
            pokemonPagingSource = PokemonPagingSource.Factory()
        )
    } bind PokemonRepository::class

    factory { PokemonPagingSource.Factory() }
}