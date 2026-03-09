package ru.kpfu.itis.pokemon

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.kpfu.itis.pokemon.di.commonDiModule
import ru.kpfu.itis.pokemon.di.initKoin

class PokemonApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@PokemonApp)
            modules(commonDiModule)
        }
    }
}