package codes.zaak.architecturesample.di

import androidx.lifecycle.ViewModel
import codes.zaak.architecturesample.viewmodel.MainViewModel
import codes.zaak.architecturesample.viewmodel.SagaViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
internal abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    internal abstract fun bindMainViewModels(mainViewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SagaViewModel::class)
    internal abstract fun bindSagaViewModels(sagaViewModel: SagaViewModel): ViewModel
}