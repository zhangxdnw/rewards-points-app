package cn.zxd.app.ui.fragment

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.view.View
import cn.zxd.app.R
import cn.zxd.app.databinding.FragmentCouponBinding
import cn.zxd.app.net.CouponResponse
import cn.zxd.app.net.CouponResponseData
import cn.zxd.app.ui.MainActivity
import cn.zxd.app.ui.view.countdown.CountDownView
import com.bumptech.glide.Glide
import com.google.gson.Gson

class CouponFragment : BaseFragment<FragmentCouponBinding>(R.layout.fragment_coupon) {

    private val TAG = "CouponFragment"

    lateinit var couponResponse: CouponResponseData
    var sharedPreferences: SharedPreferences? = null

    override fun initBinding(view: View): FragmentCouponBinding {
        return FragmentCouponBinding.bind(view)
    }

    override fun initView() {
        //开始扫描人脸
        binding.ivCardImage.setOnClickListener {
            (activity as MainActivity).clickToFaceDetect(1, couponResponse)
        }
    }

    override fun loadDate() {
        sharedPreferences = context?.applicationContext?.getSharedPreferences(
            "server_response",
            Context.MODE_PRIVATE
        )
    }

    override fun onResume() {
        super.onResume()
        val couponResponseString = sharedPreferences?.getString("coupon_server_data", "")
        if (!couponResponseString.isNullOrEmpty()) {
            couponResponse = Gson().fromJson(couponResponseString, CouponResponseData::class.java)
            binding.tvCardMessage1.text = couponResponse.message
            binding.tvCardMessage2.text = couponResponse.message2
            Glide.with(requireActivity()).load(couponResponse.cardUrl).into(binding.ivCardImage)
        }
    }
}