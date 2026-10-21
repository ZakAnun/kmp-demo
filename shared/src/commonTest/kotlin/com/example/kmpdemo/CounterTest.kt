package com.example.kmpdemo

import kotlin.test.Test
import kotlin.test.assertEquals

class CounterTest {
    @Test
    fun incrementAddsOne() {
        val counter = Counter()
        assertEquals(0, counter.value)
        assertEquals(1, counter.increment())
        assertEquals(1, counter.value)
    }
}
