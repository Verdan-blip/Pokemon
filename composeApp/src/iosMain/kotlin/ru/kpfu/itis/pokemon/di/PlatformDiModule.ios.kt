package ru.kpfu.itis.pokemon.di

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import org.koin.dsl.module
import ru.kpfu.itis.pokemon.PokemonDataBase

actual val platformModule = module {
    single<SqlDriver> {
        NativeSqliteDriver(
            schema = PokemonDataBase.Schema,
            name = "pokemon_app.db"
        )
    }
}