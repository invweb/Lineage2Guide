package com.zx_tole.lineage2_guide.ui.items

import androidx.compose.foundation.background
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import org.koin.androidx.compose.koinViewModel
import com.zx_tole.lineage2_guide.domain.model.*
import com.zx_tole.lineage2_guide.ui.common.components.FilterBottomSheet
import com.zx_tole.lineage2_guide.ui.common.components.RarityBadge
import com.zx_tole.lineage2_guide.ui.common.components.SearchBar
import com.zx_tole.lineage2_guide.ui.common.components.getRarityColor
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemsScreen(
    viewModel: ItemsViewModel = koinViewModel(),
    onNavigateToDetail: (Long) -> Unit = {},
    onNavigateToClassDetail: (String) -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()

    when (val state = uiState) {
        is ItemsUiState.Success -> {
            ItemsSuccessScreen(
                state = state,
                isRefreshing = state.isRefreshing,
                onSearchQueryChanged = viewModel::onSearchQueryChanged,
                onClassSelected = viewModel::onClassSelected,
                onLevelRangeChanged = viewModel::onLevelRangeChanged,
                onTypeSelected = viewModel::onTypeSelected,
                onRaritySelected = viewModel::onRaritySelected,
                onLocationSelected = viewModel::onLocationSelected,
                onSortChanged = viewModel::onSortChanged,
                onRefresh = viewModel::refresh,
                onClearFilters = viewModel::clearFilters,
                onItemClicked = onNavigateToDetail,
                onClassClicked = onNavigateToClassDetail
            )
        }
        is ItemsUiState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        is ItemsUiState.Error -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        Icons.Default.Error,
                        contentDescription = null,
                        modifier = Modifier.size(48.dp),
                        tint = MaterialTheme.colorScheme.error
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Error: ${state.message}",
                        color = MaterialTheme.colorScheme.error
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = viewModel::refresh) {
                        Text("Retry")
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ItemsSuccessScreen(
    state: ItemsUiState.Success,
    isRefreshing: Boolean,
    onSearchQueryChanged: (String) -> Unit,
    onClassSelected: (String?) -> Unit,
    onLevelRangeChanged: (IntRange?) -> Unit,
    onTypeSelected: (String?) -> Unit,
    onRaritySelected: (String?) -> Unit,
    onLocationSelected: (String?) -> Unit,
    onSortChanged: (SortOption) -> Unit,
    onRefresh: () -> Unit,
    onClearFilters: () -> Unit,
    onItemClicked: (Long) -> Unit,
    onClassClicked: (String) -> Unit
) {
    var showFilterSheet by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Lineage 2",
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                },
                actions = {
                    IconButton(onClick = { showFilterSheet = true }) {
                        Icon(
                            Icons.Default.FilterList,
                            contentDescription = "Filters",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
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
        Column(modifier = Modifier.padding(paddingValues)) {
            SearchBar(
                query = state.filterState.searchQuery,
                onQueryChanged = onSearchQueryChanged,
                onClear = { onSearchQueryChanged("") }
            )

            PullToRefreshBox(
                isRefreshing = isRefreshing,
                onRefresh = onRefresh,
                modifier = Modifier.weight(1f)
            ) {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(state.items, key = { it.id }) { item ->
                        ItemCard(
                            item = item,
                            onClick = { onItemClicked(item.id) },
                            onClassClick = { onClassClicked(item.classRestriction ?: "") }
                        )
                    }

                    if (state.items.isEmpty()) {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(32.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Icon(
                                        Icons.Default.Search,
                                        contentDescription = null,
                                        modifier = Modifier.size(48.dp),
                                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        text = "No items found",
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                    if (state.filterState.searchQuery.isNotBlank() ||
                                        state.filterState.selectedTypes.isNotEmpty() ||
                                        state.filterState.selectedRarities.isNotEmpty()) {
                                        TextButton(onClick = onClearFilters) {
                                            Text("Clear filters")
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    if (showFilterSheet) {
        FilterBottomSheet(
            filterState = state.filterState,
            onApply = { /* handled by state */ },
            onDismiss = { showFilterSheet = false }
        )
    }
}

@Composable
fun ItemCard(
    item: Item,
    onClick: () -> Unit,
    onClassClick: () -> Unit
) {
    val rarityColor = getRarityColor(item.rarity)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerHigh
        ),
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .width(4.dp)
                    .height(48.dp)
                    .background(rarityColor, MaterialTheme.shapes.small)
            )

            Spacer(modifier = Modifier.width(12.dp))

            AsyncImage(
                model = item.iconUrl,
                contentDescription = null,
                modifier = Modifier
                    .size(48.dp)
                    .background(rarityColor.copy(alpha = 0.2f), MaterialTheme.shapes.small)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = item.name,
                    style = MaterialTheme.typography.titleMedium,
                    color = rarityColor,
                    fontWeight = FontWeight.SemiBold
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Lv. ${item.level}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "•",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = item.type.name,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                item.classRestriction?.let { restriction ->
                    Text(
                        text = restriction,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary,
                        textDecoration = TextDecoration.Underline,
                        modifier = Modifier.clickable(onClick = onClassClick)
                    )
                }
            }

            RarityBadge(rarity = item.rarity)
        }
    }
}
