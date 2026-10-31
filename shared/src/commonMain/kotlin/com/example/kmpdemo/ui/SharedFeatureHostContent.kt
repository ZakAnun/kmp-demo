package com.example.kmpdemo.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import com.example.kmpdemo.host.LocalHostContext
import com.example.kmpdemo.host.SharedFeatureRuntime

/**
 * 每次重组时从 [SharedFeatureRuntime] 读取最新 [HostContext] 并注入 Compose 树，
 * 避免 remember 缓存导致换参后不刷新。
 */
@Composable
fun SharedFeatureHostContent(content: @Composable () -> Unit) {
    val host = SharedFeatureRuntime.requireHostContext()
    CompositionLocalProvider(LocalHostContext provides host) {
        content()
    }
}
