package codes.zaak.architecturesample.repository.api

import androidx.lifecycle.LiveData
import codes.zaak.architecturesample.repository.model.response.Character
import codes.zaak.architecturesample.repository.model.response.Saga
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("/sagas")
    fun getAllSagas() : LiveData<List<Saga>>

    @GET("/sagas/{saga_id}")
    fun getSaga(@Path("saga_id") id: Int): LiveData<Saga>

    @GET("/characters")
    fun getAllCharacters(): LiveData<List<Character>>

    @GET("/characters/{char_id}")
    fun getCharacter(@Path("char_id") id: Int): LiveData<Character>
}