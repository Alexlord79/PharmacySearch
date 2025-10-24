package ru.hwdoc.pharmacysearch.presentation.screen.pharmacies

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import ru.hwdoc.pharmacysearch.R
import ru.hwdoc.pharmacysearch.domain.entity.Pharmacy
import ru.hwdoc.pharmacysearch.domain.entity.PharmacyFilters

@Composable
fun PharmaciesScreen(
    modifier: Modifier = Modifier,
    viewModel: PharmaciesViewModel = hiltViewModel(),
    onPharmacyClick: (Pharmacy) -> Unit
) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        modifier = modifier.fillMaxWidth(),
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.secondary)
            ) {
                Column(
                    modifier = Modifier.statusBarsPadding()
                ) {
                    Title(
                        modifier = Modifier.padding(horizontal = 24.dp),
                        fontSize = 24.sp,
                        text = stringResource(R.string.app_name)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    SearchBar(
                        modifier = Modifier
                            .padding(horizontal = 24.dp),
                        query = state.filters.query,
                        onQueryChange = {newQuery ->
                            viewModel.processCommand(
                                PharmaciesCommand.InputSearchQuery(
                                    filters = state.filters.copy(query = newQuery)
                                )
                            )
                        },
                        onClearSearch = {
                            viewModel.processCommand(
                                PharmaciesCommand.ClearSearch
                            )
                        }
                    )
                    FilterChips(
                        modifier = Modifier
                            .padding(horizontal = 24.dp),
                        filters = state.filters,
                        onFilterToggle = {filterType ->
                            viewModel.processCommand(
                                PharmaciesCommand.ToggleFilter(filterType = filterType)
                            )
                        }
                    )
                }
            }

        }
    ) {innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            contentPadding = innerPadding
        ) {
            item {
                Text(
                    text = "ghbghjzdfdszjsdj"
                )
            }

        }
    }
}

@Composable
private fun FilterChips(
    modifier: Modifier,
    filters: PharmacyFilters,
    onFilterToggle: (FilterType) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Title(
            modifier = Modifier,
            fontSize = 14.sp,
            text = stringResource(R.string.searchBy)
        )
        val allFilterOption = listOf(
            Triple(FilterType.ALL, stringResource(R.string.filterAll), filters.isAll),
            Triple(FilterType.NUMBER, stringResource(R.string.filterNumber), filters.isNumber),
            Triple(FilterType.LOCALITY, stringResource(R.string.filterLocality), filters.isLocality),
            Triple(FilterType.ADDRESS, stringResource(R.string.filterAddress), filters.isAddress),
            Triple(FilterType.PHONE, stringResource(R.string.filterPhone), filters.isPhoneNumber),
            Triple(FilterType.VSA, stringResource(R.string.filterVsa), filters.isVsa),
            Triple(FilterType.INTERNET,
                stringResource(R.string.filterInternetProvider), filters.isInternetProvider),
        )
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            allFilterOption.forEach {(filterType, label, isSelected) ->
                FilterChip(
                    selected = isSelected,
                    onClick = {
                        onFilterToggle(filterType)
                    },
                    label = {
                        Text(
                            text = label
                        )
                    },
                    colors = FilterChipDefaults.filterChipColors(
                        containerColor = MaterialTheme.colorScheme.secondary,
                        labelColor = MaterialTheme.colorScheme.onSurface,
                        selectedLabelColor = if (isSelected) {
                            MaterialTheme.colorScheme.onSurface
                        } else {
                            MaterialTheme.colorScheme.onSurface
                        },
                        selectedContainerColor = if (isSelected) {
                            MaterialTheme.colorScheme.outline
                        } else {
                            MaterialTheme.colorScheme.secondary
                        }
                    )
                )
            }
        }
    }
}

@Composable
private fun Title(
    modifier: Modifier,
    fontSize: TextUnit,
    text: String
) {
    Text(
        modifier = modifier,
        text = text,
        fontSize = fontSize,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onSurface
    )
}

@Composable
private fun SearchBar(
    modifier: Modifier = Modifier,
    query: String,
    onQueryChange: (String) -> Unit,
    onClearSearch: () -> Unit
) {
    TextField(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                shape = RoundedCornerShape(13)
            ),
        value = query,
        onValueChange = onQueryChange,
        placeholder = {
            Text(
                text = stringResource(R.string.placeholderSearch),
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.surface,
            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            cursorColor = MaterialTheme.colorScheme.onSurface
        ),
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = stringResource(R.string.placeholderSearch),
                tint = MaterialTheme.colorScheme.onSurface
            )
        },
        trailingIcon = {
            if (query.isNotEmpty()) {
                IconButton(
                    onClick = onClearSearch
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = stringResource(R.string.clearSearch),
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        },
        shape = RoundedCornerShape(13)
    )
}