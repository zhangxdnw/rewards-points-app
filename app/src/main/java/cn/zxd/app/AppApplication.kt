package cn.zxd.app

import android.app.Application
import android.content.Intent
import android.content.IntentFilter
import cn.zxd.app.net.ApiUtils
import cn.zxd.app.net.NettyClient
import cn.zxd.app.receiver.TimeTickReceiver
import cn.zxd.app.util.ActionUtils

class AppApplication : Application() {

    companion object {
        lateinit var instance: AppApplication
        val handler = GlobalExceptionHandler
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        registerReceiver(TimeTickReceiver, IntentFilter(Intent.ACTION_TIME_TICK))
        ActionUtils.doRequestAdvertise()
        ActionUtils.doRequestCoupon()
        NettyClient.start(ApiUtils.baseUrl)
    }

    override fun onTerminate() {
        super.onTerminate()
        unregisterReceiver(TimeTickReceiver)
    }
}