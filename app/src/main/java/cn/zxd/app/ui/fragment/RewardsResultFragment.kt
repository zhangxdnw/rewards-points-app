package cn.zxd.app.ui.fragment

import android.view.View
import cn.zxd.app.R
import cn.zxd.app.databinding.FragmentRewordsResultBinding
import cn.zxd.app.net.FacePointResponseData
import cn.zxd.app.ui.MainActivity
import cn.zxd.app.util.QrUtils
import com.bumptech.glide.Glide

class RewardsResultFragment(val data: FacePointResponseData) :
    BaseFragment<FragmentRewordsResultBinding>(R.layout.fragment_rewords_result) {
    override fun initBinding(view: View): FragmentRewordsResultBinding {
        return FragmentRewordsResultBinding.bind(view)
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