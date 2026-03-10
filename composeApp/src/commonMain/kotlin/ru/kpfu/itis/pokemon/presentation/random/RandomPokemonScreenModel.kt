package ru.kpfu.itis.pokemon.presentation.random

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.kpfu.itis.pokemon.domain.usecase.GetCachedFavouritePokemonListUseCase
import ru.kpfu.itis.pokemon.presentation.entity.DataState
import ru.kpfu.itis.pokemon.presentation.entity.updateStateIfDone

class RandomPokemonScreenModel(
    private val getCachedFavouritePokemonListUseCase: GetCachedFavouritePokemonListUseCase
) : ScreenModel {
    private val _uiState = MutableStateFlow<DataState<RandomPokemonUiState>>(DataState.Loading)
    val uiState = _uiState.asStateFlow()

    fun onUiEvent(uiEvent: RandomPokemonUiEvent) {
        when (uiEvent) {
            RandomPokemonUiEvent.Init -> onInit()
            RandomPokemonUiEvent.SpinClick -> onSpinClick()
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
                    DataState.Done(
                        data = RandomPokemonUiState(
                            pokemons = pokemons,
                            isSpinning = false
                        )
                    )
                }
            }
    }

    private fun onSpinClick() = screenModelScope.launch(Dispatchers.IO) {
        _uiState.updateStateIfDone {
            it.copy(isSpinning = true)
        }

        delay(SPINNING_TIME)

        _uiState.updateStateIfDone {
            it.copy(
                currentPokemon = it.pokemons.random(),
                isSpinning = false
            )
        }
    }

    override fun onDispose() {
        super.onDispose()
    }

    companion object {
        private const val SPINNING_TIME = 2_000L
    }
}