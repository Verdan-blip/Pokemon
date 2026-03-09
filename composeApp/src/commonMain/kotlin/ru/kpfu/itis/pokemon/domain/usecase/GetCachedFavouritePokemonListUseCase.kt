package ru.kpfu.itis.pokemon.domain.usecase

import ru.kpfu.itis.pokemon.domain.repository.PokemonCacheRepository

class GetCachedFavouritePokemonListUseCase(
    private val repository: PokemonCacheRepository
) {
    operator fun invoke() = repository.getAllFavourites()
}