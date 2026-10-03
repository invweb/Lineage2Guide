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
    onApply: () -> Unit,
    onDismiss: () -> Unit
) {
    var selectedClass by remember { mutableStateOf(filterState.selectedClasses.firstOrNull()) }
    var selectedType by remember { mutableStateOf(filterState.selectedTypes.firstOrNull()) }
    var selectedRarity by remember { mutableStateOf(filterState.selectedRarities.firstOrNull()) }
    var levelStart by remember { mutableIntStateOf(filterState.levelRange?.start ?: 1) }
    var levelEnd by remember { mutableIntStateOf(filterState.levelRange?.endInclusive ?: 99) }

    val classes = listOf("Warrior", "Mage", "Archer", "Assassin", "Knight", "Warlord", "Sorceress", "Archmage")
    val types = listOf("WEAPON", "ARMOR", "BOOTS", "GLOVES", "HELMET", "SHIELD", "POTION", "SCROLL", "MATERIAL", "JEWELRY")
    val rarities = listOf("COMMON", "UNCOMMON", "RARE", "EPIC", "LEGENDARY", "DIVINE")

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
                text = "Class",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(8.dp))
            FilterChips(
                options = classes,
                selectedOption = selectedClass,
                onOptionSelected = { selectedClass = if (selectedClass == it) null else it }
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
                onOptionSelected = { selectedType = if (selectedType == it) null else it }
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
                onOptionSelected = { selectedRarity = if (selectedRarity == it) null else it }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Level Range",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = levelStart.toString(),
                    onValueChange = { levelStart = it.toIntOrNull() ?: 1 },
                    modifier = Modifier.weight(1f),
                    label = { Text("Min") },
                    singleLine = true
                )
                OutlinedTextField(
                    value = levelEnd.toString(),
                    onValueChange = { levelEnd = it.toIntOrNull() ?: 99 },
                    modifier = Modifier.weight(1f),
                    label = { Text("Max") },
                    singleLine = true
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    modifier = Modifier.weight(1f),
                    onClick = onDismiss,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {
                    Text("Cancel")
                }
                Button(
                    modifier = Modifier.weight(1f),
                    onClick = {
                        onApply()
                        onDismiss()
                    }
                ) {
                    Text("Apply")
                }
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
