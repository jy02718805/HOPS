///*
// * 文件名：CmpayCheck.java
// * 版权：Copyright by www.365haoyou.com
// * 描述：
// * 修改人：Administrator
// * 修改时间：2015年11月16日
// * 跟踪单号：
// * 修改单号：
// * 修改内容：
// */
//
//package com.yuecheng.hops.test;
//
//import java.io.BufferedReader;
//import java.io.BufferedWriter;
//import java.io.FileReader;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//
//import org.apache.http.HttpEntity;
//import org.apache.http.HttpResponse;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.impl.client.DefaultHttpClient;
//
//import com.hisun.iposm.HiiposmUtil;
//import com.yuecheng.hops.common.utils.UUIDUtils;
//
//public class CmpayCheck
//{
//    static HiiposmUtil util = new HiiposmUtil();
//    
//    public static void main(String[] args) throws Exception
//    {
//        BufferedReader b1 = new BufferedReader(new FileReader("F:\\a.txt"));
//        BufferedWriter bw = new BufferedWriter(new FileWriter("F:\\ab.txt"));
//        
//        String s1 = "";
//        int i = 0;
//        while((s1=b1.readLine())!=null){
//            String url = buildUrl(s1);
//            HttpClient httpclient = new DefaultHttpClient();  
//            // 创建Get方法实例     
//            HttpGet httpgets = new HttpGet(url);    
//            HttpResponse response = httpclient.execute(httpgets);    
//            HttpEntity entity = response.getEntity();    
//            if (entity != null) {    
//                InputStream instreams = entity.getContent();    
//                String str = convertStreamToString(instreams);
//                bw.write(s1+"|"+str+"\r\n");
//                httpgets.abort();
//            }
//            System.out.println(i++);
//        }
//        bw.close();
//        b1.close();
//    }
//    
//    public static String buildUrl(String queryRequestId){
//        String merchantId = "888009972990026";
//        String requestId = UUIDUtils.uuid();
//        String signType = "MD5";
//        String version = "2.0.0";
//        String key = "3769191d6f5ab6df125d525e42d62562";
//        
//        String type = "PhoneChargesQuery";
//        String sign = merchantId + requestId + signType + type + version + queryRequestId;
//        sign = util.MD5Sign(sign, key);
//        
//        System.out.println("sign    "+sign);
//        String url = "https://ipos.10086.cn/ips/phoneChargeService?" + 
//            "merchantId="+merchantId
//            +"&requestId="+requestId
//            +"&signType="+signType
//            +"&type="+type
//            +"&version="+version
//            +"&queryRequestId="+queryRequestId
//            +"&hmac="+sign;
//        return url;
//    }
//    
//    public static String convertStreamToString(InputStream is) {      
//        BufferedReader reader = new BufferedReader(new InputStreamReader(is));      
//        StringBuilder sb = new StringBuilder();      
//       
//        String line = null;      
//        try {      
//            while ((line = reader.readLine()) != null) {  
//                sb.append(line + "\n");      
//            }      
//        } catch (IOException e) {      
//            e.printStackTrace();      
//        } finally {      
//            try {      
//                is.close();      
//            } catch (IOException e) {      
//               e.printStackTrace();      
//            }      
//        }      
//        return sb.toString();      
//    }
//}
