package cn.zxd.app.ui.fragment

import android.view.View
import cn.zxd.app.R
import cn.zxd.app.databinding.FragmentFaceDetectBinding
import cn.zxd.app.ui.MainActivity
import cn.zxd.app.util.FileUtils

class FaceDetectFragment(private val line: Int, private val data: Any) :
    BaseFragment<FragmentFaceDetectBinding>(R.layout.fragment_face_detect) {

    lateinit var registerData:String
    lateinit var notRegisterData:String

    override fun initBinding(view: View): FragmentFaceDetectBinding {
        return FragmentFaceDetectBinding.bind(view)
    }

    override fun initView() {
        binding.button3.setOnClickListener {
            (activity as MainActivity).transFragment(RequestLoadingFragment(line, registerData, data))
        }
        binding.button4.setOnClickListener {
            (activity as MainActivity).transFragment(RequestLoadingFragment(line, notRegisterData, data))
        }
    }

    override fun loadDate() {
        registerData = FileUtils.readAllText(activity?.assets!!.open("register_image.txt"))
        notRegisterData = FileUtils.readAllText(activity?.assets!!.open("not_register_image.txt"))
    }

    override fun onResume() {
        super.onResume()
        if (!(activity as MainActivity).isShowFaceDetect()) {
            (activity as MainActivity).showFaceDetect()
        }
    }

    override fun onPause() {
        super.onPause()
        (activity as MainActivity).dismissFaceDetect()
    }
}