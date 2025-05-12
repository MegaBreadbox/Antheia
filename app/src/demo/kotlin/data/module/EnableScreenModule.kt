package data.module

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import data.EnableScreenImpl
import data.EnableScreenRepo
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class EnableScreenModule {
    @Binds
    @Singleton
    abstract fun bindEnableScreen(
        enableScreenImpl: EnableScreenImpl
    ): EnableScreenRepo
}