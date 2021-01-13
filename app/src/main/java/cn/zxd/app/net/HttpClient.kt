package cn.zxd.app.net

import com.google.gson.Gson
import okhttp3.*
import okhttp3.Request

object HttpClient {

    private const val HTTP_PORT = ":8086"

    private var client: OkHttpClient =
        OkHttpClient.Builder().hostnameVerifier(SSLSocketClient.getHostnameVerifier())
            .sslSocketFactory(
                SSLSocketClient.getSSLSocketFactory(),
                SSLSocketClient.myX509TrustManager
            ).build()

    fun get(url: String, params: Map<String, String>, callback: Callback) {
        val httpUrlBuilder = HttpUrl.parse(url)!!.newBuilder()
        for ((key, value) in params) {
            httpUrlBuilder.addQueryParameter(key, value)
        }
        val request = Request.Builder().url(httpUrlBuilder.build()).get().build()
        client.newCall(request).enqueue(callback)
    }

    fun post(url: String, body: RequestBody, callback: Callback) {
        val request = Request.Builder().url(url).post(body).build()
        client.newCall(request).enqueue(callback)
    }

    fun getAdvertise(request: AdvertiseRequest, callback: Callback) {
        val params = HashMap<String, String>()
        params["equipmentId"] = request.equipmentId
        params["t"] = request.t.toString()
        get(ApiUtils.baseUrl + HTTP_PORT + ApiUtils.advertiseApi, params, callback)
    }

    fun getCoupon(request: cn.zxd.app.net.Request, callback: Callback) {
        val params = HashMap<String, String>()
        params["equipmentId"] = request.equipmentId
        get(ApiUtils.baseUrl + HTTP_PORT + ApiUtils.couponApi, params, callback)
    }

    fun postFaceCard(request: FaceCardRequest, callback: Callback) {
        val formBody = FormBody.create(
            MediaType.parse("application/json"), Gson().toJson(request)
        )
        post(ApiUtils.baseUrl + HTTP_PORT + ApiUtils.collectCouponApi, formBody, callback)
    }
}