package cn.zxd.app.ui.fragment

import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.view.View
import cn.zxd.app.R
import cn.zxd.app.banner.ImageBannerAdapter
import cn.zxd.app.banner.ImageDataBean
import cn.zxd.app.databinding.FragmentMainBinding
import cn.zxd.app.net.AdvertiseResponseData
import cn.zxd.app.ui.MainActivity
import com.bumptech.glide.Glide

class MainFragment : BaseFragment<FragmentMainBinding>(R.layout.fragment_main) {

    val dataList: MutableList<ImageDataBean> = ArrayList()
    lateinit var serverData:List<AdvertiseResponseData>
    val MSG_NEXT = 1000

    var currentItemIndex = 0
    var currentArrayIndex= 0

    val handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            Log.d("ZXD", "msg: what:${msg.what},currentItem:${currentItemIndex},currentArray:${currentArrayIndex}")
            when (msg.what) {
                MSG_NEXT -> {
                    currentItemIndex++
                    if (currentItemIndex > serverData[currentArrayIndex].center.size) {
                        currentArrayIndex++
                        if (currentArrayIndex > serverData.size - 1) {
                            currentArrayIndex = 0
                        }
                        dataList.clear()
                        for (ad in serverData[currentArrayIndex].center) {
                            dataList.add(ImageDataBean(1, 1, 0, ad.url))
                        }
                        if (serverData[currentArrayIndex].center.size > 1) {
                            currentItemIndex = 1
                        }
                        binding.bAd.currentItem = currentItemIndex
                        binding.bAd.adapter.notifyDataSetChanged()
                        activityReloadBottomBanner(currentArrayIndex)
                        next()
                    } else {
                        binding.bAd.setCurrentItem(currentItemIndex, true)
                        next()
                    }
                }
            }
        }
    }

    override fun initBinding(view: View): FragmentMainBinding {
        return FragmentMainBinding.bind(view)
    }

    override fun initView() {
        binding.bAd.adapter = ImageBannerAdapter(this, dataList)
        binding.bAd.isAutoLoop(false)
        binding.bAd.setUserInputEnabled(false)
    }

    override fun loadDate() {
        val data = (activity as MainActivity).serverData
        if (data == null || data.isEmpty() || data[0].center.isEmpty()) {
            dataList.add(ImageDataBean(1, 0, R.drawable.image1, ""))
            binding.bAd.adapter.notifyDataSetChanged()
        } else {
            serverData = data
            for (ad in serverData[0].center) {
                dataList.add(ImageDataBean(1, 1, 0, ad.url))
            }
            if (serverData[0].center.size > 1) {
                currentItemIndex = 1
                binding.bAd.setCurrentItem(currentItemIndex, false)
            }
            next()
        }
    }

    fun next() {
        handler.sendEmptyMessageDelayed(
            MSG_NEXT,
            (serverData[currentArrayIndex].center[currentItemIndex - 1].seconds * 1000).toLong()
        )
    }

    fun activityReloadBottomBanner(index:Int) {
        Glide.with(this).load(serverData[index].bottom[0].url)
            .into((activity as MainActivity).dataBinding.ivBottomBanner)
    }

    override fun onResume() {
        super.onResume()
        binding.root.postDelayed({
            Log.d("MainFragment", "Start Detect")
            if (activity != null) {
                (activity as MainActivity).faceCount = 0
                (activity as MainActivity).needFace = true
            }
        }, 10000)
    }

    override fun onPause() {
        super.onPause()
        if (activity == null) return
        (activity as MainActivity).needFace = false
    }

}