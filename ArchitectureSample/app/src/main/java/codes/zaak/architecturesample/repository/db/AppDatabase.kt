package codes.zaak.architecturesample.repository.db

import androidx.room.Database
import androidx.room.RoomDatabase
import codes.zaak.architecturesample.repository.model.entity.CharacterEntity
import codes.zaak.architecturesample.repository.model.entity.SagaEntity
import codes.zaak.architecturesample.repository.model.response.Character
import codes.zaak.architecturesample.repository.model.response.Saga

@Database(entities = [(CharacterEntity::class), (SagaEntity::class)], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun characterDao(): CharacterDao
    abstract fun sagaDao(): SagaDao
}
