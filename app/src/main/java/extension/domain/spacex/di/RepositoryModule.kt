package extension.domain.spacex.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import javax.inject.Singleton
import dagger.hilt.components.SingletonComponent
import extension.domain.spacex.data.database.daos.LaunchesDAO
import extension.domain.spacex.data.network.LaunchService
import extension.domain.spacex.data.repositories.launch.LaunchRepository


@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {
    @Singleton
    @Provides
    fun provideLaunchRepository(launchService: LaunchService, launchesDAO: LaunchesDAO): LaunchRepository = LaunchRepository(launchService, launchesDAO)
}