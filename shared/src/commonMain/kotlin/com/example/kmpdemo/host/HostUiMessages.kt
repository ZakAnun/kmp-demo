package com.example.kmpdemo.host

/** 双端统一的交互文案（Compose Snackbar / 宿主回调）。 */
object HostUiMessages {
    fun nativeOrderOpened(orderId: String, shopId: String): String =
        "宿主：打开订单 $orderId（shop=$shopId）"

    fun bleRefreshed(generation: Int): String =
        "蓝牙摘要已更新（第 $generation 次读取）"
}
