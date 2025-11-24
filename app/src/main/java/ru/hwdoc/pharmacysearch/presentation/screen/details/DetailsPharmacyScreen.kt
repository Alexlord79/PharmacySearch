@file:OptIn(ExperimentalMaterial3Api::class)

package ru.hwdoc.pharmacysearch.presentation.screen.details

import android.util.Log.d
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.hwdoc.pharmacysearch.domain.entity.Pharmacy
import ru.hwdoc.pharmacysearch.domain.entity.PharmacyBrand
import ru.hwdoc.pharmacysearch.presentation.ui.theme.Health
import ru.hwdoc.pharmacysearch.presentation.ui.theme.MiniPrice
import ru.hwdoc.pharmacysearch.presentation.ui.theme.White200

@Composable
fun DetailsPharmacyScreen(
    modifier: Modifier = Modifier,
    number: Int = 96,
    viewModel: DetailsPharmacyViewModel = hiltViewModel(
        creationCallback = {factory: DetailsPharmacyViewModel.Factory ->
            factory.create(number)
        }
    ),
    onFinished: () -> Unit
) {
    val state = viewModel.state.collectAsState()
    val currentState = state.value

    when(currentState) {
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
                                    text = "Детализация информации а${currentState.pharmacy.number}",
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
                                            viewModel.processCommand(DetailsPharmacyCommand.Back)
                                        },
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = "Назад",
                                    tint = MaterialTheme.colorScheme.onSurface
                                )
                            },
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor =Color.Transparent,
                                navigationIconContentColor = MaterialTheme.colorScheme.onSurface
                            )
                        )
                    }
                }
            ) {innerPadding ->
                Column(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(innerPadding)
                ) {
                    PharmacyCard(
                        modifier = Modifier
                            .padding(top = 8.dp, end = 8.dp, start = 8.dp),
                        pharmacy = currentState.pharmacy
                    )
                }
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

@Composable
private fun PharmacyCard(
    modifier: Modifier = Modifier,
    pharmacy: Pharmacy
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(11.dp))
                .background(
                    when (pharmacy.pharmacyBrand) {
                        PharmacyBrand.ZDOROVIE -> Health
                        PharmacyBrand.MINIPRICE -> MiniPrice
                        PharmacyBrand.NO_DATA_AVAILABLE -> White200
                    }
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = pharmacy.number.toString(),
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = RoundedCornerShape(16.dp)
            )
            .background(MaterialTheme.colorScheme.primary)
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "${pharmacy.region}, район: ${pharmacy.districtOfTheRegion}\n${pharmacy.localityType.nameOfLocalityType} " +
                        "${pharmacy.locality}, ${pharmacy.address}",
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onTertiary
            )
        }
    }
    Text(
        modifier = Modifier
            .padding(top = 8.dp, start = 8.dp),
        text = "Информация о персонале".uppercase(),
        fontSize = 16.sp,
        textAlign = TextAlign.Start,
        color = MaterialTheme.colorScheme.onTertiary.copy(alpha = 0.5f)
    )
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = RoundedCornerShape(16.dp)
            )
            .background(MaterialTheme.colorScheme.primary)
            .padding(8.dp)
    ) {
        InfoRow(
            modifier = Modifier,
            description = "Зав. аптекой",
            value = pharmacy.pharmacyManageress.fullName
        )
        InfoRow(
            modifier = Modifier,
            description = "Тел. зав. аптекой",
            value = pharmacy.pharmacyManageress.mobilePhone
        )
        InfoRow(
            modifier = Modifier,
            description = "Рук. макрорегиона",
            value = pharmacy.directorOfMacroregion.fullName
        )
        InfoRow(
            modifier = Modifier,
            description = "Рук. региона",
            value = pharmacy.headOfTheRegional.fullName
        )
        InfoRow(
            modifier = Modifier,
            description = "Управляющий",
            value = pharmacy.manager.fullName
        )
        InfoRow(
            modifier = Modifier,
            description = "Тел. управляющего",
            value = pharmacy.manager.mobilePhone
        )
    }
    Text(
        modifier = Modifier
            .padding(top = 8.dp, start = 8.dp),
        text = "Юридическая информация".uppercase(),
        fontSize = 16.sp,
        textAlign = TextAlign.Start,
        color = MaterialTheme.colorScheme.onTertiary.copy(alpha = 0.5f)
    )
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = RoundedCornerShape(16.dp)
            )
            .background(MaterialTheme.colorScheme.primary)
            .padding(8.dp)
    ) {
        InfoRow(
            modifier = Modifier,
            description = "Юр. лицо",
            value = pharmacy.legalEntity.fullName
        )
        InfoRow(
            modifier = Modifier,
            description = "ИНН",
            value = pharmacy.legalEntity.inn.replace("(\\d{3})(\\d{3})(\\d{2})(\\d{2})".toRegex(), "$1 $2 $3 $4")
        )
        InfoRow(
            modifier = Modifier,
            description = "ОГРН",
            value = pharmacy.legalEntity.ogrn
        )
        InfoRow(
            modifier = Modifier,
            description = "Управляющий-ИП",
            value = pharmacy.legalEntity.superintendent
        )
    }
}

@Composable
private fun InfoRow(
    modifier: Modifier = Modifier,
    description: String,
    value: String = ""
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = description,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onTertiary.copy(alpha = 0.6f)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onTertiary
        )
    }
}
