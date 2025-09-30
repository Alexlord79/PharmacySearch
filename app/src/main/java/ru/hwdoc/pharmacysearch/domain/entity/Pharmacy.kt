package ru.hwdoc.pharmacysearch.domain.entity

data class Pharmacy(
    // общая информация
    val id: Int = 0,                        // id аптеки, из базы данных
    val number: Int,                        // номер аптеки (видимый пользователю)
    val pharmacyBrand: PharmacyBrand,       // бренд аптеки: Миницена или Здоровье
    // географическая информация
    val region: String,                     // регион страны (какой край, область)(Краснодарский край)
    val districtOfTheRegion: String,        // район региона (Каневской район Краснодарского края)
    val localityType: LocalityType,         // тип населенного пункта
    val locality: String,                   // населенный пункт
    val address: String,                    // адрес (улица, дом, помещение)
    val yandexMapsLink: String,             // координаты расположения аптеки на Яндекс.Картах
    // контактная информация и время работы
    val mobileNumber: String,               // основной контактный телефон аптеки
    val openingTime: String,                // время открытия
    val closingTime: String,                // время закрытия
    // персонал
    val pharmacyManageress: Person,         // заведка
    val directorOfMacroregion: Person,      // руководитель макрорегиона
    val headOfTheRegional: Person,          // руководитель региона
    val manager: Person,                    // управляющий (завхоз)
    // юридическая информация
    val openingDate: String,                // дата открытия аптеки
    val legalEntity: LegalEntity,           // юридическое лицо
    val pharmacyType: PharmacyType,         // тип аптеки: аптека или аптечный пункт
    val ownershipStatus: OwnershipStatus,   // статус собственности помещения: собственность или аренда
    val email: String,                      // mail аптеки
    // IT инфраструктура
    val vsa: Vsa,                           // ВСА
    val internetProvider: InternetProvider, // провайдер интернет-услуг
    // доп. информация об аптеке
    val k: String,                          // не знаю зачем нужен
    val openingHours: String,               // общее количесво часов когда аптека открыта
    val workingCashRegisters: Int,          // количество рабочих касс (кассовых модулей)
    val modulesCashRegister: Int,           // количество кассовых модулей (всех, даже не трансформированных в витрины)
    val floor: String,                      // этаж расположения аптеки
    val totalArea: String,                  // Общая площадь аптеки
    val areaOfTheTradingFloor: String,      // площадь торгового зала
    val kW: String,                         // расход киловатт (наверное)
    val numberOfSplits: Int,                // колическтво сплитов в аптеке
    val semiIndustrial: Int,                // полупромышленные
    val coolness: Int,                      // прохлад (не знаю что такое)
    // информация о машине с товаром
    val drivingRoute: String,               // маршрут движения машины
    val availableDays: Set<DayOfWeek> = emptySet() // дни недели, когда есть машина с товаром
)