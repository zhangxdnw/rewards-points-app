package cn.zxd.app.net

import org.tio.client.intf.ClientAioListener
import org.tio.core.ChannelContext
import org.tio.core.intf.Packet

class ClientAioListener : ClientAioListener {

    override fun onAfterConnected(p0: ChannelContext?, p1: Boolean, p2: Boolean) {
        TODO("Not yet implemented")
    }

    override fun onAfterDecoded(p0: ChannelContext?, p1: Packet?, p2: Int) {
        TODO("Not yet implemented")
    }

    override fun onAfterReceivedBytes(p0: ChannelContext?, p1: Int) {
        TODO("Not yet implemented")
    }

    override fun onAfterSent(p0: ChannelContext?, p1: Packet?, p2: Boolean) {
        TODO("Not yet implemented")
    }

    override fun onAfterHandled(p0: ChannelContext?, p1: Packet?, p2: Long) {
        TODO("Not yet implemented")
    }

    override fun onBeforeClose(p0: ChannelContext?, p1: Throwable?, p2: String?, p3: Boolean) {
        TODO("Not yet implemented")
    }
}