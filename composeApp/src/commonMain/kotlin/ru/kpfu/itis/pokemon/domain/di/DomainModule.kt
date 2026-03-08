package ru.kpfu.itis.pokemon.domain.di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import ru.kpfu.itis.pokemon.domain.usecase.GetPokemonInfoUseCase
import ru.kpfu.itis.pokemon.domain.usecase.GetPokemonListUseCase

internal val domainModule = module {
    factoryOf(::GetPokemonListUseCase)
    factoryOf(::GetPokemonInfoUseCase)
}