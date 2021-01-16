package cn.zxd.app.ui

import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.SeekBar
import cn.zxd.app.R
import cn.zxd.app.databinding.ActivityVolumeSettingsBinding


class VolumeActivity : BaseActivity() {

    lateinit var binding: ActivityVolumeSettingsBinding
    lateinit var am: AudioManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        am = getSystemService(AUDIO_SERVICE) as AudioManager
        binding = ActivityVolumeSettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val current = am.getStreamVolume(AudioManager.STREAM_MUSIC)
        if (current == 0) {
            binding.switch1.isChecked = false
        }
        binding.sbVolume.progress = current
        binding.switch1.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.sbVolume.progress = 1
                am.setStreamVolume(AudioManager.STREAM_MUSIC, 1, AudioManager.FLAG_PLAY_SOUND)
            } else {
                binding.sbVolume.progress = 0
                am.setStreamVolume(AudioManager.STREAM_MUSIC, 0, AudioManager.FLAG_PLAY_SOUND)
            }
        }
        binding.sbVolume.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                val progress = seekBar?.progress
                am.setStreamVolume(
                    AudioManager.STREAM_MUSIC,
                    progress!!,
                    AudioManager.FLAG_PLAY_SOUND
                )
                if (progress > 0 ) MediaPlayer.create(this@VolumeActivity, R.raw.dingding).start()
                binding.switch1.isChecked = progress > 0
            }

        })
    }

}