package com.example.kmpdemo.host

import platform.Foundation.NSLock

internal actual object HostContextLocker {
    private val lock = NSLock()

    actual fun <T> withLock(block: () -> T): T {
        lock.lock()
        return try {
            block()
        } finally {
            lock.unlock()
        }
    }
}
