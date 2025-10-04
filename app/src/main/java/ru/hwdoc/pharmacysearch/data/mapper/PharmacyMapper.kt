package ru.hwdoc.pharmacysearch.data.mapper

import ru.hwdoc.pharmacysearch.data.local.entity.InternetProviderDbModel
import ru.hwdoc.pharmacysearch.data.local.entity.LegalEntityDbModel
import ru.hwdoc.pharmacysearch.data.local.entity.PersonDbModel
import ru.hwdoc.pharmacysearch.data.local.entity.PharmacyWithAllDataDbModel
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
import kotlin.collections.toSet

fun PharmacyWithAllDataDbModel.toEntity(): Pharmacy {
    return with(pharmacy) {
        Pharmacy(
            id = id,
            number = number,
            pharmacyBrand = PharmacyBrand.valueOf(pharmacyBrand),
            region = region,
            districtOfTheRegion = districtOfTheRegion,
            localityType = LocalityType.valueOf(localityType),
            locality = locality,
            address = address,
            yandexMapsLink = yandexMapsLink,
            phoneNumber = phoneNumber,
            openingTime = openingTime,
            closingTime = closingTime,
            pharmacyManageress = pharmacyManageress.toEntity(),
            directorOfMacroregion = directorOfMacroregion.toEntity(),
            headOfTheRegional = headOfTheRegional.toEntity(),
            manager = manager.toEntity(),
            openingDate = openingDate,
            legalEntity = legalEntity.toEntity(),
            pharmacyType = PharmacyType.valueOf(pharmacyType),
            ownershipStatus = OwnershipStatus.valueOf(ownershipStatus),
            email = email,
            vsa = vsa.toEntity(),
            internetProvider = internetProvider.toEntity(),
            k = k,
            openingHours = openingHours,
            workingCashRegisters = workingCashRegisters,
            modulesCashRegister = modulesCashRegister,
            floor = floor,
            totalArea = totalArea,
            areaOfTheTradingFloor = areaOfTheTradingFloor,
            kw = kw,
            numberOfSplits = numberOfSplits,
            semiIndustrial = semiIndustrial,
            coolness = coolness,
            drivingRoute = drivingRoute,
            availableDays = availableDays.toDayOfWeekSet()
        )
    }
}

fun List<PharmacyWithAllDataDbModel>.toEntity(): List<Pharmacy> {
    return this.map{
        it.toEntity()
    }
}

private fun String.toDayOfWeekSet(): Set<DayOfWeek> {
    return if (this.isBlank()) {
        emptySet()
    } else {
        this.trim().split(",")
            .map { DayOfWeek.valueOf(it.uppercase()) }
            .toSet()
    }
}

private fun PersonDbModel.toEntity(): Person {
    return Person(
        id = this.id,
        fullName = this.fullName,
        position = this.position,
        mobilePhone = this.phoneNumber,
        email = this.email
    )
}

private fun LegalEntityDbModel.toEntity(): LegalEntity {
    return LegalEntity(
        id = this.id,
        fullName = this.fullName,
        legalAddress = this.legalAddress,
        inn = this.inn,
        ogrn = this.ogrn,
        superintendent = this.superintendent,
        basisOfAuthority = this.basisOfAuthority
    )
}

private fun VsaDbModel.toEntity(): Vsa {
    return Vsa(
        id = this.id,
        fullName = this.fullName,
        districtName = this.districtName,
        phoneNumber = this.phoneNumber,
        email = this.email
    )
}

private fun InternetProviderDbModel.toEntity(): InternetProvider {
    return InternetProvider(
        id = this.id,
        fullName = this.fullName,
        phoneNumber = this.phoneNumber,
        email = this.email
    )
}