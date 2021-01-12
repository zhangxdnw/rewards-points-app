package cn.zxd.app.net

import okhttp3.Callback
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request

object HttpClient {

    var client: OkHttpClient =
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
        val request = Request.Builder().url(httpUrlBuilder.build()).build()
        client.newCall(request).enqueue(callback)
    }

    fun getAdvertise(request: AdvertiseRequest, callback: Callback) {
        val params = HashMap<String, String>()
        params.put("equipmentId", request.equipment)
        params.put("t", request.t.toString())
        get(ApiUtils.baseUrl+ ":8086" + ApiUtils.advertiseApi, params, callback)
    }
}