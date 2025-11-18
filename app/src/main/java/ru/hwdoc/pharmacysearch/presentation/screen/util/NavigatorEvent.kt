package ru.hwdoc.pharmacysearch.presentation.screen.util

sealed class NavigatorEvent {

    data class WithLink(val yandexMapsLink: String): NavigatorEvent()
    data class WithAddress(val locality: String, val address: String) : NavigatorEvent()
    data class CopiInformation(val formattedAddress: String): NavigatorEvent()
}