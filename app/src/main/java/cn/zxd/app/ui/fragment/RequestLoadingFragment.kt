package cn.zxd.app.ui.fragment

import android.util.Log
import android.view.View
import android.widget.Toast
import cn.zxd.app.R
import cn.zxd.app.databinding.FragmentRequestLoadingBinding
import cn.zxd.app.net.CouponResponseData
import cn.zxd.app.net.FaceCardRequest
import cn.zxd.app.net.FaceCardResponse
import cn.zxd.app.net.FaceCardResponseData
import cn.zxd.app.ui.MainActivity
import cn.zxd.app.util.ActionUtils
import cn.zxd.app.util.getSerial
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException

class RequestLoadingFragment(private val line: Int,private val imageData:String, private val data: Any) :
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
                ActionUtils.doRequestFaceCard(FaceCardRequest(
                    getSerial(),
                    couponInfo.card_id,
                    imageData
                ),
                    object : Callback {
                        override fun onFailure(call: Call, e: IOException) {
                            Toast.makeText(context, "doRequestFaceCard失败", Toast.LENGTH_SHORT)
                                .show()
                        }

                        override fun onResponse(call: Call, response: Response) {
                            response.body()?.let {
                                val realResponseString = it.string()
                                Log.d(TAG, "doRequestFaceCard$realResponseString")
                                val faceCardResponse = Gson().fromJson(realResponseString, FaceCardResponse::class.java)
                                if (faceCardResponse.data.url.isNullOrEmpty()) {
                                    //展示信息
                                    toCouponResult(faceCardResponse.data)
                                } else {
                                    //显示二维码
                                    toQrCode(faceCardResponse.data.url)
                                }
                            }
                        }

                    })
            }
            0 -> {

            }
        }
    }

    fun toQrCode(url:String) {
        //停止扫描人脸 并 获取一张图片
        (activity as MainActivity).transFragment(QrCodeFragment(url))
    }

    fun toRewardsResult() {
        (activity as MainActivity).transFragment(RewardsResultFragment())
    }

    fun toCouponResult(data: FaceCardResponseData) {
        (activity as MainActivity).transFragment(CouponResultFragment(data))
    }
}