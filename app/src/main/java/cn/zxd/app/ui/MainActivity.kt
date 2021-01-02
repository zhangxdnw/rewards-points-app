package cn.zxd.app.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import cn.zxd.app.databinding.ActivityMainBinding
import cn.zxd.app.service.VMDaemonService
import cn.zxd.app.work.FaceDetectWork
import com.hjimi.api.iminect.ImiDevice
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : BaseActivity() {

    lateinit var dataBinding: ActivityMainBinding
    private val clickMax = 1
    private var clickCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(dataBinding.root)
        startService(Intent(this, VMDaemonService::class.java))
        FaceDetectWork.cameraInit(object : ImiDevice.OpenDeviceListener {
            override fun onOpenDeviceSuccess() {
                GlobalScope.launch {
                    FaceDetectWork.detectingFace()
                }
            }

            override fun onOpenDeviceFailed(p0: String?) {
                Toast.makeText(this@MainActivity, p0, Toast.LENGTH_SHORT).show()
            }

        })
    }

    override fun onStop() {
        super.onStop()
        FaceDetectWork.canceled = true
    }

    fun clickToSettings(view: View) {
        clickCount++
        if (clickCount >= clickMax) {
            clickCount = 0
            startActivity(Intent(view.context, SettingsActivity::class.java))
        }
    }
}