package ru.hwdoc.pharmacysearch.domain.entity

//Типы населенных пунктов
enum class LocalityType(val nameOfLocalityType: String) {

    CITY("Город"),
    URBAN_TYPE_SETTLEMENT("п.г.т."),
    STANITSA("Станица"),
    VILLAGE("Село"),
    AUL("Аул"),
    VILLAGE_SMALL("Деревня"),
    HAMLET("Хутор");
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
    MINITSENA("Миницена");
}

//Тип: аптека или аптечный пункт
enum class PharmacyType(val nameOfPharmacyType: String) {
    PHARMACY("Аптека"),
    PHARMACY_POINT("Аптечный пункт");
}

//статус собственности помещения
enum class OwnershipStatus(val nameOfOwnershipStatus: String) {
    OWNED("Собственность"),
    RENTED("Аренда");
}