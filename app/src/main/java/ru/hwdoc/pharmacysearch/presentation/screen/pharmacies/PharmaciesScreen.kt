package ru.hwdoc.pharmacysearch.presentation.screen.pharmacies

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import ru.hwdoc.pharmacysearch.domain.entity.Pharmacy
import ru.hwdoc.pharmacysearch.presentation.ui.theme.CustomIcons

@Composable
fun PharmaciesScreen(
    modifier: Modifier = Modifier,
    viewModel: PharmaciesViewModel = hiltViewModel(),
    onPharmacyClick: (Pharmacy) -> Unit
) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        modifier = modifier,
        topBar = {

        }
    ) {innerPadding ->
        LazyColumn(
            contentPadding = innerPadding,
        ) {

        }
    }
}

@Composable
private fun SearchBar(
    modifier: Modifier = Modifier,
    query: String,
    onQueryChange: (String) -> Unit,
    onClearSearch: () -> Unit
) {
    TextField(
        modifier = modifier,
        value = query,
        onValueChange = onQueryChange,
        placeholder = {
            Text(
                text = "Поиск аптеки..",
                fontSize = 14.sp,
            )
        },
        trailingIcon = {
            if (query.isNotEmpty()) {
                IconButton(
                    onClick = onClearSearch
                ) {
                    Icon(
                        imageVector = CustomIcons.Close_small,
                        contentDescription = "Очистить поле ввода"
                    )
                }
            }
        }
    )
}