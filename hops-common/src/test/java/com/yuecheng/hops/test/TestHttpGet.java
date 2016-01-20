/*
 * 文件名：TestHttpGet.java 版权：Copyright by www.365haoyou.com 描述： 修改人：Administrator 修改时间：2015年5月13日
 * 跟踪单号： 修改单号： 修改内容：
 */

package com.yuecheng.hops.test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

public class TestHttpGet
{
    public static void main(String[] args)
    {
        try
        {
            System.out.println(new TestHttpGet().executeGet("http://127.0.0.1:48455/queryOrder?oid=fake20150519141700455336727&cid=kachuang&tsp=20150519151219&sign=5b72d009f8513ae1bbb23aa953c12880"));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public String executeGet(String url)
        throws Exception
    {
        BufferedReader in = null;

        String content = null;
        try
        {
            // 定义HttpClient
            HttpClient client = new DefaultHttpClient();
            // 实例化HTTP方法
            HttpGet request = new HttpGet();
            request.setURI(new URI(url));
            HttpResponse response = client.execute(request);
            
            in = new BufferedReader(new InputStreamReader(response.getEntity().getContent(),"GBK"));
            StringBuffer sb = new StringBuffer("");
            String line = "";
            String NL = System.getProperty("line.separator");
            while ((line = in.readLine()) != null)
            {
                sb.append(line + NL);
            }
            in.close();
            content = sb.toString();
        }
        finally
        {
            if (in != null)
            {
                try
                {
                    in.close();// 最后要关闭BufferedReader
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
            return content;
        }
    }
}
