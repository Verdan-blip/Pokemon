package ru.kpfu.itis.pokemon.presentation.random

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.kpfu.itis.pokemon.domain.entity.PokemonInfo
import ru.kpfu.itis.pokemon.presentation.entity.DataState
import ru.kpfu.itis.pokemon.presentation.uikit.KitErrorView
import ru.kpfu.itis.pokemon.presentation.uikit.KitImage
import ru.kpfu.itis.pokemon.presentation.uikit.KitLoadingView
import ru.kpfu.itis.pokemon.presentation.uikit.KitTopBar

@Composable
internal fun RandomPokemonScene(
    uiState: DataState<RandomPokemonUiState>,
    onUiEvent: (RandomPokemonUiEvent) -> Unit
) {
    when (uiState) {
        is DataState.Done -> {
            RandomPokemonSceneContent(
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
private fun RandomPokemonSceneContent(
    uiState: RandomPokemonUiState,
    onUiEvent: (RandomPokemonUiEvent) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        KitTopBar(
            title = "Случайный покемон",
            modifier = Modifier
                .align(Alignment.TopStart)
        )

        Column(
            modifier = Modifier
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(
                modifier = Modifier
                    .size(250.dp)
                    .border(
                        width = 6.dp,
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .background(
                        color = MaterialTheme.colorScheme.primaryContainer,
                        shape = RoundedCornerShape(16.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                AnimatedContent(
                    targetState = uiState.isSpinning,
                    transitionSpec = {
                        fadeIn(animationSpec = tween(500)) togetherWith fadeOut(
                            animationSpec = tween(
                                500
                            )
                        )
                    },
                    label = "SlotAnimation"
                ) { spinning ->
                    if (spinning) {
                        CircularProgressIndicator(modifier = Modifier.size(32.dp))
                    } else if (uiState.currentPokemon != null) {
                        PokemonResultCard(uiState.currentPokemon)
                    } else {
                        Text("?", fontSize = 64.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.height(48.dp))

            Button(
                onClick = {
                    if (uiState.isSpinning.not()) {
                        onUiEvent(RandomPokemonUiEvent.SpinClick)
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                enabled = uiState.isSpinning.not(),
                shape = CircleShape
            ) {
                Text(
                    text = if (uiState.isSpinning) {
                        "Крутим..."
                    } else {
                        "Крутить"
                    },
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }

}

@Composable
internal fun PokemonResultCard(pokemon: PokemonInfo) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.padding(16.dp)
    ) {
        KitImage(
            model = pokemon.sprites.frontUrl,
            size = DpSize(100.dp, 100.dp),
            indicatorSize = DpSize(32.dp, 32.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = pokemon.name.uppercase(),
            color = MaterialTheme.colorScheme.onSecondary,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            pokemon.types.forEach { type ->
                Text(
                    text = type,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onSecondary,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .background(
                            color = MaterialTheme.colorScheme.secondary,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }
        }
    }
}

@Preview
@Composable
private fun RandomPokemonScreenPreview() = MaterialTheme {
    RandomPokemonScene(
        uiState = DataState.Done(
            RandomPokemonUiState(
                pokemons = listOf(),
                currentPokemon = null,
                isSpinning = false
            )
        ),
        onUiEvent = { }
    )
}