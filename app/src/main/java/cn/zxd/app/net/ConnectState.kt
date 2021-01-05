package cn.zxd.app.net

class ConnectState {

    companion object {

        @JvmField
        val STATUS_CONNECT_ERROR = -1

        @JvmField
        val STATUS_CONNECT_CLOSED = 0

        @JvmField
        val STATUS_CONNECT_SUCCESS = 1
    }
}