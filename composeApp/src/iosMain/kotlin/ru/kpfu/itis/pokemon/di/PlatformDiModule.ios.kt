package ru.kpfu.itis.pokemon.di

import org.koin.dsl.module
import ru.kpfu.itis.pokemon.platform.DatabaseDriverFactory

actual val platformModule = module {
    single { DatabaseDriverFactory() }
}