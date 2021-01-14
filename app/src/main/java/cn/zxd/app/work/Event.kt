package cn.zxd.app.work

import com.hjimi.api.iminect.ImiDevice

data class CameraFrame(var type: Int, var frame: ImiDevice.ImiFrame, var time: Long)

data class PreviewData(var data: ByteArray, var time: Long)

data class RewardsPoint(val message:String)