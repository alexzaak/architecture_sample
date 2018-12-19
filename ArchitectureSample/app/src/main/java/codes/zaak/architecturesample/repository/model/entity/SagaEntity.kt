package codes.zaak.architecturesample.repository.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import codes.zaak.architecturesample.repository.model.response.Character

@Entity(tableName = "saga")
data class SagaEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "name")
    val name: String,
    //@ColumnInfo(name = "characters")
    //val characters: List<Character>,
    @ColumnInfo(name = "description")
    val description: String,
    @ColumnInfo(name = "image")
    val image: String
)