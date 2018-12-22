package codes.zaak.architecturesample.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import codes.zaak.architecturesample.repository.CharacterRepository
import codes.zaak.architecturesample.repository.model.response.Character
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class CharacterViewModel @Inject constructor(private val characterRepository: CharacterRepository) : ViewModel() {

    init {
        Timber.d("CharacterViewModel injected")
    }

    private var characterResult: MediatorLiveData<List<Character>> = MediatorLiveData()
    private var characterError: MediatorLiveData<String> = MediatorLiveData()
    private var characterLoader: MediatorLiveData<Boolean> = MediatorLiveData()
    lateinit var disposableObserver: DisposableObserver<List<Character>>

    fun result(): LiveData<List<Character>> {
        return this.characterResult
    }

    fun error(): LiveData<String> {
        return this.characterError
    }

    fun loader(): LiveData<Boolean> {
        return this.characterLoader
    }


    fun loadCharacters(sagaId: Int?) {
        this.disposableObserver = object : DisposableObserver<List<Character>>() {
            override fun onComplete() {
                characterLoader.postValue(false)
            }

            override fun onStart() {
                characterLoader.postValue(true)
            }

            override fun onNext(chracterList: List<Character>) {
                characterResult.postValue(chracterList)
                characterLoader.postValue(false)
            }

            override fun onError(e: Throwable) {
                characterError.postValue(e.message)
                characterLoader.postValue(false)
            }
        }

        this.characterRepository.getCharacterList(sagaId!!)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnTerminate {
                this.characterLoader.postValue(false)
            }
            .debounce(200, TimeUnit.MILLISECONDS)
            .subscribe(this.disposableObserver)
    }

    fun disposeElements() {
        if (null != disposableObserver && !disposableObserver.isDisposed) disposableObserver.dispose()
    }
}