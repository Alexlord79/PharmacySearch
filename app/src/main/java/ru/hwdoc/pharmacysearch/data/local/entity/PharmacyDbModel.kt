package ru.hwdoc.pharmacysearch.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "pharmacies",
    indices = [
        // Индексы для быстрого поиска по полям
        Index(value = ["number"], unique = true),
        Index(value = ["pharmacyManageressId"]), // Индекс на заведующую
        Index(value = ["directorOfMacroregionId"]), // Индекс на руководителя макрорегиона
        Index(value = ["headOfTheRegionalId"]), // Индекс на руководителя региона
        Index(value = ["managerId"]), // Индекс на управляющего
        Index(value = ["legalEntityId"]), // Индекс на юридическое лицо
        Index(value = ["vsaId"]), // Индекс на ВСА
        Index(value = ["internetProviderId"]) // Индекс на интернет-провайдера
    ],
    foreignKeys = [
        // Связи с другими сущностями (внешние ключи)
        ForeignKey(
            entity = PersonDbModel::class,
            parentColumns = ["id"],
            childColumns = ["pharmacyManageressId"],
            onDelete = ForeignKey.SET_NULL
        ),
        // Внешний ключ к таблице persons для руководителя макрорегиона
        ForeignKey(
            entity = PersonDbModel::class,
            parentColumns = ["id"],
            childColumns = ["directorOfMacroregionId"],
            onDelete = ForeignKey.SET_NULL
        ),
        // Внешний ключ к таблице persons для руководителя региона
        ForeignKey(
            entity = PersonDbModel::class,
            parentColumns = ["id"],
            childColumns = ["headOfTheRegionalId"],
            onDelete = ForeignKey.SET_NULL
        ),
        // Внешний ключ к таблице persons для управляющего
        ForeignKey(
            entity = PersonDbModel::class,
            parentColumns = ["id"],
            childColumns = ["managerId"],
            onDelete = ForeignKey.SET_NULL
        ),
        // Внешний ключ к таблице legal_entities для юридического лица
        ForeignKey(
            entity = LegalEntityDbModel::class,
            parentColumns = ["id"],
            childColumns = ["legalEntityId"],
            onDelete = ForeignKey.SET_NULL
        ),
        // Внешний ключ к таблице vsas для ВСА
        ForeignKey(
            entity = VsaDbModel::class,
            parentColumns = ["id"],
            childColumns = ["vsaId"],
            onDelete = ForeignKey.SET_NULL
        ),
        // Внешний ключ к таблице internet_providers для интернет-провайдера
        ForeignKey(
            entity = InternetProviderDbModel::class,
            parentColumns = ["id"],
            childColumns = ["internetProviderId"],
            onDelete = ForeignKey.SET_NULL
        )
    ]
)
data class PharmacyDbModel(
    // общая информация
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,                        // id аптеки в локальной БД
    val number: Int,                        // номер аптеки (видимый пользователю)
    val pharmacyBrand: String,              // бренд аптеки: Миницена или Здоровье
    // Географическая информация
    val region: String,                     // Регион страны
    val districtOfTheRegion: String,        // Район региона
    val localityType: String,               // Тип населенного пункта (сериализованная строка)
    val locality: String,                   // Населенный пункт
    val address: String,                    // Адрес
    val yandexMapsLink: String,             // Ссылка на Яндекс.Карты

    // Контактная информация
    val mobileNumber: String,               // Основной телефон
    val openingTime: String,                // Время открытия
    val closingTime: String,                // Время закрытия

    // Ссылки на связанные сущности (внешние ключи)
    val pharmacyManageressId: Int?,          // ID заведующей аптекой
    val directorOfMacroregionId: Int?,       // ID руководителя макрорегиона
    val headOfTheRegionalId: Int?,           // ID руководителя региона
    val managerId: Int?,                     // ID управляющего

    // Юридическая информация
    val openingDate: String,                // Дата открытия
    val legalEntityId: Int?,                // ID юридического лица
    val pharmacyType: String,               // Тип аптеки (сериализованная строка)
    val ownershipStatus: String,            // Статус собственности (сериализованная строка)
    val email: String,                      // Email аптеки

    // IT инфраструктура
    val vsaId: Int?,                         // ID ВСА
    val internetProviderId: Int?,            // ID интернет-провайдера

    // Дополнительная информация
    val k: String,                          // Дополнительный параметр
    val openingHours: String,               // Количество рабочих часов
    val workingCashRegisters: Int,          // Рабочие кассы
    val modulesCashRegister: Int,           // Кассовые модули
    val floor: String,                      // Этаж
    val totalArea: String,                  // Общая площадь
    val areaOfTheTradingFloor: String,      // Площадь торгового зала
    val kW: String,                         // Расход электроэнергии
    val numberOfSplits: Int,                // Количество сплитов
    val semiIndustrial: Int,                // Полупромышленные кондиционеры
    val coolness: Int,                      // Параметр "прохлад"

    // Информация о доставке
    val drivingRoute: String                // Маршрут движения машины
)