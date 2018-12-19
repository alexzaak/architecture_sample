package codes.zaak.architecturesample.di

import android.app.Application
import androidx.room.Room
import codes.zaak.architecturesample.repository.db.AppDatabase
import codes.zaak.architecturesample.repository.db.CharacterDao
import codes.zaak.architecturesample.repository.db.SagaDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(application: Application): AppDatabase {
        return Room.databaseBuilder(application, AppDatabase::class.java, "dbz.db").allowMainThreadQueries().build()
    }

    @Provides
    @Singleton
    fun provideCharacterDao(database: AppDatabase): CharacterDao {
        return database.characterDao()
    }

    @Provides
    @Singleton
    fun provideSagaDao(database: AppDatabase): SagaDao {
        return database.sagaDao()
    }
}