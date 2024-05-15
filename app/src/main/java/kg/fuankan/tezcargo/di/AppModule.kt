package kg.fuankan.tezcargo.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kg.fuankan.tezcargo.data.local.AppPreferences
import kg.fuankan.tezcargo.data.network.util.ServerErrorHandler
import kg.fuankan.tezcargo.data.network.util.ServerErrorHandlerImpl
import kg.fuankan.tezcargo.utils.Dispatcher
import kg.fuankan.tezcargo.utils.DispatcherImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun provideContext(
        @ApplicationContext context: Context,
    ): Context {
        return context
    }

    @Provides
    @Singleton
    fun provideServerErrorHandler(@ApplicationContext context: Context, appPreferences: AppPreferences)
            : ServerErrorHandler = ServerErrorHandlerImpl(context, appPreferences)

    @Provides
    @Singleton
    fun provideDispatcher(): Dispatcher = DispatcherImpl()
}