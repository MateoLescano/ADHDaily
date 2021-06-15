package com.citesoftware.test4.notificaciones.util

import java.util.concurrent.atomic.AtomicInteger

object RandomIntUtil {

    private val seed = AtomicInteger()

    fun getRandomInt() = seed.getAndIncrement()+ System.currentTimeMillis().toInt()
}