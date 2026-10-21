package com.zx_tole.lineage2_guide.ui.common.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zx_tole.lineage2_guide.domain.model.Rarity

@Composable
fun RarityBadge(
    rarity: Rarity,
    modifier: Modifier = Modifier
) {
    val color = getRarityColor(rarity)

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(4.dp))
            .background(color.copy(alpha = 0.2f))
            .padding(horizontal = 6.dp, vertical = 2.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = rarity.name.take(3).uppercase(),
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            color = color
        )
    }
}

fun getRarityColor(rarity: Rarity): Color {
    return when (rarity) {
        Rarity.COMMON -> Color(0xFF9E9E9E)
        Rarity.UNCOMMON -> Color(0xFF4CAF50)
        Rarity.RARE -> Color(0xFF2196F3)
        Rarity.EPIC -> Color(0xFF9C27B0)
        Rarity.LEGENDARY -> Color(0xFFFFC107)
        Rarity.DIVINE -> Color(0xFFFF5722)
    }
}
