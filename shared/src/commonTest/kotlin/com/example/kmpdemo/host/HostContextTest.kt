package com.example.kmpdemo.host

import kotlin.test.AfterTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class HostContextTest {
    @AfterTest
    fun tearDown() {
        SharedFeatureRuntime.clear()
    }

    @Test
    fun installExposesShopIdAndUserId() {
        SharedFeatureRuntime.install(
            SimpleHostContext(shopId = "shop-42", userId = "user-7"),
        )
        val host = SharedFeatureRuntime.requireHostContext()
        assertEquals("shop-42", host.shopId)
        assertEquals("user-7", host.userId)
    }

    @Test
    fun requireHostContextFailsWhenNotInstalled() {
        assertFailsWith<IllegalStateException> {
            SharedFeatureRuntime.requireHostContext()
        }
    }

    @Test
    fun openNativeOrderDetailDelegatesToCallback() {
        var capturedOrderId: String? = null
        SharedFeatureRuntime.install(
            SimpleHostContext(shopId = "s", userId = null) { orderId ->
                capturedOrderId = orderId
            },
        )
        SharedFeatureRuntime.requireHostContext().openNativeOrderDetail("ORDER-1")
        assertEquals("ORDER-1", capturedOrderId)
    }
}
