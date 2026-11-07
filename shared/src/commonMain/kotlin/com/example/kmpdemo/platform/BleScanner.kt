package com.example.kmpdemo.platform

/**
 * 平台蓝牙摘要（expect/actual）：展示 KMP 调用各端系统 API 的常见写法。
 * Android 读取已配对设备；iOS 仅占位说明（完整扫描需 CoreBluetooth 委托）。
 */
expect class BleScanner() {
    suspend fun readSummary(): BluetoothSummary
}
