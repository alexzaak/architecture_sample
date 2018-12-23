package codes.zaak.architecturesample.repository.api

import codes.zaak.architecturesample.repository.model.response.Saga
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Path

interface SagaService {

    @GET("/sagas/")
    fun getAllSagas(): Flowable<List<Saga>>

    @GET("/sagas/{saga_id}")
    fun getSaga(@Path("saga_id") id: Int): Flowable<Saga>
}