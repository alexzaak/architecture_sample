package codes.zaak.architecturesample.repository

import codes.zaak.architecturesample.repository.api.CharacterService
import codes.zaak.architecturesample.repository.db.CharacterDao
import codes.zaak.architecturesample.repository.model.entity.CharacterEntity
import codes.zaak.architecturesample.repository.model.response.Character
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CharacterRepository @Inject constructor(private val service: CharacterService, private val dao: CharacterDao) {

    fun getCharacterList(sagaId: Int): Observable<List<Character>> {
        val remoteData = this.getRemoteData(sagaId)
        val localData = this.getLocalData(sagaId)

        return Observable.concatArrayDelayError(remoteData.toObservable(), localData.toObservable())
    }

    private fun getRemoteData(sagaId: Int): Flowable<List<Character>> {
        return this.service.getCharacters(sagaId)
            .subscribeOn(Schedulers.io())
            .doOnNext {
                Timber.d("Fetched Characters ${it.size}")
                this.dao.insertCharacterList(it.map { character ->
                    CharacterEntity(
                        character.id,
                        character.name, character.image, character.power, character.race, character.saga, character.sagaId
                    )
                })
            }
    }

    private fun getLocalData(sagaId: Int): Flowable<List<Character>> {
        return this.dao.getCharacterList(sagaId)
            .subscribeOn(Schedulers.computation())
            .map { it.map { entity -> Character.create(entity) } }
            .doOnNext {
                Timber.d("Get Local data: ${it.size}")
            }
    }
}