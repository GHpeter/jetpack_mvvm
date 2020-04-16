package com.fuxing.libnetwork;

import com.fuxing.libnetwork.inter.Convert;
import com.fuxing.libnetwork.inter.JsonConvert;

import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * @author ：Create by lichunfu
 * @Date : 2020-04-10
 * Description:
 **/
public class ApiService {
    protected static final int TIMEOUT = 30;
    protected static final OkHttpClient ok_http_client;
    protected static String mBaseUrl;
    protected static Convert sConvert;


    static {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        ok_http_client = new OkHttpClient.Builder()
                .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
                .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .build();
        verify();
    }


    /**
     * http 证书问题
     */
    protected static void verify() {
        TrustManager[] managers = new TrustManager[]{new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        }};
        try {
            SSLContext ssl = SSLContext.getInstance("SSL");
            ssl.init(null, managers, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(ssl.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });

        } catch (Exception e) {

        }

    }

    public static void init(String baseUrl, Convert convert) {
        mBaseUrl = baseUrl;
        if (convert == null) {
            convert = new JsonConvert();

        }
        sConvert = convert;
    }

    public static <T> GetRequest<T> get(String url) {
        return new GetRequest(mBaseUrl + url);
    }

    public static <T> PostRequest<T> post(String url) {
        return new PostRequest(mBaseUrl + url);
    }

}
