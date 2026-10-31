package com.example.kmpdemo.host

internal expect object HostContextLocker {
    fun <T> withLock(block: () -> T): T
}
