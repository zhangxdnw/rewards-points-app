package cn.zxd.app.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import cn.zxd.app.ui.MainActivity
import cn.zxd.app.service.VMDaemonService
import cn.zxd.app.ui.SplashActivity


class BootCompletedReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent!!.action.equals("android.intent.action.BOOT_COMPLETED")) {
            context!!.startActivity(Intent(context, SplashActivity::class.java))
            context.startService(Intent(context, VMDaemonService::class.java))
        }
    }

}