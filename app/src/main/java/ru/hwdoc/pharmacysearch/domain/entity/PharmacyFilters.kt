package ru.hwdoc.pharmacysearch.domain.entity

data class PharmacyFilters(
    val query: String = "",                 // Поисковый запрос
    val isAll: Boolean = true,              // Поиск по всем параметрам
    val isNumber: Boolean = false,          // По номеру аптеки
    val isLocality: Boolean = false,        // По населенному пункту расположения аптеки
    val isLocationAddress: Boolean = false, // По адресу -  улица, дом
    val isMobileNumber: Boolean = false,    // По номеру телефона аптеки
    val isVsa: Boolean = false,             // По ВСА
    val isInternetProvider: Boolean = false // По интернет-провайдеру
) {

    fun hasSpecificFilters(): Boolean {
        return isNumber || isLocality || isLocationAddress || isMobileNumber || isVsa || isInternetProvider
    }

    fun hasQuery(): Boolean {
        return query.isNotBlank()
    }
}
