package cn.zxd.app.ui.fragment

import android.view.View
import cn.zxd.app.R
import cn.zxd.app.banner.ImageBannerAdapter
import cn.zxd.app.banner.ImageDataBean
import cn.zxd.app.bean.Advertisement
import cn.zxd.app.databinding.FragmentMainBinding
import cn.zxd.app.ui.MainActivity
import com.alibaba.fastjson.JSON

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
            dataList.add(ImageDataBean(1, 0, R.drawable.image2, ""))
            dataList.add(ImageDataBean(1, 0, R.drawable.image3, ""))
            dataList.add(ImageDataBean(1, 0, R.drawable.image4, ""))
            binding.bAd.adapter.notifyDataSetChanged()
        } else {
            for (url in serverData.page.image) {
                dataList.add(ImageDataBean(serverData.id, 1, 0, url))
            }
        }
    }

}