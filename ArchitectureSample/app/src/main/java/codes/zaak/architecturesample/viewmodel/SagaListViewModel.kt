package codes.zaak.architecturesample.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import codes.zaak.architecturesample.repository.SagaRepository
import codes.zaak.architecturesample.repository.model.response.Saga
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SagaListViewModel @Inject constructor(private val sagaRepository: SagaRepository) : ViewModel() {

    init {
        Timber.d("SagaListViewModel injected")
    }

    private var result: MediatorLiveData<List<Saga>> = MediatorLiveData()
    private var error: MediatorLiveData<String> = MediatorLiveData()
    private var loader: MediatorLiveData<Boolean> = MediatorLiveData()
    lateinit var disposableObserver: DisposableObserver<List<Saga>>

    fun result(): LiveData<List<Saga>> {
        return this.result
    }

    fun error(): LiveData<String> {
        return this.error
    }

    fun loader(): LiveData<Boolean> {
        return this.loader
    }

    fun loadSagaList() {
        this.disposableObserver = object : DisposableObserver<List<Saga>>() {
            override fun onComplete() {
                loader.postValue(false)
            }

            override fun onStart() {
                loader.postValue(true)
            }

            override fun onNext(sagaList: List<Saga>) {
                result.postValue(sagaList)
                loader.postValue(false)
            }

            override fun onError(e: Throwable) {
                error.postValue(e.message)
                loader.postValue(false)
            }
        }

        this.sagaRepository.getSagaList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnTerminate {
                this.loader.postValue(false)
            }
            .debounce(1000, TimeUnit.MILLISECONDS)
            .subscribe(this.disposableObserver)
    }

    fun disposeElements() {
        if (null != disposableObserver && !disposableObserver.isDisposed) disposableObserver.dispose()
    }
}