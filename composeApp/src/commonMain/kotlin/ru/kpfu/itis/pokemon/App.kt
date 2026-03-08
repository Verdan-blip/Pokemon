package ru.kpfu.itis.pokemon

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import ru.kpfu.itis.pokemon.presentation.main_page.MainPageScreen

@Composable
@Preview
fun App() {
    MaterialTheme {
        Scaffold { paddings ->
            Navigator(
                screen = MainPageScreen()
            ) { navigator ->
                SlideTransition(
                    modifier = Modifier
                        .padding(paddings),
                    navigator = navigator
                )
            }
        }
    }
}