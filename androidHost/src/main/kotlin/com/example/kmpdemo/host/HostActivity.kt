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
        val button = Button(this).apply {
            text = "打开 KMP 共享页"
            setOnClickListener {
                startActivity(Intent(this@HostActivity, SharedFeatureActivity::class.java))
            }
        }
        setContentView(
            LinearLayout(this).apply {
                orientation = LinearLayout.VERTICAL
                setPadding(48, 96, 48, 48)
                addView(title)
                addView(button)
            },
        )
    }
}
