import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import coil3.compose.SubcomposeAsyncImage
import org.jetbrains.compose.resources.painterResource
import pokemon.composeapp.generated.resources.Res
import pokemon.composeapp.generated.resources.ic_back
import pokemon.composeapp.generated.resources.img_failure
import ru.kpfu.itis.pokemon.domain.entity.PokemonInfo
import ru.kpfu.itis.pokemon.domain.entity.PokemonStat
import ru.kpfu.itis.pokemon.presentation.entity.DataState
import ru.kpfu.itis.pokemon.presentation.pokemon_details.PokemonDetailsEffect
import ru.kpfu.itis.pokemon.presentation.pokemon_details.PokemonDetailsUiEvent
import ru.kpfu.itis.pokemon.presentation.pokemon_details.PokemonDetailsUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun PokemonDetailsScene(
    uiState: DataState<PokemonDetailsUiState>,
    effect: PokemonDetailsEffect?,
    onUiEvent: (PokemonDetailsUiEvent) -> Unit
) {
    val navigator = LocalNavigator.current
    LaunchedEffect(effect) {
        when (effect) {
            PokemonDetailsEffect.GoBack -> navigator?.pop()
            null -> Unit
        }
    }

    Column {
        TopAppBar(
            title = {
                Text(
                    text = "Информация о покемоне",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
            },
            navigationIcon = {
                IconButton(
                    onClick = {
                        onUiEvent(PokemonDetailsUiEvent.GoBackClick)
                    }
                ) {
                    Icon(
                        painter = painterResource(Res.drawable.ic_back),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.surface,
                scrolledContainerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        )

        when (uiState) {
            is DataState.Done -> {
                PokemonDetailsSceneContent(
                    uiState = uiState.data
                )
            }

            DataState.Failure -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Не удалось загрузить страницу, попробуйте позже",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            DataState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(32.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun PokemonDetailsSceneContent(
    uiState: PokemonDetailsUiState
) {
    val pokemon = uiState.pokemonInfo

    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surface)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "#${pokemon.id.toString().padStart(3, '0')}",
            style = MaterialTheme.typography.labelLarge,
            color = Color.Gray
        )

        Text(
            text = pokemon.name.replaceFirstChar { it.uppercase() },
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        SubcomposeAsyncImage(
            modifier = Modifier
                .size(200.dp)
                .clip(RoundedCornerShape(24.dp)),
            contentScale = ContentScale.Crop,
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

        Spacer(modifier = Modifier.height(4.dp))


        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            pokemon.types.forEach { type ->
                SuggestionChip(
                    onClick = { },
                    label = { Text(type) }
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            InfoColumn(label = "Weight", value = "${pokemon.weight / 10.0} kg")
            InfoColumn(label = "Height", value = "${pokemon.height / 10.0} m")
        }

        Spacer(modifier = Modifier.height(24.dp))

        PokemonAbilitiesSection(abilities = pokemon.abilities)

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "Base Stats",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(4.dp))

        pokemon.stats.forEach { stat ->
            StatRow(stat)
        }

        Spacer(modifier = Modifier.height(4.dp))

        PokemonMovesSection(moves = pokemon.moves)
    }
}

@Composable
private fun PokemonAbilitiesSection(abilities: List<String>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
    ) {
        Text(
            text = "Abilities",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            abilities.forEach { ability ->
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(12.dp))
                        .background(MaterialTheme.colorScheme.primaryContainer)
                        .padding(12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = ability.replace("-", " ").replaceFirstChar { it.uppercase() },
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Composable
private fun StatRow(stat: PokemonStat) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stat.name.uppercase(),
            modifier = Modifier.width(80.dp),
            style = MaterialTheme.typography.labelMedium,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.width(8.dp))

        val progress = stat.value / 255f

        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier
                .weight(1f)
                .height(8.dp)
                .clip(CircleShape),
            color = if (progress > 0.5f) Color.Green else Color.Red,
            trackColor = Color.LightGray
        )

        Text(
            text = stat.value.toString(),
            modifier = Modifier.width(40.dp),
            textAlign = TextAlign.End,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
private fun InfoColumn(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray
        )
    }
}

@Composable
private fun PokemonMovesSection(moves: List<String>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
    ) {
        Text(
            text = "Moves",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            moves.forEach { move ->
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    tonalElevation = 2.dp
                ) {
                    Text(
                        text = move.replace("-", " "),
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun PokemonDetailsScenePreview() = MaterialTheme {
    PokemonDetailsSceneContent(
        uiState = PokemonDetailsUiState(
            pokemonInfo = PokemonInfo
                .mock()
                .copy(
                    stats = List(size = 6) { PokemonStat.mock() }
                )
        )
    )
}