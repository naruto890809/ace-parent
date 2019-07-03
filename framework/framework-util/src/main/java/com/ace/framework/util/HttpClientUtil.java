package com.ace.framework.util;


import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author WuZhiWei
 * @since 2015-12-19 13:23:00
 */
public class HttpClientUtil {

    private static final Charset CHARSET = Consts.UTF_8;

    public static String doPost(CloseableHttpClient httpClient, String url, HttpEntity entity) {
        if (httpClient == null) {
            httpClient = HttpClients.custom().build();
        }
        CloseableHttpResponse response = null;
        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(entity);
        try {
            response = httpClient.execute(httpPost);
            HttpEntity httpEntity = response.getEntity();
            if (httpEntity == null) {
                return "";
            }
            try {
                String content = EntityUtils.toString(httpEntity, CHARSET);
                EntityUtils.consume(httpEntity);
                return content;
            } catch (Exception e) {
                try {
                    EntityUtils.consume(httpEntity);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (httpClient != null) {
                try {
                    httpClient.close();
                } catch (Exception e) {

                }
            }
            if (response != null) {
                try {
                    response.close();
                } catch (Exception e) {

                }
            }
        }
        return "";
    }

    public static String doGet(CloseableHttpClient httpClient, String url) {
        if (httpClient == null) {
            httpClient = HttpClients.custom().build();
        }
        CloseableHttpResponse response = null;
        HttpGet httpGet = new HttpGet(url);
        try {
            response = httpClient.execute(httpGet);
            HttpEntity httpEntity = response.getEntity();
            if (httpEntity == null) {
                return "";
            }
            try {
                String content = EntityUtils.toString(httpEntity, CHARSET);
                EntityUtils.consume(httpEntity);
                return content;
            } catch (Exception e) {
                try {
                    EntityUtils.consume(httpEntity);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (httpClient != null) {
                try {
                    httpClient.close();
                } catch (Exception e) {

                }
            }
            if (response != null) {
                try {
                    response.close();
                } catch (Exception e) {

                }
            }
        }
        return "";
    }


    public static String doPost(String url, Map<String, String> paramMap) {
        return doPost(null, url, map2entity(paramMap));
    }

    public static String doPost(CloseableHttpClient httpClient,String url, Map<String, String> paramMap) {
        return doPost(httpClient, url, map2entity(paramMap));
    }


    public static String doPost(String url, String param) {
        try {
            return doPost(null, url, new StringEntity(param, CHARSET));
        }catch (Exception e){
            return "";
        }
    }

    public static String doPostSSL(String url, Map<String, String> paramMap) {
        try {
            return doPost(getSSLHttpClient(), url, map2entity(paramMap));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
    public static String doPostSSL(String url, String param) {
        try {
            return doPost(getSSLHttpClient(), url, new StringEntity(param, CHARSET));
        }catch (Exception e){
            return "";
        }
    }

    public static String doGetSSL(String url){
        try {
            return doGet(getSSLHttpClient(), url);
        }catch (Exception e){
            return "";
        }
    }

    private static CloseableHttpClient getSSLHttpClient() throws NoSuchAlgorithmException, KeyManagementException {
        SSLContext sslcontext = SSLContexts.custom().build();
        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }

                    public void checkClientTrusted(X509Certificate[] certs, String authType) {
                    }

                    public void checkServerTrusted(X509Certificate[] certs, String authType) {
                    }

                }
        };
        sslcontext.init(null, trustAllCerts, null);
        SSLConnectionSocketFactory sslsf =
                new SSLConnectionSocketFactory(sslcontext,
                        new String[]{"TLSv1"}, null,
                        SSLConnectionSocketFactory.getDefaultHostnameVerifier());
        return HttpClients.custom().setSSLSocketFactory(sslsf).build();
    }

    private static UrlEncodedFormEntity map2entity(Map<String, String> paramMap) {
        List<NameValuePair> params = new ArrayList<NameValuePair>(paramMap.size());
        for (Map.Entry<String, String> entry : paramMap.entrySet()) {
            params.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        return new UrlEncodedFormEntity(params, CHARSET);
    }
}
