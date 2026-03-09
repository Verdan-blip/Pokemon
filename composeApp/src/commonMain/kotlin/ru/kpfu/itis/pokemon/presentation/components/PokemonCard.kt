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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage
import org.jetbrains.compose.resources.painterResource
import pokemon.composeapp.generated.resources.Res
import pokemon.composeapp.generated.resources.ic_heart
import pokemon.composeapp.generated.resources.ic_heart_checked
import pokemon.composeapp.generated.resources.img_failure
import ru.kpfu.itis.pokemon.domain.entity.PokemonInfo

@Composable
internal fun PokemonCard(
    pokemon: PokemonInfo,
    onClick: () -> Unit,
    onAddToFavourites: () -> Unit,
    onRemoveFromFavourites: () -> Unit
) = Row(
    modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(16.dp))
        .background(Color.White)
        .clickable(onClick = onClick)
        .padding(8.dp)
) {
    val isAddedToFavourites = remember { mutableStateOf(pokemon.isFavourite) }

    SubcomposeAsyncImage(
        modifier = Modifier
            .size(64.dp)
            .clip(RoundedCornerShape(8.dp)),
        model = pokemon.sprites.frontUrl,
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
            text = "Name: ${pokemon.name}",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface
        )

        Text(
            text = "Width: ${pokemon.weight}",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface
        )

        Text(
            text = "Height: ${pokemon.height}",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }

    Spacer(modifier = Modifier.weight(1f))

    IconButton(
        onClick = {
            if (isAddedToFavourites.value) {
                onRemoveFromFavourites()
                isAddedToFavourites.value = false
            } else {
                onAddToFavourites()
                isAddedToFavourites.value = true
            }
        },
        modifier = Modifier
            .size(48.dp)
            .background(
                color = if (isAddedToFavourites.value) {
                    MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                } else {
                    Color.Transparent
                },
                shape = CircleShape
            )
    ) {
        Icon(
            painter = if (isAddedToFavourites.value) {
                painterResource(Res.drawable.ic_heart_checked)
            } else {
                painterResource(Res.drawable.ic_heart)
            },
            contentDescription = if (isAddedToFavourites.value) {
                "Удалить из избранного"
            } else {
                "Добавить в избранное"
            },
            tint = if (isAddedToFavourites.value) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            },
            modifier = Modifier.size(24.dp)
        )
    }
}

@Preview
@Composable
private fun PokemonCardPreview() = MaterialTheme {
    PokemonCard(
        pokemon = PokemonInfo.mock(),
        onClick = { },
        onAddToFavourites = { },
        onRemoveFromFavourites = { }
    )
}