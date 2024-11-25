package com.app.quench.log.history

import android.app.Activity
import java.lang.ref.WeakReference
import java.util.concurrent.ConcurrentLinkedDeque

object CodeActivityUtil {

    private var activityDeque: ConcurrentLinkedDeque<Activity>? = null
    private var trDeque: WeakReference<Activity>? = null

    fun addActivity(activity: Activity) {
        if (activityDeque == null) {
            activityDeque = ConcurrentLinkedDeque()
        }
        activityDeque?.add(activity)
    }

    fun removeActivity(activity: Activity?) {
        runCatching {
            if (activity != null && activityDeque != null) {
                activityDeque?.remove(activity)
            }
        }
    }

    fun addTrAc(activity: Activity) {
        runCatching {
            trDeque?.clear()
            trDeque = null
            trDeque = WeakReference(activity)
        }
    }

    fun removeTrAc() {
        runCatching {
            trDeque?.clear()
            trDeque = null
        }
    }

    fun finishAll() {
        runCatching {
            activityDeque?.forEach {
                it.finish()
            }
            trDeque?.get()?.finish()
        }
    }
}