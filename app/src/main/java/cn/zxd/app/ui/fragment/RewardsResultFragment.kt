package cn.zxd.app.ui.fragment

import android.view.View
import cn.zxd.app.R
import cn.zxd.app.databinding.FragmentRewordsResultBinding

class RewardsResultFragment :
    BaseFragment<FragmentRewordsResultBinding>(R.layout.fragment_rewords_result) {
    override fun initBinding(view: View): FragmentRewordsResultBinding {
        return FragmentRewordsResultBinding.bind(view)
    }

    override fun initView() {
    }

    override fun loadDate() {
    }
}