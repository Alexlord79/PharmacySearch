package ru.hwdoc.pharmacysearch.domain.entity

//Типы населенных пунктов
enum class LocalityType(val nameOfLocalityType: String) {
    CITY("Город"),
    URBAN_TYPE_SETTLEMENT("пгт"),
    STANITSA("Станица"),
    VILLAGE("Село"),
    AUL("Аул"),
    TOWNSHIP("Поселок"),
    WORKTOWNSHIP("Рабочий посёлок"),
    VILLAGE_SMALL("Деревня"),
    HAMLET("Хутор"),
    NO_DATA_AVAILABLE("Нет данных");
}

//Дни недели (когда доставка товара в аптеки)
enum class DayOfWeek(val nameOfDayOfWeek: String) {
    MON("Пн"),
    TUE("Вт"),
    WED("Ср"),
    THU("Чт"),
    FRI("Пт"),
    SAT("Сб"),
    SUN("Вс");
}

//Сегменты сети аптек
enum class PharmacyBrand(val nameOfBrand: String) {
    ZDOROVIE("Здоровье"),
    MINIPRICE("Миницена"),
    NO_DATA_AVAILABLE("");
}

//Тип: аптека или аптечный пункт
enum class PharmacyType(val nameOfPharmacyType: String) {
    PHARMACY("Аптека"),
    PHARMACY_POINT("Аптечный пункт"),
    NO_DATA_AVAILABLE("");
}

//статус собственности помещения
enum class OwnershipStatus(val nameOfOwnershipStatus: String) {
    OWNED("Собственность"),
    RENTED("Аренда"),
    NO_DATA_AVAILABLE("");
}