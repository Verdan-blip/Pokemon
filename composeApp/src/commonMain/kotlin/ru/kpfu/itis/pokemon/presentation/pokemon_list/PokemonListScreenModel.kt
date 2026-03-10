package ru.kpfu.itis.pokemon.presentation.pokemon_list

import androidx.paging.cachedIn
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.kpfu.itis.pokemon.domain.entity.PokemonInfo
import ru.kpfu.itis.pokemon.domain.usecase.AddPokemonToFavouritesUseCase
import ru.kpfu.itis.pokemon.domain.usecase.GetPokemonListUseCase
import ru.kpfu.itis.pokemon.domain.usecase.RemovePokemonFromFavouritesUseCase

class PokemonListScreenModel(
    private val getPokemonListUseCase: GetPokemonListUseCase,
    private val addPokemonToFavouritesUseCase: AddPokemonToFavouritesUseCase,
    private val removePokemonFromFavouritesUseCase: RemovePokemonFromFavouritesUseCase
) : ScreenModel {
    private val _uiState = MutableStateFlow(PokemonListUiState())
    val uiState = _uiState.asStateFlow()

    private val _effect = Channel<PokemonListEffect>()
    val effect = _effect.receiveAsFlow()

    fun onUiEvent(uiEvent: PokemonListUiEvent) {
        when (uiEvent) {
            is PokemonListUiEvent.Init -> onInit()
            is PokemonListUiEvent.PokemonClick -> onPokemonClick(id = uiEvent.id)
            is PokemonListUiEvent.AddToFavouritesClick -> onAddToFavourites(uiEvent.pokemon)
            is PokemonListUiEvent.RemoveFromFavouritesClick -> onRemoveFromFavourites(uiEvent.pokemon)
        }
    }

    private fun onInit() {
        _uiState.update {
            it.copy(
                pokemons = getPokemonListUseCase()
                    .cachedIn(screenModelScope)
            )
        }
    }

    private fun onAddToFavourites(pokemon: PokemonInfo) = screenModelScope.launch {
        addPokemonToFavouritesUseCase(pokemon = pokemon)
            .catch {
                println()
            }
            .collect {
                println()
            }
    }

    private fun onRemoveFromFavourites(pokemon: PokemonInfo) = screenModelScope.launch {
        removePokemonFromFavouritesUseCase(pokemon = pokemon)
            .catch {  }
            .collect {  }
    }

    private fun onPokemonClick(id: Int) = screenModelScope.launch {
        _effect.send(PokemonListEffect.OpenPokemonDetails(id = id))
    }

    override fun onDispose() {
        super.onDispose()
    }
}