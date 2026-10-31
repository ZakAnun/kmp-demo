package com.example.kmpdemo.host

class IosHostContext(
    override val shopId: String,
    override val userId: String?,
) : HostContext {
    override fun openNativeOrderDetail(orderId: String) {
        println("Host native: open order $orderId (shop=$shopId)")
    }
}
