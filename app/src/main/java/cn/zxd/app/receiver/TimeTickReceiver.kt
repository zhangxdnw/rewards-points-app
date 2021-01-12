package cn.zxd.app.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import cn.zxd.app.util.ActionUtils

object TimeTickReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (context != null) {
            val serverResponseSharedInfo =
                context.getSharedPreferences("server_response", Context.MODE_PRIVATE)
            //广告定时
            if (serverResponseSharedInfo.getLong(
                    "advertise_update_time",
                    0
                ) - System.currentTimeMillis() >= 1000 * 60 * 30    //30分钟
            ) {
                ActionUtils.doRequestAdvertise()
            }
            if (serverResponseSharedInfo.getLong(
                    "coupon_update_time",
                    0
                ) - System.currentTimeMillis() > 1000 * 60 * 10
            ) {
                ActionUtils.doRequestCoupon()
            }
        }
    }
}