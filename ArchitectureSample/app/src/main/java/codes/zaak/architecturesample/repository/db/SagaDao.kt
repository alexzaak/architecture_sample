package codes.zaak.architecturesample.repository.db

import androidx.room.*
import codes.zaak.architecturesample.repository.model.entity.SagaEntity
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface SagaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSaga(sagas: List<SagaEntity>)

    @Update
    fun updateSaga(saga: SagaEntity)

    @Query("SELECT * FROM SAGA WHERE id = :id_")
    fun getSaga(id_: Int): Single<SagaEntity>

    @Query("SELECT * FROM SAGA")
    fun getSagaList(): Flowable<List<SagaEntity>>
}