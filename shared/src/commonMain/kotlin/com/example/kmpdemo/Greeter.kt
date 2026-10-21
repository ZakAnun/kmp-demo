package com.example.kmpdemo

class Greeter(
    private val platform: Platform = getPlatform(),
) {
    fun greet(): String = "Hello from ${platform.name}"
}
