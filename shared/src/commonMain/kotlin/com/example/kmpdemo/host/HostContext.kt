package com.example.kmpdemo.host

/**
 * 宿主注入的上下文：店铺、用户，以及需要回调原生的能力。
 * commonMain 只依赖此接口，不依赖 androidHost / iosHost 工程。
 */
interface HostContext {
    val shopId: String
    val userId: String?

    fun openNativeOrderDetail(orderId: String)
}

/**
 * 进程内共享的宿主上下文槽位。
 *
 * **传参优先级（Android 打开 [com.example.kmpdemo.ui.SharedFeatureActivity] 时）：**
 * 1. 若打开前已 [install]，以 install 的实例为准（可保留 [AndroidHostContext] 对宿主 Activity 的引用）。
 * 2. 若未 install，则从 Intent 的 [EXTRA_SHOP_ID] / [EXTRA_USER_ID] 构造 [SimpleHostContext]。
 *
 * 请在主线程调用 install / clear。iOS 在 present 共享页前 install，dismiss 后 clear。
 */
object SharedFeatureRuntime {
    private var hostContext: HostContext? = null

    fun install(context: HostContext) {
        HostContextLocker.withLock { hostContext = context }
    }

    fun clear() {
        HostContextLocker.withLock { hostContext = null }
    }

    fun currentHostContext(): HostContext? =
        HostContextLocker.withLock { hostContext }

    fun requireHostContext(): HostContext =
        HostContextLocker.withLock {
            hostContext ?: error(
                "HostContext 未注入。打开共享页前调用 SharedFeatureRuntime.install()，" +
                    "或通过 SharedFeatureActivity 的 Intent 传 shopId/userId。",
            )
        }
}
