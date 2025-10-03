package ru.hwdoc.pharmacysearch.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "pharmacies",
    indices = [
        // Индексы для быстрого поиска по полям
        Index(value = ["number"], unique = true),
        Index(value = ["pharmacy_manageress_id"]),          // Индекс на заведующую
        Index(value = ["director_of_macroregion_id"]),      // Индекс на руководителя макрорегиона
        Index(value = ["manager_id"]),                      // Индекс на управляющего
        Index(value = ["legal_entity_id"]),                 // Индекс на юридическое лицо
        Index(value = ["vsa_id"]),                          // Индекс на ВСА
        Index(value = ["internet_provider_id"]),            // Индекс на интернет-провайдера
        Index(value = ["available_days"])                   // Индекс на дни доставки
    ],
    foreignKeys = [
        // Связи с другими сущностями (внешние ключи)
        // Внешний ключ к таблице persons для заведующих
        ForeignKey(
            entity = PersonDbModel::class,
            parentColumns = ["id"],
            childColumns = ["pharmacy_manageress_id"],
            onDelete = ForeignKey.SET_NULL
        ),
        // Внешний ключ к таблице persons для руководителя макрорегиона
        ForeignKey(
            entity = PersonDbModel::class,
            parentColumns = ["id"],
            childColumns = ["director_of_macroregion_id"],
            onDelete = ForeignKey.SET_NULL
        ),
        // Внешний ключ к таблице persons для руководителя региона
        ForeignKey(
            entity = PersonDbModel::class,
            parentColumns = ["id"],
            childColumns = ["head_of_the_regional_id"],
            onDelete = ForeignKey.SET_NULL
        ),
        // Внешний ключ к таблице persons для управляющего
        ForeignKey(
            entity = PersonDbModel::class,
            parentColumns = ["id"],
            childColumns = ["manager_id"],
            onDelete = ForeignKey.SET_NULL
        ),
        // Внешний ключ к таблице legal_entities для юридического лица
        ForeignKey(
            entity = LegalEntityDbModel::class,
            parentColumns = ["id"],
            childColumns = ["legal_entity_id"],
            onDelete = ForeignKey.SET_NULL
        ),
        // Внешний ключ к таблице vsas для ВСА
        ForeignKey(
            entity = VsaDbModel::class,
            parentColumns = ["id"],
            childColumns = ["vsa_id"],
            onDelete = ForeignKey.SET_NULL
        ),
        // Внешний ключ к таблице internet_providers для интернет-провайдера
        ForeignKey(
            entity = InternetProviderDbModel::class,
            parentColumns = ["id"],
            childColumns = ["internet_provider_id"],
            onDelete = ForeignKey.SET_NULL
        )
    ]
)
data class PharmacyDbModel(
    // общая информация
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,                        // id аптеки в локальной БД
    val number: Int,                        // номер аптеки (видимый пользователю)
    @ColumnInfo(name = "pharmacy_brand")
    val pharmacyBrand: String,              // бренд аптеки: Миницена или Здоровье
    // Географическая информация
    val region: String,                     // Регион страны
    @ColumnInfo(name = "district_of_the_region")
    val districtOfTheRegion: String,        // Район региона
    @ColumnInfo(name = "locality_type")
    val localityType: String,               // Тип населенного пункта
    val locality: String,                   // Населенный пункт
    val address: String,                    // Адрес
    @ColumnInfo(name = "yandex_maps_link")
    val yandexMapsLink: String,             // Ссылка на Яндекс.Карты
    // Контактная информация
    @ColumnInfo(name = "phone_number")
    val phoneNumber: String,               // Основной телефон
    @ColumnInfo(name = "opening_time")
    val openingTime: String,                // Время открытия
    @ColumnInfo(name = "closing_time")
    val closingTime: String,                // Время закрытия
    // Ссылки на связанные сущности (внешние ключи)
    @ColumnInfo(name = "pharmacy_manageress_id")
    val pharmacyManageressId: Int?,          // ID заведующей аптекой
    @ColumnInfo(name = "director_of_macroregion_id")
    val directorOfMacroregionId: Int?,       // ID руководителя макрорегиона
    @ColumnInfo(name = "head_of_the_regional_id")
    val headOfTheRegionalId: Int?,           // ID руководителя региона
    @ColumnInfo(name = "manager_id")
    val managerId: Int?,                     // ID управляющего
    // Юридическая информация
    @ColumnInfo(name = "opening_date")
    val openingDate: String,                // Дата открытия
    @ColumnInfo(name = "legal_entity_id")
    val legalEntityId: Int?,                // ID юридического лица
    @ColumnInfo(name = "pharmacy_type")
    val pharmacyType: String,               // Тип аптеки `
    @ColumnInfo(name = "ownership_status")
    val ownershipStatus: String,            // Статус собственности `
    val email: String,                      // Email аптеки
    // IT
    @ColumnInfo(name = "vsa_id")
    val vsaId: Int?,                         // ID ВСА
    @ColumnInfo(name = "internet_provider_id")
    val internetProviderId: Int?,            // ID интернет-провайдера
    // Дополнительная информация
    val k: String,                          // Дополнительный параметр
    @ColumnInfo(name = "opening_hours")
    val openingHours: String,               // Количество рабочих часов
    @ColumnInfo(name = "working_cash_registers")
    val workingCashRegisters: Int,          // Рабочие кассы
    @ColumnInfo(name = "modules_cash_register")
    val modulesCashRegister: Int,           // Кассовые модули
    val floor: String,                      // Этаж
    @ColumnInfo(name = "total_area")
    val totalArea: String,                  // Общая площадь
    @ColumnInfo(name = "area_of_the_trading_floor")
    val areaOfTheTradingFloor: String,      // Площадь торгового зала
    val kw: String,                         // Расход электроэнергии
    @ColumnInfo(name = "number_of_splits")
    val numberOfSplits: Int,                // Количество сплитов
    @ColumnInfo(name = "semi_industrial")
    val semiIndustrial: Int,                // Полупромышленные кондиционеры
    val coolness: Int,                      // Параметр "прохлад"
    // Информация о доставке
    @ColumnInfo(name = "driving_route")
    val drivingRoute: String,               // Маршрут движения машины
    @ColumnInfo(name = "available_days")
    val availableDays: String               // Дни доставки
)