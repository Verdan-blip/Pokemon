package ru.kpfu.itis.pokemon.platform

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import ru.kpfu.itis.pokemon.PokemonDataBase

actual class DatabaseDriverFactory(
    private val context: Context
) {
    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(PokemonDataBase.Schema, context, "pokemon_app.db")
    }
}