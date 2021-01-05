package cn.zxd.app.net

data class ClientPacket(val messageType:Byte, var body:ByteArray)

