package com.example.kmpdemo.host

import com.example.kmpdemo.platform.showHostFeedback

internal fun deliverHostUserMessage(orderId: String, shopId: String) {
    val message = HostUiMessages.nativeOrderOpened(orderId, shopId)
    if (!HostNotifier.notify(message)) {
        showHostFeedback(message)
    }
}
