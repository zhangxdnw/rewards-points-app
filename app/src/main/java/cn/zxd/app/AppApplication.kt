package cn.zxd.app

import android.app.Application
import com.tencent.bugly.crashreport.CrashReport

class AppApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        CrashReport.initCrashReport(this)
    }
}