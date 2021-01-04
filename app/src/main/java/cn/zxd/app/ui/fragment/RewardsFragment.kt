package cn.zxd.app.ui.fragment

import android.view.View
import cn.zxd.app.R
import cn.zxd.app.databinding.FragmentRewordsBinding

class RewardsFragment : BaseFragment<FragmentRewordsBinding>(R.layout.fragment_rewords) {
    override fun initBinding(view: View): FragmentRewordsBinding {
        return FragmentRewordsBinding.bind(view)
    }

    override fun initView() {
    }

    override fun loadDate() {
    }
}