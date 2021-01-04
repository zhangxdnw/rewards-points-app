package cn.zxd.app.net

import org.tio.client.ClientChannelContext
import org.tio.client.ClientTioConfig
import org.tio.client.ReconnConf
import org.tio.client.TioClient
import org.tio.core.Node

class ClientStarter {

    companion object {
        @JvmStatic
        fun getInstance(): ClientStarter {
            return SingletonHolder.mInstance
        }
    }

    private object SingletonHolder {
        val mInstance: ClientStarter = ClientStarter()
    }

    private fun readResolve(): Any {
        return SingletonHolder.mInstance
    }

    var tioClient: TioClient? = null
    var clientChannelContext: ClientChannelContext? = null

    fun connect(host: String) {
        val tioConfig = ClientTioConfig(ClientAioHandler(), ClientAioListener(), ReconnConf(5000L))
        tioClient = TioClient(tioConfig)
        clientChannelContext = tioClient?.connect(Node(host, ApiConst.port))
    }

}