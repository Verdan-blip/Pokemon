package ru.kpfu.itis.pokemon

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform