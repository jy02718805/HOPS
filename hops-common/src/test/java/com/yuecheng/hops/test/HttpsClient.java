/*
 * 文件名：HttpsClient.java
 * 版权：Copyright by www.365haoyou.com
 * 描述：
 * 修改人：Administrator
 * 修改时间：2015年11月16日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.yuecheng.hops.test;

import java.io.BufferedReader; 
import java.io.FileInputStream; 
import java.io.InputStreamReader; 
import java.security.KeyStore; 
import java.util.ArrayList; 
import java.util.List; 
import javax.net.ssl.KeyManagerFactory; 
import javax.net.ssl.SSLContext; 
import javax.net.ssl.TrustManager; 
import javax.net.ssl.TrustManagerFactory; 
import junit.framework.TestCase; 
import org.apache.http.HttpResponse; 
import org.apache.http.NameValuePair; 
import org.apache.http.client.HttpClient; 
import org.apache.http.client.entity.UrlEncodedFormEntity; 
import org.apache.http.client.methods.HttpPost; 
import org.apache.http.conn.scheme.Scheme; 
import org.apache.http.conn.ssl.SSLSocketFactory; 
import org.apache.http.impl.client.DefaultHttpClient; 
import org.apache.http.message.BasicNameValuePair; 
import org.apache.http.protocol.HTTP; 
import org.junit.Before; 
import org.junit.Test; 

public class HttpsClient extends TestCase { 
    private String httpUrl = "https://localhost:8443/global/httpsClientRequest"; 
    // 客户端密钥库 
    private String sslKeyStorePath; 
    private String sslKeyStorePassword; 
    private String sslKeyStoreType; 
    // 客户端信任的证书 
    private String sslTrustStore; 
    private String sslTrustStorePassword; 

    @Before 
    public void setUp() { 
        sslKeyStorePath = "D:\\home\\tomcat.keystore"; 
        sslKeyStorePassword = "stevenjohn"; 
        sslKeyStoreType = "JKS"; // 密钥库类型，有JKS PKCS12等 
        sslTrustStore = "D:\\home\\tomcat.keystore"; 
        sslTrustStorePassword = "stevenjohn"; 
        System.setProperty("javax.net.ssl.keyStore", sslKeyStorePath); 
        System.setProperty("javax.net.ssl.keyStorePassword", sslKeyStorePassword); 
        System.setProperty("javax.net.ssl.keyStoreType", sslKeyStoreType); 
        // 设置系统参数 
        System.setProperty("javax.net.ssl.trustStore", sslTrustStore); 
        System.setProperty("javax.net.ssl.trustStorePassword", sslTrustStorePassword); 
    } 
    @Test 
    public void testHttpsClient() { 
        SSLContext sslContext = null; 
        try { 
            KeyStore kstore = KeyStore.getInstance("jks"); 
            kstore.load(new FileInputStream(sslKeyStorePath), 
            sslKeyStorePassword.toCharArray()); 
            KeyManagerFactory keyFactory = KeyManagerFactory 
            .getInstance("sunx509"); 
            keyFactory.init(kstore, sslKeyStorePassword.toCharArray()); 
            KeyStore tstore = KeyStore.getInstance("jks"); 
            tstore.load(new FileInputStream(sslTrustStore), 
            sslTrustStorePassword.toCharArray()); 
            TrustManager[] tm; 
            TrustManagerFactory tmf = TrustManagerFactory 
            .getInstance("sunx509"); 
            tmf.init(tstore); 
            tm = tmf.getTrustManagers(); 
            sslContext = SSLContext.getInstance("SSL"); 
            sslContext.init(keyFactory.getKeyManagers(), tm, null); 
        } catch (Exception e) { 
            e.printStackTrace(); 
        } 
        try { 
            HttpClient httpClient = new DefaultHttpClient(); 
            SSLSocketFactory socketFactory = new SSLSocketFactory(sslContext); 
            Scheme sch = new Scheme("https", 8443, socketFactory); 
            httpClient.getConnectionManager().getSchemeRegistry().register(sch); 
            HttpPost httpPost = new HttpPost(httpUrl); 
            List<NameValuePair> nvps = new ArrayList<NameValuePair>(); 
            nvps.add(new BasicNameValuePair("user", "abin")); 
            nvps.add(new BasicNameValuePair("pwd", "abing")); 
            httpPost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8)); 
            HttpResponse httpResponse = httpClient.execute(httpPost); 
            String spt = System.getProperty("line.separator"); 
            BufferedReader buffer = new BufferedReader(new InputStreamReader( 
            httpResponse.getEntity().getContent())); 
            StringBuffer stb=new StringBuffer(); 
            String line=null; 
            while((line=buffer.readLine())!=null){ 
                stb.append(line); 
            } 
            buffer.close(); 
            String result=stb.toString(); 
            System.out.println("result="+result); 
        } catch (Exception e) { 
        e.printStackTrace(); 
        } 
    } 
} 