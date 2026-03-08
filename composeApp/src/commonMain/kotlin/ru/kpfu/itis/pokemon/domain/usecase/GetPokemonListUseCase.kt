package ru.kpfu.itis.pokemon.domain.usecase

import ru.kpfu.itis.pokemon.domain.repository.PokemonRepository

class GetPokemonListUseCase(
    private val repository: PokemonRepository
) {
    operator fun invoke() = repository.getPokemonList()
}