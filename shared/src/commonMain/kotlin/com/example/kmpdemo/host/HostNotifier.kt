package com.example.kmpdemo.host

/**
 * 由 [SharedFeatureScreen] 注册，用于在 Compose 内用 Snackbar 展示宿主回调等提示，
 * 使 Android / iOS 交互一致；未注册时由 [com.example.kmpdemo.platform.showHostFeedback] 兜底。
 */
object HostNotifier {
    private var listener: ((String) -> Unit)? = null

    fun setListener(listener: ((String) -> Unit)?) {
        this.listener = listener
    }

    fun notify(message: String): Boolean {
        listener?.invoke(message)
        return listener != null
    }
}
