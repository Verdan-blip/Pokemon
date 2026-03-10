package ru.kpfu.itis.pokemon.di

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import org.koin.dsl.module
import ru.kpfu.itis.pokemon.PokemonDataBase
import ru.kpfu.itis.pokemon.platform.DatabaseDriverFactory

actual val platformModule = module {
    single<SqlDriver> {
        AndroidSqliteDriver(
            schema = PokemonDataBase.Schema,
            context = get(),
            name = "pokemon_app.db"
        )
    }
}