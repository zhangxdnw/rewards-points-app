package cn.zxd.app.util

import java.lang.reflect.Method


fun getSerial(): String {
    var serial = "unknown"
    try {
        val clazz = Class.forName("android.os.Build")
        val method: Method = clazz.getDeclaredMethod("getString", String.javaClass)
        method.isAccessible = true
        serial = method.invoke(null, "ro.serialno").toString()
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return serial
}

