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
            note = "iOS：展示 expect/actual 分支；配对接入请用 CoreBluetooth 单独实现",
        )
    }
}
