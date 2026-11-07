package com.example.kmpdemo.platform

import platform.Foundation.NSURL
import platform.UIKit.UIApplication

actual fun openUrlInBrowser(url: String) {
    val nsUrl = NSURL.URLWithString(url) ?: return
    UIApplication.sharedApplication.openURL(nsUrl)
}
