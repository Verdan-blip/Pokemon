package ru.kpfu.itis.pokemon.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import org.jetbrains.compose.resources.painterResource
import pokemon.composeapp.generated.resources.Res
import pokemon.composeapp.generated.resources.ic_shuffle
import ru.kpfu.itis.pokemon.domain.entity.PokemonInfo
import ru.kpfu.itis.pokemon.presentation.random.RandomPokemonScene
import ru.kpfu.itis.pokemon.presentation.random.RandomPokemonScreenModel

object RandomPokemonTab : Tab {

    override val options: TabOptions
        @Composable
        get() = TabOptions(
            index = 2u,
            title = "Случайный",
            icon = painterResource(Res.drawable.ic_shuffle)
        )

    @Composable
    override fun Content() {
        val screenModel = koinScreenModel<RandomPokemonScreenModel>()

        val uiState by screenModel.uiState.collectAsStateWithLifecycle()

        RandomPokemonScene(
            uiState = uiState,
            onUiEvent = screenModel::onUiEvent
        )
    }
}