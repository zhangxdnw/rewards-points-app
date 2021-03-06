package cn.zxd.app.util

import java.util.*

object RandomUtils {

    lateinit var SAVE_RANDOM: String

    private const val KEY = "0123456789"
    fun getRandomKey(length: Int): String? { // length 字符串长度
        val sb = StringBuffer()
        val r = Random()
        val range = KEY.length
        for (i in 0 until length) {
            sb.append(KEY[r.nextInt(range)])
        }
        SAVE_RANDOM = sb.toString()
        return SAVE_RANDOM
    }

}