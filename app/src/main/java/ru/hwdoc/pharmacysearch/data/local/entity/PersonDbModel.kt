package ru.hwdoc.pharmacysearch.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "persons",
    indices = [
        Index(value = ["fullName"]),
    ]
)
data class PersonDbModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,            //id в локальной БД
    val fullName: String,      //полное ФИО
    val position: String?,       //должность
    val mobilePhone: String?,   //мобильный телефон
    val email: String?          //почта Email
)
