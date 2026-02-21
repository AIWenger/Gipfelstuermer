package com.gipfelstuermer.ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.gipfelstuermer.R
import com.gipfelstuermer.ui.theme.TextPrimary

@Composable
fun GameCountLabel(
    count: Int,
    modifier: Modifier = Modifier
) {
    AnimatedContent(
        targetState = count,
        transitionSpec = { fadeIn() togetherWith fadeOut() },
        label = "game_count",
        modifier = modifier
    ) { targetCount ->
        Text(
            text = stringResource(R.string.games_found, targetCount),
            color = TextPrimary,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
