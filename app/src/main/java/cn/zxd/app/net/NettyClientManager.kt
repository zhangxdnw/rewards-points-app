package cn.zxd.app.net

enum class NettyClientManager {
    INSTANCE;

    lateinit var client: NettyConnectClient

    private val listener: NettyClientListener<ClientPacket> = object : NettyClientListener<ClientPacket> {
        override fun onMessageResponseClient(msg: ClientPacket, index: Int) {
        }

        override fun onClientStatusConnectChanged(statusCode: Int, index: Int) {
            when (statusCode) {
                ConnectState.STATUS_CONNECT_SUCCESS -> {
                    client.sendMsgToServer(
                        LoginMessage("00000001").encode()
                    )
                }
            }
        }

    }

    fun connect(host: String) {
        client = NettyConnectClient.Builder().setHost(host).setTcpPort(5678).build()
        client.setListener(listener)
        client.connect()
    }
}