package ru.hwdoc.pharmacysearch.domain.entity

data class Person(
    val id: Int = 0, // Уникальный идентификатор персоны
    val fullName: String, // Полное ФИО человека
    val position: String? = null, // Должность/позиция человека
    val mobilePhone: String? = null, // Мобильный телефон (опционально)
    val email: String? = null, // Email адрес (опционально)
)