package cn.zxd.app.ui

import android.os.Bundle
import cn.zxd.app.R
import cn.zxd.app.databinding.ActivityWebViewBinding

class WebViewActivity : BaseActivity() {

    lateinit var dataBinding: ActivityWebViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = ActivityWebViewBinding.inflate(layoutInflater)
        setContentView(dataBinding.root)
        intent.getStringExtra("url")?.let { dataBinding.wvWeb.loadUrl(it) }
    }
}