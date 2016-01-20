// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space
// Source File Name: TxtDataToDBThread.java

package com.yuecheng.hops.mportal.thread;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yuecheng.hops.batch.entity.BatchOrderRequestHandler;
import com.yuecheng.hops.batch.service.order.BatchOrderRequestHandlerManagement;


public class TxtDataToDBThread implements Runnable
{
	private static final Logger logger = LoggerFactory.getLogger(TxtDataToDBThread.class);
    private BatchOrderRequestHandlerManagement batchOrderRequestHandlerManagement;

    private String displayName;

    private String filepath;

    private String filename;

    public TxtDataToDBThread(String displayName, String filepath, String filename,
                             BatchOrderRequestHandlerManagement batchOrderRequestHandlerManagement)
    {
        this.displayName = displayName;
        this.filepath = filepath;
        this.filename = filename;
        this.batchOrderRequestHandlerManagement = batchOrderRequestHandlerManagement;
    }

    public void run()
    {
        try
        {
            FileReader fr = new FileReader(filepath);
            BufferedReader br = new BufferedReader(fr);
            String read;
            while ((read = br.readLine()) != null)
            {
                BatchOrderRequestHandler borh = new BatchOrderRequestHandler();
                String str[] = read.toString().split(",");
                if (str[0].trim().length() == 11)
                {
                    borh.setPhoneNo(str[0].trim());
                    BigDecimal bd = new BigDecimal(str[1]);
                    borh.setOrderFee(bd);
                    borh.setUpFile(filename);
                    borh.setOrderStatus(Long.valueOf(0L));
                    borh.setOrderRequestTime(getNowTime());
                    borh.setOperator(displayName);
                    batchOrderRequestHandlerManagement.save(borh);
                }
            }
            br.close();
            fr.close();
        }
        catch (FileNotFoundException e)
        {
        	logger.error("TxtDataToDBThread:[run-FileNotFoundException]["+e.getMessage()+"]");
        }
        catch (IOException e)
        {
        	logger.error("TxtDataToDBThread:[run-IOException]["+e.getMessage()+"]");
        }
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
            logger.info(fmtrq.format(nowdate));
        }
        catch (ParseException e)
        {
        	logger.error("TxtDataToDBThread:[getNowTime]["+e.getMessage()+"]");
        }
        return nowdate;
    }
}
