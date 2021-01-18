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
import org.greenrobot.eventbus.EventBus
import java.nio.ByteBuffer


object FaceDetectWork {

    var canceled: Boolean = false
    var needFace = true
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

    private fun clone(original: ByteBuffer): ByteBuffer? {
        val clone = ByteBuffer.allocate(original.capacity())
        // 使缓冲区准备好重新读取已经包含的数据：它保持限制不变，并将位置设置为零。
        // 因为 put(buffer) 会在内部遍历buffer，如果不执行rewind，position值将不会被重置
        original.rewind()
        clone.put(original)
        original.rewind()
        // 这个是将clone转换为读的这状态，否则将无法读取出数据
        clone.flip()
        return clone
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
                EventBus.getDefault().post(CameraFrame(0, colorFrame, System.currentTimeMillis()))
                if (needFace) {
                    val data: ByteBuffer? = clone(colorFrame.data)
                    if (data != null) {
                        val byteArray = ByteArray(data.remaining())
                        data.get(byteArray, 0, byteArray.size)
                        //处理区域
                        val code = faceEngine.detectFaces(
                            byteArray,
                            colorFrame.width,
                            colorFrame.height,
                            FaceEngine.CP_PAF_BGR24,
                            faceInfoList
                        )
                        if (code == ErrorInfo.MOK && faceInfoList.isNotEmpty()) {
                            val rect = faceInfoList[0].rect
                            if ((rect.bottom - rect.top) * (rect.right - rect.left) >= 640 * 480 / 25)
                                EventBus.getDefault()
                                    .post(
                                        PreviewData(
                                            byteArray,
                                            faceInfoList,
                                            System.currentTimeMillis()
                                        )
                                    )
                        } else {
                            continue
                        }
                    }
                }
            }
        }
        ImiDevice.getInstance().stopStream(
            ImiDevice.ImiStreamType.COLOR.toNative()
                    and ImiDevice.ImiStreamType.DEPTH.toNative() and ImiDevice.ImiStreamType.IR.toNative()
        )
    }
}