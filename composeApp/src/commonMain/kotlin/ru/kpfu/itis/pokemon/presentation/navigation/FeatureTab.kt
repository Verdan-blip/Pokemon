package ru.kpfu.itis.pokemon.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import org.jetbrains.compose.resources.painterResource
import pokemon.composeapp.generated.resources.Res
import pokemon.composeapp.generated.resources.ic_favorite
import ru.kpfu.itis.pokemon.presentation.features.FavouritePokemonScreen
import ru.kpfu.itis.pokemon.presentation.features.FavouritePokemonsScene
import ru.kpfu.itis.pokemon.presentation.features.FavouritePokemonsScreenModel

object FeatureTab : Tab {

    override val options: TabOptions
        @Composable
        get() = TabOptions(
            index = 1u,
            title = "Избранное",
            icon = painterResource(Res.drawable.ic_favorite)
        )

    @Composable
    override fun Content() {
        Navigator(
            screen = FavouritePokemonScreen
        ) {
            CurrentScreen()
        }
    }
}