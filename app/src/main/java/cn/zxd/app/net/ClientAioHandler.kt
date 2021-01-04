package cn.zxd.app.net

import cn.zxd.app.util.getSerial
import com.alibaba.fastjson.JSON
import org.tio.client.intf.ClientAioHandler
import org.tio.core.ChannelContext
import org.tio.core.TioConfig
import org.tio.core.exception.TioDecodeException
import org.tio.core.intf.Packet
import java.nio.ByteBuffer


class ClientAioHandler : ClientAioHandler {

    override fun decode(
        buffer: ByteBuffer?,
        limit: Int,
        position: Int,
        readableLength: Int,
        channelContext: ChannelContext?
    ): Packet? {
        if (readableLength < ClientPacket.HEADER_LENGTH) {
            return null
        }

        //消息类型
        val type = buffer!!.get()
        val bodyLength = buffer.int

        if (bodyLength < 0) {
            throw TioDecodeException("bodyLength [" + bodyLength + "] is not right, remote:" + channelContext!!.clientNode)
        }

        val neededLength: Int = ClientPacket.HEADER_LENGTH + bodyLength

        return if (readableLength < neededLength) {
            null
        } else {
            if (bodyLength > 0) {
                val dst = ByteArray(bodyLength)
                buffer.get(dst)
                ClientPacket(type, dst)
            } else {
                ClientPacket(type, null)
            }
        }

    }

    override fun encode(
        packet: Packet?,
        tioConfig: TioConfig?,
        channelContext: ChannelContext?
    ): ByteBuffer? {
        if (packet == null) return null
        val bodyLength = if((packet as ClientPacket).body == null) 0 else packet.body!!.size

        //总长度是消息头的长度+消息体的长度
        val allLen: Int = ClientPacket.HEADER_LENGTH + bodyLength

        val buffer = ByteBuffer.allocate(allLen)
        buffer.order(tioConfig!!.byteOrder)

        //写入消息类型
        buffer.put(packet.type)
        //写入消息体长度
        //写入消息体长度
        buffer.putInt(bodyLength)

        //写入消息体

        //写入消息体
        if (packet.body != null) {
            buffer.put(packet.body)
        }

        return buffer
    }

    override fun handler(packet: Packet?, channelContext: ChannelContext?) {

    }

    override fun heartbeatPacket(p0: ChannelContext?): Packet {
        return ClientPacket(
            MessageType.HEART_BEAT_REQ,
            JSON.toJSONBytes(HeartBeatReqBody(getSerial()))
        )
    }


}