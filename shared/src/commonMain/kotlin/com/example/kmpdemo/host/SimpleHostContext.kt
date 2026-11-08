package com.example.kmpdemo.host

/** sample / 单测用的默认实现；宿主可自定义 HostContext。 */
class SimpleHostContext(
    override val shopId: String,
    override val userId: String?,
    private val onOpenNativeOrderDetail: (orderId: String) -> Unit = { orderId ->
        deliverHostUserMessage(orderId, shopId)
    },
) : HostContext {
    override fun openNativeOrderDetail(orderId: String) {
        onOpenNativeOrderDetail(orderId)
    }
}
