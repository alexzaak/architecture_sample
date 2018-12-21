package codes.zaak.architecturesample.di

import androidx.lifecycle.ViewModel
import codes.zaak.architecturesample.viewmodel.CharacterViewModel
import codes.zaak.architecturesample.viewmodel.SagaViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
internal abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(CharacterViewModel::class)
    internal abstract fun bindMainViewModels(characterViewModel: CharacterViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SagaViewModel::class)
    internal abstract fun bindSagaViewModels(sagaViewModel: SagaViewModel): ViewModel
}