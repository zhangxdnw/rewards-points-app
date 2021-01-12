package cn.zxd.app.net

import android.util.Log
import cn.zxd.app.util.*
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import okhttp3.OkHttpClient
import org.json.JSONObject


object NettyClient {

    lateinit var socket: Socket

    private const val TAG = "NettyClient"

    private val onConnectError = Emitter.Listener {
        Log.e("NettyClient", "onConnectError", (it[0] as Exception))
    }

    fun start(host: String) {
        val okHttpClient = OkHttpClient.Builder()
            .hostnameVerifier(SSLSocketClient.getHostnameVerifier())
            .sslSocketFactory(
                SSLSocketClient.getSSLSocketFactory(),
                SSLSocketClient.myX509TrustManager
            ).build()
        IO.setDefaultOkHttpWebSocketFactory(okHttpClient)
        IO.setDefaultOkHttpCallFactory(okHttpClient)

        val opts = IO.Options()
        opts.callFactory = okHttpClient
        opts.webSocketFactory = okHttpClient
        opts.query = "token=" + getSerial() //传参数

        socket = IO.socket("${host}:5679", opts)
        socket.on(Socket.EVENT_CONNECT) {
            val deviceId = getSerial()
            Log.d(TAG, "loginname:${deviceId}")
            val password = RSAUtils.encrypt(
                "${deviceId}:${ApiUtils.appKey}:${RandomUtils.getRandomKey(6)}",
                ApiUtils.publicKey
            )
            Log.d(TAG, "password:${password}")
            val request = LoginRequest(getSerial(), password)
            Log.d(TAG, "request:${request.toJsonString()}")
            socket.emit(Message.CONNECT_LOGIN_REQ, request.toJsonObject())
        }.on(Socket.EVENT_CONNECT_ERROR, onConnectError).on(Message.CONNECT_LOGIN_RESP) { args ->
            val response = LoginResponse.fromJsonObject(args[0] as JSONObject)
            Log.d(TAG, "客户端收到登录响应数据:${response.toJSonString()}")
            if (response.code == 0) {
                val md5str: String =
                    MD5Utils.getHexMD5Str(getSerial() + ":" + RandomUtils.SAVE_RANDOM)
                Log.d(TAG, "客户端校验token:$response.sign")
                if (md5str == response.sign) {
                    Log.d(TAG, "客户端登录成功.")
                } else {
                    Log.d(TAG, "客户端登录失败, 客户端校验未通过,断开连接.")
                }
            } else {
                Log.d(TAG, "客户端登录失败, 服务端校验未通过,断开连接.")
            }
        }.on(Message.TRANSPORT_REQ) { args ->

            /**
             * 客户端收到来自服务端 数据, 需返回数据给服务器
             * 根据key, 返回设备配置
             * @param args
             */
            val obj = args[0] as JSONObject
            Log.d(TAG, "客户端收到交互请求数据:$obj")
            try {
                val key = obj.getString("key")
                val json = JSONObject()
                json.put("equipmentId", getSerial())
                json.put("key", key)
                json.put("message", "设备配置信息")
                Log.d(TAG, "客户端返回(交互请求)结果:$json")
                socket.emit(Message.TRANSPORT_REQ, json)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }.on(Message.TRANSPORT_RESP, Emitter.Listener { args ->
            val pushCommand = PushCommand.fromJsonObject(args[0] as JSONObject)
            Log.d(TAG, "客户端收到来自服务端响应数据:${pushCommand.toJsonString()}")
            when (pushCommand.action) {
                "CM_UPDATE" -> {
                    Log.d(TAG, "收到更新广告通知")
                    ActionUtils.doRequestAdvertise()
                }
                "CARD_UPDATE" -> {
                    Log.d(TAG, "收到更新卡券通知")
                    ActionUtils.doRequestAdvertise()
                }
                "NOTICE" -> {
                    Log.d(TAG, "收到普通服务端 /api/socketio/send JSON格式内容 消息")
                }
                else -> Log.d(TAG, "收到")
            }
        }).on(
            Socket.EVENT_DISCONNECT
        ) {
            Log.d(TAG, "disconnect")
        }
        socket.connect()
    }

    fun isConnected(): Boolean {
        return socket.connected()
    }

    fun stop() {
        socket.off(Socket.EVENT_CONNECT_ERROR, onConnectError)
        socket.disconnect()
    }

}