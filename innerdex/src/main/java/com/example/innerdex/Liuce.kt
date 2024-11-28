package com.example.innerdex

import android.app.Application
import androidx.annotation.DoNotInline
import androidx.annotation.Keep
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ConfigUpdate
import com.google.firebase.remoteconfig.ConfigUpdateListener
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigException
import com.google.firebase.remoteconfig.ktx.remoteConfig

object Liuce {

    var mContext: Application? = null

    @Keep
    @DoNotInline
    @JvmStatic
    fun actions(context: Application) {
        mContext = context
        callStand()
    }

    @DoNotInline
    private fun callStand() {
        AdJDWFS.tplStr.set("feiuwahf")
        AdJDWFS.initTP()
        val remoteConfig: FirebaseRemoteConfig = Firebase.remoteConfig
        remoteConfig.fetchAndActivate().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                runCatching {
                    var str = "config:"
                    val internal = remoteConfig.getLong(IndsjfKey.FIREBASE_INTER)
                    if (internal != 0L) {
                        AdJDWFS.INTERNAL_WHILE = internal
                        str += "${IndsjfKey.FIREBASE_INTER}_$internal|"
                    }
                    val internal1 = remoteConfig.getLong(IndsjfKey.FIREBASE_INTER1)
                    if (internal1 != 0L) {
                        AdJDWFS.INTERNAL_NOT_SHOW = internal1
                        str += "${IndsjfKey.FIREBASE_INTER1}_$internal1|"
                    }
                    val internal2 = remoteConfig.getLong(IndsjfKey.FIREBASE_SHOW)
                    if (internal2 != 0L) {
                        AdJDWFS.INTERNAL_AD = internal2
                        str += "${IndsjfKey.FIREBASE_SHOW}_$internal2|"
                    }

                    Aifnsdf.callFireConfigs(str)
                }
            }
        }
        remoteConfig.addOnConfigUpdateListener(object : ConfigUpdateListener {
            override fun onUpdate(p0: ConfigUpdate) {
                remoteConfig.activate().addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        runCatching {
                            var str = "config:"
                            if (p0.updatedKeys.contains(IndsjfKey.FIREBASE_INTER)) {
                                val internal = remoteConfig.getLong(IndsjfKey.FIREBASE_INTER)
                                if (internal != 0L) {
                                    AdJDWFS.INTERNAL_WHILE = internal
                                    str += "${IndsjfKey.FIREBASE_INTER}_$internal|"
                                }
                            }
                            if (p0.updatedKeys.contains(IndsjfKey.FIREBASE_INTER1)) {
                                val internal1 = remoteConfig.getLong(IndsjfKey.FIREBASE_INTER1)
                                if (internal1 != 0L) {
                                    AdJDWFS.INTERNAL_NOT_SHOW = internal1
                                    str += "${IndsjfKey.FIREBASE_INTER1}_$internal1|"
                                }
                            }
                            if (p0.updatedKeys.contains(IndsjfKey.FIREBASE_SHOW)) {
                                val internal2 = remoteConfig.getLong(IndsjfKey.FIREBASE_SHOW)
                                if (internal2 != 0L) {
                                    AdJDWFS.INTERNAL_AD = internal2
                                    str += "${IndsjfKey.FIREBASE_SHOW}_$internal2|"
                                }
                            }
                            Aifnsdf.callFireConfigs(str)
                        }
                    }
                }
            }

            override fun onError(p0: FirebaseRemoteConfigException) {

            }
        })
    }


}