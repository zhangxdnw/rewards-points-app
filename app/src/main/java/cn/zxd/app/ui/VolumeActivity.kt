package cn.zxd.app.ui

import android.os.Bundle
import cn.zxd.app.databinding.ActivityVolumeSettingsBinding

class VolumeActivity : BaseActivity() {

    lateinit var binding: ActivityVolumeSettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVolumeSettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}