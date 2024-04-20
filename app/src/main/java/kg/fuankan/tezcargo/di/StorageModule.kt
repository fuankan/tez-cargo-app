package kg.fuankan.tezcargo.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kg.fuankan.tezcargo.data.local.AppPreferences
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class StorageModule {

    @Provides
    @Singleton
    fun provideAppPreferences(@ApplicationContext context: Context) = AppPreferences(context)
}