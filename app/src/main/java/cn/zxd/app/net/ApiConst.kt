package cn.zxd.app.net

object ApiConst {
    val port = 5678
}

object MessageType {
    /**
     * 登录消息请求
     */
    val LOGIN_REQ: Byte = 1

    /**
     * 登录消息响应
     */
    val LOGIN_RESP: Byte = 2

    /**
     * 心跳
     */
    var HEART_BEAT_REQ: Byte = 99
}