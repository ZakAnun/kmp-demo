package com.example.kmpdemo

import kotlin.test.Test
import kotlin.test.assertTrue

class AndroidPlatformTest {
    @Test
    fun platformNameContainsAndroid() {
        assertTrue(getPlatform().name.contains("Android", ignoreCase = true))
    }
}
