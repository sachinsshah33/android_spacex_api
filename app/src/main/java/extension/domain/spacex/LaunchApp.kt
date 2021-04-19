package extension.domain.spacex

import android.app.Application
import extension.domain.spacex.di.networkModule
import extension.domain.spacex.di.repositoryModule
import extension.domain.spacex.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class LaunchApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@LaunchApp)
            modules(listOf(networkModule, repositoryModule, viewModelModule))
        }
    }
}