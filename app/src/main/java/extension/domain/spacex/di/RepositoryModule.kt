package extension.domain.spacex.di

import extension.domain.spacex.data.network.LaunchService
import extension.domain.spacex.data.repositories.launch.LaunchRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { createRepository(get()) }
}

fun createRepository(
    launchService: LaunchService
): LaunchRepository = LaunchRepository(launchService)