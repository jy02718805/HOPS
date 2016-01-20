/*
 * 文件名：HttpClientTest.java
 * 版权：Copyright by www.365haoyou.com
 * 描述：
 * 修改人：Administrator
 * 修改时间：2015年1月12日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.yuecheng.hops.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.apache.commons.beanutils.converters.URLConverter;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

public class HttpClientTest5
{
    public static void main(String[] args) throws InterruptedException, IOException 
    {
//        for (int i = 0; i < 10000; i++ )
//        {
//            new Thread().sleep(100);
//            new Thread(new SendThread()).start();
//        }
        String unicodeStr = "%E7%BC%B4%E8%AF%9D%E8%B4%B9%E7%94%B3%E8%AF%B7%E6%88%90%E5%8A%9F";
        System.out.println(URLDecoder.decode(unicodeStr)); 
        
//        BufferedReader strin=new BufferedReader(new InputStreamReader(System.in));  
//        System.out.print("请输入一个字符串：");  
//        String str = strin.readLine();
//        System.out.println(str);
//        str = str.replaceAll("\"", "\\\\\"");
//        System.out.println(str);
     // 创建HttpClient实例     
        HttpClient httpclient = new DefaultHttpClient();  
        // 创建Get方法实例     
//        HttpGet httpgets = new HttpGet("https://ipos.10086.cn/ips/phoneChargeService?merchantId=888009972990026&requestId=ttUT2vVnKSlkSlqKt9E&signType=MD5&type=PhoneChargesQuery&version=2.0.0&queryRequestId=300049782839&hmac=6dea884eec91f671e7347e3ccb8e482d");    
        HttpGet httpgets = new HttpGet("http://127.0.0.1:58080/hops-robot/order/query");
//        HttpGet httpgets = new HttpGet("http://192.168.1.42:9206/hops-robot/order/query");
//        HttpGet httpgets = new HttpGet("http://192.168.1.31:8081/hops-robot/order/query");
//        HttpGet httpgets = new HttpGet("http://192.168.1.43:12304/hops-robot/order/query");
//        HttpGet httpgets = new HttpGet("http://192.168.1.40:12304/hops-robot/order/query");
//        HttpGet httpgets = new HttpGet("http://192.168.1.40:12305/hops-robot/order/query");
        HttpResponse response = httpclient.execute(httpgets);    
        HttpEntity entity = response.getEntity();    
        if (entity != null) {    
            InputStream instreams = entity.getContent();    
            String str = convertStreamToString(instreams);
            System.out.println("Do something");   
            System.out.println(str);  
            // Do not need the rest    
            httpgets.abort();
        }
    }
    
    public static String convertStreamToString(InputStream is) {      
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));      
        StringBuilder sb = new StringBuilder();      
       
        String line = null;      
        try {      
            while ((line = reader.readLine()) != null) {  
                sb.append(line + "\n");      
            }      
        } catch (IOException e) {      
            e.printStackTrace();      
        } finally {      
            try {      
                is.close();      
            } catch (IOException e) {      
               e.printStackTrace();      
            }      
        }      
        return sb.toString();      
    }
}



class SendThread implements Runnable
{

    @Override
    public void run()
    {
        try
        {
            send();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    
    public void send() throws ClientProtocolException, IOException
    {
        // 创建HttpClient实例     
        HttpClient httpclient = new DefaultHttpClient();  
        // 创建Get方法实例     
        HttpGet httpgets = new HttpGet("http://192.168.1.31:8080/hops-robot/tel/request?cid=test&oid=101538788&pr=50&nb=1&fm=50&pn=13102739319&ct=0&ru=http://192.168.1.31:8087/t.html&tsp=20150112201833&key=N6L2N4JVBZ0JR8B026RN880Z6TDN468B&sign=9AA79DE7DE770F9FAC74DB98BBD277CE");    
        HttpResponse response = httpclient.execute(httpgets);    
        HttpEntity entity = response.getEntity();    
        if (entity != null) {    
            InputStream instreams = entity.getContent();    
            String str = convertStreamToString(instreams);  
            System.out.println("Do something");   
            System.out.println(str);  
            // Do not need the rest    
            httpgets.abort();
        }
    }
    
    public String convertStreamToString(InputStream is) {      
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));      
        StringBuilder sb = new StringBuilder();      
       
        String line = null;      
        try {      
            while ((line = reader.readLine()) != null) {  
                sb.append(line + "\n");      
            }      
        } catch (IOException e) {      
            e.printStackTrace();      
        } finally {      
            try {      
                is.close();      
            } catch (IOException e) {      
               e.printStackTrace();      
            }      
        }      
        return sb.toString();      
    }
}