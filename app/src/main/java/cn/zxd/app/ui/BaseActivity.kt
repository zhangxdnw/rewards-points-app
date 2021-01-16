package cn.zxd.app.ui

import android.os.Bundle
import android.view.KeyEvent
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

//    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
//        return when (keyCode) {
//            KeyEvent.KEYCODE_BACK -> {
//                true
//            }
//            else -> {
//                super.onKeyUp(keyCode, event)
//            }
//        }
//    }

}