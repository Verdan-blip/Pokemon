package ru.kpfu.itis.pokemon.presentation.pokemon_list

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel

object PokemonListScreen : Screen {

    @Composable
    override fun Content() {
        val screenModel = koinScreenModel<PokemonListScreenModel>()
        val uiState by screenModel.uiState.collectAsStateWithLifecycle()
        val effect by screenModel.effect.collectAsStateWithLifecycle(null)

        PokemonListScene(
            uiState = uiState,
            onUiEvent = screenModel::onUiEvent,
            effect = effect
        )
    }
}