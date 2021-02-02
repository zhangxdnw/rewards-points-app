package cn.zxd.app.ui.fragment

import android.content.Context
import android.content.Intent
import android.view.View
import cn.zxd.app.R
import cn.zxd.app.databinding.FragmentRewordsBinding
import cn.zxd.app.net.AppConfigResponseData
import cn.zxd.app.net.FacePointPushData
import cn.zxd.app.ui.MainActivity
import cn.zxd.app.ui.WebViewActivity
import com.google.gson.Gson

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

    override fun onResume() {
        super.onResume()
        val sharedPreferences=activity?.getSharedPreferences("server_response", Context.MODE_PRIVATE)
        val dataStr = sharedPreferences?.getString("server_config", "")
        val configData = Gson().fromJson(dataStr, AppConfigResponseData::class.java)
        binding.tvUserPermission.text = configData?.protocolTip
        binding.tvUserPermission.setOnClickListener {
            if (!configData?.protocolUrl.isNullOrEmpty()) {
                val intent = Intent(activity, WebViewActivity::class.java)
                intent.putExtra("url", configData?.protocolUrl)
                startActivity(intent)
            }
        }
        (info != null).let {
            binding.tvPointDetail.text = String.format("%.2f", info?.totalPrice)
        }
    }
}