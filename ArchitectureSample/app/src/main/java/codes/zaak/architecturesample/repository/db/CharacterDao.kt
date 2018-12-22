package codes.zaak.architecturesample.repository.db

import androidx.room.*
import codes.zaak.architecturesample.repository.model.entity.CharacterEntity
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface CharacterDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCharacterList(characters: List<CharacterEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCharacter(character: CharacterEntity)

    @Update
    fun updateCharacter(character: CharacterEntity)

    @Query("SELECT * FROM CHARACTER WHERE id = :id_")
    fun getCharacter(id_: Int): Single<CharacterEntity>

    @Query("SELECT * FROM CHARACTER")
    fun getCharacterList(): Flowable<List<CharacterEntity>>

    @Query("SELECT * FROM CHARACTER WHERE saga_id = :saga_id_")
    fun getCharacterList(saga_id_: Int): Flowable<List<CharacterEntity>>
}