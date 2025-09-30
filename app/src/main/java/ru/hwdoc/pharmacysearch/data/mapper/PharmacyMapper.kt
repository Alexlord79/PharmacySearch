package ru.hwdoc.pharmacysearch.data.mapper

import ru.hwdoc.pharmacysearch.data.local.entity.AvailableDaysDbModel
import ru.hwdoc.pharmacysearch.data.local.entity.InternetProviderDbModel
import ru.hwdoc.pharmacysearch.data.local.entity.LegalEntityDbModel
import ru.hwdoc.pharmacysearch.data.local.entity.PersonDbModel
import ru.hwdoc.pharmacysearch.data.local.entity.PharmacyDbModel
import ru.hwdoc.pharmacysearch.data.local.entity.VsaDbModel
import ru.hwdoc.pharmacysearch.domain.entity.DayOfWeek
import ru.hwdoc.pharmacysearch.domain.entity.InternetProvider
import ru.hwdoc.pharmacysearch.domain.entity.LegalEntity
import ru.hwdoc.pharmacysearch.domain.entity.LocalityType
import ru.hwdoc.pharmacysearch.domain.entity.OwnershipStatus
import ru.hwdoc.pharmacysearch.domain.entity.Person
import ru.hwdoc.pharmacysearch.domain.entity.Pharmacy
import ru.hwdoc.pharmacysearch.domain.entity.PharmacyBrand
import ru.hwdoc.pharmacysearch.domain.entity.PharmacyType
import ru.hwdoc.pharmacysearch.domain.entity.Vsa

fun PharmacyDbModel.toEntity(
    pharmacyManageress: PersonDbModel,
    directorOfMacroregion: PersonDbModel,
    headOfTheRegional: PersonDbModel,
    manager: PersonDbModel,
    legalEntity: LegalEntityDbModel,
    vsa: VsaDbModel,
    internetProvider: InternetProviderDbModel,
    availableDays: List<AvailableDaysDbModel>
): Pharmacy {
    return Pharmacy(
        id = this.id,
        number = this.number,
        pharmacyBrand = PharmacyBrand.valueOf(this.pharmacyBrand),
        region = this.region,
        districtOfTheRegion = this.districtOfTheRegion,
        localityType = LocalityType.valueOf(this.localityType),
        locality = locality,
        address = this.address,
        yandexMapsLink = this.yandexMapsLink,
        mobileNumber = this.mobileNumber,
        openingTime = this.openingTime,
        closingTime = this.closingTime,
        pharmacyManageress = mapPersonDbModelToEntity(pharmacyManageress),  // заведка
        directorOfMacroregion = mapPersonDbModelToEntity(directorOfMacroregion),// руководитель макрорегиона
        headOfTheRegional = mapPersonDbModelToEntity(headOfTheRegional),    // руководитель региона
        manager = mapPersonDbModelToEntity(manager),                        // управляющий (завхоз)
        openingDate = this.openingDate,                                                     // дата открытия аптеки
        legalEntity = mapLegalEntityDbModelToEntity(legalEntity),         // юридическое лицо
        pharmacyType = PharmacyType.valueOf(this.pharmacyType),                      // тип аптеки: аптека или аптечный пункт
        ownershipStatus = OwnershipStatus.valueOf(this.ownershipStatus),   // статус собственности помещения: собственность или аренда
        email = this.email,
        vsa = mapVsaDbModelToEntity(vsa),                           // ВСА
        internetProvider = mapInternetProviderDbModelToEntity(internetProvider), // провайдер интернет-услуг
        k = this.k,                          // не знаю зачем нужен
        openingHours = this.openingHours,               // общее количесво часов когда аптека открыта
        workingCashRegisters = this.workingCashRegisters,          // количество рабочих касс (кассовых модулей)
        modulesCashRegister = this.modulesCashRegister,           // количество кассовых модулей (всех, даже не трансформированных в витрины)
        floor = this.floor,                      // этаж расположения аптеки
        totalArea = this.totalArea,                  // Общая площадь аптеки
        areaOfTheTradingFloor = this.areaOfTheTradingFloor,      // площадь торгового зала
        kW = this.kW,                         // расход киловатт (наверное)
        numberOfSplits = this.numberOfSplits,                // колическтво сплитов в аптеке
        semiIndustrial = this.semiIndustrial,                // полупромышленные
        coolness = this.coolness,                      // прохлад (не знаю что такое)
        // информация о машине с товаром
        drivingRoute = this.drivingRoute,               // маршрут движения машины
        availableDays = availableDays.map { DayOfWeek.valueOf(it.dayOfWeek) }.toSet()
    )
}

private fun mapPersonDbModelToEntity(personDbModel: PersonDbModel): Person {
    return Person(
        id = personDbModel.id,
        fullName = personDbModel.fullName,
        position = personDbModel.position,
        mobilePhone = personDbModel.mobilePhone,
        email = personDbModel.email
    )
}

private fun mapLegalEntityDbModelToEntity(legalEntityDbModel: LegalEntityDbModel): LegalEntity {
    return LegalEntity(
        id = legalEntityDbModel.id,
        fullName = legalEntityDbModel.fullName,
        legalAddress = legalEntityDbModel.legalAddress,
        inn = legalEntityDbModel.inn,
        ogrn = legalEntityDbModel.ogrn,
        superintendentId = legalEntityDbModel.superintendentId,
        basisOfAuthority = legalEntityDbModel.basisOfAuthority
    )
}

private fun mapVsaDbModelToEntity(vsaDbModel: VsaDbModel): Vsa {
    return Vsa(
        id = vsaDbModel.id,
        fullName = vsaDbModel.fullName,
        districtName = vsaDbModel.districtName,
        mobilePhone = vsaDbModel.mobilePhone,
        email = vsaDbModel.email
    )
}

private fun mapInternetProviderDbModelToEntity(internetProviderDbModel: InternetProviderDbModel): InternetProvider {
    return InternetProvider(
        id = internetProviderDbModel.id,
        name = internetProviderDbModel.nameProviders,
        phoneNumber = internetProviderDbModel.phoneNumber,
        email = internetProviderDbModel.email
    )
}
