package ru.hwdoc.pharmacysearch.data.repository

import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.hwdoc.pharmacysearch.data.local.dao.PharmacyDao
import ru.hwdoc.pharmacysearch.data.mapper.toEntity
import ru.hwdoc.pharmacysearch.domain.entity.Pharmacy
import ru.hwdoc.pharmacysearch.domain.entity.PharmacyFilters
import ru.hwdoc.pharmacysearch.domain.repository.PharmacyRepository

class PharmacyRepositoryImpl @Inject constructor(
    private val pharmacyDao: PharmacyDao
): PharmacyRepository {
    override fun getAllPharmacies(): Flow<List<Pharmacy>> {
        return pharmacyDao.getAllPharmacy().map {
            it.toEntity()
        }
    }

    override suspend fun getPharmacyByNumber(number: Int): Pharmacy {
        return pharmacyDao.getPharmacyByNumber(number).toEntity()
    }

    override fun searchPharmacy(filters: PharmacyFilters): Flow<List<Pharmacy>> {
        return pharmacyDao.searchPharmacy(filters).map { it.toEntity() }
    }
}