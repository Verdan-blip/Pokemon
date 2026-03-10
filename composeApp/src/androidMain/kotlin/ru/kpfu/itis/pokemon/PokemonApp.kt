package ru.kpfu.itis.pokemon

import android.app.Application
import org.koin.android.ext.koin.androidContext
import ru.kpfu.itis.pokemon.di.initKoin

class PokemonApp : Application() {

    override fun onCreate() {
        super.onCreate()

        initKoin {
            androidContext(this@PokemonApp)
        }
    }
}