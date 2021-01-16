package cn.zxd.app.util

import android.content.Context
import android.util.Log
import android.widget.Toast
import cn.zxd.app.AppApplication
import cn.zxd.app.net.*
import com.alibaba.fastjson.JSON
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException

object ActionUtils {

    private const val TAG = "ActionUtils"
    val application = AppApplication.instance
    val serverResponseSharedInfo =
        application.getSharedPreferences("server_response", Context.MODE_PRIVATE)!!

    fun doRequestAdvertise() {
        HttpClient.getAdvertise(AdvertiseRequest(getSerial(), 1), object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Toast.makeText(application, "获取广告信息失败", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call, response: Response) {
                response.body()?.let {
                    val realResponseString = it.string()
                    Log.d(TAG, "doRequestAdvertise$realResponseString")
                    val adResponse =
                        Gson().fromJson(realResponseString, AdvertiseResponse::class.java)
                    if (adResponse.code == 0) {
                        serverResponseSharedInfo.edit()
                            .putLong("advertise_update_time", System.currentTimeMillis())
                            .putString("advertise_server_data", JSON.toJSONString(adResponse.data))
                            .apply()
                    } else {
                        Toast.makeText(application, adResponse.message, Toast.LENGTH_LONG).show()
                    }
                } ?: let { Log.d(TAG, "获取广告信息返回为空") }
            }
        })
    }

    fun doRequestCoupon() {
        HttpClient.getCoupon(Request(getSerial()), object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Toast.makeText(application, "获取广告信息失败", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call, response: Response) {
                response.body()?.let {
                    val realResponseString = it.string()
                    Log.d(TAG, "doRequestCoupon:$realResponseString")
                    val couponResponse =
                        Gson().fromJson(realResponseString, CouponResponse::class.java)
                    if (couponResponse.code == 0) {
                        serverResponseSharedInfo.edit()
                            .putLong("coupon_update_time", System.currentTimeMillis())
                            .putString("coupon_server_data", JSON.toJSONString(couponResponse.data))
                            .apply()
                    } else {
                        Toast.makeText(application, couponResponse.message, Toast.LENGTH_LONG)
                            .show()
                    }
                }
            }
        })
    }

    fun doRequestFaceCard(request: FaceCardRequest, callback: Callback) {
        HttpClient.postFaceCard(request, callback)
    }

    fun doRequestFacePoint(request: FacePointRequest, callback: Callback) {
        HttpClient.postFacePoint(request, callback)
    }

    fun doRequestConfig() {
        HttpClient.postAppConfig(Request(getSerial()), object:Callback{
            override fun onFailure(call: Call, e: IOException) {
            }

            override fun onResponse(call: Call, response: Response) {
                response.body()?.let {
                    val realResponseString = it.string()
                    Log.d(TAG, "doRequestConfig:$realResponseString")
                    val configResponse =
                        Gson().fromJson(realResponseString, AppConfigResponse::class.java)
                    if (configResponse.code == 0) {
                        serverResponseSharedInfo.edit()
                            .putString("server_config", JSON.toJSONString(configResponse.data))
                            .apply()
                    }
                }
            }
        })
    }
}