/*
 * 文件名：Task.java 版权：Copyright by www.365haoyou.com 描述： 修改人：Administrator 修改时间：2015年8月25日 跟踪单号：
 * 修改单号： 修改内容：
 */

package com.yuecheng.hops.threadTest;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hisun.iposm.HiiposmUtil;
import com.yuecheng.hops.common.utils.UUIDUtils;


public class Task
{
    static HiiposmUtil util = new HiiposmUtil();
    
    private static Logger logger = LoggerFactory.getLogger(Task.class);
    
    public static final int READY = 0;

    public static final int RUNNING = 1;

    public static final int FINISHED = 2;

    private String deliveryId;

    @SuppressWarnings("unused")
    private int status;

    // 声明一个任务的自有业务含义的变量，用于标识任务
    private int taskId;

    // 任务的初始化方法
    public Task(int taskId, String deliveryId)
    {
        this.status = READY;
        this.taskId = taskId;
        this.deliveryId = deliveryId;
    }


    public String getDeliveryId()
    {
        return deliveryId;
    }


    public void setDeliveryId(String deliveryId)
    {
        this.deliveryId = deliveryId;
    }


    /**
     * 执行任务
     */
    public void execute()
    {
        BufferedWriter bw = null;
        try
        {
            bw = new BufferedWriter(new FileWriter("F:\\ab.txt", true));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
        // 设置状态为运行中
        setStatus(Task.RUNNING);
        logger.debug("当前线程 ID 是：" + Thread.currentThread().getName() + " | 任务 ID 是：" + this.taskId);
        // 附加一个延时
        try
        {
            String url = buildUrl(deliveryId);
            HttpClient httpclient = new DefaultHttpClient();  
            // 创建Get方法实例     
            HttpGet httpgets = new HttpGet(url);    
            HttpResponse response = httpclient.execute(httpgets);    
            HttpEntity entity = response.getEntity();    
            if (entity != null) {    
                InputStream instreams = entity.getContent();    
                String str = convertStreamToString(instreams);
                bw.write(deliveryId+"|"+str+"\r\n");                                                  
                bw.close(); 
                httpgets.abort();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        // 执行完成，改状态为完成
        setStatus(FINISHED);
    }

    public void setStatus(int status)
    {
        this.status = status;
    }

    public int getTaskId()
    {
        return taskId;
    }
    
    public Date getNowTime()
    {
        GregorianCalendar now = new GregorianCalendar();
        SimpleDateFormat fmtrq = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss|SSSS", Locale.CHINA);
        String nowDate = fmtrq.format(now.getTime());
        Date nowdate = null;
        try
        {
            nowdate = fmtrq.parse(nowDate);
        }
        catch (ParseException e)
        {
            logger.error("AatchOrderListThread:[getNowTime]["+e.getMessage()+"]");
        }
        return nowdate;
    }
    
    public static String buildUrl(String queryRequestId){
        String merchantId = "888009972990026";
        String requestId = UUIDUtils.uuid();
        String signType = "MD5";
        String version = "2.0.0";
        String key = "3769191d6f5ab6df125d525e42d62562";
        
        String type = "PhoneChargesQuery";
        String sign = merchantId + requestId + signType + type + version + queryRequestId;
        sign = util.MD5Sign(sign, key);
        
        System.out.println("sign    "+sign);
        String url = "https://ipos.10086.cn/ips/phoneChargeService?" + 
            "merchantId="+merchantId
            +"&requestId="+requestId
            +"&signType="+signType
            +"&type="+type
            +"&version="+version
            +"&queryRequestId="+queryRequestId
            +"&hmac="+sign;
        return url;
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
