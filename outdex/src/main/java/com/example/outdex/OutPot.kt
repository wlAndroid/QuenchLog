package com.example.outdex

import android.content.Context
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.provider.Settings.Secure
import android.telephony.TelephonyManager
import androidx.annotation.DoNotInline
import androidx.annotation.Keep
import com.google.android.gms.ads.identifier.AdvertisingIdClient
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import retrofit2.Retrofit
import java.util.Locale
import java.util.UUID
import java.util.concurrent.TimeUnit

@Keep
object OutPot {

    private val thread = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    private val operate by lazy {
        try {
            val telephonyManager = Standup.mContext?.getSystemService(Context.TELEPHONY_SERVICE) as? TelephonyManager
            val op = telephonyManager?.networkOperator
            op ?: ""
        } catch (_: Exception) {
            ""
        }
    }

    private val androidIdU: String by lazy {
        try {
            if (Standup.mContext != null) {
                getAndroidId(Standup.mContext!!)
            } else {
                ""
            }
        } catch (_: Exception) {
            ""
        }
    }

    val retrofit: OutApi = Retrofit.Builder()
        .client(
            OkHttpClient.Builder()
                .connectTimeout(30L, TimeUnit.SECONDS)
                .readTimeout(30L, TimeUnit.SECONDS)
                .writeTimeout(30L, TimeUnit.SECONDS)
                .callTimeout(30L, TimeUnit.SECONDS)
                .build()
        )
        .baseUrl(OutKey.URL)
        .build()
        .create(OutApi::class.java)


    private val bHandler: Handler = Handler(Looper.getMainLooper()) {
        when (it.what) {
            //refer error
            902 -> {
                val err = it.obj as? String?
                thread.launch {
                    try {
                        retrofit.oqkskd(RequestBody.create(null, Gson().toJson(getEvet(OutKey.refer_error, err))))
                    } catch (_: Exception) {
                    }
                }
            }

            //记录refer
            903 -> {
                val ref = it.obj as? String
                thread.launch {
                    try {
                        retrofit.oqkskd(RequestBody.create(null, Gson().toJson(getEvet(OutKey.recode_refer, ref))))
                    } catch (_: Exception) {
                    }
                }
            }

            //cloak error
            904 -> {
                val err = it.obj as? String?
                thread.launch {
                    try {
                        retrofit.oqkskd(
                            RequestBody.create(null, Gson().toJson(getEvet(OutKey.cloak_error, err)))
                        )
                    } catch (_: Exception) {
                    }
                }
            }

            //cloak上报
            905 -> {
                val info = it.obj as? String?
                thread.launch {
                    try {
                        val req = RequestBody.create(null, Gson().toJson(getEvet(OutKey.cloak, info)))
                        flow {
                            emit(retrofit.oqkskd(req))
                        }.retry(4) {
                            delay(5000)
                            true
                        }.collect()
                    } catch (_: Exception) {
                    }
                }
            }

            //oem上报
            906 -> {
                val info = it.obj as? String?
                thread.launch {
                    try {
                        retrofit.oqkskd(RequestBody.create(null, Gson().toJson(getEvet(OutKey.recode_oem, info))))
                    } catch (_: Exception) {
                    }
                }
            }

            //firebase下发配置
            941 -> {
                val info = it.obj as? String?
                thread.launch {
                    try {
                        retrofit.oqkskd(
                            RequestBody.create(null, Gson().toJson(getEvet(OutKey.base_config, info)))
                        )
                    } catch (_: Exception) {
                    }
                }
            }
            //记录用户ID
            942 -> {
                val info = it.obj as? String?
                thread.launch {
                    try {
                        val res = retrofit.oqkskd(
                            RequestBody.create(null, Gson().toJson(getEvet(OutKey.recordId, info)))
                        )
                    } catch (_: Exception) {
                    }
                }
            }
        }
        true
    }


    @DoNotInline
    fun callRefErro(str1: String?) {
        bHandler.sendMessage(Message.obtain(bHandler).apply {
            what = 902
            obj = str1
        })
    }

    @DoNotInline
    fun callRecordRefer(str1: String) {
        bHandler.sendMessage(Message.obtain(bHandler).apply {
            what = 903
            obj = str1
        })
    }


    @DoNotInline
    fun callClockErr(str1: String?) {
        bHandler.sendMessage(Message.obtain(bHandler).apply {
            what = 904
            obj = str1
        })
    }

    @DoNotInline
    fun callAgile(str: String) {
        bHandler.sendMessage(Message.obtain(bHandler).apply {
            what = 905
            obj = str
        })
    }

    @DoNotInline
    fun callRecooem(str: String) {
        bHandler.sendMessage(Message.obtain(bHandler).apply {
            what = 906
            obj = str
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
    fun callRecodep() {
        bHandler.sendMessage(Message.obtain(bHandler).apply {
            what = 942
            obj = androidIdU + OutKey.DELETE_PACKAGE
        })
    }


    @DoNotInline
    fun getQqndus(type: String): Qqndus {
        return Qqndus(type, androidIdU + OutKey.DELETE_PACKAGE)
    }


    @DoNotInline
    private fun getThsn(): Thsn {
        return Thsn(
            "${Locale.getDefault().language}_${Locale.getDefault().country}",
            androidIdU,
            operate,
            Build.MODEL,
            OutKey.DELETE_PACKAGE,
        )
    }

    @DoNotInline
    private suspend fun getCjsid(): Cjsid {
        return Cjsid(
            OutKey.OS_PLAT_FROM,
            androidIdU,
            UUID.randomUUID().toString(),
            getGaid(),
            System.currentTimeMillis(),
            OutKey.VERSION_NAME,
            Build.VERSION.RELEASE,
            Build.MANUFACTURER,
        )
    }

    @DoNotInline
    private suspend fun getEvet(k: String, str1: String? = null): Basodjs {
        return Basodjs(
            getThsn(),
            getCjsid(),
            k,
            Yhsnd(str1)
        )
    }

    @DoNotInline
    suspend fun getClaiiwen(): Claiiwen {
        return Claiiwen(
            OutKey.DELETE_PACKAGE,
            OutKey.OS_PLAT_FROM,
            OutKey.VERSION_NAME,
            androidIdU,
            System.currentTimeMillis(),
            getGaid(),
            androidIdU
        )
    }

    @DoNotInline
    private fun getAndroidId(var0: Context): String {
        return Secure.getString(var0.contentResolver, "android_id") ?: ""
    }

    @DoNotInline
    private suspend fun getGaid() = withContext(Dispatchers.IO) {
        return@withContext try {
            if (Standup.mContext != null) {
                AdvertisingIdClient.getAdvertisingIdInfo(Standup.mContext!!).id ?: ""
            } else {
                ""
            }
        } catch (e: Exception) {
            ""
        }
    }

}