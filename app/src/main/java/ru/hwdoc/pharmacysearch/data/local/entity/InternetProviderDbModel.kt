package ru.hwdoc.pharmacysearch.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "internet_providers",
    indices = [
        Index(value = ["full_name"], unique = true)  // Уникальный индекс на название провайдера
    ]
)
data class InternetProviderDbModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "full_name")
    val fullName: String,                   //Название провайдера
    @ColumnInfo(name = "phone_number", defaultValue = "нет_данных")
    val phoneNumber: String,                //Номер телефона ТП
    @ColumnInfo(defaultValue = "нет_данных")
    val email: String                       //Email ТП
)
