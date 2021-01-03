package cn.zxd.app.ui.fragment

import android.view.View
import cn.zxd.app.R
import cn.zxd.app.databinding.FragmentCouponBinding
import cn.zxd.app.ui.MainActivity

class CouponFragment : BaseFragment<FragmentCouponBinding>(R.layout.fragment_coupon) {
    override fun initBinding(view: View): FragmentCouponBinding {
        return FragmentCouponBinding.bind(view)
    }

    override fun initView() {
        //开始扫描人脸
    }

    override fun loadDate() {
    }

    fun toRequestLoading() {
        //停止扫描人脸 并 获取一张图片
        (activity as MainActivity).transFragment(FaceDetectFragment(0))
    }
}