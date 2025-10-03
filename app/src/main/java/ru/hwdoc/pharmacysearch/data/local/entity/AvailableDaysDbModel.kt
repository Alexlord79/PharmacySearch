package ru.hwdoc.pharmacysearch.data.local.entity

//@Entity(
//    tableName = "available_days",
//    indices = [
//        Index(value = ["pharmacyId", "dayOfWeek"], unique = true)
//    ],
//    foreignKeys = [
//        ForeignKey(
//            entity = PharmacyDbModel::class,
//            parentColumns = ["id"],
//            childColumns = ["pharmacyId"],
//            onDelete = ForeignKey.CASCADE
//        )
//    ]
//)
//data class AvailableDaysDbModel(
//    @PrimaryKey(autoGenerate = true)
//    val id: Int,
//    @ColumnInfo(name = "pharmacy_id")
//    val pharmacyId: Int,    // ID аптеки (внешний ключ)
//    @ColumnInfo(name = "day_of_week")
//    val dayOfWeek: String   // день недели доставки (сериализованная строка)
//)
