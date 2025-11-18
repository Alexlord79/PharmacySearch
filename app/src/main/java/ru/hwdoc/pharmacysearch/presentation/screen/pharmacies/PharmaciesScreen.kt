package ru.hwdoc.pharmacysearch.presentation.screen.pharmacies

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Context.*
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.HomeRepairService
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.PhoneInTalk
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Wifi
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import ru.hwdoc.pharmacysearch.R
import ru.hwdoc.pharmacysearch.domain.entity.DayOfWeek
import ru.hwdoc.pharmacysearch.domain.entity.Pharmacy
import ru.hwdoc.pharmacysearch.domain.entity.PharmacyBrand
import ru.hwdoc.pharmacysearch.domain.entity.PharmacyFilters
import ru.hwdoc.pharmacysearch.presentation.screen.util.NavigatorEvent
import ru.hwdoc.pharmacysearch.presentation.ui.theme.Health
import ru.hwdoc.pharmacysearch.presentation.ui.theme.MiniPrice
import ru.hwdoc.pharmacysearch.presentation.ui.theme.White200

@Composable
fun PharmaciesScreen(
    modifier: Modifier = Modifier,
    viewModel: PharmaciesViewModel = hiltViewModel(),
    onPharmacyClick: (Pharmacy) -> Unit
) {
    val state by viewModel.state.collectAsState()

    val uiEvents by viewModel.uiEvent.collectAsState()

    val intentLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = {}
    )

    val context = LocalContext.current

    LaunchedEffect(uiEvents) {
        uiEvents?.let { event ->
            when (event) {
                is PharmaciesUiEvent.LaunchIntent -> {
                    intentLauncher.launch(event.intent)
                    viewModel.processCommand(PharmaciesCommand.ClearUiEvent)
                }
                is PharmaciesUiEvent.CopyToClipboard -> {
                    val clipboardManager = context.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
                    val clipData = ClipData.newPlainText("Данные из приложения \"Поиск аптеки\"\n", event.text)
                    clipboardManager.setPrimaryClip(clipData)
                    Toast.makeText(context, "Скопировано в буфер обмена", Toast.LENGTH_SHORT).show()
                    viewModel.processCommand(PharmaciesCommand.ClearUiEvent)
                }
            }
        }
    }

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
                            .padding(horizontal = 16.dp),
                        query = state.filters.query,
                        onQueryChange = { newQuery ->
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
                            .padding(horizontal = 16.dp),
                        filters = state.filters,
                        onFilterToggle = { filterType ->
                            viewModel.processCommand(
                                PharmaciesCommand.ToggleFilter(filterType = filterType)
                            )
                        }
                    )
                }
            }

        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            contentPadding = innerPadding
        ) {
            state.pharmacies.forEach { pharmacy ->
                item {
                    PharmacyCard(
                        modifier = Modifier
                            .padding(top = 8.dp, end = 16.dp, start = 16.dp),
                        pharmacy = pharmacy,
                        onPhoneCallClick = { phoneNumber ->
                            viewModel.processCommand(
                                PharmaciesCommand.MakePhoneCall(phoneNumber)
                            )
                        },
                        onNavigationClick = { navigatorEvent ->
                            viewModel.processCommand(
                                PharmaciesCommand.OpenNavigator(navigatorEvent)
                            )

                        },
                        onCopyClick = {formattedAddress ->
                            viewModel.processCommand(
                                PharmaciesCommand.CopyToClipboard(
                                    NavigatorEvent.CopiInformation(formattedAddress)
                                )
                            )

                        },
                        onPharmacyClick = onPharmacyClick
                    )
                }
            }
        }
    }
}

@Composable
private fun PharmacyCard(
    modifier: Modifier = Modifier,
    pharmacy: Pharmacy,
    onPhoneCallClick: (String) -> Unit,
    onNavigationClick: (NavigatorEvent) -> Unit,
    onCopyClick: (String) -> Unit,
    onPharmacyClick: (Pharmacy) -> Unit
) {
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
            .combinedClickable(
                onClick = { onPharmacyClick(pharmacy) }
            )
            .padding(8.dp)
    ) {
        Row(
            //verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            Box(
                modifier = Modifier
                    .weight(0.2f)
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(11.dp))
                    .background(
                        when (pharmacy.pharmacyBrand) {
                            PharmacyBrand.ZDOROVIE -> Health
                            PharmacyBrand.MINIPRICE -> MiniPrice
                            PharmacyBrand.NO_DATA_AVAILABLE -> White200
                        }
                    ),
                //.padding(16.dp),
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
            Column(
                modifier = Modifier
                    .weight(0.8f)
                    .padding(start = 8.dp)
            ) {

                Text(
                    text = "${pharmacy.localityType.nameOfLocalityType} ${pharmacy.locality} ",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onTertiary
                )
//                    подсказка о плном адресе аптеки, не знаю - нужна она или нет, на подумать оставлю
//                    val tooltipState = rememberTooltipState(isPersistent = false)
//                    val scope = rememberCoroutineScope()
//                    TooltipBox(
//                        positionProvider = TooltipDefaults.rememberTooltipPositionProvider(
//                            positioning = TooltipAnchorPosition.Above,
//                            spacingBetweenTooltipAndAnchor = 8.dp
//                        ),
//                        tooltip = {
//                            PlainTooltip {
//                                Text("Hello World")
//                            }
//                        },
//                        state = tooltipState
//                    ) {
//                        IconButton(
//                            onClick = {
//                                scope.launch {
//                                    tooltipState.show()
//                                    delay(5000L)
//                                    if (tooltipState.isVisible) {
//                                        tooltipState.dismiss()
//                                    }
//                                }
//                            }
//                        ) {
//                            Icon(
//                                imageVector = Icons.Default.Info,
//                                contentDescription = "подробная инфо об адресе"
//                            )
//                        }
//                    }

                Text(
                    text = pharmacy.address,
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onTertiary,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        ) {
            Column(
                modifier = Modifier
            ) {
                if (pharmacy.phoneNumber.isNotBlank()) {
                    ContactRow(
                        modifier = Modifier,
                        icon = Icons.Default.PhoneInTalk,
                        text = pharmacy.phoneNumber,
                        onClick = {
                            onPhoneCallClick(pharmacy.phoneNumber)
                        }
                    )
                }
                ContactRow(
                    modifier = Modifier,
                    icon = Icons.Default.AccessTime,
                    text = "${pharmacy.openingTime} - ${pharmacy.closingTime}"
                )
                if (pharmacy.availableDays != emptySet<DayOfWeek>()) {
                    ContactRow(
                        modifier = Modifier,
                        icon = Icons.Default.LocalShipping,
                        text = pharmacy.availableDays.joinToString(separator = ", ") { dayOfWeek ->
                            dayOfWeek.nameOfDayOfWeek
                        }
                    )
                }
                ContactRow(
                    modifier = Modifier,
                    icon = Icons.Default.CalendarMonth,
                    text = pharmacy.openingDate.ifBlank { "Еще не открыта" }
                )
            }
            Column(
                modifier = Modifier
                    .padding(start = 16.dp)
            ) {
                ContactRow(
                    modifier = Modifier,
                    icon = Icons.Default.HomeRepairService,
                    text = pharmacy.vsa.districtName
                )
                ContactRow(
                    modifier = Modifier,
                    icon = Icons.Default.Wifi,
                    text = pharmacy.internetProvider.fullName
                )
                for (phoneNumber in pharmacy.internetProvider.phoneNumber.split(",").toList()) {
                    if (phoneNumber.isNotEmpty()) {
                        ContactRow(
                            modifier = Modifier,
                            icon = Icons.Default.Wifi,
                            text = phoneNumber.trim(),
                            onClick = {
                                onPhoneCallClick(phoneNumber)
                            }
                        )
                    }
                }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            horizontalArrangement = Arrangement.End
        ) {
            if (pharmacy.phoneNumber.isNotBlank()) {
                IconButton(
                    modifier = Modifier,
                    onClick = {
                        onPhoneCallClick(pharmacy.phoneNumber)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.PhoneInTalk,
                        contentDescription = "Позвонить",
                        tint = MaterialTheme.colorScheme.onTertiary.copy(alpha = 0.7f)
                    )
                }
            }
            if (pharmacy.yandexMapsLink.isNotBlank() || pharmacy.address.isNotBlank()) {
                IconButton(
                    modifier = Modifier,
                    onClick = {
                        val navigationEvent = if (pharmacy.yandexMapsLink.isNotBlank()) {
                            NavigatorEvent.WithLink(pharmacy.yandexMapsLink)
                        } else {
                            NavigatorEvent.WithAddress(pharmacy.locality, pharmacy.address)
                        }
                        onNavigationClick(navigationEvent)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = "Навигация",
                        tint = MaterialTheme.colorScheme.onTertiary.copy(alpha = 0.7f)
                    )
                }
            }
            IconButton(
                modifier = Modifier,
                onClick = {
                    val formattedAddress = "" +
                            "Аптека: ${pharmacy.number}\n" +
                            "Регион: ${pharmacy.region}\n" +
                            "${pharmacy.localityType.nameOfLocalityType} " +
                            "${pharmacy.locality}\n" +
                            "Телефон: ${pharmacy.phoneNumber}"
                    onCopyClick(formattedAddress)
                }
            ) {
                Icon(
                    imageVector = Icons.Default.ContentCopy,
                    contentDescription = "Копирование информации об аптеке",
                    tint = MaterialTheme.colorScheme.onTertiary.copy(alpha = 0.7f)
                )
            }
        }
    }
}

@Composable
private fun ContactRow(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    text: String,
    onClick: (() -> Unit)? = null
) {
    Row(
        modifier = modifier
            .clickable(enabled = onClick != null) { onClick?.invoke() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier
                .size(20.dp),
            imageVector = icon,
            contentDescription = text,
            tint = MaterialTheme.colorScheme.onTertiary.copy(alpha = 0.7f)
        )
        Text(
            modifier = Modifier
                .padding(start = 8.dp),
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onTertiary
        )
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
            Triple(
                FilterType.LOCALITY,
                stringResource(R.string.filterLocality),
                filters.isLocality
            ),
            Triple(FilterType.ADDRESS, stringResource(R.string.filterAddress), filters.isAddress),
//            Triple(FilterType.PHONE, stringResource(R.string.filterPhone), filters.isPhoneNumber),
//            Triple(FilterType.VSA, stringResource(R.string.filterVsa), filters.isVsa),
//            Triple(
//                FilterType.INTERNET,
//                stringResource(R.string.filterInternetProvider), filters.isInternetProvider
//            ),
        )
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            maxLines = 1
        ) {
            allFilterOption.forEach { (filterType, label, isSelected) ->
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
        shape = RoundedCornerShape(13),
        maxLines = 1,
        keyboardOptions = KeyboardOptions(
            autoCorrectEnabled = false,
            imeAction = ImeAction.Done,
            showKeyboardOnFocus = true
        )
    )
}