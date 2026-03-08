package ru.kpfu.itis.pokemon.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage
import org.jetbrains.compose.resources.painterResource
import pokemon.composeapp.generated.resources.Res
import pokemon.composeapp.generated.resources.img_failure
import ru.kpfu.itis.pokemon.domain.entity.PokemonInfo
import ru.kpfu.itis.pokemon.presentation.utils.shimmer

@Composable
internal fun PokemonCard(
    pokemon: PokemonInfo,
    onClick: () -> Unit
) = Row(
    modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(16.dp))
        .background(Color.White)
        .clickable(onClick = onClick)
        .padding(8.dp)
) {
    PokemonCardSuccessContent(
        pokemonInfo = pokemon
    )
}

@Composable
private fun PokemonCardSuccessContent(
    pokemonInfo: PokemonInfo
) {
    SubcomposeAsyncImage(
        modifier = Modifier
            .size(64.dp)
            .clip(RoundedCornerShape(8.dp)),
        model = pokemonInfo.sprites.frontUrl,
        contentDescription = null,
        loading = {
            Box {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(16.dp)
                        .align(Alignment.Center)
                )
            }
        },
        error = {
            Image(
                painter = painterResource(Res.drawable.img_failure),
                contentDescription = null
            )
        }
    )

    Spacer(modifier = Modifier.width(8.dp))

    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = "Name: ${pokemonInfo.name}",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface
        )

        Text(
            text = "Width: ${pokemonInfo.weight}",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface
        )

        Text(
            text = "Height: ${pokemonInfo.height}",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
private fun PokemonCardFailureContent() {
    Box(
        modifier = Modifier
            .size(48.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Не удалось загрузить данные",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
private fun PokemonCardLoadingContent() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .size(64.dp)
                .shimmer()
        )

        Spacer(modifier = Modifier.width(8.dp))

        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Box(
                modifier = Modifier
                    .width(64.dp)
                    .height(14.dp)
                    .shimmer()
            )

            Box(
                modifier = Modifier
                    .width(64.dp)
                    .height(14.dp)
                    .shimmer()
            )

            Box(
                modifier = Modifier
                    .width(64.dp)
                    .height(14.dp)
                    .shimmer()
            )
        }
    }
}

@Preview
@Composable
private fun PokemonCardPreview() = MaterialTheme {
    PokemonCard(
        pokemon = PokemonInfo.mock(),
        onClick = { }
    )
}