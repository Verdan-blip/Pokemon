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

    override fun addToFeatures(id: Int) = flow {
        dao.addToFeatures(id)

        emit(Unit)
    }

    override fun removeFromFavourites(id: Int) = flow {
        dao.removeFromFeatures(id)

        emit(Unit)
    }

    override fun getAllFavourites() = flow {
        emit(dao.getAllFavourites())
    }
}