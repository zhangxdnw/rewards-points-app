package cn.zxd.app.ui

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.media.MediaPlayer
import android.os.Bundle
import android.os.PowerManager
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import cn.zxd.app.R
import cn.zxd.app.databinding.ActivityMainBinding
import cn.zxd.app.net.AdvertiseResponseData
import cn.zxd.app.net.FacePointPushData
import cn.zxd.app.ui.fragment.CouponFragment
import cn.zxd.app.ui.fragment.FaceDetectFragment
import cn.zxd.app.ui.fragment.MainFragment
import cn.zxd.app.ui.fragment.RewardsFragment
import cn.zxd.app.ui.view.CoverDrawable
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
    private val clickMax = 10
    private var clickCount = 0

    val frameMode = ImiDevice.getInstance().getCurrentFrameMode(ImiDevice.ImiStreamType.COLOR)
//    val drawHelper = DrawHelper(640, 480, 640, 480, 0, 0, false, false, false)

    var needFace = false
        set(value) {
            FaceDetectWork.needFace = value
            field = value
        }

    var firstFace = 0L
    var lastFace = 0L
    var faceCount = 0
    var sharedPreferences: SharedPreferences? = null
    var serverData: List<AdvertiseResponseData>? = null

    val mainFragment = MainFragment()
    val couponFragment = CouponFragment()
    val rewardsFragment = RewardsFragment()

    val faceCenterRect = Rect(75, 75, 225, 225)

    inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> Unit) {
        val fragmentTransaction = beginTransaction()
        fragmentTransaction.func()
        fragmentTransaction.commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(dataBinding.root)
        sharedPreferences = getSharedPreferences("server_response", MODE_PRIVATE)
        val serverDataStr = sharedPreferences?.getString("advertise_server_data", "")
        serverData = if (!serverDataStr.isNullOrEmpty()) JSON.parseArray(
            serverDataStr,
            AdvertiseResponseData::class.java
        ) else null
        if (serverData != null) {
            if (serverData!!.isNotEmpty() && serverData!![0].bottom.isNotEmpty()) {
                Glide.with(this).load(serverData!![0].bottom[0].url)
                    .into(dataBinding.ivBottomBanner)
            }
        }
        dataBinding.vMask.background = CoverDrawable(ColorDrawable(Color.WHITE), 320, 240, 150)
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
                runOnUiThread {
                    AlertDialog.Builder(this@MainActivity).setTitle("摄像头初始化失败")
                        .setMessage("是否重启设备？").setPositiveButton(
                            "重启"
                        ) { _, _ ->
                            //重启
                            val pm = getSystemService(POWER_SERVICE) as PowerManager
                            pm.reboot("")
                        }
                        .setNegativeButton("取消", null)
                        .setCancelable(false)
                        .create().show()
                }
            }

        })
    }

    override fun onStop() {
        super.onStop()
        FaceDetectWork.canceled = true
        FaceDetectWork.cameraDeInit()
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
        dataBinding.vMask.visibility = View.VISIBLE
    }

    fun dismissFaceDetect() {
        dataBinding.glpColor.visibility = View.INVISIBLE
        dataBinding.vMask.visibility = View.INVISIBLE
    }

    fun backToMain() {
        supportFragmentManager.inTransaction { replace(R.id.rl_topBanner, mainFragment) }
    }

    fun transFragment(fragment: Fragment) {
        if(supportFragmentManager.findFragmentById(R.id.rl_topBanner) == fragment) {
            fragment.onPause()
            fragment.onResume()
        } else {
            supportFragmentManager.inTransaction { replace(R.id.rl_topBanner, fragment) }
        }
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
        if (needFace) {
            if (dataBinding.vMask.visibility == View.VISIBLE) {
                val area = (previewData.faces[0].rect.right-previewData.faces[0].rect.left) *
                        (previewData.faces[0].rect.bottom - previewData.faces[0].rect.top)
                if (faceCenterRect.contains(
                        previewData.faces[0].rect.centerX(),
                        previewData.faces[0].rect.centerY()
                    )
                    && (area >= 120 * 120) && area <= 180 * 180) {
                    Log.d("MainActivity", "get face to upload${previewData.faces}")
                    needFace = false
                    Thread.sleep(1000)
                    EventBus.getDefault().post(SendData(previewData.data, previewData.faces[0]))
                }
            } else {
                Log.d("MainActivity", "get face")
                if (firstFace <= 0) {
                    firstFace = System.currentTimeMillis()
                    lastFace = firstFace
                    faceCount = 1
                } else {
                    if (System.currentTimeMillis() - lastFace > 200) {
                        //200毫秒没有人脸 重新计算
                        firstFace = System.currentTimeMillis()
                        lastFace = firstFace
                        faceCount = 1
                    } else {
                        lastFace = System.currentTimeMillis()
                        faceCount++
                    }
                }
                if (faceCount >= 10) {
                    Log.d("MainActivity", "get face to card")
                    faceCount = 0
                    firstFace = 0
                    lastFace = 0
                    needFace = false
                    MediaPlayer.create(this, R.raw.welcome).start()
                    transFragment(couponFragment)
                }
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun toRewardsPoint(data: RewardsPoint) {
        MediaPlayer.create(this, R.raw.dingding).start()
        rewardsFragment.info = FacePointPushData.fromJsonObject(data.message)
        transFragment(rewardsFragment)
    }
}