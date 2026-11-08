package com.example.kmpdemo.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.kmpdemo.Counter
import com.example.kmpdemo.Greeter
import com.example.kmpdemo.feed.GitHubEventRow
import com.example.kmpdemo.feed.SharedFeedViewModel
import com.example.kmpdemo.host.HostNotifier
import com.example.kmpdemo.host.HostUiMessages
import com.example.kmpdemo.host.LocalHostContext
import com.example.kmpdemo.platform.openUrlInBrowser
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SharedFeatureScreen() {
    val host = LocalHostContext.current
    val greeting = remember { Greeter().greet() }
    val counter = remember { Counter() }
    var count by remember { mutableStateOf(counter.value) }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val vm = remember { SharedFeedViewModel() }
    val ui by vm.uiState.collectAsState()

    DisposableEffect(Unit) {
        onDispose { vm.onCleared() }
    }

    DisposableEffect(snackbarHostState) {
        HostNotifier.setListener { message ->
            scope.launch { snackbarHostState.showSnackbar(message) }
        }
        onDispose { HostNotifier.setListener(null) }
    }

    LaunchedEffect(Unit) {
        vm.loadFeed()
        vm.refreshBleSummary(userInitiated = false)
    }

    LaunchedEffect(ui.errorMessage) {
        ui.errorMessage?.let { msg ->
            scope.launch { snackbarHostState.showSnackbar(msg) }
        }
    }

    LaunchedEffect(ui.bleReadGeneration) {
        if (ui.bleReadGeneration > 0) {
            snackbarHostState.showSnackbar(HostUiMessages.bleRefreshed(ui.bleReadGeneration))
        }
    }

    MaterialTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("GitHub 公共动态 + KMP/CMP") },
                    actions = {
                        IconButton(
                            onClick = {
                                vm.loadFeed()
                                vm.refreshBleSummary()
                            },
                            enabled = !ui.isLoading,
                        ) {
                            Icon(Icons.Default.Refresh, contentDescription = "刷新")
                        }
                    },
                )
            },
            snackbarHost = { SnackbarHost(snackbarHostState) },
        ) { padding ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    ) {
                        Column(Modifier.padding(16.dp)) {
                            Text(greeting, style = MaterialTheme.typography.titleMedium)
                            Spacer(Modifier.height(8.dp))
                            Text("shopId: ${host.shopId}", style = MaterialTheme.typography.bodyMedium)
                            Text(
                                "userId: ${host.userId ?: "（未登录）"}",
                                style = MaterialTheme.typography.bodyMedium,
                            )
                            Spacer(Modifier.height(12.dp))
                            Text("Count: $count", style = MaterialTheme.typography.titleSmall)
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Button(onClick = { count = counter.increment() }) {
                                    Text("+1")
                                }
                                Button(onClick = { host.openNativeOrderDetail("ORDER-DEMO-001") }) {
                                    Text("回调宿主")
                                }
                            }
                        }
                    }
                }

                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.secondaryContainer,
                        ),
                    ) {
                        Column(Modifier.padding(16.dp)) {
                            Text("蓝牙摘要（expect/actual）", style = MaterialTheme.typography.titleSmall)
                            Spacer(Modifier.height(6.dp))
                            if (ui.bleReadGeneration > 0) {
                                Text(
                                    "已重读 ${ui.bleReadGeneration} 次",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.primary,
                                )
                                Spacer(Modifier.height(4.dp))
                            }
                            when {
                                ui.bluetooth == null || ui.isBleRefreshing ->
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        if (ui.isBleRefreshing) {
                                            CircularProgressIndicator(
                                                modifier = Modifier.width(16.dp).height(16.dp),
                                                strokeWidth = 2.dp,
                                            )
                                            Spacer(Modifier.width(8.dp))
                                        }
                                        Text(
                                            if (ui.isBleRefreshing) "正在重读蓝牙…" else "读取中…",
                                            style = MaterialTheme.typography.bodySmall,
                                        )
                                    }
                                else -> {
                                    val b = ui.bluetooth!!
                                    Text(
                                        "支持硬件: ${b.adapterSupported}  已开启: ${b.isBluetoothOn}",
                                        style = MaterialTheme.typography.bodySmall,
                                    )
                                    Text(
                                        "已配对数量: ${b.pairedDeviceCount}",
                                        style = MaterialTheme.typography.bodySmall,
                                    )
                                    b.firstPairedName?.let {
                                        Text("首个设备: $it", style = MaterialTheme.typography.bodySmall)
                                    }
                                    b.note?.let {
                                        Text(it, style = MaterialTheme.typography.bodySmall)
                                    }
                                }
                            }
                            ui.bleError?.let {
                                Spacer(Modifier.height(4.dp))
                                Text(it, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
                            }
                            Spacer(Modifier.height(8.dp))
                            Button(
                                onClick = { vm.refreshBleSummary() },
                                enabled = !ui.isBleRefreshing,
                            ) {
                                Text("重读蓝牙")
                            }
                        }
                    }
                }

                if (ui.isLoading && ui.events.isEmpty()) {
                    item {
                        Row(
                            Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            CircularProgressIndicator(Modifier.width(32.dp).height(32.dp))
                            Spacer(Modifier.width(12.dp))
                            Text("加载 GitHub Events…")
                        }
                    }
                }

                items(ui.events, key = { it.id }) { row ->
                    GitHubEventRowCard(row)
                }

                if (!ui.isLoading && ui.events.isEmpty() && ui.errorMessage == null) {
                    item {
                        Text(
                            "暂无数据，点击右上角刷新",
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun GitHubEventRowCard(row: GitHubEventRow) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = row.link != null) {
                row.link?.let { openUrlInBrowser(it) }
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
    ) {
        Column(Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    row.type,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary,
                )
                row.createdAt?.take(10)?.let {
                    Text(it, style = MaterialTheme.typography.labelSmall)
                }
            }
            Spacer(Modifier.height(4.dp))
            Text(
                "${row.actor} → ${row.repo}",
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
            if (row.title.isNotBlank()) {
                Spacer(Modifier.height(4.dp))
                Text(
                    row.title,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                )
            }
            HorizontalDivider(Modifier.padding(vertical = 8.dp))
            Text(
                if (row.link != null) "点击打开链接（expect/actual 调系统浏览器）"
                else "无可用链接",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.outline,
            )
        }
    }
}
