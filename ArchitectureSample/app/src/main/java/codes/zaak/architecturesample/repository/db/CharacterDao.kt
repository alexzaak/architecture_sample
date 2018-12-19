package codes.zaak.architecturesample.repository.db

import androidx.lifecycle.LiveData
import androidx.room.*
import codes.zaak.architecturesample.repository.model.entity.CharacterEntity
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface CharacterDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCharacter(characters: List<CharacterEntity>)

    @Update
    fun updateCharacter(character: CharacterEntity)

    @Query("SELECT * FROM CHARACTER WHERE id = :id_")
    fun getCharacter(id_: Int): Single<CharacterEntity>

    @Query("SELECT * FROM CHARACTER")
    fun getCharacterList(): Flowable<List<CharacterEntity>>
}