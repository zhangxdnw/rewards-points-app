package cn.zxd.app.net

import org.apache.mina.core.service.IoConnector
import org.apache.mina.core.service.IoHandler
import org.apache.mina.filter.codec.ProtocolCodecFilter
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory
import org.apache.mina.filter.logging.LoggingFilter
import org.apache.mina.transport.socket.nio.NioSocketConnector
import java.io.Serializable

class ClientManager private constructor() : Serializable {

    companion object {
        @JvmStatic
        fun getInstance(): ClientManager {
            return SingletonHolder.mInstance
        }
    }

    private object SingletonHolder {
        val mInstance: ClientManager = ClientManager()
    }

    private fun readResolve(): Any {
        return SingletonHolder.mInstance
    }

    lateinit var mConnector: IoConnector

    fun init(handler: IoHandler) {
        mConnector = NioSocketConnector();

        mConnector.filterChain.addLast("logf", LoggingFilter())
        mConnector.filterChain.addLast(
            "objType",
            ProtocolCodecFilter(ObjectSerializationCodecFactory())
        )

        mConnector.handler = handler

        mConnector.sessionConfig.readBufferSize = 2048

    }

    fun connect() {

    }

}