package cn.zxd.app.work

import android.util.Log
import android.widget.Toast
import cn.zxd.app.AppApplication
import com.arcsoft.face.ErrorInfo
import com.arcsoft.face.FaceEngine
import com.arcsoft.face.FaceInfo
import com.arcsoft.face.enums.DetectFaceOrientPriority
import com.arcsoft.face.enums.DetectMode
import com.hjimi.api.iminect.ImiDevice
import com.hjimi.api.iminect.ImiNect
import com.hjimi.api.iminect.Utils
import org.greenrobot.eventbus.EventBus
import java.util.*


object FaceDetectWork {

    var canceled: Boolean = false
    private val faceEngine = FaceEngine()

    fun cameraInit(listener: ImiDevice.OpenDeviceListener) {
        ImiNect.initialize()
        ImiDevice.getInstance().open(
            AppApplication.instance, ImiDevice.ImiStreamType.COLOR.toNative()
                    or ImiDevice.ImiStreamType.IR.toNative(),
            listener
        )
    }

    fun cameraDeInit() {
        ImiDevice.getInstance().close()
        ImiNect.destroy()
    }

    fun detectInit(): Boolean {
        val result = faceEngine.init(
            AppApplication.instance,
            DetectMode.ASF_DETECT_MODE_IMAGE,
            DetectFaceOrientPriority.ASF_OP_0_ONLY,
            32, 3,
            FaceEngine.ASF_FACE_DETECT
        )
        if (result != ErrorInfo.MOK) {
            Log.e("FaceDetectWork", "Detect Init Error:$result")
            Toast.makeText(AppApplication.instance, "Detect Init Error:$result", Toast.LENGTH_LONG)
                .show()
        }
        return result == ErrorInfo.MOK
    }

    fun detectDeInit() {
        faceEngine.unInit()
    }

    fun detectingFace() {
        ImiDevice.getInstance().startStream(
            ImiDevice.ImiStreamType.COLOR.toNative()
                    and ImiDevice.ImiStreamType.IR.toNative()
        )
        val faceInfoList: List<FaceInfo> = ArrayList()
        while (!canceled) {
            val colorFrame =
                ImiDevice.getInstance().readNextFrame(ImiDevice.ImiStreamType.COLOR, 50)
            if (colorFrame != null) {
                EventBus.getDefault().post(CameraFrame(0, colorFrame))
                Log.d("FaceDetectWork", "getImage")
                val byteArray = ByteArray(colorFrame.data.remaining())
                colorFrame.data.get(byteArray, 0, byteArray.size)
                Log.d("FaceDetectWork", "copy RGB data")
                val code = faceEngine.detectFaces(
                    byteArray,
                    colorFrame.width,
                    colorFrame.height,
                    FaceEngine.CP_PAF_BGR24,
                    faceInfoList
                )
                Log.d("FaceDetectWork", "detectFaces result:$code")
                if (code == ErrorInfo.MOK) {
                    EventBus.getDefault().post(faceInfoList)
                } else {
                    continue
                }
            }
//            val irFrame = ImiDevice.getInstance().readNextFrame(ImiDevice.ImiStreamType.IR, 50)
//            if (irFrame != null)
//                EventBus.getDefault().post(CameraFrame(1, irFrame))
        }
        ImiDevice.getInstance().stopStream(
            ImiDevice.ImiStreamType.COLOR.toNative()
                    and ImiDevice.ImiStreamType.DEPTH.toNative() and ImiDevice.ImiStreamType.IR.toNative()
        )
    }
}