package cn.zxd.app.ui

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import cn.zxd.app.databinding.ActivitySeniorSettingsBinding
import cn.zxd.app.net.ApiUtils

class SeniorSettingsActivity : BaseActivity() {

    lateinit var dataBinding: ActivitySeniorSettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = ActivitySeniorSettingsBinding.inflate(layoutInflater)
        setContentView(dataBinding.root)
        dataBinding.etBaseHost.setText(ApiUtils.baseUrl)
        dataBinding.etApiKey.setText(ApiUtils.appKey)
    }

    fun clickToSave(view: View) {
        if (!TextUtils.isEmpty(dataBinding.etBaseHost.text)
            && !TextUtils.isEmpty(dataBinding.etApiKey.text)
        ) {
            ApiUtils.baseUrl = dataBinding.etBaseHost.text.toString()
            ApiUtils.appKey = dataBinding.etApiKey.text.toString()
            Toast.makeText(view.context, "保存成功", Toast.LENGTH_SHORT).show()
            finish()
        } else {
            Toast.makeText(view.context, "参数错误", Toast.LENGTH_SHORT).show()
        }
    }
}