package cn.zxd.app.ui.fragment

import android.view.View
import cn.zxd.app.R
import cn.zxd.app.databinding.FragmentRewordsBinding
import cn.zxd.app.work.RewardsPointData
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class RewardsFragment : BaseFragment<FragmentRewordsBinding>(R.layout.fragment_rewords) {
    override fun initBinding(view: View): FragmentRewordsBinding {
        return FragmentRewordsBinding.bind(view)
    }

    override fun initView() {
        EventBus.getDefault().register(this)
    }

    override fun loadDate() {
    }

    @Subscribe
    fun showInfo(data: RewardsPointData) {

    }
}