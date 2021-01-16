package cn.zxd.app.ui.fragment

import android.content.Context
import android.util.Log
import android.view.View
import cn.zxd.app.R
import cn.zxd.app.databinding.FragmentCouponBinding
import cn.zxd.app.net.CouponResponse
import cn.zxd.app.net.CouponResponseData
import cn.zxd.app.ui.MainActivity
import cn.zxd.app.ui.view.countdown.CountDownView
import com.google.gson.Gson

class CouponFragment : BaseFragment<FragmentCouponBinding>(R.layout.fragment_coupon) {

    private val TAG = "CouponFragment"

    lateinit var couponResponse: CouponResponseData

    override fun initBinding(view: View): FragmentCouponBinding {
        return FragmentCouponBinding.bind(view)
    }

    override fun initView() {
        //开始扫描人脸
        binding.cdvBack.setCountDownTimerListener(object : CountDownView.CountDownTimerListener {
            override fun onStartCount() {
                Log.d(TAG, "倒计时开始")
            }

            override fun onFinishCount() {
                binding.cdvBack.cancel()
                (activity as MainActivity).backToMain()
            }

        })
        binding.cdvBack.start()
        binding.btnGetCoupon.setOnClickListener {
            binding.cdvBack.cancel()
            (activity as MainActivity).clickToFaceDetect(1, couponResponse)
        }
    }

    override fun loadDate() {
        val sharedPreferences = context?.applicationContext?.getSharedPreferences(
            "server_response",
            Context.MODE_PRIVATE
        )
        val couponResponseString = sharedPreferences?.getString("coupon_server_data", "")
        if (!couponResponseString.isNullOrEmpty()) {
            couponResponse = Gson().fromJson(couponResponseString, CouponResponseData::class.java)
            binding.tvCouponInfo.text =
                "${couponResponse.card_title}\n优惠券金额:${couponResponse.card_amount}"
        }
    }
}