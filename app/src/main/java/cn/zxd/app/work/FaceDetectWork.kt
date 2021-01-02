package cn.zxd.app.work

import cn.zxd.app.AppApplication
import com.arcsoft.face.ErrorInfo
import com.arcsoft.face.FaceEngine
import com.arcsoft.face.enums.DetectFaceOrientPriority
import com.arcsoft.face.enums.DetectMode
import com.hjimi.api.iminect.ImiDevice

object FaceDetectWork {

    var canceled: Boolean = false
    private val faceEngine = FaceEngine()

    fun cameraInit(listener: ImiDevice.OpenDeviceListener) {
        ImiDevice.getInstance().open(
            AppApplication.instance, ImiDevice.ImiStreamType.COLOR.toNative()
                    or ImiDevice.ImiStreamType.DEPTH.toNative() or ImiDevice.ImiStreamType.IR.toNative(),
            listener
        )
    }

    fun cameraDeInit() {
        ImiDevice.getInstance().close()
    }

    fun detectInit(): Boolean {
        val result = faceEngine.init(
            AppApplication.instance,
            DetectMode.ASF_DETECT_MODE_IMAGE,
            DetectFaceOrientPriority.ASF_OP_0_ONLY,
            32, 3,
            FaceEngine.ASF_FACE_DETECT or FaceEngine.ASF_FACE_RECOGNITION or FaceEngine.ASF_LIVENESS
        )
        return result == ErrorInfo.MOK
    }

    fun detectDeInit() {
        faceEngine.unInit()
    }

    fun detectingFace() {
        //TODO 调用native检测人脸 有可能要用到 camera preview
        ImiDevice.getInstance().startStream(
            ImiDevice.ImiStreamType.COLOR.toNative()
                    and ImiDevice.ImiStreamType.DEPTH.toNative() and ImiDevice.ImiStreamType.IR.toNative()
        )
        while (!canceled) {
            val frame = ImiDevice.getInstance().readNextFrame(ImiDevice.ImiStreamType.COLOR, 50)

//            ImiDevice.getInstance().readNextFrame(ImiDevice.ImiStreamType.DEPTH, 50)
//            ImiDevice.getInstance().readNextFrame(ImiDevice.ImiStreamType.IR, 50)
        }
        ImiDevice.getInstance().stopStream(
            ImiDevice.ImiStreamType.COLOR.toNative()
                    and ImiDevice.ImiStreamType.DEPTH.toNative() and ImiDevice.ImiStreamType.IR.toNative()
        )
    }


}