package cn.zxd.app.util

import java.security.KeyFactory
import java.security.PrivateKey
import java.security.PublicKey
import java.security.spec.X509EncodedKeySpec
import javax.crypto.Cipher


object RSAUtils {
    private const val transformation = "RSA"

    /**
     * 私钥加密
     * @param input 原文
     * @param privateKey 私钥
     */
    fun encryptByPrivateKey(input: String, privateKey: PrivateKey): String {

        //1 创建cipher对象
        val cipher = Cipher.getInstance(transformation)

        //2 初始化cipher对象
        cipher.init(Cipher.ENCRYPT_MODE, privateKey)

        //3 加密或解密
        val encrypt = cipher.doFinal(input.toByteArray())

        return Base64Utils.encode(encrypt)
    }

    /**
     * 公钥加密
     * @param input 原文
     * @param publicKey 公钥
     */
    fun encryptByPublicKey(input: String, publicKey: PublicKey): String {

        //1 创建cipher对象
        val cipher = Cipher.getInstance(transformation)

        //2 初始化cipher对象
        cipher.init(Cipher.ENCRYPT_MODE, publicKey)

        //3 加密或解密
        val encrypt = cipher.doFinal(input.toByteArray())

        return Base64Utils.encode(encrypt)
    }

    fun getPublicKeyFromX509(strKey: String): PublicKey {
        val decodedKey: ByteArray = Base64Utils.decode(strKey)
        val x509 = X509EncodedKeySpec(decodedKey)

        val keyFactory = KeyFactory.getInstance(transformation)
        return keyFactory.generatePublic(x509)
    }
}