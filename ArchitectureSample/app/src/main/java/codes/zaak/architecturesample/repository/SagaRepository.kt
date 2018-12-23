package codes.zaak.architecturesample.repository

import codes.zaak.architecturesample.repository.api.SagaService
import codes.zaak.architecturesample.repository.db.SagaDao
import codes.zaak.architecturesample.repository.model.entity.SagaEntity
import codes.zaak.architecturesample.repository.model.response.Saga
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SagaRepository @Inject constructor(private val service: SagaService, private val sagaDao: SagaDao) {

    fun getSaga(id: Int): Observable<Saga> {
        val remoteData = this.getRemoteData(id)
        val localData = this.getLocalData(id)

        return Flowable.mergeDelayError(remoteData, localData).toObservable()
    }

    fun getSagaList(): Observable<List<Saga>> {
        val remoteData = this.getRemoteData()
        val localData = this.getLocalData()

        return Flowable.mergeArrayDelayError(remoteData, localData).toObservable()
    }

    private fun getRemoteData(): Flowable<List<Saga>> {
        return this.service.getAllSagas()
            .subscribeOn(Schedulers.io())
            .doOnNext {
                Timber.d("Fetched Sagas ${it.size}")
                this.sagaDao.insertSagaList(it.map { saga ->
                    SagaEntity(
                        saga.id,
                        saga.name, saga.description, saga.image
                    )
                })
            }
    }

    private fun getLocalData(): Flowable<List<Saga>> {
        return this.sagaDao.getSagaList()
            .subscribeOn(Schedulers.computation())
            .map { it.map { entity -> Saga.create(entity) } }
            .doOnNext {
                Timber.d("Get Local data: ${it.size}")
            }
    }

    private fun getRemoteData(id: Int): Flowable<Saga> {
        return this.service.getSaga(id)
            .subscribeOn(Schedulers.io())
            .doOnNext { saga ->
                Timber.d("Fetched Sagas ${saga.id}")
                this.sagaDao.insertSaga(
                    SagaEntity(
                        saga.id,
                        saga.name, saga.description, saga.image
                    )
                )
            }
    }

    private fun getLocalData(id: Int): Flowable<Saga> {
        return this.sagaDao.getSaga(id)
            .subscribeOn(Schedulers.computation())
            .map { entity -> Saga.create(entity) }
            .doOnNext {
                Timber.d("Get Local data: ${it.id}")
            }
    }
}