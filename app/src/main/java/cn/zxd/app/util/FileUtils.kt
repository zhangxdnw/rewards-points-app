package cn.zxd.app.util

import android.graphics.Bitmap
import java.io.*
import java.nio.ByteBuffer

object FileUtils {

    fun readAllText(inputStream: InputStream): String {
        val inputReader = InputStreamReader(inputStream)
        val buffReader = BufferedReader(inputReader)
        return buffReader.readText()
    }

    fun writeToFile(data: ByteArray, filePath: String) {
        File(filePath).writeBytes(data)
    }

    fun writeToFile(bitmap:Bitmap, filePath: String) {
        val data= ByteArray(bitmap.getWidth() * bitmap.getHeight() * 4)
        bitmap.copyPixelsToBuffer(ByteBuffer.wrap(data));
        File(filePath).writeBytes(data)
    }
}