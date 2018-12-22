package codes.zaak.architecturesample.repository.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "character")
data class CharacterEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "image")
    val image: String,
    @ColumnInfo(name = "power")
    val power: String,
    @ColumnInfo(name = "race")
    val race: String,
    @ColumnInfo(name = "saga")
    val saga: String,
    @ColumnInfo(name = "saga_id")
    val sagaId: Int
)