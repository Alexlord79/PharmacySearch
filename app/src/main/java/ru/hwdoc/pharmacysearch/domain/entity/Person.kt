package ru.hwdoc.pharmacysearch.domain.entity

data class Person(
    val id: Int = 0, // Уникальный идентификатор персоны
    val fullName: String, // Полное ФИО человека
    val position: String = "", // Должность/позиция человека
    val mobilePhone: String = "", // Мобильный телефон (опционально)
    val email: String = "", // Email адрес (опционально)
)