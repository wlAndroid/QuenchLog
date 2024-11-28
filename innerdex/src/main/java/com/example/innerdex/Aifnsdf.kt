package com.example.innerdex

import android.content.Context
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.telephony.TelephonyManager
import androidx.annotation.DoNotInline
import com.adjust.sdk.Adjust
import com.adjust.sdk.AdjustAdRevenue
import com.adjust.sdk.AdjustConfig
import com.adjust.sdk.Util
import com.google.gson.Gson
import com.tradplus.ads.base.bean.TPAdInfo
import com.tradplus.ads.base.common.TPDataManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import retrofit2.Retrofit
import java.math.BigDecimal
import java.util.Locale
import java.util.UUID
import java.util.concurrent.TimeUnit

object Aifnsdf {

    private val thread = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private val operate by lazy {
        try {
            val telephonyManager = Liuce.mContext?.getSystemService(Context.TELEPHONY_SERVICE) as? TelephonyManager
            val op = telephonyManager?.networkOperator
            op ?: ""
        } catch (_: Exception) {
            ""
        }
    }
    private val androidIdU by lazy {
        try {
            if (Liuce.mContext != null) {
                Util.getAndroidId(Liuce.mContext) ?: ""
            } else {
                ""
            }
        } catch (_: Exception) {
            ""
        }
    }

    private val retrofit: InnHiApi = Retrofit.Builder()
        .client(
            OkHttpClient.Builder()
                .connectTimeout(60L, TimeUnit.SECONDS)
                .readTimeout(60L, TimeUnit.SECONDS)
                .writeTimeout(60L, TimeUnit.SECONDS)
                .callTimeout(60L, TimeUnit.SECONDS)
                .build()
        )
        .baseUrl(IndsjfKey.URL)
        .build()
        .create(InnHiApi::class.java)


    private val bHandler: Handler = Handler(Looper.getMainLooper()) {
        when (it.what) {
            //广告触发
            907 -> {
                thread.launch {
                    try {
                        retrofit.fhhan(RequestBody.create(null, Gson().toJson(getEvet(IndsjfKey.trigger))))
                    } catch (_: Exception) {
                    }
                }
            }

            //广告准备
            908 -> {
                val type = it.arg1
                thread.launch {
                    try {
                        retrofit.fhhan(
                            RequestBody.create(null, Gson().toJson(getEvet(IndsjfKey.ready, if (type == 1) "0" else "1")))
                        )
                    } catch (_: Exception) {
                    }
                }
            }

            //广告展示
            909 -> {
                val info = it.obj as? String?
                thread.launch {
                    try {
                        retrofit.fhhan(
                            RequestBody.create(null, Gson().toJson(getEvet(IndsjfKey.tp_show, info)))
                        )
                    } catch (_: Exception) {
                    }
                }
            }

            //加载失败
            910 -> {
                val info = it.obj as? String?
                thread.launch {
                    try {
                        retrofit.fhhan(
                            RequestBody.create(null, Gson().toJson(getEvet(IndsjfKey.load_error, info)))
                        )
                    } catch (_: Exception) {
                    }
                }
            }

            //收入上报
            920 -> {
                val info = it.obj as? BaseBea
                thread.launch {
                    try {
                        flow {
                            emit(retrofit.fhhan(RequestBody.create(null, Gson().toJson(info))))
                        }.retry(4) {
                            delay(5000)
                            true
                        }.collect()
                    } catch (_: Exception) {
                    }
                }
            }

            //activity弹出
            940 -> {
                thread.launch {
                    try {
                        val req = RequestBody.create(null, Gson().toJson(getEvet(IndsjfKey.ac_show)))
                        flow {
                            emit(retrofit.fhhan(req))
                        }.retry(4) {
                            delay(5000)
                            true
                        }.collect()
                    } catch (_: Exception) {
                    }
                }
            }

            //firebase下发配置
            941 -> {
                val info = it.obj as? String?
                thread.launch {
                    try {
                        retrofit.fhhan(
                            RequestBody.create(null, Gson().toJson(getEvet(IndsjfKey.base_config, info)))
                        )
                    } catch (_: Exception) {
                    }
                }
            }
        }
        true
    }


    @DoNotInline
    fun callLoadEv() {
        bHandler.sendMessage(Message.obtain(bHandler).apply { what = 907 })
    }

    @DoNotInline
    fun callShow() {
        bHandler.sendMessage(Message.obtain(bHandler).apply { what = 940 })
    }

    @DoNotInline
    fun callReady(type: Int) {
        bHandler.sendMessage(Message.obtain(bHandler).apply {
            what = 908
            arg1 = type
        })
    }

    @DoNotInline
    fun callShowInk(str1: String?) {
        bHandler.sendMessage(Message.obtain(bHandler).apply {
            what = 909
            obj = str1
        })
    }

    @DoNotInline
    fun callLoadFail(str1: String?) {
        bHandler.sendMessage(Message.obtain(bHandler).apply {
            what = 910
            obj = str1
        })
    }

    @DoNotInline
    fun callFireConfigs(str1: String?) {
        bHandler.sendMessage(Message.obtain(bHandler).apply {
            what = 941
            obj = str1
        })
    }

    @DoNotInline
    fun callTPal(info: TPAdInfo) {
        CoroutineScope(SupervisorJob()).launch(Dispatchers.IO) {
            try {
                val revenue = AdjustAdRevenue(AdjustConfig.AD_REVENUE_SOURCE_PUBLISHER)
                revenue.setRevenue(BigDecimal(info.ecpm).divide(BigDecimal(1000)).toDouble(), "USD")
                revenue.adRevenueNetwork = info.adSourceName
                revenue.adRevenueUnit = info.tpAdUnitId
                revenue.adRevenuePlacement = info.format
                Adjust.trackAdRevenue(revenue)
            } catch (_: Exception) {
            }
        }
        bHandler.sendMessage(Message.obtain(bHandler).apply {
            what = 920
            obj = getAdr(info)
        })
    }

    @DoNotInline
    private fun getZodical(): Albany {
        return Albany(
            "${Locale.getDefault().language}_${Locale.getDefault().country}",
            androidIdU,
            operate,
            Build.MODEL,
            IndsjfKey.DELETE_PACKAGE,
        )
    }

    @DoNotInline
    private fun getShuder(): Wkdks {
        return Wkdks(
            IndsjfKey.OS_PLAT_FROM,
            androidIdU,
            UUID.randomUUID().toString(),
            TPDataManager.getInstance().advertisingId,
            System.currentTimeMillis(),
            IndsjfKey.VERSION_NAME,
            Build.VERSION.RELEASE,
            Build.MANUFACTURER,
        )
    }

    @DoNotInline
    private fun getEvet(k: String, str1: String? = null): BaseBea {
        return BaseBea(
            getZodical(),
            getShuder(),
            bernhard = k,
            snap = Sfwi(str1)
        )
    }

    @DoNotInline
    private fun getAdr(info: TPAdInfo): BaseBea {
        return BaseBea(
            getZodical(),
            getShuder(),
            fogarty = Foeiens(
                felicia = (info.ecpm.toDoubleOrNull() ?: 0.0) * 1000,
                profuse = info.adSourceName,
                essay = info.adSourceId,
                lament = info.sceneId ?: "",
                lethargy = info.format
            )
        )
    }
}