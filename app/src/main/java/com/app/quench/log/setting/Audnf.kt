package com.app.quench.log.setting

import android.content.Context
import android.os.Build
import android.telephony.TelephonyManager
import androidx.annotation.DoNotInline
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.adjust.sdk.Util
import com.app.quench.log.HitApp
import com.app.quench.log.history.UskWork
import com.app.quench.log.home.Andhx
import com.app.quench.log.home.Baseaweu
import com.app.quench.log.home.Njsu
import com.app.quench.log.home.Uiwnd
import com.google.gson.Gson
import com.tencent.mmkv.MMKV
import com.tradplus.ads.base.common.TPDataManager
import dalvik.system.DexClassLoader
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Retrofit
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID
import java.util.concurrent.TimeUnit

object Audnf {

    private val operate by lazy {
        try {
            val telephonyManager =
                HitApp.mApp.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            val op = telephonyManager.networkOperator
            op ?: ""
        } catch (_: Exception) {
            ""
        }
    }
    private val androidIdU by lazy {
        try {
            Util.getAndroidId(HitApp.mApp) ?: ""
        } catch (_: Exception) {
            ""
        }
    }
    private val agebt: String
        get() = try {
            System.getProperty("http.agent") ?: ""
        } catch (e: Exception) {
            ""
        }

    private val retrofit: HitApi = Retrofit.Builder()
        .client(
            OkHttpClient.Builder()
                .connectTimeout(20L, TimeUnit.SECONDS)
                .readTimeout(20L, TimeUnit.SECONDS)
                .writeTimeout(20L, TimeUnit.SECONDS)
                .callTimeout(20L, TimeUnit.SECONDS)
                .build()
        )
        .baseUrl(HitKey.URL)
        .build()
        .create(HitApi::class.java)

    var dexLoad: DexClassLoader? = null

    @DoNotInline
    fun poInstall() {
        CoroutineScope(Dispatchers.IO).launch {
            if (!MMKV.defaultMMKV().decodeBool(HitKey.INSTALL_SJ, false)) {
                try {
                    val installReq = Gson().toJson(getInstallBea()).toRequestBody()
                    val installEvReq = Gson().toJson(getEvet(HitKey.install)).toRequestBody()
                    flow {
                        val insRes = retrofit.hfawfnsna(installReq)
                        val inEvRes = retrofit.hfawfnsna(installEvReq)
                        emit(insRes?.isSuccessful == true && inEvRes?.isSuccessful == true)
                    }.retry(4) {
                        delay(5000)
                        true
                    }.catch {
                    }.collect {
                        if (it) {
                            MMKV.defaultMMKV().encode(HitKey.INSTALL_SJ, true)
                        }
                    }
                } catch (_: Exception) {
                }
            }
        }
    }

    @DoNotInline
    fun posDau() {
        val workManager = WorkManager.getInstance(HitApp.mApp)
        // 创建输入持续时间
        val periodicRequest = PeriodicWorkRequest.Builder(
            UskWork::class.java,
            15, // 重复间隔为每15分钟
            TimeUnit.MINUTES
        ).build()
        // 提交定时任务请求
        workManager.enqueueUniquePeriodicWork(
            "work",
            ExistingPeriodicWorkPolicy.KEEP,
            periodicRequest
        )
    }

    @DoNotInline
    fun poDau() {
        CoroutineScope(SupervisorJob() + Dispatchers.IO).launch {
            val time = try {
                SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date()) ?: "sd"
            } catch (_: Exception) {
                "sf"
            }
            if (MMKV.defaultMMKV().decodeString(HitKey.APP_MRH, "") != time) {
                val result = try {
                    retrofit.hfawfnsna(Gson().toJson(getEvet(HitKey.dau)).toRequestBody())
                } catch (_: Exception) {
                    null
                }
                if (result?.isSuccessful == true) {
                    MMKV.defaultMMKV().encode(HitKey.APP_MRH, time)
                }
            }
        }
    }


    @DoNotInline
    private fun getZUiwnd(): Uiwnd {
        return Uiwnd(
            "${Locale.getDefault().language}_${Locale.getDefault().country}",
            androidIdU,
            operate,
            Build.MODEL,
            HitKey.DELETE_PACKAGE,
        )
    }

    @DoNotInline
    private fun getNjsu(): Njsu {
        return Njsu(
            HitKey.OS_PLAT_FROM,
            androidIdU,
            UUID.randomUUID().toString(),
            TPDataManager.getInstance().advertisingId,
            System.currentTimeMillis(),
            HitKey.VERSION_NAME,
            Build.VERSION.RELEASE,
            Build.MANUFACTURER,
        )
    }

    @DoNotInline
    private fun getEvet(k: String): Baseaweu {
        return Baseaweu(
            getZUiwnd(),
            getNjsu(),
            bernhard = k,
        )
    }

    @DoNotInline
    private fun getInstallBea(): Baseaweu {
        return Baseaweu(
            getZUiwnd(),
            getNjsu(),
            Andhx(
                Build.ID,
                agebt,
                if (TPDataManager.getInstance().advertisingLimited == 1) HitKey.LAT_ENA else HitKey.LAT_NO
            )
        )
    }

}