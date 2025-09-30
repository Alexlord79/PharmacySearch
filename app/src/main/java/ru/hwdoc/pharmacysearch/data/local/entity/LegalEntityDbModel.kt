package ru.hwdoc.pharmacysearch.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "legal_entity",
    indices = [
        Index(value = ["fullName"], unique = true),
        Index(value = ["inn"], unique = true),
        Index(value = ["ogrn"], unique = true)
    ]
)
data class LegalEntityDbModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val fullName: String,           // Полное наименование юридического лица
    val legalAddress: String,       // Юридический адрес регистрации
    val inn: String,                // ИНН (Идентификационный номер налогоплательщика)
    val ogrn: String,               // ОГРН (Основной государственный регистрационный номер)
    val superintendentId: Int,      // ID управляющего лица
    val basisOfAuthority: String    // Документ-основание полномочий
)
