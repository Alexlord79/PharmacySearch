package ru.hwdoc.pharmacysearch.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import ru.hwdoc.pharmacysearch.domain.entity.Pharmacy
import ru.hwdoc.pharmacysearch.domain.entity.PharmacyFilters

@Dao
interface PharmacyDao {

    @Transaction
    @Query("SELECT * FROM pharmacies")
    fun getAllPharmacies(): Flow<List<Pharmacy>>

    @Transaction
    @Query("SELECT * FROM pharmacies WHERE id = :id")
    fun getPharmacyById(id: Int): Flow<Pharmacy>

    @Transaction
    @Query("""
        SELECT pharmacies.* FROM pharmacies JOIN 
    """)
    fun searchPharmacies(query: PharmacyFilters): Flow<List<Pharmacy>>
}