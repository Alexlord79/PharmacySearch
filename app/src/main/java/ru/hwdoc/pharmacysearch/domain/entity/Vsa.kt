package ru.hwdoc.pharmacysearch.domain.entity

data class Vsa(
    val id: Int = 0,
    val fullName: String, // ФИО ВСА
    val districtName: String,//название зоны ответственности (например - ВСА Каневская)
    val mobilePhone: String,
    val email: String,
)
