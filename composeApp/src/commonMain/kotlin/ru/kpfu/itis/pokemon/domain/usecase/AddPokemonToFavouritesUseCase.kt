package ru.kpfu.itis.pokemon.domain.usecase

import ru.kpfu.itis.pokemon.domain.entity.PokemonInfo
import ru.kpfu.itis.pokemon.domain.repository.PokemonCacheRepository

class AddPokemonToFavouritesUseCase(
    private val repository: PokemonCacheRepository
) {
    operator fun invoke(pokemon: PokemonInfo) = repository.addToFeatures(pokemon)
}