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

data class PushCommand(val action: String, val data: String) {
    companion object {
        fun fromJsonObject(data: JSONObject): PushCommand {
            return PushCommand(
                data.getString("action"),
                data.getString("data")
            )
        }
    }

    fun toJsonString(): String {
        return JSON.toJSONString(this)
    }
}

data class FacePointPushData(
    val shopCode: String,
    val totalPrice: Double,
    val orderNum: String,
    val equipmentId: String
) {
    companion object {
        fun fromJsonObject(data: String): FacePointPushData {
            val jsonObject = JSONObject(data)
            return FacePointPushData(
                jsonObject.getString("shopCode"),
                jsonObject.getDouble("totalPrice"),
                jsonObject.getString("orderNum"),
                jsonObject.getString("equipmentId")
            )
        }
    }

    fun toJsonString(): String {
        return JSON.toJSONString(this)
    }
}