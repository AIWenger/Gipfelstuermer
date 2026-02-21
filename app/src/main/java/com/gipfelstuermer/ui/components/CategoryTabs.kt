package com.gipfelstuermer.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FilterDrama
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gipfelstuermer.R
import com.gipfelstuermer.ui.theme.AgeGroupGross
import com.gipfelstuermer.ui.theme.AgeGroupKlein
import com.gipfelstuermer.ui.theme.AgeGroupMittel
import com.gipfelstuermer.ui.theme.GameTypeAufwaermen
import com.gipfelstuermer.ui.theme.GameTypeKlettern
import com.gipfelstuermer.ui.theme.GameTypeKraft
import com.gipfelstuermer.ui.theme.TabActiveDark
import com.gipfelstuermer.ui.theme.TabActiveGreen
import com.gipfelstuermer.ui.theme.TextPrimary
import com.gipfelstuermer.ui.viewmodel.AgeFilter
import com.gipfelstuermer.ui.viewmodel.LocationFilter
import com.gipfelstuermer.ui.viewmodel.TypeFilter

@Composable
fun CategoryTabs(
    selectedFilter: LocationFilter,
    onFilterSelected: (LocationFilter) -> Unit,
    ageFilter: AgeFilter,
    onAgeFilterSelected: (AgeFilter) -> Unit,
    typeFilter: TypeFilter,
    onTypeFilterSelected: (TypeFilter) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        // Zeile 1: Ort-Filter
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FilterChip(
                label = stringResource(R.string.tab_all),
                icon = null,
                isSelected = selectedFilter == LocationFilter.ALL,
                activeColor = TabActiveDark,
                onClick = { onFilterSelected(LocationFilter.ALL) },
                modifier = Modifier.weight(1f)
            )
            FilterChip(
                label = stringResource(R.string.tab_indoor),
                icon = Icons.Outlined.Home,
                isSelected = selectedFilter == LocationFilter.INDOOR,
                activeColor = TabActiveDark,
                onClick = { onFilterSelected(LocationFilter.INDOOR) },
                modifier = Modifier.weight(1f)
            )
            FilterChip(
                label = stringResource(R.string.tab_outdoor),
                icon = Icons.Outlined.FilterDrama,
                isSelected = selectedFilter == LocationFilter.OUTDOOR,
                activeColor = TabActiveGreen,
                onClick = { onFilterSelected(LocationFilter.OUTDOOR) },
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(6.dp))

        // Zeile 2: Altersgruppen-Filter
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            FilterChip(
                label = stringResource(R.string.age_all),
                icon = null,
                isSelected = ageFilter == AgeFilter.ALL_AGES,
                activeColor = TabActiveDark,
                onClick = { onAgeFilterSelected(AgeFilter.ALL_AGES) },
                modifier = Modifier.weight(1f)
            )
            FilterChip(
                label = stringResource(R.string.age_klein),
                icon = null,
                isSelected = ageFilter == AgeFilter.KLEIN,
                activeColor = AgeGroupKlein,
                onClick = { onAgeFilterSelected(AgeFilter.KLEIN) },
                modifier = Modifier.weight(1f)
            )
            FilterChip(
                label = stringResource(R.string.age_mittel),
                icon = null,
                isSelected = ageFilter == AgeFilter.MITTEL,
                activeColor = AgeGroupMittel,
                onClick = { onAgeFilterSelected(AgeFilter.MITTEL) },
                modifier = Modifier.weight(1f)
            )
            FilterChip(
                label = stringResource(R.string.age_gross),
                icon = null,
                isSelected = ageFilter == AgeFilter.GROSS,
                activeColor = AgeGroupGross,
                onClick = { onAgeFilterSelected(AgeFilter.GROSS) },
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(6.dp))

        // Zeile 3: Spieltyp-Filter
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            FilterChip(
                label = stringResource(R.string.type_all),
                icon = null,
                isSelected = typeFilter == TypeFilter.ALL_TYPES,
                activeColor = TabActiveDark,
                onClick = { onTypeFilterSelected(TypeFilter.ALL_TYPES) },
                modifier = Modifier.weight(1f)
            )
            FilterChip(
                label = stringResource(R.string.type_klettern),
                icon = null,
                isSelected = typeFilter == TypeFilter.KLETTERN,
                activeColor = GameTypeKlettern,
                onClick = { onTypeFilterSelected(TypeFilter.KLETTERN) },
                modifier = Modifier.weight(1f)
            )
            FilterChip(
                label = stringResource(R.string.type_aufwaermen),
                icon = null,
                isSelected = typeFilter == TypeFilter.AUFWAERMEN,
                activeColor = GameTypeAufwaermen,
                onClick = { onTypeFilterSelected(TypeFilter.AUFWAERMEN) },
                modifier = Modifier.weight(1f)
            )
            FilterChip(
                label = stringResource(R.string.type_kraft),
                icon = null,
                isSelected = typeFilter == TypeFilter.KRAFT,
                activeColor = GameTypeKraft,
                onClick = { onTypeFilterSelected(TypeFilter.KRAFT) },
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))
        HorizontalDivider(thickness = 1.dp, color = Color.LightGray.copy(alpha = 0.5f))
    }
}

@Composable
private fun FilterChip(
    label: String,
    icon: ImageVector?,
    isSelected: Boolean,
    activeColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val backgroundColor by animateColorAsState(
        targetValue = if (isSelected) activeColor else Color.Transparent,
        animationSpec = tween(durationMillis = 300),
        label = "chip_bg"
    )
    val textColor by animateColorAsState(
        targetValue = if (isSelected) Color.White else TextPrimary,
        animationSpec = tween(durationMillis = 300),
        label = "chip_text"
    )

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(24.dp))
            .background(backgroundColor)
            .clickable { onClick() }
            .padding(vertical = 8.dp, horizontal = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = textColor,
                    modifier = Modifier.height(18.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
            }
            Text(
                text = label,
                color = textColor,
                fontSize = 13.sp,
                fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal
            )
        }
    }
}
