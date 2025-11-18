package ru.hwdoc.pharmacysearch.di

import android.content.Context
import androidx.room.Room
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.hwdoc.pharmacysearch.data.local.dao.PharmacyDao
import ru.hwdoc.pharmacysearch.data.local.database.PharmacyDatabase
import ru.hwdoc.pharmacysearch.data.repository.PharmacyRepositoryImpl
import ru.hwdoc.pharmacysearch.domain.repository.PharmacyRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    @Singleton
    fun bindPharmacyRepository(
        impl: PharmacyRepositoryImpl
    ): PharmacyRepository


    companion object {

        @Provides
        @Singleton
        fun providePharmacyDatabase(
            @ApplicationContext context: Context
        ): PharmacyDatabase {
            return Room.databaseBuilder(
                context = context,
                klass = PharmacyDatabase::class.java,
                name = "pharmacy.db"
            )
                .fallbackToDestructiveMigration(true)
                .createFromAsset("bd_to_upload_to_room.db")
                .build()
        }

        @Provides
        @Singleton
        fun providePharmacyDao(
            database: PharmacyDatabase
        ): PharmacyDao = database.pharmacyDao()
    }
}