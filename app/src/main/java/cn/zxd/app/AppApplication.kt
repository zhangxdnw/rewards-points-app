package cn.zxd.app

import android.app.Application
import android.content.Intent
import android.content.IntentFilter
import cn.zxd.app.net.ApiUtils
import cn.zxd.app.net.NettyClient
import cn.zxd.app.receiver.TimeTickReceiver
import cn.zxd.app.util.ActionUtils
import cn.zxd.app.util.ProcessUtils
import cn.zxd.app.work.FaceDetectWork
import com.hjimi.api.iminect.ImiNect

class AppApplication : Application() {

    companion object {
        lateinit var instance: AppApplication
        val handler = GlobalExceptionHandler
    }

    override fun onCreate() {
        super.onCreate()
        if (applicationInfo.packageName == ProcessUtils.getCurrentProcessName(this)) {
            instance = this
            registerReceiver(TimeTickReceiver, IntentFilter(Intent.ACTION_TIME_TICK))
            ActionUtils.doRequestAdvertise()
            ActionUtils.doRequestCoupon()
            ActionUtils.doRequestConfig()
            NettyClient.start(ApiUtils.baseUrl)
            ImiNect.initialize()
            sendBroadcast(
                Intent("android.intent.action.STATUSBAR_VISIBLE").putExtra(
                    "visible",
                    false
                )
            )
        }
    }

    override fun onTerminate() {
        super.onTerminate()
        FaceDetectWork.canceled = true
        FaceDetectWork.cameraDeInit()
        FaceDetectWork.detectDeInit()
        unregisterReceiver(TimeTickReceiver)
    }
}