package cn.zxd.app.util

import android.content.Context
import android.util.Log
import android.widget.Toast
import cn.zxd.app.AppApplication
import cn.zxd.app.net.*
import com.alibaba.fastjson.JSON
import com.google.gson.Gson
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
//                    val realResponseString = it.string()
                    val realResponseString =
                        "{\"msg\":\"成功\",\"code\":0,\"data\":{\"bottom\":{\"seconds\":3.0,\"ordering\":10,\"describe\":\"广告资源1\",\"type\":1,\"url\":\"http://8.135.63.236/resource/advert//2021/01/08/f1e86786-290b-4533-a203-8972d40a6af4.jpg\"},\"center\":[{\"seconds\":3.0,\"ordering\":10,\"describe\":\"描述2\",\"type\":1,\"url\":\"http://8.135.63.236/resource/advert//2021/01/08/9660403c-859c-49c4-ae2b-4322a68ad44f.jpg\"}],\"name\":\"数据采集\",\"id\":1,\"describe\":null,\"order\":100}}"
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
//                    val realResponseString = it.string()
                    val realResponseString =
                        "{\"msg\":\"成功\",\"code\":0,\"data\":{\"card_title\":\"卡券标题\",\"card_url\":\"http://8.135.63.236/resource/advert/default/card.png\",\"card_amount\":100.0,\"card_type\":1,\"message\":\"\",\"card_id\":1}}"
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

    fun doRequestFacePoint() {

    }
}