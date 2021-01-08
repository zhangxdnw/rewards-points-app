package cn.zxd.app

import android.app.Application
import cn.zxd.app.net.ApiUtils

class AppApplication : Application() {

    companion object {
        lateinit var instance: AppApplication
        val handler = GlobalExceptionHandler
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
//        CrashReport.initCrashReport(this)
//        ImiNect.initialize()
    }
}