package com.example.kmpdemo.host

class IosHostContext(
    override val shopId: String,
    override val userId: String?,
) : HostContext {
    override fun openNativeOrderDetail(orderId: String) {
        deliverHostUserMessage(orderId, shopId)
    }
}
