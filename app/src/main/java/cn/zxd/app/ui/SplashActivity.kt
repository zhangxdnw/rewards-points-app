package cn.zxd.app.ui

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import cn.zxd.app.R
import com.arcsoft.face.ActiveFileInfo
import com.arcsoft.face.ErrorInfo
import com.arcsoft.face.FaceEngine
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class SplashActivity : BaseActivity() {

    lateinit var dialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }

    override fun onStart() {
        super.onStart()
        //在线初始化人脸SDK
        val activeInfo = ActiveFileInfo()
        val result = FaceEngine.getActiveFileInfo(this, activeInfo)
        if (result != ErrorInfo.MOK) {
            //需在线激活
            Log.e("SplashActivity", "FaceEngine Active Error:$result")
            dialog = ProgressDialog(this)
            dialog.setTitle("Active Face SDK")
            dialog.setMessage("First active FaceSDK online, need connect INTERNET. Please wait...")
            GlobalScope.async {
                FaceEngine.activeOnline(
                    this@SplashActivity,
                    "8hPXvnwXn9njNBFgSzSgvaY4j4bBMUpgcuDZ9wnU1vjS",
                    "7xeRySuBjrESXKyCBu7kfXuee3mZxWceLm7kkPDYcgkE"
                )
                dialog.dismiss()
                jumpToMain()
            }
            dialog.show()
        } else {
        jumpToMain()
        }
    }

    private fun jumpToMain() {
        window.decorView.postDelayed({ startActivity(Intent(this, MainActivity::class.java)) }, 500)
    }
}