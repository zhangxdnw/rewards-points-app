package cn.zxd.app.ui.fragment

import android.view.View
import cn.zxd.app.R
import cn.zxd.app.databinding.FragmentRewordsBinding
import cn.zxd.app.net.FacePointPushData
import cn.zxd.app.ui.MainActivity

class RewardsFragment : BaseFragment<FragmentRewordsBinding>(R.layout.fragment_rewords) {

    var info: FacePointPushData? = null

    override fun initBinding(view: View): FragmentRewordsBinding {
        return FragmentRewordsBinding.bind(view)
    }

    override fun initView() {
        binding.btnRewardsPoint.setOnClickListener {
            (info != null).let {
                (activity as MainActivity).clickToFaceDetect(0, info!!)
            }
        }
    }

    override fun loadDate() {
    }
}