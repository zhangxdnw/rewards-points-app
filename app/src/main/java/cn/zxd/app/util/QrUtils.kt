package cn.zxd.app.util

import android.graphics.Bitmap
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix
import com.journeyapps.barcodescanner.BarcodeEncoder


object QrUtils {

    fun createBitmap(str: String, width:Int, height:Int): Bitmap? {
        var bitmap: Bitmap? = null
        var result: BitMatrix? = null
        val multiFormatWriter = MultiFormatWriter()
        try {
            result = multiFormatWriter.encode(str, BarcodeFormat.QR_CODE, width, height)
            val barcodeEncoder = BarcodeEncoder()
            bitmap = barcodeEncoder.createBitmap(result)
        } catch (e: Exception) {
            return null
        }
        return bitmap
    }

}