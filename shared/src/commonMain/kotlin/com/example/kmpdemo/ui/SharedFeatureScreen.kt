package com.example.kmpdemo.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.kmpdemo.Counter
import com.example.kmpdemo.Greeter
import com.example.kmpdemo.host.LocalHostContext

@Composable
fun SharedFeatureScreen() {
    val host = LocalHostContext.current
    val greeting = remember { Greeter().greet() }
    val counter = remember { Counter() }
    var count by remember { mutableStateOf(counter.value) }

    MaterialTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(text = greeting, style = MaterialTheme.typography.headlineSmall)
            Spacer(Modifier.height(12.dp))
            Text(text = "shopId: ${host.shopId}", style = MaterialTheme.typography.bodyLarge)
            Text(
                text = "userId: ${host.userId ?: "（未登录）"}",
                style = MaterialTheme.typography.bodyLarge,
            )
            Spacer(Modifier.height(16.dp))
            Text(text = "Count: $count", style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.height(16.dp))
            Button(onClick = { count = counter.increment() }) {
                Text("Increment")
            }
            Spacer(Modifier.height(8.dp))
            Button(onClick = { host.openNativeOrderDetail("ORDER-DEMO-001") }) {
                Text("回调宿主：打开订单详情")
            }
        }
    }
}
