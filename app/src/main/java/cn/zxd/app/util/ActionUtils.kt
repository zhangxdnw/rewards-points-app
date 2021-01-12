package cn.zxd.app.util

import android.util.Log
import android.widget.Toast
import cn.zxd.app.AppApplication
import cn.zxd.app.net.AdvertiseRequest
import cn.zxd.app.net.AdvertiseResponse
import cn.zxd.app.net.HttpClient
import com.alibaba.fastjson.JSON
import com.bumptech.glide.Glide
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException

object ActionUtils {

    private const val TAG = "ActionUtils"
    val application = AppApplication.instance

    fun doRequestAdvertise() {
        HttpClient.getAdvertise(AdvertiseRequest(getSerial(), 1), object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Toast.makeText(application, "获取广告信息失败", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call, response: Response) {
                response.body()?.let {
                    Log.d(TAG, it.string())
                    val adResponse = JSON.parseObject(it.string(), AdvertiseResponse::class.java)
                    if (adResponse.code == 0) {
                    } else {
                        Toast.makeText(application, adResponse.message, Toast.LENGTH_LONG).show()
                    }
                } ?: let { Log.d(TAG, "获取广告信息返回为空") }
            }
        })
    }

}