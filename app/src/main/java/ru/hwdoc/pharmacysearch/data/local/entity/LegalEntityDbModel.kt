package ru.hwdoc.pharmacysearch.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "legal_entity",
    indices = [
        Index(value = ["full_name"], unique = true), // Уникальный индекс на нименование организации
        Index(value = ["inn"], unique = true)       // индекс на ИНН
    ]
)
data class LegalEntityDbModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "full_name")
    val fullName: String,               // Полное наименование юридического лица
    @ColumnInfo(name = "legal_address", defaultValue = "Нет данных")
    val legalAddress: String? = null,           // Юридический адрес регистрации
    @ColumnInfo(defaultValue = "Нет данных")
    val inn: String? = null,                    // ИНН (Идентификационный номер налогоплательщика)
    @ColumnInfo(defaultValue = "Нет данных")
    val ogrn: String? = null,                   // ОГРН (Основной государственный регистрационный номер)
    @ColumnInfo(defaultValue = "Нет данных")
    val superintendent: String? = null,         // ID управляющего лица
    @ColumnInfo(name = "basis_of_authority", defaultValue = "Нет данных")
    val basisOfAuthority: String? = null        // Документ-основание полномочий
)
