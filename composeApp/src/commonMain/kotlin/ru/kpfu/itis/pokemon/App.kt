package ru.kpfu.itis.pokemon

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import ru.kpfu.itis.pokemon.presentation.navigation.PokemonScaffold

@Composable
@Preview
fun App() {
    MaterialTheme {
        PokemonScaffold()
    }
}