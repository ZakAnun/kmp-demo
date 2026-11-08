package com.example.kmpdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.kmpdemo.host.SharedFeatureRuntime
import com.example.kmpdemo.host.SimpleHostContext
import com.example.kmpdemo.ui.SharedFeatureHostContent
import com.example.kmpdemo.ui.SharedFeatureScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SharedFeatureRuntime.install(
            SimpleHostContext(shopId = "sample-shop-001", userId = "sample-user"),
        )
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
}
