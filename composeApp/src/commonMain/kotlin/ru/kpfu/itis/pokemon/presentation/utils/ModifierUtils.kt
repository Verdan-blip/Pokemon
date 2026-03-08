package ru.kpfu.itis.pokemon.presentation.utils

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp

private val colors = listOf(
    Color.LightGray.copy(alpha = 0.2f),
    Color.LightGray.copy(alpha = 1.0f),
    Color.LightGray.copy(alpha = 0.2f),
)

@Composable
fun Modifier.shimmer(
    durationMillis: Int = 1000,
): Modifier {
    val transition = rememberInfiniteTransition(label = "")

    val translateAnimation by transition.animateFloat(
        initialValue = 0f,
        targetValue = 500f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = durationMillis,
                easing = LinearEasing,
            ),
            repeatMode = RepeatMode.Restart,
        ),
        label = "Shimmer animation",
    )

    val radius = with(LocalDensity.current) { 2.dp.toPx() }

    return drawBehind {
        drawRoundRect(
            cornerRadius = CornerRadius(radius),
            brush = Brush.linearGradient(
                colors = colors,
                start = Offset(x = translateAnimation, y = translateAnimation),
                end = Offset(x = translateAnimation + 100f, y = translateAnimation + 100f),
            )
        )
    }
}

inline fun Modifier.thenIf(
    condition: Boolean,
    action: Modifier.() -> Modifier
) = if (condition) then(action()) else this