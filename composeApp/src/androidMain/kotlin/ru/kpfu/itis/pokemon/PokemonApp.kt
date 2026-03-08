package ru.kpfu.itis.pokemon

import android.app.Application
import ru.kpfu.itis.pokemon.di.initKoin

class PokemonApp : Application() {

    override fun onCreate() {
        super.onCreate()

        initKoin()
    }
}