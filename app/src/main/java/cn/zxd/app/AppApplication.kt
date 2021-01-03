package cn.zxd.app

import android.app.Application
import android.widget.Toast
import com.arcsoft.face.ActiveFileInfo
import com.arcsoft.face.ErrorInfo
import com.arcsoft.face.FaceEngine
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

        CrashReport.initCrashReport(this)
        ImiNect.initialize()
    }
}