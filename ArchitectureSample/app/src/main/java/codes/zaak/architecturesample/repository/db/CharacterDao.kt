package codes.zaak.architecturesample.repository.db

import androidx.lifecycle.LiveData
import androidx.room.*
import codes.zaak.architecturesample.repository.model.response.Character

@Dao
interface CharacterDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCharacter(characters: List<Character>)

    @Update
    fun updateCharacter(character: Character)

    @Query("SELECT * FROM CHARACTER WHERE id = :id_")
    fun getCharacter(id_: Int): Character

    @Query("SELECT * FROM CHARACTER")
    fun getCharacterList(): LiveData<List<Character>>
}