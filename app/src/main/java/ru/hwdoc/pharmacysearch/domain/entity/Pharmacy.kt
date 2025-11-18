package ru.hwdoc.pharmacysearch.domain.entity

data class Pharmacy(
    // общая информация
    val id: Int = 0,                        // id аптеки, из базы данных
    val number: Int,                        // номер аптеки (видимый пользователю)
    val pharmacyBrand: PharmacyBrand,       // бренд аптеки: Миницена или Здоровье
    // географическая информация
    val region: String = "Нет данных",                     // регион страны (какой край, область)(Краснодарский край)
    val districtOfTheRegion: String = "Нет данных",        // район региона (Каневской район Краснодарского края)
    val localityType: LocalityType,         // тип населенного пункта
    val locality: String = "Нет данных",                   // населенный пункт
    val address: String = "Нет данных",                    // адрес (улица, дом, помещение)
    val yandexMapsLink: String = "Нет данных",             // координаты расположения аптеки на Яндекс.Картах
    // контактная информация и время работы
    val phoneNumber: String = "Нет данных",               // основной контактный телефон аптеки
    val openingTime: String = "Нет данных",                // время открытия
    val closingTime: String = "Нет данных",                // время закрытия
    // персонал
    val pharmacyManageress: Person,         // заведка
    val directorOfMacroregion: Person,      // руководитель макрорегиона
    val headOfTheRegional: Person,          // руководитель региона
    val manager: Person,                    // управляющий (завхоз)
    // юридическая информация
    val openingDate: String = "Нет данных",                // дата открытия аптеки
    val legalEntity: LegalEntity,           // юридическое лицо
    val pharmacyType: PharmacyType,         // тип аптеки: аптека или аптечный пункт
    val ownershipStatus: OwnershipStatus,   // статус собственности помещения: собственность или аренда
    val email: String = "Нет данных",                      // mail аптеки
    // IT инфраструктура
    val vsa: Vsa,                           // ВСА
    val internetProvider: InternetProvider, // провайдер интернет-услуг
    // доп. информация об аптеке
    val k: String = "Нет данных",                          // не знаю зачем нужен
    val openingHours: String = "Нет данных",               // общее количесво часов когда аптека открыта
    val workingCashRegisters: String = "Нет данных",          // количество рабочих касс (кассовых модулей)
    val modulesCashRegister: String = "Нет данных",           // количество кассовых модулей (всех, даже не трансформированных в витрины)
    val floor: String = "Нет данных",                      // этаж расположения аптеки
    val totalArea: String = "Нет данных",                  // Общая площадь аптеки
    val areaOfTheTradingFloor: String = "Нет данных",      // площадь торгового зала
    val kw: String = "",                         // расход киловатт (наверное)
    val numberOfSplits: String = "Нет данных",                // колическтво сплитов в аптеке
    val semiIndustrial: String = "Нет данных",                // полупромышленные
    val coolness: String = "Нет данных",                      // прохлад (не знаю что такое)
    // информация о машине с товаром
    val drivingRoute: String = "Нет данных",               // маршрут движения машины
    val availableDays: Set<DayOfWeek> = emptySet() // дни недели, когда есть машина с товаром
)