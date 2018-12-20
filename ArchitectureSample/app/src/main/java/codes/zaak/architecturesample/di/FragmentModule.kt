package codes.zaak.architecturesample.di

import codes.zaak.architecturesample.view.character.CharacterFragment
import codes.zaak.architecturesample.view.saga.SagaFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {
    @ContributesAndroidInjector
    internal abstract fun contributeSagaFragment(): SagaFragment

    @ContributesAndroidInjector
    internal abstract fun contributeCharacterFragment(): CharacterFragment
}