package ru.kpfu.itis.pokemon.platform

import app.cash.sqldelight.db.SqlDriver
import ru.kpfu.itis.pokemon.PokemonDataBase

expect class DatabaseDriverFactory {
    fun createDriver(): SqlDriver
}

fun createDataBase(driverFactory: DatabaseDriverFactory): PokemonDataBase {
    val driver = driverFactory.createDriver()
    return PokemonDataBase(driver)
}