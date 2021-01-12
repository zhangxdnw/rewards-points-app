package cn.zxd.app.ui

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import cn.zxd.app.R
import cn.zxd.app.bean.Advertisement
import cn.zxd.app.databinding.ActivityMainBinding
import cn.zxd.app.net.AdvertiseRequest
import cn.zxd.app.net.HttpClient
import cn.zxd.app.ui.fragment.CouponFragment
import cn.zxd.app.ui.fragment.MainFragment
import cn.zxd.app.ui.fragment.RewardsFragment
import cn.zxd.app.ui.view.face.model.DrawInfo
import cn.zxd.app.ui.view.face.util.DrawHelper
import cn.zxd.app.work.CameraFrame
import cn.zxd.app.work.FaceDetectWork
import cn.zxd.app.work.PreviewData
import com.alibaba.fastjson.JSON
import com.arcsoft.face.FaceInfo
import com.bumptech.glide.Glide
import com.hjimi.api.iminect.ImiDevice
import com.hjimi.api.iminect.ImiPixelFormat
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.io.IOException
import java.util.*


class MainActivity : BaseActivity() {

    lateinit var dataBinding: ActivityMainBinding
    private val clickMax = 1
    private var clickCount = 0
//    val frameMode = ImiDevice.getInstance().getCurrentFrameMode(ImiDevice.ImiStreamType.COLOR)
    val drawHelper = DrawHelper(640, 480, 640, 480, 0, 0, false, false, false)

    val faceDelay = 5000L
    var firstFace = 0L
    var sharedPreferences: SharedPreferences? = null
    var serverData: Advertisement? = null

    val mainFragment = MainFragment()
    val couponFragment = CouponFragment()
    val rewardsFragment = RewardsFragment()

    inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> Unit) {
        val fragmentTransaction = beginTransaction()
        fragmentTransaction.func()
        fragmentTransaction.commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(dataBinding.root)
        sharedPreferences = getSharedPreferences("app_data", MODE_PRIVATE)
        val serverDataStr = sharedPreferences?.getString("ServerData", "")
        serverData = if (!serverDataStr.isNullOrEmpty()) JSON.parseObject(
            serverDataStr,
            Advertisement::class.java
        ) else null
        if (serverData!= null) {
            Glide.with(this).load(serverData!!.bottom.path).into(dataBinding.ivBottomBanner)
        }
        EventBus.getDefault().register(this)

//        FaceDetectWork.detectInit()
        supportFragmentManager.inTransaction { add(R.id.rl_topBanner, mainFragment) }
    }

    override fun onStart() {
        super.onStart()
//        FaceDetectWork.cameraInit(object : ImiDevice.OpenDeviceListener {
//            override fun onOpenDeviceSuccess() {
//                GlobalScope.launch {
//                    FaceDetectWork.canceled = false
//                    FaceDetectWork.detectingFace()
//                }
//            }
//
//            override fun onOpenDeviceFailed(p0: String?) {
//                Log.e("FaceDetectWork", "onOpenDeviceFailed:$p0")
//                Toast.makeText(this@MainActivity.applicationContext, p0, Toast.LENGTH_SHORT).show()
//            }
//
//        })
    }

    override fun onStop() {
        super.onStop()
//        FaceDetectWork.canceled = true
//        FaceDetectWork.cameraDeInit()
//        FaceDetectWork.detectDeInit()
    }


    fun clickToSettings(view: View) {
        clickCount++
        if (clickCount >= clickMax) {
            clickCount = 0
            startActivity(Intent(view.context, SettingsActivity::class.java))
        }
    }

    var count: Int = 0

    fun openFaceDetect() {
        dataBinding.glpColor.visibility = View.VISIBLE
        dataBinding.frvFaceRect.visibility = View.VISIBLE
    }

    fun closeFaceDetect() {

    }

    fun transFragment(fragment: Fragment) {
        supportFragmentManager.inTransaction { replace(R.id.rl_topBanner, fragment) }
    }

    fun clickToTest(view: View) {
        if (count++ % 2 == 0) {
            transFragment(couponFragment)
        } else {
            transFragment(rewardsFragment)
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN_ORDERED)
    fun onEvent(frame: CameraFrame) {
        when (frame.type) {
            0 -> {
                if (dataBinding.glpColor.visibility != View.VISIBLE) return
//                when (frameMode.format) {
//                    ImiPixelFormat.IMI_PIXEL_FORMAT_IMAGE_YUV420SP, ImiPixelFormat.IMI_PIXEL_FORMAT_IMAGE_RGB24 -> {
//                        dataBinding.glpColor.paint(
//                            null,
//                            frame.frame.data,
//                            frame.frame.width,
//                            frame.frame.height
//                        )
//                    }
//                    else -> {
//                        Log.e("MainActivity", "Format NOT support")
//                    }
//                }
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onDrawFace(previewData: PreviewData) {
        if (dataBinding.frvFaceRect.visibility == View.VISIBLE) {   //需要识别人脸


            if (dataBinding.glpColor.visibility == View.VISIBLE) {  //显示人脸

            }
        }
//        if (faceInfoList.isNotEmpty()) {
//            if (firstFace == 0L) {
//                firstFace = System.currentTimeMillis()
//            }
//            val drawInfoList: MutableList<DrawInfo> = ArrayList()
//            for (i in faceInfoList.indices) {
//                drawInfoList.add(
//                    DrawInfo(
//                        drawHelper.adjustRect(faceInfoList[i].rect),
//                        0, 0, 0, Color.YELLOW, ""
//                    )
//                )
//            }
//            drawHelper.draw(dataBinding.frvFaceRect, drawInfoList)
//        } else {
//            firstFace = 0;
//        }
//        if (firstFace > 0 && (System.currentTimeMillis() - firstFace >= faceDelay)) {
//            firstFace = 0;
//
//        }
    }

    fun clickToGetAd(view: View) {

    }
}