package com.hci.healthtracker

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform