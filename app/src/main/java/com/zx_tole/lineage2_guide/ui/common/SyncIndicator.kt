package com.zx_tole.lineage2_guide.ui.common

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material.icons.filled.SyncProblem
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun SyncIndicator(
    isSyncing: Boolean = false,
    lastSyncTime: String? = null,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition()
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = if (isSyncing) {
            infiniteRepeatable(
                animation = tween(1000, easing = LinearEasing),
                repeatMode = RepeatMode.Restart
            )
        } else {
            infiniteRepeatable(
                animation = tween(500, easing = EaseInOut),
                repeatMode = RepeatMode.Reverse
            )
        }
    )
    
    val iconColor by animateColorAsState(
        targetValue = if (isSyncing) {
            MaterialTheme.colorScheme.primary
        } else {
            MaterialTheme.colorScheme.onSurfaceVariant
        },
        animationSpec = tween(300)
    )
    
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Icon(
            imageVector = if (isSyncing) Icons.Default.Sync else Icons.Default.SyncProblem,
            contentDescription = if (isSyncing) "Syncing" else "Sync status",
            modifier = Modifier
                .size(20.dp)
                .rotate(rotation),
            tint = iconColor
        )
        
        if (!isSyncing && lastSyncTime != null) {
            Text(
                text = lastSyncTime,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(start = 4.dp)
            )
        }
    }
}
