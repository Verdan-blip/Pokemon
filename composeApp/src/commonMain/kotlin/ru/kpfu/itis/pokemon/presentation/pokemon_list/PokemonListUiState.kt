package ru.kpfu.itis.pokemon.presentation.pokemon_list

import androidx.compose.foundation.text.input.TextFieldState
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import ru.kpfu.itis.pokemon.domain.entity.PokemonInfo

data class PokemonListUiState(
    val searchTextState: TextFieldState = TextFieldState(),
    val pokemons: Flow<PagingData<PokemonInfo>> = flowOf(PagingData.empty())
)