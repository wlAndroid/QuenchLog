package com.example.outdex

import okio.*
import java.io.File
import javax.crypto.Cipher
import javax.crypto.CipherInputStream
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

object AESFUtil {

    // AES 解密文件的方法
    fun File.decryptFile(outputFile: File) {
        try {
            // 1. 使用 Okio 读取文件流
            Okio.buffer(Okio.source(this)).use { source ->
                // 2. 读取并提取初始化向量（IV）
                val iv = ByteArray(IV_SIZE)
                source.read(iv, 0, IV_SIZE) // 假设文件前 16 字节是 IV
                // 3. 创建初始化向量参数
                val ivSpec = IvParameterSpec(iv)
                // 4. 创建 AES 密钥对象（根据密码生成）
                val secretKeySpec = SecretKeySpec(AES_PASSWORD.toByteArray(Charsets.UTF_8), AES_ALGORITHM)
                // 5. 初始化 Cipher 实例，指定解密模式（Cipher.DECRYPT_MODE）
                val cipher = Cipher.getInstance(AES_TRANSFORMATION)
                cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivSpec)

                // 6. 创建输出文件的 Okio Sink
                Okio.buffer(Okio.sink(outputFile)).use { sink ->
                    // 7. 使用 CipherInputStream 进行流解密
                    val cipherInputStream = CipherInputStream(source.inputStream(), cipher)
                    // 8. 将解密后的数据写入到输出文件
                    Okio.source(cipherInputStream).use { cipherSource ->
                        sink.writeAll(cipherSource) // 将解密后的内容写入输出文件
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            // 可以根据需要进行更细致的异常处理
        }
    }

    private const val IV_SIZE = 16  // IV 长度，AES CBC 模式使用 16 字节
    private const val AES_ALGORITHM = "AES"
    private const val AES_TRANSFORMATION = "AES/CBC/PKCS5Padding" // 使用ECB模式
    private const val AES_PASSWORD = "7efdc3259df3a80b00664214ca13fe1f"

}