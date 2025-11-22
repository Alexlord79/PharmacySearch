@file:OptIn(ExperimentalMaterial3Api::class)

package ru.hwdoc.pharmacysearch.presentation.screen.details

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.hwdoc.pharmacysearch.domain.entity.Pharmacy

@Composable
fun DetailsPharmacyScreen(
    modifier: Modifier = Modifier,
    number: Int = 45,
    viewModel: DetailsPharmacyViewModel = hiltViewModel(
        creationCallback = {factory: DetailsPharmacyViewModel.Factory ->
            factory.create(number)
        }
    ),
    onFinished: () -> Unit
) {
    val state = viewModel.state.collectAsState()
    when(val currentState = state.value) {
        is DetailsPharmacyState.Success -> {
            Scaffold(
                modifier = modifier,
                topBar = {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.secondary)
                    ) {
                        TopAppBar(
                            title = {
                                Text(
                                    text = "Детализация аптеки" + currentState.pharmacy.number,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            },
                            navigationIcon = {
                                Icon(
                                    modifier = Modifier
                                        .padding(start = 16.dp, end = 8.dp)
                                        .clickable {

                                        },
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = "Назад"
                                )
                            }
                        )
                    }
                }
            ) {innerPadding ->
                LazyColumn(
                    modifier = Modifier,
                    contentPadding = innerPadding
                ) { }
            }
        }
        DetailsPharmacyState.Finished -> {
            LaunchedEffect(key1 = Unit) {
                onFinished()
            }
        }
        DetailsPharmacyState.Initial -> {}
    }
}
