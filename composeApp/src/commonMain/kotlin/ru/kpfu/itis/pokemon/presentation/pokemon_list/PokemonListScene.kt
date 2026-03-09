package ru.kpfu.itis.pokemon.presentation.pokemon_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import cafe.adriel.voyager.navigator.LocalNavigator
import ru.kpfu.itis.pokemon.domain.entity.PokemonInfo
import ru.kpfu.itis.pokemon.presentation.components.PokemonCard
import ru.kpfu.itis.pokemon.presentation.pokemon_details.PokemonDetailsScreen
import ru.kpfu.itis.pokemon.presentation.pokemon_details.PokemonDetailsUiEvent
import ru.kpfu.itis.pokemon.presentation.uikit.KitErrorView
import ru.kpfu.itis.pokemon.presentation.uikit.KitLoadingView
import ru.kpfu.itis.pokemon.presentation.uikit.KitTopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun PokemonListScene(
    uiState: PokemonListUiState,
    onUiEvent: (PokemonListUiEvent) -> Unit,
    effect: PokemonListEffect?,
) {
    val navigator = LocalNavigator.current
    LaunchedEffect(effect) {
        when (effect) {
            is PokemonListEffect.OpenPokemonDetails -> {
                navigator?.push(PokemonDetailsScreen(id = effect.id))
            }

            else -> Unit
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp)
    ) {
        val pokemons = uiState
            .pokemons
            .collectAsLazyPagingItems()

        when (pokemons.loadState.refresh) {
            is LoadState.Error -> {
                KitErrorView()
            }

            LoadState.Loading -> {
                KitLoadingView()
            }

            is LoadState.NotLoading -> {
                MainPageSceneContent(
                    items = pokemons,
                    onUiEvent = onUiEvent
                )
            }
        }
    }
}

@Composable
private fun MainPageSceneContent(
    items: LazyPagingItems<PokemonInfo>,
    onUiEvent: (PokemonListUiEvent) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        KitTopBar(
            title = "Покемоны"
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            items(
                items.itemCount
            ) { index ->
                items[index]?.also { pokemon ->
                    PokemonCard(
                        pokemon = pokemon,
                        onClick = {
                            onUiEvent(PokemonListUiEvent.PokemonClick(pokemon.id))
                        },
                        onAddToFavourites = {
                            onUiEvent(PokemonListUiEvent.AddToFavouritesClick(pokemon))
                        },
                        onRemoveFromFavourites = {
                            onUiEvent(PokemonListUiEvent.AddToFavouritesClick(pokemon))
                        }
                    )

                    Spacer(
                        modifier = Modifier.height(12.dp)
                    )
                }
            }

            if (items.loadState.append is LoadState.Loading) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .size(16.dp)
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun PokemonListScenePreview() {
    PokemonListScene(
        uiState = PokemonListUiState(),
        onUiEvent = { },
        effect = null
    )
}

