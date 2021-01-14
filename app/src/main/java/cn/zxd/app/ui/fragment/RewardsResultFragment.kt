package cn.zxd.app.ui.fragment

import android.view.View
import cn.zxd.app.R
import cn.zxd.app.databinding.FragmentRewordsResultBinding
import cn.zxd.app.net.FacePointResponseData

class RewardsResultFragment(val data: FacePointResponseData) :
    BaseFragment<FragmentRewordsResultBinding>(R.layout.fragment_rewords_result) {
    override fun initBinding(view: View): FragmentRewordsResultBinding {
        return FragmentRewordsResultBinding.bind(view)
    }

    override fun initView() {
    }

    override fun loadDate() {
        binding.tvPointResult.text = "名称:${data.name}\n积分:${data.score}"
    }
}