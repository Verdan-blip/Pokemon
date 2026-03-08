package ru.kpfu.itis.pokemon.presentation.main_page

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel

class MainPageScreen : Screen {
    @Composable
    override fun Content() {
        val screenModel = koinScreenModel<MainPageScreenModel>()
        val uiState by screenModel.uiState.collectAsStateWithLifecycle()
        val effect by screenModel.effect.collectAsStateWithLifecycle(null)

        MainPageScene(
            uiState = uiState,
            onUiEvent = screenModel::onUiEvent,
            effect = effect
        )
    }
}