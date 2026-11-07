package com.example.kmpdemo.platform

import android.bluetooth.BluetoothManager
import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

private fun applicationContext(): Context? =
    try {
        val clazz = Class.forName("android.app.ActivityThread")
        val method = clazz.getMethod("currentApplication")
        method.invoke(null) as Context
    } catch (_: Exception) {
        null
    }

actual class BleScanner {
    actual suspend fun readSummary(): BluetoothSummary = withContext(Dispatchers.Default) {
        val ctx = applicationContext() ?: return@withContext BluetoothSummary(
            adapterSupported = false,
            isBluetoothOn = false,
            pairedDeviceCount = 0,
            firstPairedName = null,
            note = "无法获取 ApplicationContext，宿主请注入 Context 后再读蓝牙",
        )

        val adapter =
            (ctx.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager).adapter
            ?: return@withContext BluetoothSummary(
                adapterSupported = false,
                isBluetoothOn = false,
                pairedDeviceCount = 0,
                firstPairedName = null,
                note = "设备不支持蓝牙",
            )

        if (!adapter.isEnabled) {
            return@withContext BluetoothSummary(
                adapterSupported = true,
                isBluetoothOn = false,
                pairedDeviceCount = 0,
                firstPairedName = null,
                note = "请在系统设置中打开蓝牙后重试",
            )
        }

        return@withContext try {
            val paired = adapter.bondedDevices ?: emptySet()
            val first = paired.firstOrNull()
            BluetoothSummary(
                adapterSupported = true,
                isBluetoothOn = true,
                pairedDeviceCount = paired.size,
                firstPairedName = first?.name?.takeIf { it.isNotBlank() } ?: first?.address,
                note = "Android：已配对设备（bondedDevices）",
            )
        } catch (se: SecurityException) {
            BluetoothSummary(
                adapterSupported = true,
                isBluetoothOn = true,
                pairedDeviceCount = 0,
                firstPairedName = null,
                note = "Android 12+ 需要 BLUETOOTH_CONNECT 权限后才能读取配对列表（${se.message}）",
            )
        }
    }
}
