package cn.zxd.app.net

import com.alibaba.fastjson.JSON
import org.json.JSONObject

class Message {
    companion object {
        // 登录请求
        const val CONNECT_LOGIN_REQ = "LOGIN_REQ"

        // 登录响应
        const val CONNECT_LOGIN_RESP = "LOGIN_RESP"

        // 交互请求
        const val TRANSPORT_REQ = "TRANSPORT_REQ"

        // 交互响应
        const val TRANSPORT_RESP = "TRANSPORT_RESP"
    }

}

data class LoginRequest(val loginname: String, val password: String) {

    fun toJsonObject(): JSONObject {
        return JSONObject().put("loginname", loginname).put("password", password)
    }

    fun toJsonString(): String {
        return JSON.toJSONString(this)
    }
}

data class LoginResponse(val code: Int, val sign: String) {
    companion object {
        fun fromJsonObject(data: JSONObject): LoginResponse {
            return LoginResponse(data.getInt("code"), data.getString("sign"))
        }
    }

    fun toJSonString(): String {
        return JSON.toJSONString(this)
    }
}

data class PushCommand(val action: String, val data: PushData?) {
    companion object {
        fun fromJsonObject(data: JSONObject): PushCommand {
            return PushCommand(
                data.getString("action"),
                PushData.fromJsonObject(data.getJSONObject("data"))
            )
        }
    }

    fun toJsonString(): String {
        return JSON.toJSONString(this)
    }
}

data class PushData(
    val shopCode: String,
    val totalPrice: Double,
    val orderNum: String,
    val equipmentId: String
) {
    companion object {
        fun fromJsonObject(data: JSONObject): PushData {
            return PushData(
                data.getString("shopCode"),
                data.getDouble("totalPrice"),
                data.getString("orderNum"),
                data.getString("equipmentId")
            )
        }
    }

    fun toJsonString(): String {
        return JSON.toJSONString(this)
    }
}