package ru.hwdoc.pharmacysearch.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "vsa",
    indices = [
        Index(value = ["fullName"], unique = true),
        Index(value = ["districtName"])
    ]
)
data class VsaDbModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val fullName: String,       // ФИО ВСА
    val districtName: String,   // Зона ответственности
    val mobilePhone: String,    // Мобильный телефон
    val email: String           // E-mail
)
