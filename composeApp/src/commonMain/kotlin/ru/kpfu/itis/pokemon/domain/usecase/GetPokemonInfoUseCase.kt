package ru.kpfu.itis.pokemon.domain.usecase

import ru.kpfu.itis.pokemon.domain.repository.PokemonRepository

class GetPokemonInfoUseCase(
    private val repository: PokemonRepository
) {
    operator fun invoke(id: Int) = repository.getPokemon(id)
}