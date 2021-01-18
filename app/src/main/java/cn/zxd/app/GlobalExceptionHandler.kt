package cn.zxd.app

import android.os.Process
import android.util.Log
import java.lang.Thread.UncaughtExceptionHandler
import kotlin.system.exitProcess


object GlobalExceptionHandler : UncaughtExceptionHandler {

    //系统默认的UncaughtException处理类
    var mDefaultHandler: UncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler()

    init {
        Thread.setDefaultUncaughtExceptionHandler(this)
    }

    private fun handleException(e: Throwable): Boolean {
        e.printStackTrace()
        return true
    }

    override fun uncaughtException(t: Thread, e: Throwable) {
        if (!handleException(e)) {
            //如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultHandler.uncaughtException(t, e)
        } else {
            AppApplication.instance.onTerminate()
            try {
                Thread.sleep(3000)
            } catch (e: InterruptedException) {
                Log.e("GlobalExceptionHandler", "error : ", e)
            }
            //退出程序
            Process.killProcess(Process.myPid())
            exitProcess(1)
        }
    }
}