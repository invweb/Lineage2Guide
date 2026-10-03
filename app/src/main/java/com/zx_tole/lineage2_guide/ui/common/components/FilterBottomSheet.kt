package com.zx_tole.lineage2_guide.ui.common.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.zx_tole.lineage2_guide.domain.model.FilterState
import com.zx_tole.lineage2_guide.domain.model.SortOption

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterBottomSheet(
    filterState: FilterState,
    onTypeSelected: (String?) -> Unit,
    onRaritySelected: (String?) -> Unit,
    onDismiss: () -> Unit
) {
    var selectedType by remember { mutableStateOf(filterState.selectedTypes.firstOrNull()) }
    var selectedRarity by remember { mutableStateOf(filterState.selectedRarities.firstOrNull()) }

    val classes = emptyList<String>()
    val types = listOf("Weapon", "Armor", "Consumable")
    val rarities = listOf("Common", "Uncommon", "Rare", "Epic", "Legendary")

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
        tonalElevation = 8.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            Text(
                text = "Filter Items",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Type",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(8.dp))
            FilterChips(
                options = types,
                selectedOption = selectedType,
                onOptionSelected = {
                    selectedType = if (selectedType == it) null else it
                    onTypeSelected(selectedType)
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Rarity",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(8.dp))
            FilterChips(
                options = rarities,
                selectedOption = selectedRarity,
                onOptionSelected = {
                    selectedRarity = if (selectedRarity == it) null else it
                    onRaritySelected(selectedRarity)
                }
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = onDismiss
            ) {
                Text("Close")
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun FilterChips(
    options: List<String>,
    selectedOption: String?,
    onOptionSelected: (String) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        options.forEach { option ->
            FilterChip(
                selected = selectedOption == option,
                onClick = { onOptionSelected(option) },
                label = {
                    Text(
                        option.take(12).ifBlank { option },
                        maxLines = 1,
                        overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                    )
                },
                leadingIcon = {
                    if (selectedOption == option) {
                        Icon(
                            Icons.Default.Check,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    }
}
