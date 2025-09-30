package ru.hwdoc.pharmacysearch.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.hwdoc.pharmacysearch.data.local.dao.PharmacyDao
import ru.hwdoc.pharmacysearch.data.local.entity.AvailableDaysDbModel
import ru.hwdoc.pharmacysearch.data.local.entity.InternetProviderDbModel
import ru.hwdoc.pharmacysearch.data.local.entity.LegalEntityDbModel
import ru.hwdoc.pharmacysearch.data.local.entity.PersonDbModel
import ru.hwdoc.pharmacysearch.data.local.entity.PharmacyDbModel
import ru.hwdoc.pharmacysearch.data.local.entity.VsaDbModel

@Database(
    entities = [
            PharmacyDbModel::class,
            VsaDbModel::class,
            PersonDbModel::class,
            LegalEntityDbModel::class,
            InternetProviderDbModel::class,
            AvailableDaysDbModel::class
               ],
    version = 1,
    exportSchema = false)
abstract class PharmacyDatabase: RoomDatabase() {
    abstract fun pharmacyDao(): PharmacyDao
}