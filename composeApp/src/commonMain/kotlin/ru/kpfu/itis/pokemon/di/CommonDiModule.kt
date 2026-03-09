package ru.kpfu.itis.pokemon.di

import org.koin.dsl.module
import ru.kpfu.itis.pokemon.PokemonDataBase
import ru.kpfu.itis.pokemon.data.di.dataModule
import ru.kpfu.itis.pokemon.domain.di.domainModule
import ru.kpfu.itis.pokemon.platform.DatabaseDriverFactory
import ru.kpfu.itis.pokemon.presentation.di.presentationModule

val commonDiModule = module {
    includes(
        dataModule,
        domainModule,
        presentationModule
    )

    includes(platformModule)

    single<PokemonDataBase> {
        val driver = get<DatabaseDriverFactory>().createDriver()
        PokemonDataBase(driver)
    }
}
