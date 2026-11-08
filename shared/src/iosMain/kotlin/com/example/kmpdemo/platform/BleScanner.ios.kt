package com.example.kmpdemo.platform

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

actual class BleScanner {
    actual suspend fun readSummary(): BluetoothSummary = withContext(Dispatchers.Default) {
        // iOS 不对外暴露「已配对 BLE 设备」列表；完整能力需 CoreBluetooth（CBCentralManager）异步扫描。
        BluetoothSummary(
            adapterSupported = true,
            isBluetoothOn = true,
            pairedDeviceCount = 0,
            firstPairedName = null,
            note = "平台：iOS 不提供系统配对列表，需用 CoreBluetooth 扫描",
        )
    }
}
