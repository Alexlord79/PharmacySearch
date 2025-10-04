package ru.hwdoc.pharmacysearch.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import ru.hwdoc.pharmacysearch.data.local.entity.PharmacyWithAllDataDbModel

@Dao
interface PharmacyDao {

    @Transaction
    @Query("SELECT * FROM pharmacies ORDER BY number ASC")
    fun getAllPharmacy(): Flow<List<PharmacyWithAllDataDbModel>>

    @Transaction
    @Query("SELECT * FROM pharmacies WHERE number = :numberPharmacy")
    suspend fun getPharmacyByNumber(numberPharmacy: Int): PharmacyWithAllDataDbModel

    /*
    @Transaction
    @Query(
        """
        SELECT DISTINCT * FROM (
            SELECT p.* FROM pharmacies p
            WHERE (:searchNumber = 1 AND p.number LIKE '%' || :query || '%')
            UNION
            SELECT p.* FROM pharmacies p
            WHERE (:searchLocality = 1 AND p.locality LIKE '%' || :query || '%')
            UNION
            SELECT p.* FROM pharmacies p
            WHERE (:searchAddress = 1 AND p.address LIKE '%' || :query || '%')
            UNION
            SELECT p.* FROM pharmacies p
            WHERE (:searchMobileNumber = 1 AND p.phoneNumber LIKE '%' || :query || '%')
            UNION
            SELECT p.* FROM pharmacies p
            JOIN vsa v ON p.vsaId = v.id
            WHERE (:searchVsa = 1 AND v.fullName LIKE '%' || :query || '%')
            UNION
            SELECT p.* FROM pharmacies p
            JOIN internet_providers ip ON p.internetProviderId = ip.id
            WHERE (:searchInternetProvider = 1 AND ip.nameProviders LIKE '%' || :query || '%')
        )
        ORDER BY number ASC
    """
    )
    fun searchPharmacy(query: String,
                         searchNumber: Boolean,
                         searchLocality: Boolean,
                         searchAddress: Boolean,
                         searchMobileNumber: Boolean,
                         searchVsa: Boolean,
                         searchInternetProvider: Boolean
    ): Flow<List<PharmacyDbModel>>
     */
}