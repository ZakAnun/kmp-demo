package com.example.kmpdemo

import androidx.compose.ui.window.ComposeUIViewController
import com.example.kmpdemo.ui.SharedFeatureHostContent
import com.example.kmpdemo.ui.SharedFeatureScreen
import platform.UIKit.UIViewController

fun SharedFeatureViewController(): UIViewController =
    ComposeUIViewController {
        SharedFeatureHostContent {
            SharedFeatureScreen()
        }
    }
