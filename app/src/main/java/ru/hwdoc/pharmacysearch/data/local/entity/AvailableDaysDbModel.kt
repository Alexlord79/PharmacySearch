package ru.hwdoc.pharmacysearch.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "available_days",
    indices = [
        Index(value = ["pharmacyId", "dayOfWeek"], unique = true)
    ],
    foreignKeys = [
        ForeignKey(
            entity = PharmacyDbModel::class,
            parentColumns = ["id"],
            childColumns = ["pharmacyId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class AvailableDaysDbModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val pharmacyId: Int,    // ID аптеки (внешний ключ)
    val dayOfWeek: String   // дни недели доставки (сериализованная строка)
)
