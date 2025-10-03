package ru.hwdoc.pharmacysearch.data.local.entity

import androidx.room.ColumnInfo
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
    @ColumnInfo(name = "pharmacy_id")
    val pharmacyId: Int,    // ID аптеки (внешний ключ)
    @ColumnInfo(name = "day_of_week")
    val dayOfWeek: String   // день недели доставки (сериализованная строка)
)
