package cn.zxd.app.net

import cn.zxd.app.BuildConfig

object ApiUtils {

    var baseUrl: String = BuildConfig.BaseHost

    var appKey: String = BuildConfig.ApiKey

    const val publicKey: String =
        "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC8XUd5AA/Q6UlUJBItb8m9KNOhSHbiQw3XyRpM/eWO7J/m/PBp1uEcrBs9HpY6uC6ZkIQThI7BFQgvrTfaxn/FY/A2piy75P+7+YyE+ykwHWgT8ELM+bYtm1FRN0gVqV8K1vjS63zFQ+oKfoD9rb+YmD4o2ieBkDzZCa+qo9yuCQIDAQAB"


    const val advertiseApi = "/api/advert/list"

    const val appImageConfigApi = "/api/app/img"

    const val rewardsPointApi = "/api/member/face/score"

    const val collectCouponApi = "/api/member/face/card"

    const val couponApi = "/api/card/gefromcrm"

}

open class Request(val equipmentId: String)

open class Response<T>(var code: Int = -1, val message: String, val data: T)

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

//人脸积分接口
class FacePointRequest(
    equipmentId: String, val orderNum: String,
    val totalPrice: Double, val shopCode: String,
    val colorImage: String,
    var depthImage: String? = null,
    var irImage: String? = null
) : Request(equipmentId)

data class FacePointResponseData(
    val name: String,
    val score: Double,
    val url: String,
    val message: String
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
    val cardTitle: String,
    val cardUrl: String,
    val url: String,
    val message: String
)

class FaceCardResponse(code: Int, message: String, data: FaceCardResponseData) :
    Response<FaceCardResponseData>(code, message, data)

//APP拉取优惠券
data class CouponResponseData(
    val card_id: String,
    val card_type: Int,
    val card_title: String,
    val card_url: String,
    val card_amount: Double,
    val message: String
)

class CouponResponse(code: Int, message: String, data: CouponResponseData) :
    Response<CouponResponseData>(code, message, data)