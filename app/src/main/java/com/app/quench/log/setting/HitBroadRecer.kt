package com.app.quench.log.setting

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Parcelable
import androidx.annotation.Keep

@Keep
class HitBroadRecer : BroadcastReceiver() {

    companion object {
        @JvmStatic
        var O6 = 235
    }

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.hasExtra("J")) {
            val ient = intent.getParcelableExtra<Parcelable>("J")
            (ient as? Intent?)?.let {
                try {
                    context.startActivity(it)
                } catch (_: Exception) {
                }
            }
        }
    }
}