package codes.zaak.architecturesample.repository

import codes.zaak.architecturesample.repository.api.CharacterService
import codes.zaak.architecturesample.repository.db.CharacterDao
import codes.zaak.architecturesample.repository.model.entity.CharacterEntity
import codes.zaak.architecturesample.repository.model.response.Character
import io.reactivex.Flowable
import io.reactivex.Observable
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CharacterRepository @Inject constructor(val service: CharacterService, val dao: CharacterDao) {

    fun getCharacterList(): Observable<List<Character>> {
        val remoteData = this.getRemoteData()
        val localData = this.getLocalData()
        return Observable.concatArrayEager(remoteData.toObservable(), localData.toObservable())

    }

    private fun getRemoteData(): Flowable<List<Character>> {
        return this.service.getAllCharacters().doOnNext {
            Timber.d("Fetched Characters ${it.size}")
            this.dao.insertCharacter(it.map { character -> CharacterEntity(character.id,
                character.name,character.image,character.power,character.race,character.saga) })
        }
    }

    private fun getLocalData(): Flowable<List<Character>> {
        return this.dao.getCharacterList().map { it.map { entity -> Character.create(entity) } }.doOnNext {
            Timber.d("Get Local data: ${it.size}")
        }
    }
}