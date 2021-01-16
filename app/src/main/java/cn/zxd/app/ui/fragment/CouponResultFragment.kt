package cn.zxd.app.ui.fragment

import android.view.View
import cn.zxd.app.R
import cn.zxd.app.databinding.FragmentCouponResultBinding
import cn.zxd.app.net.FaceCardResponseData
import cn.zxd.app.ui.MainActivity
import cn.zxd.app.util.QrUtils
import com.bumptech.glide.Glide

class CouponResultFragment(val data: FaceCardResponseData) :
    BaseFragment<FragmentCouponResultBinding>(R.layout.fragment_coupon_result) {
    override fun initBinding(view: View): FragmentCouponResultBinding {
        return FragmentCouponResultBinding.bind(view)
    }

    override fun initView() {
        binding.tvBack.setOnClickListener {
            (activity as MainActivity).backToMain()
        }
    }

    override fun loadDate() {
        binding.tvMessage1.text = data.message
        binding.tvMessage2.text = data.message2
        if (!data.url.isNullOrEmpty()) {
            binding.ivQr.setImageBitmap(QrUtils.createBitmap(data.url, 200, 200))
        }
    }
}