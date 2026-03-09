package ru.kpfu.itis.pokemon.presentation.di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import ru.kpfu.itis.pokemon.presentation.main_page.PokemonListScreenModel
import ru.kpfu.itis.pokemon.presentation.pokemon_details.PokemonDetailsScreenModel

internal val presentationModule = module {
    factoryOf(::PokemonListScreenModel)
    factory { (id: Int) ->
        PokemonDetailsScreenModel(
            id = id,
            getPokemonInfoUseCase = get()
        )
    }
}