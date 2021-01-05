package cn.zxd.app.ui

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import cn.zxd.app.net.ApiUtils
import cn.zxd.app.net.ConnectState
import cn.zxd.app.net.NettyClientListener
import cn.zxd.app.net.NettyConnectClient

class SplashActivity : BaseActivity() {

    lateinit var dialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        val client = NettyConnectClient.Builder().setHost(ApiUtils.baseUrl).setTcpPort(5678).build()
//        client.setListener(object : NettyClientListener<String> {
//            override fun onMessageResponseClient(msg: String, index: Int) {
//
//            }
//
//            override fun onClientStatusConnectChanged(statusCode: Int, index: Int) {
//                when (statusCode == ConnectState.STATUS_CONNECT_SUCCESS) {
//                    //login
//                    //client.sendMsgToServer()
//                }
//            }
//
//        })
//        client.connect()
    }

    override fun onStart() {
        super.onStart()
//        //在线初始化人脸SDK
//        val activeInfo = ActiveFileInfo()
//        val result = FaceEngine.getActiveFileInfo(this, activeInfo)
//        if (result != ErrorInfo.MOK) {
//            //需在线激活
//            Log.e("SplashActivity", "FaceEngine Active Error:$result")
//            dialog = ProgressDialog(this)
//            dialog.setTitle("Active Face SDK")
//            dialog.setMessage("First active FaceSDK online, need connect INTERNET. Please wait...")
//            GlobalScope.async {
//                FaceEngine.activeOnline(
//                    this@SplashActivity,
//                    "8hPXvnwXn9njNBFgSzSgvaY4j4bBMUpgcuDZ9wnU1vjS",
//                    "7xeRySuBjrESXKyCBu7kfXuee3mZxWceLm7kkPDYcgkE"
//                )
//                dialog.dismiss()
//                jumpToMain()
//            }
//            dialog.show()
//        } else {
        jumpToMain()
//        }
    }

    private fun jumpToMain() {
        window.decorView.postDelayed({ startActivity(Intent(this, MainActivity::class.java)) }, 500)
    }
}