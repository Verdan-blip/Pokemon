package ru.kpfu.itis.pokemon.presentation.uikit

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage
import org.jetbrains.compose.resources.painterResource
import pokemon.composeapp.generated.resources.Res
import pokemon.composeapp.generated.resources.img_failure

@Composable
fun KitImage(
    model: Any?,
    size: DpSize,
    indicatorSize: DpSize = DpSize(16.dp, 16.dp)
) {
    SubcomposeAsyncImage(
        modifier = Modifier
            .size(size)
            .clip(RoundedCornerShape(8.dp)),
        model = model,
        contentDescription = null,
        loading = {
            Box {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(indicatorSize)
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
}