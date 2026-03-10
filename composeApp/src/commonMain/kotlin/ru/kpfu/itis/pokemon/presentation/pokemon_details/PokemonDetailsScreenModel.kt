package ru.kpfu.itis.pokemon.presentation.pokemon_details

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.kpfu.itis.pokemon.domain.usecase.GetPokemonInfoUseCase
import ru.kpfu.itis.pokemon.presentation.entity.DataState

class PokemonDetailsScreenModel(
    private val id: Int,
    private val getPokemonInfoUseCase: GetPokemonInfoUseCase
) : ScreenModel {
    private val _uiState = MutableStateFlow<DataState<PokemonDetailsUiState>>(DataState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _effect = Channel<PokemonDetailsEffect>()
    val effect = _effect.receiveAsFlow()

    fun onUiEvent(uiEvent: PokemonDetailsUiEvent) {
        when (uiEvent) {
            PokemonDetailsUiEvent.Init -> onInit()
            PokemonDetailsUiEvent.GoBackClick -> onGoBackClick()
        }
    }

    private fun onInit() = screenModelScope.launch {
        getPokemonInfoUseCase(id)
            .onStart {
                _uiState.update { DataState.Loading }
            }
            .catch {
                _uiState.update { DataState.Failure }
            }
            .collect { pokemonInfo ->
                _uiState.update {
                    DataState.Done(PokemonDetailsUiState(pokemonInfo = pokemonInfo))
                }
            }
    }

    private fun onGoBackClick() = screenModelScope.launch {
        _effect.send(PokemonDetailsEffect.GoBack)
    }

    override fun onDispose() {
        super.onDispose()
    }
}