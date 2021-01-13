package cn.zxd.app.util

import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

object FileUtils {

    fun readAllText(inputStream: InputStream):String {
        val inputReader = InputStreamReader(inputStream)
        val buffReader = BufferedReader(inputReader)
        return buffReader.readText()
    }

}