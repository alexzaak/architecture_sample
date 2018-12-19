package codes.zaak.architecturesample.repository.db

import androidx.room.*
import codes.zaak.architecturesample.repository.model.response.Saga

@Dao
interface SagaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSaga(sagas: List<Saga>)

    @Update
    fun updateSaga(saga: Saga)

    @Query("SELECT * FROM SAGA WHERE id = :id_")
    fun getSaga(id_: Int): Saga

    @Query("SELECT * FROM SAGA")
    fun getSagaList(): Saga
}