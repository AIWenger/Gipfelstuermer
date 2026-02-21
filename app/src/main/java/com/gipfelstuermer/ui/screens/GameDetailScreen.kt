package com.gipfelstuermer.ui.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.outlined.FilterDrama
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gipfelstuermer.R
import com.gipfelstuermer.data.local.AgeGroup
import com.gipfelstuermer.data.local.Difficulty
import com.gipfelstuermer.data.local.GameType
import com.gipfelstuermer.ui.theme.AgeGroupGross
import com.gipfelstuermer.ui.theme.AgeGroupKlein
import com.gipfelstuermer.ui.theme.AgeGroupMittel
import com.gipfelstuermer.ui.theme.DifficultyEasy
import com.gipfelstuermer.ui.theme.DifficultyHard
import com.gipfelstuermer.ui.theme.DifficultyMedium
import com.gipfelstuermer.ui.theme.GameTypeAufwaermen
import com.gipfelstuermer.ui.theme.GameTypeKlettern
import com.gipfelstuermer.ui.theme.GameTypeKraft
import com.gipfelstuermer.ui.theme.HeroGradientEnd
import com.gipfelstuermer.ui.theme.HeroGradientStart
import com.gipfelstuermer.ui.theme.TextPrimary
import com.gipfelstuermer.ui.theme.TextSecondary
import com.gipfelstuermer.ui.viewmodel.GameViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameDetailScreen(
    gameId: Int,
    viewModel: GameViewModel,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val game by viewModel.getGameById(gameId).collectAsStateWithLifecycle(initialValue = null)

    val favoriteScale by animateFloatAsState(
        targetValue = if (game?.isFavorite == true) 1.2f else 1f,
        animationSpec = tween(durationMillis = 200),
        label = "favorite_scale"
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back),
                            tint = Color.White
                        )
                    }
                },
                actions = {
                    game?.let { g ->
                        IconButton(onClick = { viewModel.toggleFavorite(g.id, g.isFavorite) }) {
                            Icon(
                                imageVector = if (g.isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                                contentDescription = stringResource(R.string.favorite),
                                tint = if (g.isFavorite) Color.Red else Color.White,
                                modifier = Modifier.scale(favoriteScale)
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        },
        modifier = modifier
    ) { paddingValues ->
        game?.let { g ->
            val name = viewModel.getStringByKey(g.nameKey)
            val description = viewModel.getStringByKey(g.descriptionKey)
            val rules = g.rulesKey?.let { viewModel.getStringByKey(it) }
            val difficultyColor = when (g.difficulty) {
                Difficulty.EASY -> DifficultyEasy
                Difficulty.MEDIUM -> DifficultyMedium
                Difficulty.HARD -> DifficultyHard
            }
            val difficultyLabel = viewModel.getStringByKey(
                when (g.difficulty) {
                    Difficulty.EASY -> "difficulty_easy"
                    Difficulty.MEDIUM -> "difficulty_medium"
                    Difficulty.HARD -> "difficulty_hard"
                }
            )
            val ageGroupColor = when (g.ageGroup) {
                AgeGroup.KLEIN -> AgeGroupKlein
                AgeGroup.MITTEL -> AgeGroupMittel
                AgeGroup.GROSS -> AgeGroupGross
            }
            val ageGroupLabel = viewModel.getStringByKey(
                when (g.ageGroup) {
                    AgeGroup.KLEIN -> "age_klein"
                    AgeGroup.MITTEL -> "age_mittel"
                    AgeGroup.GROSS -> "age_gross"
                }
            )
            val gameTypeColor = when (g.gameType) {
                GameType.KLETTERN -> GameTypeKlettern
                GameType.AUFWAERMEN -> GameTypeAufwaermen
                GameType.KRAFT -> GameTypeKraft
            }
            val gameTypeLabel = viewModel.getStringByKey(
                when (g.gameType) {
                    GameType.KLETTERN -> "type_klettern"
                    GameType.AUFWAERMEN -> "type_aufwaermen"
                    GameType.KRAFT -> "type_kraft"
                }
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                // Header
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(HeroGradientStart, HeroGradientEnd)
                            )
                        )
                        .padding(paddingValues)
                        .padding(horizontal = 20.dp),
                    contentAlignment = Alignment.BottomStart
                ) {
                    Column(modifier = Modifier.padding(bottom = 20.dp)) {
                        Text(
                            text = name,
                            color = Color.White,
                            fontSize = 26.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                // Content
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {
                    // Badges Zeile 1: Schwierigkeit + Ort
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Card(
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = difficultyColor)
                        ) {
                            Text(
                                text = difficultyLabel,
                                color = Color.White,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        if (g.locationIndoor) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Outlined.Home,
                                    contentDescription = null,
                                    tint = TextSecondary,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = stringResource(R.string.location_indoor),
                                    color = TextSecondary,
                                    fontSize = 14.sp
                                )
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                        }
                        if (g.locationOutdoor) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Outlined.FilterDrama,
                                    contentDescription = null,
                                    tint = TextSecondary,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = stringResource(R.string.location_outdoor),
                                    color = TextSecondary,
                                    fontSize = 14.sp
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Badges Zeile 2: Altersgruppe + Spieltyp
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Card(
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = ageGroupColor.copy(alpha = 0.15f))
                        ) {
                            Text(
                                text = ageGroupLabel,
                                color = ageGroupColor,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Card(
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = gameTypeColor.copy(alpha = 0.15f))
                        ) {
                            Text(
                                text = gameTypeLabel,
                                color = gameTypeColor,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                            )
                        }
                    }

                    // Dauer
                    if (g.durationMinutes > 0) {
                        Spacer(modifier = Modifier.height(12.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Outlined.Timer,
                                contentDescription = null,
                                tint = TextSecondary,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = stringResource(R.string.duration_label, g.durationMinutes),
                                color = TextSecondary,
                                fontSize = 14.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Description
                    Text(
                        text = stringResource(R.string.description),
                        color = TextPrimary,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = description,
                        color = TextPrimary,
                        fontSize = 16.sp,
                        lineHeight = 24.sp
                    )

                    // Materialien
                    val materials = g.materialsKey?.let { viewModel.getStringByKey(it) }
                    if (materials != null) {
                        Spacer(modifier = Modifier.height(24.dp))
                        Text(
                            text = stringResource(R.string.materials),
                            color = TextPrimary,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Card(
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color(0xFFF5F5F5)
                            ),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = materials,
                                color = TextPrimary,
                                fontSize = 15.sp,
                                lineHeight = 24.sp,
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    }

                    // Rules
                    if (rules != null) {
                        Spacer(modifier = Modifier.height(24.dp))
                        Text(
                            text = stringResource(R.string.rules),
                            color = TextPrimary,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Card(
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color(0xFFF5F5F5)
                            ),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = rules,
                                color = TextPrimary,
                                fontSize = 15.sp,
                                lineHeight = 24.sp,
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(32.dp))
                }
            }
        }
    }
}
