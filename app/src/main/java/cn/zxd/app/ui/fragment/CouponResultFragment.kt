package cn.zxd.app.ui.fragment

import android.view.View
import cn.zxd.app.R
import cn.zxd.app.databinding.FragmentCouponResultBinding
import cn.zxd.app.net.FaceCardResponseData
import com.bumptech.glide.Glide

class CouponResultFragment(val data: FaceCardResponseData) :
    BaseFragment<FragmentCouponResultBinding>(R.layout.fragment_coupon_result) {
    override fun initBinding(view: View): FragmentCouponResultBinding {
        return FragmentCouponResultBinding.bind(view)
    }

    override fun initView() {
    }

    override fun loadDate() {
        Glide.with(this).load(data.cardUrl).into(binding.tvCouponResult)
    }
}