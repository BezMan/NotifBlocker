package com.bez.notifblocker

import android.util.Base64
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

object EncryptionUtils {

    private val config = ConfigManager.getConfig()

    fun encrypt(data: String): String {
        val secretKeySpec = SecretKeySpec(config.secretKey.toByteArray(), "AES")
        val ivSpec = IvParameterSpec(config.ivParameter.toByteArray())
        val cipher = Cipher.getInstance(config.algorithm)
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivSpec)
        val encryptedData = cipher.doFinal(data.toByteArray())
        return Base64.encodeToString(encryptedData, Base64.DEFAULT)
    }

    fun decrypt(data: String): String {
        val secretKeySpec = SecretKeySpec(config.secretKey.toByteArray(), "AES")
        val ivSpec = IvParameterSpec(config.ivParameter.toByteArray())
        val cipher = Cipher.getInstance(config.algorithm)
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivSpec)
        val decodedData = Base64.decode(data, Base64.DEFAULT)
        val decryptedData = cipher.doFinal(decodedData)
        return String(decryptedData)
    }

}
