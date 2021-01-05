package cn.zxd.app.net

interface MessageStateListener {
    fun isSendSuccess(isSuccess: Boolean)
}