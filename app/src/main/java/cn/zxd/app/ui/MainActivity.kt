package cn.zxd.app.ui

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import cn.zxd.app.databinding.ActivityMainBinding
import cn.zxd.app.ui.view.face.model.DrawInfo
import cn.zxd.app.ui.view.face.util.DrawHelper
import cn.zxd.app.work.CameraFrame
import cn.zxd.app.work.FaceDetectWork
import com.arcsoft.face.FaceInfo
import com.hjimi.api.iminect.ImiDevice
import com.hjimi.api.iminect.ImiPixelFormat
import com.hjimi.api.iminect.Utils
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
    val drawHelper = DrawHelper(640, 480, 640, 480, 0, 0, false, false, false)

    val faceDelay = 5000L
    var firstFace = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(dataBinding.root)
//        startService(Intent(this, VMDaemonService::class.java))
        EventBus.getDefault().register(this)
        FaceDetectWork.detectInit()
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
                Toast.makeText(this@MainActivity, p0, Toast.LENGTH_SHORT).show()
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

    @Subscribe(threadMode = ThreadMode.MAIN_ORDERED)
    fun onEvent(frame: CameraFrame) {
        when (frame.type) {
            0 -> {
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

                    }
                }
            }
            1 -> {
                dataBinding.glpIr.paint(
                    null,
                    Utils.ir2RGB888(frame.frame, false),
                    frame.frame.width,
                    frame.frame.height
                )
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onDrawFace(faceInfoList: List<FaceInfo>) {
        if (faceInfoList.isNotEmpty()) {
            if (firstFace == 0L) {
                firstFace = System.currentTimeMillis()
            }
            val drawInfoList: MutableList<DrawInfo> = ArrayList()
            for (i in faceInfoList.indices) {
                drawInfoList.add(
                    DrawInfo(
                        drawHelper.adjustRect(faceInfoList[i].rect),
                        0, 0, 0, Color.YELLOW, ""
                    )
                )
            }
            drawHelper.draw(dataBinding.frvFaceRect, drawInfoList)
        } else {
            firstFace = 0;
        }
        if (firstFace > 0 && (System.currentTimeMillis() - firstFace >= faceDelay)) {
            firstFace = 0;
            startActivity(Intent(this@MainActivity, CouponActivity::class.java))
        }
    }
}