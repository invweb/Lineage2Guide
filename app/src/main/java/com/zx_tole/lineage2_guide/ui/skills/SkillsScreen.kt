package com.zx_tole.lineage2_guide.ui.skills

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import org.koin.androidx.compose.koinViewModel
import com.zx_tole.lineage2_guide.domain.model.Skill
import com.zx_tole.lineage2_guide.ui.common.EmptyScreen
import com.zx_tole.lineage2_guide.ui.common.ErrorScreen
import com.zx_tole.lineage2_guide.ui.common.LoadingScreen
import com.zx_tole.lineage2_guide.ui.common.SyncIndicator

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SkillsScreen(
    viewModel: SkillsViewModel = koinViewModel(),
    onNavigateToDetail: (Long) -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()

    when {
        uiState.isLoading -> {
            LoadingScreen(
                message = "Loading skills...",
                modifier = Modifier.fillMaxSize()
            )
        }
        uiState.error != null -> {
            ErrorScreen(
                message = uiState.error!!,
                onRetry = viewModel::refresh,
                modifier = Modifier.fillMaxSize()
            )
        }
        else -> {
            SkillsSuccessScreen(
                skills = uiState.skills,
                isRefreshing = uiState.isRefreshing,
                onRefresh = viewModel::refresh,
                onSkillClicked = onNavigateToDetail
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SkillsSuccessScreen(
    skills: List<Skill>,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    onSkillClicked: (Long) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Skills",
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                },
                actions = {
                    SyncIndicator(isSyncing = isRefreshing)
                    IconButton(onClick = onRefresh) {
                        Icon(
                            Icons.Default.Refresh,
                            contentDescription = "Refresh",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        PullToRefreshBox(
            isRefreshing = isRefreshing,
            onRefresh = onRefresh,
            modifier = Modifier.padding(paddingValues)
        ) {
            if (skills.isEmpty()) {
                EmptyScreen(
                    message = "No skills found",
                    icon = Icons.Default.Bolt
                )
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(skills, key = { it.id }) { skill ->
                        SkillCard(
                            skill = skill,
                            onClick = { onSkillClicked(skill.id) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun SkillCard(
    skill: Skill,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerHigh
        ),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = skill.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Class: ${skill.classRestriction}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "Type: ${skill.type}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = "Level: ${skill.level}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            skill.description?.let { desc ->
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = desc,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
