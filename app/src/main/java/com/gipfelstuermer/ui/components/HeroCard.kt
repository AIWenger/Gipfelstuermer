package com.gipfelstuermer.ui.components

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Landscape
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gipfelstuermer.R
import com.gipfelstuermer.ui.theme.HeroGradientEnd
import com.gipfelstuermer.ui.theme.HeroGradientStart
import com.gipfelstuermer.ui.theme.TextOnPrimary
import com.gipfelstuermer.util.LanguageManager

@Composable
fun HeroCard(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    currentLanguage: String,
    onLanguageToggle: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(HeroGradientStart, HeroGradientEnd)
                )
            )
            .padding(24.dp)
    ) {
        Column(
            modifier = Modifier.align(Alignment.TopEnd),
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color.White.copy(alpha = 0.2f))
                    .clickable { onLanguageToggle() }
                    .padding(horizontal = 10.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = "DE",
                    color = if (currentLanguage == LanguageManager.GERMAN) Color.White else Color.White.copy(alpha = 0.45f),
                    fontSize = 11.sp,
                    fontWeight = if (currentLanguage == LanguageManager.GERMAN) FontWeight.Bold else FontWeight.Normal
                )
                Text(
                    text = "|",
                    color = Color.White.copy(alpha = 0.4f),
                    fontSize = 11.sp
                )
                Text(
                    text = "EN",
                    color = if (currentLanguage == LanguageManager.ENGLISH) Color.White else Color.White.copy(alpha = 0.45f),
                    fontSize = 11.sp,
                    fontWeight = if (currentLanguage == LanguageManager.ENGLISH) FontWeight.Bold else FontWeight.Normal
                )
            }
            Icon(
                imageVector = Icons.Outlined.Landscape,
                contentDescription = null,
                tint = TextOnPrimary.copy(alpha = 0.4f),
                modifier = Modifier.size(64.dp)
            )
        }

        Column {
            Text(
                text = stringResource(R.string.app_name),
                color = TextOnPrimary,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = stringResource(R.string.app_subtitle),
                color = TextOnPrimary.copy(alpha = 0.7f),
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                value = searchQuery,
                onValueChange = onSearchQueryChange,
                placeholder = {
                    Text(
                        text = stringResource(R.string.search_placeholder),
                        color = Color.White.copy(alpha = 0.5f),
                        fontSize = 14.sp
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.Search,
                        contentDescription = null,
                        tint = Color.White.copy(alpha = 0.5f)
                    )
                },
                singleLine = true,
                shape = RoundedCornerShape(28.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    cursorColor = Color.White,
                    focusedBorderColor = Color.White.copy(alpha = 0.3f),
                    unfocusedBorderColor = Color.White.copy(alpha = 0.2f),
                    focusedContainerColor = Color.White.copy(alpha = 0.15f),
                    unfocusedContainerColor = Color.White.copy(alpha = 0.1f)
                ),
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
