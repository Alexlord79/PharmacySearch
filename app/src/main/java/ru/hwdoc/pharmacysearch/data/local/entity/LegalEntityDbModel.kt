package ru.hwdoc.pharmacysearch.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "legal_entity",
    indices = [
        Index(value = ["fullName"], unique = true), // Уникальный индекс на нименование организации
        Index(value = ["inn"], unique = true)       // индекс на ИНН
    ]
)
data class LegalEntityDbModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "full_name")
    val fullName: String,               // Полное наименование юридического лица
    @ColumnInfo(name = "legal_address")
    val legalAddress: String,           // Юридический адрес регистрации
    val inn: String,                    // ИНН (Идентификационный номер налогоплательщика)
    val ogrn: String,                   // ОГРН (Основной государственный регистрационный номер)
    val superintendent: String,         // ID управляющего лица
    @ColumnInfo(name = "basis_of_authority")
    val basisOfAuthority: String        // Документ-основание полномочий
)
