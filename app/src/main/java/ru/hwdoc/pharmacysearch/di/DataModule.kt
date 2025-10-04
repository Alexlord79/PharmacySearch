package ru.hwdoc.pharmacysearch.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.hwdoc.pharmacysearch.data.local.dao.PharmacyDao
import ru.hwdoc.pharmacysearch.data.local.database.PharmacyDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

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
            ).fallbackToDestructiveMigration(true).build()
        }

        @Provides
        @Singleton
        fun providePharmacyDao(
            database: PharmacyDatabase
        ): PharmacyDao = database.pharmacyDao()
    }
}