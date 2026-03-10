package ru.kpfu.itis.pokemon.data.repository

import kotlinx.coroutines.flow.flow
import ru.kpfu.itis.pokemon.data.dao.PokemonCacheDao
import ru.kpfu.itis.pokemon.domain.entity.PokemonInfo
import ru.kpfu.itis.pokemon.domain.repository.PokemonCacheRepository

class PokemonCacheRepositoryImpl(
    private val dao: PokemonCacheDao
) : PokemonCacheRepository {

    override fun getPokemon(id: Int) = flow {
        emit(dao.getPokemon(id))
    }

    override fun insertPokemon(pokemon: PokemonInfo) = flow {
        dao.insertPokemon(pokemon)

        emit(Unit)
    }

    override fun addToFeatures(pokemon: PokemonInfo) = flow {
        dao.addToFeatures(pokemon)

        emit(Unit)
    }

    override fun removeFromFavourites(pokemon: PokemonInfo) = flow {
        dao.removeFromFeatures(pokemon)

        emit(Unit)
    }

    override fun getAllFavourites() = flow {
        emit(dao.getAllFavourites())
    }
}