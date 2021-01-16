package cn.zxd.app.work

import com.arcsoft.face.FaceInfo
import com.hjimi.api.iminect.ImiDevice

data class CameraFrame(var type: Int, var frame: ImiDevice.ImiFrame, var time: Long)

data class PreviewData(val data: ByteArray, val faces: List<FaceInfo>, val time: Long)

data class SendData(val data:ByteArray, val face:FaceInfo)

data class RewardsPoint(val message: String)