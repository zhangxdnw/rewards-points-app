package cn.zxd.app.ui.fragment

import android.view.View
import cn.zxd.app.R
import cn.zxd.app.databinding.FragmentFaceDetectBinding
import cn.zxd.app.ui.MainActivity

class FaceDetectFragment(private val line: Int) :
    BaseFragment<FragmentFaceDetectBinding>(R.layout.fragment_face_detect) {

    override fun initBinding(view: View): FragmentFaceDetectBinding {
        return FragmentFaceDetectBinding.bind(view)
    }

    override fun initView() {
        binding.root.setOnClickListener {
            (activity as MainActivity).transFragment(RequestLoadingFragment(line))
        }
    }

    override fun loadDate() {
    }
}