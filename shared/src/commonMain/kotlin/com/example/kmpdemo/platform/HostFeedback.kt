package com.example.kmpdemo.platform

/** 宿主回调等需要在屏幕上可见的提示（Android Toast / iOS Alert）。 */
expect fun showHostFeedback(message: String)
