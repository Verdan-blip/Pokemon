package ru.kpfu.itis.pokemon.presentation.di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import ru.kpfu.itis.pokemon.presentation.features.FavouritePokemonsScreenModel
import ru.kpfu.itis.pokemon.presentation.pokemon_list.PokemonListScreenModel
import ru.kpfu.itis.pokemon.presentation.pokemon_details.PokemonDetailsScreenModel
import ru.kpfu.itis.pokemon.presentation.random.RandomPokemonScreenModel

internal val presentationModule = module {
    factoryOf(::PokemonListScreenModel)
    factoryOf(::RandomPokemonScreenModel)
    factoryOf(::FavouritePokemonsScreenModel)

    factory { (id: Int) ->
        PokemonDetailsScreenModel(
            id = id,
            getPokemonInfoUseCase = get()
        )
    }
}