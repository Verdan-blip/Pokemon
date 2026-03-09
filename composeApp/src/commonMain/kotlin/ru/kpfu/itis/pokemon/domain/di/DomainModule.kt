package ru.kpfu.itis.pokemon.domain.di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import ru.kpfu.itis.pokemon.domain.usecase.AddPokemonToFavouritesUseCase
import ru.kpfu.itis.pokemon.domain.usecase.GetCachedFavouritePokemonListUseCase
import ru.kpfu.itis.pokemon.domain.usecase.GetPokemonInfoUseCase
import ru.kpfu.itis.pokemon.domain.usecase.GetPokemonListUseCase
import ru.kpfu.itis.pokemon.domain.usecase.RemovePokemonFromFavouritesUseCase

internal val domainModule = module {
    factoryOf(::GetPokemonListUseCase)
    factoryOf(::GetPokemonInfoUseCase)
    factoryOf(::AddPokemonToFavouritesUseCase)
    factoryOf(::RemovePokemonFromFavouritesUseCase)
    factoryOf(::GetCachedFavouritePokemonListUseCase)
}