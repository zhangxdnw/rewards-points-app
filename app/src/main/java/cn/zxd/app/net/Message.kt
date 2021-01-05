package cn.zxd.app.net

import android.util.Log
import cn.zxd.app.util.RSAUtils
import cn.zxd.app.util.RandomUtils

object MessageType {
    /**
     * 登录消息请求
     */
    var LOGIN_REQ: Byte = 1

    /**
     * 登录消息响应
     */
    var LOGIN_RESP: Byte = 2

    /**
     * 心跳
     */
    var HEART_BEAT_REQ: Byte = 99
}

data class ClientPacket(var messageType: Byte, var body: ByteArray?) {
    companion object{
        val HEADER_LENGTH = 5
    }
}

class LoginMessage(var deviceId: String) {

    fun encode(): ClientPacket {
//        var encodeStr: String = RSAUtils.encryptByPublicKey(
//            deviceId + ":" + "111111",
//            RSAUtils.getPublicKeyFromX509(ApiUtils.appKey)
//        )
        //客户端 发送登录消息:login 00000001 pe55M2F5-3APegtmWlpX0Wfc-66VOErYc6W-QFKrb63lHRXcnbt9AcxIWwQET6kg4I3zLa2miMwRjR8EvHSEMSav6qR*s2L35rFBQuA2c*jkgnjBpIbqdlmbNdXk-xPoZb08WwIPYKOnCj-r8DUYemF7aeHJApZqKGYuf5OK59Q=
        deviceId = "00000001"
        var encodeStr = "pe55M2F5-3APegtmWlpX0Wfc-66VOErYc6W-QFKrb63lHRXcnbt9AcxIWwQET6kg4I3zLa2miMwRjR8EvHSEMSav6qR*s2L35rFBQuA2c*jkgnjBpIbqdlmbNdXk-xPoZb08WwIPYKOnCj-r8DUYemF7aeHJApZqKGYuf5OK59Q="
        Log.e("ZXD", "{\"loginname\":\"$deviceId\",\"password\":\"$encodeStr\"}")
        return ClientPacket(
            MessageType.LOGIN_REQ,
            "{\"loginname\":\"$deviceId\",\"password\":\"$encodeStr\"}".toByteArray()
        )
    }

}

