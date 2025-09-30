package ru.hwdoc.pharmacysearch.domain.entity

data class InternetProvider(
    val id: Int = 0,
    val name: String, // Название провайдера
    val phoneNumber: String?, // номер ТП
    val email: String? = null, // Email ТП
)
