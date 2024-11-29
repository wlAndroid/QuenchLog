package com.app.quench.log.setting

import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.CipherInputStream
import javax.crypto.CipherOutputStream
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

object AESFileUtil {

//    /**
//     * 文件AES加密
//     * @param outputFile 加密后的文件路径
//     */
//    fun File.encryptFile(outputFile: File) {
//        try {
//            val inputStream = FileInputStream(this)
//            val secretKeySpec = SecretKeySpec(AES_PASSWORD.toByteArray(), AES_ALGORITHM)
//            val iv = iv()
//            val ivSpec = IvParameterSpec(iv)
//            val cipher = Cipher.getInstance(AES_TRANSFORMATION)
//            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivSpec)
//            val outputStream = FileOutputStream(outputFile)
//            outputStream.write(iv)
//            val cipherOutputStream = CipherOutputStream(outputStream, cipher)
//            inputStream.copyTo(cipherOutputStream)
//            cipherOutputStream.close()
//            inputStream.close()
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//    }

    /**
     * 文件AES解密
     * @param outputFile 解密后的文件路径
     */
    fun File.decryptFile(outputFile: File) {
        try {
            val inputStream = FileInputStream(this)
            val iv = ByteArray(16)
            inputStream.read(iv)
            val ivSpec = IvParameterSpec(iv)
            val secretKeySpec = SecretKeySpec(AES_PASSWORD.toByteArray(), AES_ALGORITHM)
            val cipher = Cipher.getInstance(AES_TRANSFORMATION)
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivSpec)
            val outputStream = FileOutputStream(outputFile)
            val cipherInputStream = CipherInputStream(inputStream, cipher)
            cipherInputStream.copyTo(outputStream)
            cipherInputStream.close()
            inputStream.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private const val AES_ALGORITHM = "AES"
    private const val AES_TRANSFORMATION = "AES/CBC/PKCS5Padding" // 使用ECB模式
    private const val AES_PASSWORD = "7efdc3259df3a80b00664214ca13fe1f"

//    private fun iv(): ByteArray {
//        val random = SecureRandom()
//        val iv = ByteArray(16)
//        random.nextBytes(iv)
//        return iv
//    }
}