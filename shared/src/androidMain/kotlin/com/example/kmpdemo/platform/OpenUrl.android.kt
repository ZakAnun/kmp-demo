package com.example.kmpdemo.platform

import android.content.Intent
import android.net.Uri

actual fun openUrlInBrowser(url: String) {
    val ctx = try {
        val clazz = Class.forName("android.app.ActivityThread")
        val method = clazz.getMethod("currentApplication")
        method.invoke(null) as android.content.Context
    } catch (_: Exception) {
        null
    } ?: return
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }
    ctx.startActivity(intent)
}
