package cn.zxd.app.ui.fragment

import android.view.View
import cn.zxd.app.R
import cn.zxd.app.databinding.FragmentCouponResultBinding

class CouponResultFragment :
    BaseFragment<FragmentCouponResultBinding>(R.layout.fragment_coupon_result) {
    override fun initBinding(view: View): FragmentCouponResultBinding {
        return FragmentCouponResultBinding.bind(view)
    }

    override fun initView() {
    }

    override fun loadDate() {
    }
}