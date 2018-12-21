package codes.zaak.architecturesample.di

import codes.zaak.architecturesample.view.MainActivity
import codes.zaak.architecturesample.view.character.CharacterActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {
    @ContributesAndroidInjector(modules = [FragmentModule::class])
    internal abstract fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector(modules = [FragmentModule::class])
    internal abstract fun contributeCharacterActivity(): CharacterActivity
}