package codes.zaak.architecturesample.repository

import codes.zaak.architecturesample.repository.api.SagaService
import codes.zaak.architecturesample.repository.db.SagaDao
import codes.zaak.architecturesample.repository.model.entity.SagaEntity
import codes.zaak.architecturesample.repository.model.response.Saga
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SagaRepository @Inject constructor(private val service: SagaService, private val sagaDao: SagaDao) {

    fun getSaga(id: Int): Observable<Saga> {
        return this.service.getSaga(id).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).toObservable()
    }

    fun getSagaList(): Observable<List<Saga>> {
        val remoteData = this.getRemoteData()
        val localData = this.getLocalData()

        return Observable.concatArrayDelayError(remoteData.toObservable(), localData.toObservable())
    }

    private fun getRemoteData(): Flowable<List<Saga>> {
        return this.service.getAllSagas()
            .subscribeOn(Schedulers.io())
            .doOnNext {
                Timber.d("Fetched Sagas ${it.size}")
                this.sagaDao.insertSaga(it.map { saga ->
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
}