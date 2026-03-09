package ru.kpfu.itis.pokemon.presentation.pokemon_details

import PokemonDetailsScene
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import org.koin.core.parameter.parametersOf

class PokemonDetailsScreen(
    private val id: Int
) : Screen {

    @Composable
    override fun Content() {
        val screenModel = koinScreenModel<PokemonDetailsScreenModel>(
            parameters = { parametersOf(id) }
        )

        val uiState by screenModel.uiState.collectAsStateWithLifecycle()
        val effect by screenModel.effect.collectAsStateWithLifecycle(null)

        PokemonDetailsScene(
            uiState = uiState,
            effect = effect,
            onUiEvent = screenModel::onUiEvent
        )
    }
}