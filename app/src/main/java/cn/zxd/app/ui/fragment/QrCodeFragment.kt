package cn.zxd.app.ui.fragment

import android.view.View
import cn.zxd.app.R
import cn.zxd.app.databinding.FragmentQrCodeBinding
import cn.zxd.app.ui.MainActivity

class QrCodeFragment(val line: Int) :
    BaseFragment<FragmentQrCodeBinding>(R.layout.fragment_qr_code) {
    override fun initBinding(view: View): FragmentQrCodeBinding {
        return FragmentQrCodeBinding.bind(view)
    }

    override fun initView() {
    }

    override fun loadDate() {
    }

    fun toRewardsResult() {
        (activity as MainActivity).transFragment(RewardsResultFragment())
    }

    fun toCouponResult() {
        (activity as MainActivity).transFragment(CouponResultFragment())
    }
}