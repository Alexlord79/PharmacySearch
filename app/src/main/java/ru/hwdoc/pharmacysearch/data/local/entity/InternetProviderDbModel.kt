package ru.hwdoc.pharmacysearch.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "internet_providers",
    indices = [
        Index(value = ["nameProviders"], unique = true),  // Уникальный индекс на название провайдера
        Index(value = ["phoneNumber"]),                   // Индекс на номер телефона ТП
    ]
)
data class InternetProviderDbModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nameProviders: String,  //Название провайдера
    val phoneNumber: String?,   //Номер телефона ТП
    val email: String?          //Email ТП
)
