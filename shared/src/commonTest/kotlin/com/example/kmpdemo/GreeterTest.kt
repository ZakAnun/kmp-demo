package com.example.kmpdemo

import kotlin.test.Test
import kotlin.test.assertEquals

class GreeterTest {
    @Test
    fun greetIncludesPlatformName() {
        val greeter = Greeter(object : Platform {
            override val name: String = "Test"
        })
        assertEquals("Hello from Test", greeter.greet())
    }
}
