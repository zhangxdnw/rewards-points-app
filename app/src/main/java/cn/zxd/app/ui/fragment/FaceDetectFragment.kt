package cn.zxd.app.ui.fragment

import android.view.View
import cn.zxd.app.R
import cn.zxd.app.databinding.FragmentFaceDetectBinding
import cn.zxd.app.ui.MainActivity
import cn.zxd.app.work.SendData
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class FaceDetectFragment(private val line: Int, private val data: Any) :
    BaseFragment<FragmentFaceDetectBinding>(R.layout.fragment_face_detect) {

    override fun initBinding(view: View): FragmentFaceDetectBinding {
        return FragmentFaceDetectBinding.bind(view)
    }

    override fun initView() {
        EventBus.getDefault().register(this)
    }

    override fun loadDate() {
    }

    override fun onResume() {
        super.onResume()
        if (!(activity as MainActivity).isShowFaceDetect()) {
            (activity as MainActivity).showFaceDetect()
            (activity as MainActivity).needFace = true
        }
    }

    override fun onPause() {
        super.onPause()
        (activity as MainActivity).needFace = false
        (activity as MainActivity).dismissFaceDetect()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun sendData(sendData: SendData) {
        if (activity == null) return
        (activity as MainActivity).transFragment(
            RequestLoadingFragment(
                line,
                sendData.data,
                data
            )
        )
    }
}