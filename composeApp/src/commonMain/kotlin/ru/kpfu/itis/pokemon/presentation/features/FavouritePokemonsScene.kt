package ru.kpfu.itis.pokemon.presentation.features

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import ru.kpfu.itis.pokemon.presentation.components.PokemonRemoveableCard
import ru.kpfu.itis.pokemon.presentation.entity.DataState
import ru.kpfu.itis.pokemon.presentation.pokemon_details.PokemonDetailsScreen
import ru.kpfu.itis.pokemon.presentation.uikit.KitErrorView
import ru.kpfu.itis.pokemon.presentation.uikit.KitLoadingView
import ru.kpfu.itis.pokemon.presentation.uikit.KitTopBar

@Composable
fun FavouritePokemonsScene(
    uiState: DataState<FavouritePokemonsUiState>,
    effect: FavouritePokemonsEffect?,
    onUiEvent: (FavouritePokemonsUiEvent) -> Unit
) {
    val navigator = LocalNavigator.current
    LaunchedEffect(effect) {
        when (effect) {
            is FavouritePokemonsEffect.OpenPokemonDetails -> {
                navigator?.push(PokemonDetailsScreen(effect.id))
            }

            null -> Unit
        }
    }

    when (uiState) {
        is DataState.Done -> {
            FavouritePokemonsSceneContent(
                uiState = uiState.data,
                onUiEvent = onUiEvent
            )
        }

        DataState.Failure -> {
            KitErrorView()
        }

        DataState.Loading -> {
            KitLoadingView()
        }
    }
}

@Composable
private fun FavouritePokemonsSceneContent(
    uiState: FavouritePokemonsUiState,
    onUiEvent: (FavouritePokemonsUiEvent) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp)
    ) {
        KitTopBar(
            title = "Избранные покемоны"
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            items(
                items = uiState.pokemons,
                key = { it.id }
            ) { pokemon ->
                PokemonRemoveableCard(
                    modifier = Modifier
                        .animateItem(),
                    pokemon = pokemon,
                    onClick = {
                        onUiEvent(FavouritePokemonsUiEvent.Click(pokemon))
                    },
                    onRemoveClick = {
                        onUiEvent(FavouritePokemonsUiEvent.Remove(pokemon))
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Preview
@Composable
private fun FavouritePokemonsScenePreview() = MaterialTheme {
    FavouritePokemonsScene(
        uiState = DataState.Done(FavouritePokemonsUiState(pokemons = listOf())),
        effect = null,
        onUiEvent = { }
    )
}