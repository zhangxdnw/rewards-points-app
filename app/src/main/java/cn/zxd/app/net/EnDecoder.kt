package cn.zxd.app.net

import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.ByteToMessageDecoder
import io.netty.handler.codec.MessageToByteEncoder

class NettyEncoder : MessageToByteEncoder<ClientPacket>() {

    override fun encode(ctx: ChannelHandlerContext?, msg: ClientPacket?, out: ByteBuf?) {
        if (msg != null) {
            out?.writeByte(msg.messageType.toInt())
            if (msg.body != null) {
                out?.writeInt(ClientPacket.HEADER_LENGTH + msg.body!!.size)
                out?.writeBytes(msg.body)
            } else {
                out?.writeInt(ClientPacket.HEADER_LENGTH)
            }
        }
    }
}

class NettyDecoder : ByteToMessageDecoder() {
    override fun decode(ctx: ChannelHandlerContext?, `in`: ByteBuf?, out: MutableList<Any>?) {
        val messageType = `in`?.readByte()
        val length = `in`?.readInt()
        if (length!! < ClientPacket.HEADER_LENGTH) {
            return
        }
        val body = `in`.readBytes(length).array()
        if (messageType != null) {
            out?.add(ClientPacket(messageType, body))
        }
    }

}