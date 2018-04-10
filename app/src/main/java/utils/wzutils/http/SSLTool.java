package utils.wzutils.http;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.CertificateFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import utils.wzutils.common.LogTool;

/**
 * Created by ishare on 2016/8/12.
 * <p>
 * https 相关的
 */
public class SSLTool {
    public static SSLSocketFactory initSSLFactoryByCrt(String... crts) {
        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null);

            for (int i = 0; i < crts.length; i++) {
                InputStream inputStream = new ByteArrayInputStream(crts[i].getBytes());
                keyStore.setCertificateEntry("" + i, certificateFactory.generateCertificate(inputStream));
                inputStream.close();
            }


            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustManagerFactory.getTrustManagers(), new SecureRandom());
            SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
            return sslSocketFactory;
        } catch (Exception e) {
            LogTool.ex(e);
        }
        return null;
    }


    /***
     * 这个是不需要证书的， 也就是忽略证书，  一般还是不要用 有  证书 才能保证  链接 不会被 代理  串改， 所以 用上面的
     *
     * @return
     */
    public static SSLSocketFactory initAllowSSLFactory() {
        SSLSocketFactory sslSocketFactory = null;
        try {
            X509TrustManager x509TrustManager = new X509TrustManager() {
                @Override
                public void checkClientTrusted(java.security.cert.X509Certificate[] x509Certificates, String s) throws java.security.cert.CertificateException {

                }

                @Override
                public void checkServerTrusted(java.security.cert.X509Certificate[] x509Certificates, String s) throws java.security.cert.CertificateException {

                }

                @Override
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return new java.security.cert.X509Certificate[0];
                }
            };
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[]{x509TrustManager}, new SecureRandom());
            sslSocketFactory = sslContext.getSocketFactory();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sslSocketFactory;

    }
}
