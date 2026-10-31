package com.example.kmpdemo.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.kmpdemo.host.AndroidHostContext
import com.example.kmpdemo.host.SharedFeatureRuntime
import com.example.kmpdemo.host.SimpleHostContext

/**
 * 宿主嵌入入口。传参方式二选一（不要混用且指望 Intent 覆盖 install）：
 * - **推荐**：打开前 [SharedFeatureRuntime.install]（如 [com.example.kmpdemo.host.AndroidHostContext]）
 * - **兜底**：仅 [createIntent] 传 extra，由 [ensureHostContextFromIntentIfNeeded] 构造 [SimpleHostContext]
 */
class SharedFeatureActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ensureHostContextFromIntentIfNeeded()
        setContent {
            SharedFeatureHostContent {
                SharedFeatureScreen()
            }
        }
    }

    override fun onDestroy() {
        SharedFeatureRuntime.clear()
        super.onDestroy()
    }

    private fun ensureHostContextFromIntentIfNeeded() {
        if (SharedFeatureRuntime.currentHostContext() != null) {
            return
        }
        val shopId = intent.getStringExtra(EXTRA_SHOP_ID) ?: DEFAULT_SHOP_ID
        val userId = intent.getStringExtra(EXTRA_USER_ID)
        SharedFeatureRuntime.install(
            SimpleHostContext(shopId, userId) { orderId ->
                AndroidHostContext(this, shopId, userId).openNativeOrderDetail(orderId)
            },
        )
    }

    companion object {
        const val EXTRA_SHOP_ID = "com.example.kmpdemo.extra.SHOP_ID"
        const val EXTRA_USER_ID = "com.example.kmpdemo.extra.USER_ID"
        private const val DEFAULT_SHOP_ID = "unknown-shop"

        /** 在未 install 时使用；若已 install，extra 不会被读取。 */
        fun createIntent(
            context: Context,
            shopId: String,
            userId: String?,
        ): Intent =
            Intent(context, SharedFeatureActivity::class.java).apply {
                putExtra(EXTRA_SHOP_ID, shopId)
                putExtra(EXTRA_USER_ID, userId)
            }
    }
}
