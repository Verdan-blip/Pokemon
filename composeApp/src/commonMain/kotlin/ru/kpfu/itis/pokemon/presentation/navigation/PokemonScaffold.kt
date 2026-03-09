package ru.kpfu.itis.pokemon.presentation.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ShortNavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import org.jetbrains.compose.resources.painterResource
import pokemon.composeapp.generated.resources.Res
import ru.kpfu.itis.pokemon.presentation.main_page.PokemonListScreen

@Composable
internal fun PokemonScaffold() {
    TabNavigator(tab = HomeTab) {
        Scaffold(
            content = { paddingValues ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    CurrentTab()
                }
            },
            bottomBar = {
                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.surface
                ) {
                    val tabs = listOf(HomeTab, FeatureTab, RandomPokemonTab)
                    val tabNavigator = LocalTabNavigator.current

                    tabs.forEach { tab ->
                        val isSelected = tabNavigator.current.key == tab.key

                        NavigationBarItem(
                            selected = isSelected,
                            onClick = { tabNavigator.current = tab },
                            icon = {
                                tab.options.icon?.let {
                                    Icon(
                                        painter = it,
                                        contentDescription = tab.options.title,
                                        modifier = Modifier.size(if (isSelected) 26.dp else 24.dp)
                                    )
                                }
                            },
                            label = {
                                Text(
                                    text = tab.options.title,
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                )
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = MaterialTheme.colorScheme.primary,
                                selectedTextColor = MaterialTheme.colorScheme.primary,
                                indicatorColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f),
                                unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        )
                    }
                }
            }
        )
    }
}

@Composable
private fun TabNavigationItem(
    screen: Tab,
    selected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    ShortNavigationBarItem(
        selected = selected,
        onClick = onClick,
        icon = {
            screen.options.icon?.let {
                Icon(
                    painter = it,
                    contentDescription = null
                )
            }
        },
        label = {
            Text(
                text = screen.options.title,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        modifier = modifier
    )
}
