package codes.zaak.architecturesample.di

import androidx.lifecycle.ViewModel
import codes.zaak.architecturesample.viewmodel.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
internal abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    internal abstract fun bindMainViewModels(mainViewModel: MainViewModel): ViewModel
}