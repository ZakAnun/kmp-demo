package com.example.kmpdemo.host

import android.app.Activity

class AndroidHostContext(
    private val activity: Activity,
    override val shopId: String,
    override val userId: String?,
) : HostContext {
    override fun openNativeOrderDetail(orderId: String) {
        deliverHostUserMessage(orderId, shopId)
    }
}
