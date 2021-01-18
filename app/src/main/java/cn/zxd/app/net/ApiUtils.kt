package cn.zxd.app.net

import android.content.Context
import cn.zxd.app.AppApplication
import cn.zxd.app.BuildConfig

object ApiUtils {

    private val sharedPreferences =
        AppApplication.instance.getSharedPreferences("app_config", Context.MODE_PRIVATE)

    var baseUrl: String
        get() {
            return sharedPreferences.getString("baseUrl", BuildConfig.BaseHost)!!
        }
        set(value) {
            sharedPreferences.edit().putString("baseUrl", value).apply()
        }

    var appKey: String
        get() {
            return sharedPreferences.getString("appKey", BuildConfig.ApiKey)!!
        }
        set(value) {
            sharedPreferences.edit().putString("appKey", value).apply()
        }

    const val publicKey: String =
        "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC8XUd5AA/Q6UlUJBItb8m9KNOhSHbiQw3XyRpM/eWO7J/m/PBp1uEcrBs9HpY6uC6ZkIQThI7BFQgvrTfaxn/FY/A2piy75P+7+YyE+ykwHWgT8ELM+bYtm1FRN0gVqV8K1vjS63zFQ+oKfoD9rb+YmD4o2ieBkDzZCa+qo9yuCQIDAQAB"


    const val advertiseApi = "/api/advert/list"

    const val appImageConfigApi = "/api/app/img"

    const val rewardsPointApi = "/api/member/face/score"

    const val collectCouponApi = "/api/member/face/card"

    const val couponApi = "/api/card/gefromcrm"

    const val appConfigApi = "/api/device/config"
}

open class Request(val equipmentId: String)

open class Response<T>(var code: Int = -1, val msg: String, val data: T)

//广告接口
class AdvertiseRequest(equipmentId: String, var t: Int = 0) : Request(equipmentId)

data class AdvertiseInformation(
    val seconds: Double,
    val ordering: Int,
    val describe: String,
    val type: Int,
    val url: String
)

data class AdvertiseResponseData(
    val bottom: List<AdvertiseInformation>,
    val center: List<AdvertiseInformation>,
    val name: String,
    val id: Int,
    val describe: String,
    val order: Int
)

class AdvertiseResponse(code: Int, message: String, data: List<AdvertiseResponseData>) :
    Response<List<AdvertiseResponseData>>(code, message, data)

//APP图片更新(预留)
data class AppImageResponseData(
    val startupImgUrl: String,
    val getCouponsImgUrl: String,
    val getPointsImgUrl: String
)

class AppImageResponse(code: Int, message: String, data: AppImageResponseData) :
    Response<AppImageResponseData>(code, message, data)

//APP配置
data class AppConfigResponseData(
    val protocolUrl: String,
    val protocolTip: String,
    val faceScoreEndUrl: String,
    val faceCardEndUrl: String
)

class AppConfigResponse(code: Int, message: String, data: AppConfigResponseData) :
    Response<AppConfigResponseData>(code, message, data)

//人脸积分接口
class FacePointRequest(
    equipmentId: String, val orderNum: String,
    val totalPrice: Double, val shopCode: String,
    val colorImage: String,
    var depthImage: String? = null,
    var irImage: String? = null
) : Request(equipmentId)

data class FacePointResponseData(
    val url: String,
    val message: String,
    val message2: String
)

class FacePointResponse(code: Int, message: String, data: FacePointResponseData) :
    Response<FacePointResponseData>(code, message, data)

//人脸领取优惠券
class FaceCardRequest(
    equipmentId: String, val cardId: String, val colorImage: String,
    var depthImage: String? = null,
    var irImage: String? = null
) : Request(equipmentId)

data class FaceCardResponseData(
    val url: String,
    val message: String,
    val message2: String
)

class FaceCardResponse(code: Int, message: String, data: FaceCardResponseData) :
    Response<FaceCardResponseData>(code, message, data)

//APP拉取优惠券
data class CouponResponseData(
    val cardId: String,
    val cardType: Int,
    val message: String,
    val message2: String,
    val cardUrl: String
)

class CouponResponse(code: Int, message: String, data: CouponResponseData) :
    Response<CouponResponseData>(code, message, data)