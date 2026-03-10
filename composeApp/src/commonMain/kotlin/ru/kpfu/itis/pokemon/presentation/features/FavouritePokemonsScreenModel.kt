package ru.kpfu.itis.pokemon.presentation.features

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.kpfu.itis.pokemon.domain.entity.PokemonInfo
import ru.kpfu.itis.pokemon.domain.usecase.GetCachedFavouritePokemonListUseCase
import ru.kpfu.itis.pokemon.domain.usecase.RemovePokemonFromFavouritesUseCase
import ru.kpfu.itis.pokemon.presentation.entity.DataState
import ru.kpfu.itis.pokemon.presentation.entity.updateStateIfDone

class FavouritePokemonsScreenModel(
    private val getCachedFavouritePokemonListUseCase: GetCachedFavouritePokemonListUseCase,
    private val removePokemonFromFavouritesUseCase: RemovePokemonFromFavouritesUseCase,
) : ScreenModel {
    private val _uiState = MutableStateFlow<DataState<FavouritePokemonsUiState>>(DataState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _effect = Channel<FavouritePokemonsEffect>()
    val effect = _effect.receiveAsFlow()

    fun onUiEvent(uiEvent: FavouritePokemonsUiEvent) {
        when (uiEvent) {
            is FavouritePokemonsUiEvent.Init -> onInit()
            is FavouritePokemonsUiEvent.Click -> onPokemonClick(uiEvent.pokemon)
            is FavouritePokemonsUiEvent.Remove -> onRemoveClick(uiEvent.pokemon)
        }
    }

    private fun onInit() = screenModelScope.launch(Dispatchers.IO) {
        getCachedFavouritePokemonListUseCase()
            .onStart {
                _uiState.update { DataState.Loading }
            }
            .catch {
                _uiState.update { DataState.Failure }
            }
            .collect { pokemons ->
                _uiState.update {
                    DataState.Done(FavouritePokemonsUiState(pokemons = pokemons))
                }
            }
    }

    private fun onPokemonClick(pokemon: PokemonInfo) = screenModelScope.launch {
        _effect.send(FavouritePokemonsEffect.OpenPokemonDetails(pokemon.id))
    }

    private fun onRemoveClick(pokemon: PokemonInfo) = screenModelScope.launch {
        removePokemonFromFavouritesUseCase(pokemon)
            .catch { }
            .collect {
                _uiState.updateStateIfDone {
                    val leftPokemons = it
                        .pokemons
                        .toMutableList()

                    leftPokemons.remove(pokemon)

                    it.copy(pokemons = leftPokemons)
                }
            }
    }

    override fun onDispose() {
        super.onDispose()
    }
}