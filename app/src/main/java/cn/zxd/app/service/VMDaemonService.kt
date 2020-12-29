package cn.zxd.app.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

class VMDaemonService : Service() {


    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onStart(intent: Intent?, startId: Int) {
        super.onStart(intent, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("VMDaemonService", "onDestroy, Restart")
        startService(Intent(this, VMDaemonService::class.java))
    }
}