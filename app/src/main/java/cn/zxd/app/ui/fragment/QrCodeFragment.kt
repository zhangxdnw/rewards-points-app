package cn.zxd.app.ui.fragment

import android.view.View
import cn.zxd.app.R
import cn.zxd.app.databinding.FragmentQrCodeBinding
import cn.zxd.app.ui.MainActivity
import cn.zxd.app.util.QrUtils

class QrCodeFragment(val url:String) :
    BaseFragment<FragmentQrCodeBinding>(R.layout.fragment_qr_code) {
    override fun initBinding(view: View): FragmentQrCodeBinding {
        return FragmentQrCodeBinding.bind(view)
    }

    override fun initView() {

    }

    override fun loadDate() {
        QrUtils.createBitmap(url, 600, 600)?.let {
            binding.tvQr.setImageBitmap(it)
        }
    }
}