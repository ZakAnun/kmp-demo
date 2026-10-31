package com.example.kmpdemo.host

import android.app.Activity
import android.widget.Toast

class AndroidHostContext(
    private val activity: Activity,
    override val shopId: String,
    override val userId: String?,
) : HostContext {
    override fun openNativeOrderDetail(orderId: String) {
        Toast.makeText(
            activity,
            "宿主原生：打开订单 $orderId（shop=$shopId）",
            Toast.LENGTH_SHORT,
        ).show()
    }
}
