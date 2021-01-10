package cn.zxd.app.net

import java.security.cert.X509Certificate
import javax.net.ssl.*

object SSLSocketClient {

    var myX509TrustManager: X509TrustManager = object : X509TrustManager {
        override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {}
        override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {}
        override fun getAcceptedIssuers(): Array<X509Certificate> {
            return arrayOf()
        }
    }

    //获取这个SSLSocketFactory
    fun getSSLSocketFactory(): SSLSocketFactory {
        return try {
            val sc = SSLContext.getInstance("SSL")
            sc.init(null, getTrustManager(), null)
            sc.socketFactory
            //SSLContext sslContext = SSLContext.getInstance("SSL");
            //sslContext.init(null, getTrustManager(), new SecureRandom());
            //return sslContext.getSocketFactory();
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    // 获取TrustManager
    private fun getTrustManager(): Array<TrustManager>? {
        return arrayOf(
            myX509TrustManager
        )
    }

    // 获取HostnameVerifier
    fun getHostnameVerifier(): HostnameVerifier {
        return HostnameVerifier { _, _ -> true }
    }

}