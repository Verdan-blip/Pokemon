package ru.kpfu.itis.pokemon.presentation.navigation

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import org.jetbrains.compose.resources.painterResource
import pokemon.composeapp.generated.resources.Res
import pokemon.composeapp.generated.resources.ic_home
import ru.kpfu.itis.pokemon.presentation.pokemon_list.PokemonListScreen

object HomeTab : Tab {

    override val options: TabOptions
        @Composable
        get() = TabOptions(
            index = 0u,
            title = "Главная",
            icon = painterResource(Res.drawable.ic_home)
        )

    @Composable
    override fun Content() {
        Navigator(
            screen = PokemonListScreen
        ) {
            CurrentScreen()
        }
    }
}