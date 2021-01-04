package cn.zxd.app.ui.fragment

import android.view.View
import cn.zxd.app.R
import cn.zxd.app.databinding.FragmentRequestLoadingBinding
import cn.zxd.app.ui.MainActivity

class RequestLoadingFragment(private val line: Int) :
    BaseFragment<FragmentRequestLoadingBinding>(R.layout.fragment_request_loading) {

    override fun initBinding(view: View): FragmentRequestLoadingBinding {
        return FragmentRequestLoadingBinding.bind(view)
    }

    override fun initView() {

    }

    override fun loadDate() {
    }

    fun toQrCode() {
        //停止扫描人脸 并 获取一张图片
        (activity as MainActivity).transFragment(QrCodeFragment(0))
    }

    fun toRewardsResult() {
        (activity as MainActivity).transFragment(RewardsResultFragment())
    }

    fun toCouponResult() {
        (activity as MainActivity).transFragment(CouponResultFragment())
    }
}