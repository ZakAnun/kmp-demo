package com.example.kmpdemo.platform

import android.widget.Toast

actual fun showHostFeedback(message: String) {
    val ctx = try {
        val clazz = Class.forName("android.app.ActivityThread")
        val method = clazz.getMethod("currentApplication")
        method.invoke(null) as android.content.Context
    } catch (_: Exception) {
        null
    } ?: return
    Toast.makeText(ctx, message, Toast.LENGTH_SHORT).show()
}
