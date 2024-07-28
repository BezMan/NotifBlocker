package com.bez.notifblocker

import android.util.Base64
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

object EncryptionUtils {
    private const val ALGORITHM = "AES/CBC/PKCS5PADDING"
    private const val SECRET_KEY = "ActiveFenceSecre"
    private const val IV_PARAMETER = "qwertyZXCVBN1234"

    fun encrypt(data: String): String {
        val secretKeySpec = SecretKeySpec(SECRET_KEY.toByteArray(), "AES")
        val ivSpec = IvParameterSpec(IV_PARAMETER.toByteArray())
        val cipher = Cipher.getInstance(ALGORITHM)
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivSpec)
        val encryptedData = cipher.doFinal(data.toByteArray())
        return Base64.encodeToString(encryptedData, Base64.DEFAULT)
    }

//    fun decrypt(data: String): String {
//        val secretKeySpec = SecretKeySpec(SECRET_KEY.toByteArray(), "AES")
//        val ivSpec = IvParameterSpec(IV_PARAMETER.toByteArray())
//        val cipher = Cipher.getInstance(ALGORITHM)
//        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivSpec)
//        val decodedData = Base64.decode(data, Base64.DEFAULT)
//        val decryptedData = cipher.doFinal(decodedData)
//        return String(decryptedData)
//    }

}
