package extension.domain.spacex.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import extension.domain.spacex.data.database.AppDatabase
import extension.domain.spacex.data.database.daos.LaunchesDAO
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {
    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase = AppDatabase.getInstance(context)

    @Singleton
    @Provides
    fun provideLaunchesDAO(appDatabase: AppDatabase): LaunchesDAO = appDatabase.launchesDAO()
}