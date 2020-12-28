package cn.zxd.app.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import cn.zxd.app.R
import cn.zxd.app.service.VMDaemonService

class MainActivity : BaseActivity() {

    val clickMax = 1;
    var clickCount = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startService(Intent(this, VMDaemonService::class.java))
    }

    fun clickToSettings(view: View) {
        clickCount++
        if (clickCount >= clickMax) {
            clickCount = 0
            startActivity(Intent(view.context, SettingsActivity::class.java))
        }
    }
}