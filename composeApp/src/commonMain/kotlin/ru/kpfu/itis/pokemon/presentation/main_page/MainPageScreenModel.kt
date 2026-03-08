package ru.kpfu.itis.pokemon.presentation.main_page

import androidx.paging.cachedIn
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.kpfu.itis.pokemon.domain.usecase.GetPokemonListUseCase

class MainPageScreenModel(
    private val getPokemonListUseCase: GetPokemonListUseCase
) : ScreenModel {
    private val _uiState = MutableStateFlow(MainPageUiState())
    val uiState = _uiState.asStateFlow()

    private val _effect = Channel<MainPageEffect>()
    val effect = _effect.receiveAsFlow()

    fun onUiEvent(uiEvent: MainPageUiEvent) {
        when (uiEvent) {
            is MainPageUiEvent.Init -> onInit()
            is MainPageUiEvent.SearchTextChange -> onSearchTextChange()
            is MainPageUiEvent.PokemonClick -> onPokemonClick(id = uiEvent.id)
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

    private fun onSearchTextChange() {

    }

    private fun onPokemonClick(id: Int) = screenModelScope.launch {
        _effect.send(MainPageEffect.OpenPokemonDetails(id = id))
    }

    override fun onDispose() {
        super.onDispose()
    }
}