package com.gipfelstuermer.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gipfelstuermer.data.local.Difficulty
import com.gipfelstuermer.data.local.GameType
import com.gipfelstuermer.ui.components.CategoryTabs
import com.gipfelstuermer.ui.components.GameCard
import com.gipfelstuermer.ui.components.GameCountLabel
import com.gipfelstuermer.ui.components.HeroCard
import com.gipfelstuermer.ui.components.RandomGameButton
import com.gipfelstuermer.ui.components.TimerWidget
import com.gipfelstuermer.ui.viewmodel.GameViewModel

@Composable
fun HomeScreen(
    viewModel: GameViewModel,
    onGameClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val filteredGames by viewModel.filteredGames.collectAsStateWithLifecycle()
    val selectedFilter by viewModel.selectedFilter.collectAsStateWithLifecycle()
    val ageFilter by viewModel.ageFilter.collectAsStateWithLifecycle()
    val typeFilter by viewModel.typeFilter.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val timerMillis by viewModel.timerMillis.collectAsStateWithLifecycle()
    val timerRunning by viewModel.timerRunning.collectAsStateWithLifecycle()

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            HeroCard(
                searchQuery = searchQuery,
                onSearchQueryChange = { viewModel.setSearchQuery(it) }
            )
        }

        item {
            TimerWidget(
                timerMillis = timerMillis,
                isRunning = timerRunning,
                onToggle = { viewModel.toggleTimer() },
                onReset = { viewModel.resetTimer() }
            )
        }

        item {
            CategoryTabs(
                selectedFilter = selectedFilter,
                onFilterSelected = { viewModel.setFilter(it) },
                ageFilter = ageFilter,
                onAgeFilterSelected = { viewModel.setAgeFilter(it) },
                typeFilter = typeFilter,
                onTypeFilterSelected = { viewModel.setTypeFilter(it) }
            )
        }

        item {
            RandomGameButton(
                onClick = {
                    if (filteredGames.isNotEmpty()) {
                        val randomGame = filteredGames.random()
                        onGameClick(randomGame.id)
                    }
                }
            )
        }

        item {
            GameCountLabel(count = filteredGames.size)
        }

        items(
            items = filteredGames,
            key = { it.id }
        ) { game ->
            AnimatedVisibility(
                visible = true,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                GameCard(
                    name = viewModel.getStringByKey(game.nameKey),
                    description = viewModel.getStringByKey(game.descriptionKey),
                    difficulty = game.difficulty,
                    difficultyLabel = viewModel.getStringByKey(
                        when (game.difficulty) {
                            Difficulty.EASY -> "difficulty_easy"
                            Difficulty.MEDIUM -> "difficulty_medium"
                            Difficulty.HARD -> "difficulty_hard"
                        }
                    ),
                    gameType = game.gameType,
                    gameTypeLabel = viewModel.getStringByKey(
                        when (game.gameType) {
                            GameType.KLETTERN -> "type_klettern"
                            GameType.AUFWAERMEN -> "type_aufwaermen"
                            GameType.KRAFT -> "type_kraft"
                        }
                    ),
                    locationIndoor = game.locationIndoor,
                    locationOutdoor = game.locationOutdoor,
                    onClick = { onGameClick(game.id) }
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
