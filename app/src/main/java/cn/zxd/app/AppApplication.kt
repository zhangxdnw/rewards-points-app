package cn.zxd.app

import android.app.Application
import cn.zxd.app.net.ApiUtils
import cn.zxd.app.net.NettyClient

class AppApplication : Application() {

    companion object {
        lateinit var instance: AppApplication
        val handler = GlobalExceptionHandler
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        NettyClient.start(ApiUtils.baseUrl)
    }
}