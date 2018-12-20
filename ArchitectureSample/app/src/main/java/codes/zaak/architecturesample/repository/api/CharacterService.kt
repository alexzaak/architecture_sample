package codes.zaak.architecturesample.repository.api

import codes.zaak.architecturesample.repository.model.response.Character
import io.reactivex.Flowable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface CharacterService {

    @GET("/characters")
    fun getAllCharacters(): Flowable<List<Character>>

    @GET("/characters/saga/{saga_id}")
    fun getCharacters(@Path("saga_id") id: Int): Flowable<List<Character>>

    @GET("/characters/{char_id}")
    fun getCharacter(@Path("char_id") id: Int): Single<Character>
}