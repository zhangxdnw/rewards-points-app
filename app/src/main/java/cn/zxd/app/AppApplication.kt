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
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        CrashReport.initCrashReport(this)
        //在线初始化人脸SDK
        val activeInfo = ActiveFileInfo()
        if (FaceEngine.getActiveFileInfo(this, activeInfo) != ErrorInfo.MOK) {
            //需在线激活
            val code = FaceEngine.activeOnline(
                this,
                "8hPXvnwXn9njNBFgSzSgvaY4j4bBMUpgcuDZ9wnU1vjS",
                "SDK_KEY:7xeRySuBjrESXKyCBu7kfXuee3mZxWceLm7kkPDYcgkE"
            )
            if (code != ErrorInfo.MOK) {
                Toast.makeText(this, "人脸识别SDK激活失败", Toast.LENGTH_SHORT).show()
            }
        }
        ImiNect.initialize()
    }

    override fun onTerminate() {
        super.onTerminate()
        ImiNect.destroy()
    }
}