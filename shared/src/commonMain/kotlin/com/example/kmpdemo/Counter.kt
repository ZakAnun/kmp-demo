package com.example.kmpdemo

class Counter(initial: Int = 0) {
    var value: Int = initial
        private set

    fun increment(): Int {
        value += 1
        return value
    }
}
