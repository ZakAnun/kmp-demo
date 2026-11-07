package com.example.kmpdemo.platform

data class BluetoothSummary(
    val adapterSupported: Boolean,
    val isBluetoothOn: Boolean,
    /** 已配对设备数量（Android 可读时；iOS 需扫描才能枚举）。 */
    val pairedDeviceCount: Int,
    /** 配对列表中首个设备的可读名称，便于 demo 展示。 */
    val firstPairedName: String?,
    val note: String?,
)
