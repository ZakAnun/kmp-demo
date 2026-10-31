package com.example.kmpdemo.host

import androidx.compose.runtime.staticCompositionLocalOf

val LocalHostContext = staticCompositionLocalOf<HostContext> {
    error("LocalHostContext 未提供。请用 SharedFeatureHostContent 包裹 UI，或先 SharedFeatureRuntime.install()。")
}
