package ru.hwdoc.pharmacysearch.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "persons",
    indices = [
        Index(value = ["full_name"]),   // индекс на ФИО сотрудника
    ]
)
data class PersonDbModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,                //id в локальной БД
    @ColumnInfo(name = "full_name", defaultValue = "Нет данных")
    val fullName: String,           //полное ФИО
    @ColumnInfo(name = "phone_number", defaultValue = "Нет данных")
    val phoneNumber: String? = null,        //мобильный телефон
    @ColumnInfo(defaultValue = "Нет данных")
    val position: String? = null,           //должность
    @ColumnInfo(defaultValue = "Нет данных")
    val email: String? = null               //почта Email
)
