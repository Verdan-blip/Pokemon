package ru.kpfu.itis.pokemon.platform

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import ru.kpfu.itis.pokemon.PokemonDataBase

actual class DatabaseDriverFactory {
    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(PokemonDataBase.Schema, "pokemon_app.db")
    }
}