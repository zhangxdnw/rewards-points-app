package cn.zxd.app.ui.fragment

import android.util.Log
import android.view.View
import android.widget.Toast
import cn.zxd.app.R
import cn.zxd.app.databinding.FragmentRequestLoadingBinding
import cn.zxd.app.net.*
import cn.zxd.app.ui.MainActivity
import cn.zxd.app.util.*
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException

class RequestLoadingFragment(
    private val line: Int,
    private val imageData: ByteArray,
    private val data: Any
) :
    BaseFragment<FragmentRequestLoadingBinding>(R.layout.fragment_request_loading) {

    private val TAG = "RequestLoadingFragment"

    override fun initBinding(view: View): FragmentRequestLoadingBinding {
        return FragmentRequestLoadingBinding.bind(view)
    }

    override fun initView() {

    }

    override fun loadDate() {
        when (line) {
            1 -> {
                val couponInfo = data as CouponResponseData
                GlobalScope.async {
                    val bitmap = BitmapUtils.createMyBitmap(imageData, 640, 480)
                    val request = FaceCardRequest(
                        getSerial(),
                        couponInfo.cardId,
                        BitmapUtils.bitmapToBase64(bitmap)
                    )
                    ActionUtils.doRequestFaceCard(request, object : Callback {
                        override fun onFailure(call: Call, e: IOException) {
                            Toast.makeText(context, "doRequestFaceCard失败", Toast.LENGTH_SHORT)
                                .show()
                        }

                        override fun onResponse(call: Call, response: Response) {
                            response.body()?.let {
                                val realResponseString = it.string()
                                Log.d(TAG, "doRequestFaceCard$realResponseString")
                                val faceCardResponse = Gson().fromJson(
                                    realResponseString,
                                    FaceCardResponse::class.java
                                )
                                if(faceCardResponse.data == null) {
                                    activity?.runOnUiThread {
                                        Toast.makeText(context, "${faceCardResponse.msg}", Toast.LENGTH_SHORT)
                                            .show()
                                        (activity as MainActivity).backToMain()
                                    }
                                } else {
                                    //展示信息
                                    toCouponResult(faceCardResponse.data)
                                }
                            }
                        }

                    })
                }
            }
            0 -> {
                val pointInfo = data as FacePointPushData
                GlobalScope.async {
                    val request = FacePointRequest(
                        getSerial(),
                        pointInfo.orderNum,
                        pointInfo.totalPrice,
                        pointInfo.shopCode,
                        Base64Utils.encode(imageData)
                    )
                    ActionUtils.doRequestFacePoint(
                        request, object : Callback {
                            override fun onFailure(call: Call, e: IOException) {
                                Toast.makeText(context, "doRequestFacePoint失败", Toast.LENGTH_SHORT)
                                    .show()
                            }

                            override fun onResponse(call: Call, response: Response) {
                                response.body()?.let {
                                    val realResponseString = it.string()
                                    Log.d(TAG, "doRequestFaceCard$realResponseString")
                                    val facePointResponse = Gson().fromJson(
                                        realResponseString,
                                        FacePointResponse::class.java
                                    )
                                    if(facePointResponse.data == null) {
                                        activity?.runOnUiThread {
                                            Toast.makeText(context, "${facePointResponse.msg}", Toast.LENGTH_SHORT)
                                                .show()
                                            (activity as MainActivity).backToMain()
                                        }
                                    } else {
                                        //展示信息
                                        toRewardsResult(facePointResponse.data)
                                    }
                                }
                            }

                        })
                }
            }
        }
    }

    fun toRewardsResult(data: FacePointResponseData) {
        (activity as MainActivity).transFragment(RewardsResultFragment(data))
    }

    fun toCouponResult(data: FaceCardResponseData) {
        (activity as MainActivity).transFragment(CouponResultFragment(data))
    }
}