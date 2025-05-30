package ru.practicum.android.microhh.core

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin
import ru.practicum.android.microhh.di.dataModule
import ru.practicum.android.microhh.di.interactorModule
import ru.practicum.android.microhh.di.repositoryModule
import ru.practicum.android.microhh.di.viewModelModule

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(
                dataModule,
                repositoryModule,
                interactorModule,
                viewModelModule,
            )
        }

        /*val settingsInteractor: SettingsInteractor by inject()
        settingsInteractor.getThemeSettings().also { theme ->
            Util.applyTheme(theme.themeName)
        }*/
    }
}
