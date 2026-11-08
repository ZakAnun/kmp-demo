package com.example.kmpdemo.platform

import platform.UIKit.UIAlertAction
import platform.UIKit.UIAlertActionStyleDefault
import platform.UIKit.UIAlertController
import platform.UIKit.UIAlertControllerStyleAlert
import platform.UIKit.UIApplication

actual fun showHostFeedback(message: String) {
    val alert = UIAlertController.alertControllerWithTitle(
        title = "宿主回调",
        message = message,
        preferredStyle = UIAlertControllerStyleAlert,
    )
    alert.addAction(
        UIAlertAction.actionWithTitle(
            title = "好",
            style = UIAlertActionStyleDefault,
            handler = null,
        ),
    )
    var presenter = UIApplication.sharedApplication.keyWindow?.rootViewController
    while (presenter?.presentedViewController != null) {
        presenter = presenter.presentedViewController
    }
    presenter?.presentViewController(alert, animated = true, completion = null)
}
