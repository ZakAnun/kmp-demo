package com.example.kmpdemo.host

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.example.kmpdemo.ui.SharedFeatureActivity

class HostActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val title = TextView(this).apply {
            text = "存量 Android 宿主"
            textSize = 20f
        }
        val installButton = Button(this).apply {
            text = "方式1：install 后打开（推荐）"
            setOnClickListener {
                SharedFeatureRuntime.install(
                    AndroidHostContext(
                        activity = this@HostActivity,
                        shopId = HOST_SHOP_ID,
                        userId = HOST_USER_ID,
                    ),
                )
                startActivity(Intent(this@HostActivity, SharedFeatureActivity::class.java))
            }
        }
        val intentButton = Button(this).apply {
            text = "方式2：仅 Intent 传参（不 install）"
            setOnClickListener {
                SharedFeatureRuntime.clear()
                startActivity(
                    SharedFeatureActivity.createIntent(
                        context = this@HostActivity,
                        shopId = HOST_SHOP_ID,
                        userId = HOST_USER_ID,
                    ),
                )
            }
        }
        setContentView(
            LinearLayout(this).apply {
                orientation = LinearLayout.VERTICAL
                setPadding(48, 96, 48, 48)
                addView(title)
                addView(installButton)
                addView(intentButton)
            },
        )
    }

    companion object {
        private const val HOST_SHOP_ID = "host-shop-10086"
        private const val HOST_USER_ID = "host-user-001"
    }
}
