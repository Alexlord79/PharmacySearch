package ru.hwdoc.pharmacysearch.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "vsa",
    indices = [
        Index(value = ["full_name"], unique = true),    // Уникальный индекс на ФИО ВСА
        Index(value = ["district_name"])                // индекс на зону ответственности (может и не надо)
    ]
)
data class VsaDbModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "full_name")
    val fullName: String,               // ФИО ВСА
    @ColumnInfo(name = "phone_number", defaultValue = "нет_данных")
    val phoneNumber: String,            // Мобильный телефон
    @ColumnInfo(name = "district_name", defaultValue = "нет_данных")
    val districtName: String,           // Зона ответственности
    @ColumnInfo(defaultValue = "нет_данных")
    val email: String                   // E-mail
)
