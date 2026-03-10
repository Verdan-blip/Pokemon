package ru.kpfu.itis.pokemon.presentation.features

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel

object FavouritePokemonScreen : Screen {

    @Composable
    override fun Content() {
        val screenModel = koinScreenModel<FavouritePokemonsScreenModel>()

        val uiState by screenModel.uiState.collectAsStateWithLifecycle()
        val effect by screenModel.effect.collectAsStateWithLifecycle(null)

        FavouritePokemonsScene(
            uiState = uiState,
            effect = effect,
            onUiEvent = screenModel::onUiEvent
        )
    }
}