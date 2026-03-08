package ru.kpfu.itis.pokemon.di

import org.koin.core.context.startKoin

fun initKoin() = startKoin {
    modules(commonDiModule)
}