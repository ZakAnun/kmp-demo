package com.example.kmpdemo

import com.example.kmpdemo.host.SharedFeatureRuntime
import com.example.kmpdemo.host.SimpleHostContext
import kotlin.test.AfterTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

/**
 * 验证「install 优先、Intent 兜底」中与 install 相关的契约（Intent 路径需 Instrumentation）。
 */
class SharedFeatureActivityHostContextTest {
    @AfterTest
    fun tearDown() {
        SharedFeatureRuntime.clear()
    }

    @Test
    fun preInstallIsNotOverwrittenByLaterReader() {
        SharedFeatureRuntime.install(SimpleHostContext("installed-shop", "u1"))
        assertEquals("installed-shop", SharedFeatureRuntime.requireHostContext().shopId)
        assertNotNull(SharedFeatureRuntime.currentHostContext())
    }

    @Test
    fun clearAllowsIntentFallbackPath() {
        SharedFeatureRuntime.install(SimpleHostContext("old", null))
        SharedFeatureRuntime.clear()
        kotlin.test.assertNull(SharedFeatureRuntime.currentHostContext())
    }
}
