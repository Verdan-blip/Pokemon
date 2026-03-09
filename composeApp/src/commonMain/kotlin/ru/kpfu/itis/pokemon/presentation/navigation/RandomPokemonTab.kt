package ru.kpfu.itis.pokemon.presentation.navigation

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import org.jetbrains.compose.resources.painterResource
import pokemon.composeapp.generated.resources.Res
import pokemon.composeapp.generated.resources.ic_shuffle

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

    }
}