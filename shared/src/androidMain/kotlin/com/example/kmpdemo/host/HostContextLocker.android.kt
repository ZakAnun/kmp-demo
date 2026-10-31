package com.example.kmpdemo.host

internal actual object HostContextLocker {
    private val lock = Any()

    actual fun <T> withLock(block: () -> T): T = synchronized(lock, block)
}
