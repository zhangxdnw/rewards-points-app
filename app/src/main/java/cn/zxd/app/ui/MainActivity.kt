package cn.zxd.app.ui

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.media.MediaPlayer
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
import cn.zxd.app.net.FacePointPushData
import cn.zxd.app.ui.fragment.CouponFragment
import cn.zxd.app.ui.fragment.FaceDetectFragment
import cn.zxd.app.ui.fragment.MainFragment
import cn.zxd.app.ui.fragment.RewardsFragment
import cn.zxd.app.ui.view.CoverDrawable
import cn.zxd.app.ui.view.face.util.DrawHelper
import cn.zxd.app.work.*
import com.alibaba.fastjson.JSON
import com.bumptech.glide.Glide
import com.hjimi.api.iminect.ImiDevice
import com.hjimi.api.iminect.ImiPixelFormat
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.*


class MainActivity : BaseActivity() {

    lateinit var dataBinding: ActivityMainBinding
    private val clickMax = 1
    private var clickCount = 0

    val frameMode = ImiDevice.getInstance().getCurrentFrameMode(ImiDevice.ImiStreamType.COLOR)
    val drawHelper = DrawHelper(960, 720, 960, 720, 0, 0, false, false, false)

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
        if (serverData != null) {
            Glide.with(this).load(serverData!!.bottom.path).into(dataBinding.ivBottomBanner)
        }
        dataBinding.vMask.background = CoverDrawable(ColorDrawable(Color.WHITE), 480, 300,200)
        EventBus.getDefault().register(this)

        FaceDetectWork.detectInit()
        supportFragmentManager.inTransaction { add(R.id.rl_topBanner, mainFragment) }
    }

    override fun onStart() {
        super.onStart()
        FaceDetectWork.cameraInit(object : ImiDevice.OpenDeviceListener {
            override fun onOpenDeviceSuccess() {
                GlobalScope.launch {
                    FaceDetectWork.canceled = false
                    FaceDetectWork.detectingFace()
                }
            }

            override fun onOpenDeviceFailed(p0: String?) {
                Log.e("FaceDetectWork", "onOpenDeviceFailed:$p0")
                Toast.makeText(this@MainActivity.applicationContext, p0, Toast.LENGTH_SHORT).show()
            }

        })
    }

    override fun onStop() {
        super.onStop()
        FaceDetectWork.canceled = true
        FaceDetectWork.cameraDeInit()
        FaceDetectWork.detectDeInit()
    }


    fun clickToSettings(view: View) {
        clickCount++
        if (clickCount >= clickMax) {
            clickCount = 0
            startActivity(Intent(view.context, SettingsActivity::class.java))
        }
    }

    fun clickToFaceDetect(line: Int, data: Any) {
        transFragment(FaceDetectFragment(line, data))
    }

    var count: Int = 0

    fun openFaceDetect() {
        dataBinding.glpColor.visibility = View.VISIBLE
        dataBinding.frvFaceRect.visibility = View.VISIBLE
    }

    fun closeFaceDetect() {
    }

    fun isShowFaceDetect(): Boolean {
        return dataBinding.glpColor.visibility == View.VISIBLE
    }

    fun showFaceDetect() {
        dataBinding.glpColor.visibility = View.VISIBLE
        dataBinding.frvFaceRect.visibility = View.VISIBLE
        dataBinding.vMask.visibility = View.VISIBLE
    }

    fun dismissFaceDetect() {
        dataBinding.glpColor.visibility = View.INVISIBLE
        dataBinding.frvFaceRect.visibility = View.INVISIBLE
        dataBinding.vMask.visibility = View.INVISIBLE
    }

    fun backToMain() {
        supportFragmentManager.inTransaction { replace(R.id.rl_topBanner, mainFragment) }
    }

    fun transFragment(fragment: Fragment) {
        supportFragmentManager.inTransaction { replace(R.id.rl_topBanner, fragment) }
    }

    @Subscribe(threadMode = ThreadMode.MAIN_ORDERED)
    fun onEvent(frame: CameraFrame) {
        if (dataBinding.glpColor.visibility == View.VISIBLE) {
            when (frame.type) {
                0 -> {
                    if (dataBinding.glpColor.visibility != View.VISIBLE) return
                    when (frameMode.format) {
                        ImiPixelFormat.IMI_PIXEL_FORMAT_IMAGE_YUV420SP, ImiPixelFormat.IMI_PIXEL_FORMAT_IMAGE_RGB24 -> {
                            dataBinding.glpColor.paint(
                                null,
                                frame.frame.data,
                                frame.frame.width,
                                frame.frame.height
                            )
                        }
                        else -> {
                            Log.e("MainActivity", "Format NOT support")
                        }
                    }
                }
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun toRewardsPoint(data: RewardsPoint) {
        MediaPlayer.create(this, R.raw.dingding).start()
        rewardsFragment.info = FacePointPushData.fromJsonObject(data.message)
        transFragment(rewardsFragment)
    }

    fun clickToCollectCoupon(view: View) {
        MediaPlayer.create(this, R.raw.welcome).start()
        transFragment(couponFragment)
    }
}