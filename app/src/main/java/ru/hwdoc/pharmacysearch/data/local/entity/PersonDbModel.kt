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
    @ColumnInfo(name = "full_name", defaultValue = "нет_данных")
    val fullName: String,           //полное ФИО
    @ColumnInfo(name = "phone_number", defaultValue = "нет_данных")
    val phoneNumber: String,        //мобильный телефон
    @ColumnInfo(defaultValue = "нет_данных")
    val position: String,           //должность
    @ColumnInfo(defaultValue = "нет_данных")
    val email: String               //почта Email
)
