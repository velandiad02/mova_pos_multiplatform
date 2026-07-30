package com.example.mova_pos_multiplatform

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform