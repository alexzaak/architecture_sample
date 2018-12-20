package codes.zaak.architecturesample.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import codes.zaak.architecturesample.repository.CharacterRepository
import codes.zaak.architecturesample.repository.model.response.Character
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MainViewModel @Inject constructor(val characterRepository: CharacterRepository) : ViewModel() {

    init {
        Timber.d("MainViewModel injected")
    }

    var characterResult: MutableLiveData<List<Character>> = MutableLiveData()
    var characterError: MutableLiveData<String> = MutableLiveData()
    var characterLoader: MutableLiveData<Boolean> = MutableLiveData()
    lateinit var disposableObserver: DisposableObserver<List<Character>>

    fun characterResult(): LiveData<List<Character>> {
        return characterResult
    }

    fun characterError(): LiveData<String> {
        return characterError
    }

    fun characterLoader(): LiveData<Boolean> {
        return characterLoader
    }

    fun loadCharacters() {
        disposableObserver = object : DisposableObserver<List<Character>>() {
            override fun onComplete() {

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

        this.characterRepository.getCharacterList()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .debounce(400, TimeUnit.MILLISECONDS)
            .subscribe(disposableObserver)
    }

    fun disposeElements() {
        if (null != disposableObserver && !disposableObserver.isDisposed) disposableObserver.dispose()
    }
}