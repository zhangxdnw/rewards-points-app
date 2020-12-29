package cn.zxd.app.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import cn.zxd.app.R
import cn.zxd.app.databinding.ActivityMainBinding
import cn.zxd.app.service.VMDaemonService

class MainActivity : BaseActivity() {

    lateinit var dataBinding:ActivityMainBinding
    private val clickMax = 1
    private var clickCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(dataBinding.root)
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