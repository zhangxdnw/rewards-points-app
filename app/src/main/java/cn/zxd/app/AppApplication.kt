package cn.zxd.app

import android.app.Application
import cn.zxd.app.net.ApiUtils
import cn.zxd.app.net.NettyClientManager
import com.hjimi.api.iminect.ImiNect
import com.tencent.bugly.crashreport.CrashReport

class AppApplication : Application() {

    companion object {
        lateinit var instance: AppApplication
        val handler = GlobalExceptionHandler
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        NettyClientManager.INSTANCE.connect(ApiUtils.baseUrl)
//        CrashReport.initCrashReport(this)
//        ImiNect.initialize()
    }
}