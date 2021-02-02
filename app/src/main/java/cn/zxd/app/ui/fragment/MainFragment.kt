package cn.zxd.app.ui.fragment

import android.view.View
import cn.zxd.app.R
import cn.zxd.app.banner.ImageBannerAdapter
import cn.zxd.app.banner.ImageDataBean
import cn.zxd.app.databinding.FragmentMainBinding
import cn.zxd.app.ui.MainActivity

class MainFragment : BaseFragment<FragmentMainBinding>(R.layout.fragment_main) {

    val dataList: MutableList<ImageDataBean> = ArrayList()

    override fun initBinding(view: View): FragmentMainBinding {
        return FragmentMainBinding.bind(view)
    }

    override fun initView() {
        binding.bAd.adapter = ImageBannerAdapter(this, dataList)
    }

    override fun loadDate() {
        val serverData = (activity as MainActivity).serverData
        if (serverData == null) {
            dataList.add(ImageDataBean(1, 0, R.drawable.image1, ""))
            binding.bAd.adapter.notifyDataSetChanged()
        } else {
            for (ad in serverData[0].center) {
                dataList.add(ImageDataBean(1, 1, 0, ad.url))
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if(activity == null) return
        (activity as MainActivity).faceCount = 0
        (activity as MainActivity).needFace = true
    }

    override fun onPause() {
        super.onPause()
        if(activity == null) return
        (activity as MainActivity).needFace = false
    }

}