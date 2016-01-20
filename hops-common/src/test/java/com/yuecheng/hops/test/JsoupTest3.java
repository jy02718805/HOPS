/*
 * 文件名：JsoupTest.java
 * 版权：Copyright by www.365haoyou.com
 * 描述：
 * 修改人：Administrator
 * 修改时间：2015年8月20日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.yuecheng.hops.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Map;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.google.gson.Gson;
import com.yuecheng.hops.common.utils.BeanUtils;

public class JsoupTest3
{
private static final String APPLICATION_JSON = "application/json";
    
    private static final String CONTENT_TYPE_TEXT_JSON = "text/json";

    public static void httpPostWithJSON(String url, String json) throws Exception {
        // 将JSON进行UTF-8编码,以便传输中文
        String encoderJson = URLEncoder.encode(json, HTTP.UTF_8);
        
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader(HTTP.CONTENT_TYPE, APPLICATION_JSON);
        
        StringEntity se = new StringEntity(encoderJson);
        se.setContentType(CONTENT_TYPE_TEXT_JSON);
        se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, APPLICATION_JSON));
        httpPost.setEntity(se);
        httpClient.execute(httpPost);
    }
    
    public static void main(String[] args)
    {
//        try
//        {
//            httpPostWithJSON("http://127.0.0.1:9288/aaa", "{ \"firstName\":\"John\" , \"lastName\":\"Doe\" }");
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace();
//        }
//        number
        try
        {
//            http://116.252.222.40:8888/search/?db=entmaininfo&keyword=+regno%3A450502600365312&fields=id%2Centname%2Clerep%2Cregno%2Cdom&variable=v1
            BufferedReader b1 = new BufferedReader(new FileReader("F:\\a.txt"));
            BufferedWriter bw = new BufferedWriter(new FileWriter("F:\\ab.txt"));

            String s1 = "";
            int i = 0;
            while((s1=b1.readLine())!=null){
                String no = s1;
                String url = "http://116.252.222.40:8888/search/?db=entmaininfo&keyword=+regno%3A"+no+"&fields=id%2Centname%2Clerep%2Cregno%2Cdom&variable=v1";
                Document doc = Jsoup.connect(url).get();
                String responseStr = doc.text();
                String dom = responseStr.substring(responseStr.indexOf("[")+1, responseStr.indexOf("]"));
                Gson gson = new Gson();
                Data data = gson.fromJson(dom, Data.class);
                System.out.println(i++);
                bw.write(BeanUtils.isNotNull(data)?data.toString()+"\r\n":"null"+"\r\n");
            }
            bw.close();
            b1.close();
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
