package cn.zxd.app.net

import org.tio.core.intf.Packet

class ClientPacket(var type: Byte, var body: ByteArray?) : Packet() {

    companion object {
        val HEADER_LENGTH = 5 //消息头的长度 1+4

        val CHARSET = "utf-8"
    }

}