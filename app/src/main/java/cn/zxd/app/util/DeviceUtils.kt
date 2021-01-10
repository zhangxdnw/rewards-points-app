package cn.zxd.app.util

import android.os.Build
import java.lang.reflect.Method


fun getSerial(): String {
    var serial = Build.UNKNOWN
    try {
        val clazz = Class.forName("android.os.Build")
        val method: Method = clazz.getDeclaredMethod("getString", String::class.java)
        method.isAccessible = true
        serial = method.invoke(null, "ro.serialno").toString()
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return serial
}

