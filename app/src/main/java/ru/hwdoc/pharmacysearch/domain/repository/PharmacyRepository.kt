package ru.hwdoc.pharmacysearch.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.hwdoc.pharmacysearch.domain.entity.Pharmacy
import ru.hwdoc.pharmacysearch.domain.entity.PharmacyFilters

interface PharmacyRepository {

    fun getAllPharmacies(): Flow<List<Pharmacy>>

    suspend fun getPharmacyByNumber(number: Int): Pharmacy

    fun searchPharmacy(filters: PharmacyFilters): Flow<List<Pharmacy>>

}