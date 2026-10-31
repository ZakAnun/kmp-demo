package com.example.kmpdemo

import com.example.kmpdemo.host.HostContext
import com.example.kmpdemo.host.IosHostContext
import com.example.kmpdemo.host.SharedFeatureRuntime

/** 请在主线程、present 共享页之前调用；dismiss 后 [clearHostContext]。 */
fun installHostContext(context: HostContext) {
    SharedFeatureRuntime.install(context)
}

fun installHostContext(shopId: String, userId: String?) {
    SharedFeatureRuntime.install(IosHostContext(shopId = shopId, userId = userId))
}

fun clearHostContext() {
    SharedFeatureRuntime.clear()
}
