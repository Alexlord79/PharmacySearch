package ru.hwdoc.pharmacysearch.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import ru.hwdoc.pharmacysearch.data.local.entity.PharmacyWithAllDataDbModel
import ru.hwdoc.pharmacysearch.domain.entity.PharmacyFilters

@Dao
interface PharmacyDao {

    @Transaction
    @Query("SELECT * FROM pharmacies ORDER BY number ASC")
    fun getAllPharmacy(): Flow<List<PharmacyWithAllDataDbModel>>

    @Transaction
    @Query("SELECT * FROM pharmacies WHERE number = :numberPharmacy")
    suspend fun getPharmacyByNumber(numberPharmacy: Int): PharmacyWithAllDataDbModel

    @Transaction
    @Query(
        """
        SELECT DISTINCT p.* FROM pharmacies p
        LEFT JOIN vsa v ON p.vsa_id = v.id
        LEFT JOIN internet_providers ip ON p.internet_provider_id = ip.id
        WHERE 
            (:searchAll = 1 AND (
                p.number LIKE '%' || :query || '%' OR
                p.locality LIKE '%' || :query || '%' OR
                p.address LIKE '%' || :query || '%' /*OR
                p.phone_number LIKE '%' || :query || '%' OR
                v.full_name LIKE '%' || :query || '%' OR
                ip.full_name LIKE '%' || :query || '%'*/
            ))
            OR (:searchNumber = 1 AND p.number = CAST(:query AS INTEGER))
            OR (:searchLocality = 1 AND p.locality LIKE '%' || :query || '%')
            OR (:searchAddress = 1 AND p.address LIKE '%' || :query || '%')
            OR (:searchPhoneNumber = 1 AND p.phone_number LIKE '%' || :query || '%')
            OR (:searchVsa = 1 AND v.full_name LIKE '%' || :query || '%')
            OR (:searchInternetProvider = 1 AND ip.full_name LIKE '%' || :query || '%')
        ORDER BY p.number ASC
    """
    )
    fun searchPharmacy(
        query: String,
        searchAll: Boolean,
        searchNumber: Boolean,
        searchLocality: Boolean,
        searchAddress: Boolean,
        searchPhoneNumber: Boolean,
        searchVsa: Boolean,
        searchInternetProvider: Boolean
    ): Flow<List<PharmacyWithAllDataDbModel>>

    fun searchPharmacy(filters: PharmacyFilters): Flow<List<PharmacyWithAllDataDbModel>> {
        return searchPharmacy(
            query = filters.query,
            searchAll = filters.isAll,
            searchNumber = filters.isNumber,
            searchLocality = filters.isLocality,
            searchAddress = filters.isAddress,
            searchPhoneNumber = false,//filters.isPhoneNumber,
            searchVsa = false,//filters.isVsa,
            searchInternetProvider = false,//filters.isInternetProvider
        )
    }

}