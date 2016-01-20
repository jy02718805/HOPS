// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space
// Source File Name: AuditDataThread.java

package com.yuecheng.hops.mportal.thread;


import java.util.Iterator;
import java.util.List;

import com.yuecheng.hops.batch.entity.BatchOrderRequestHandler;
import com.yuecheng.hops.batch.service.order.BatchOrderRequestHandlerManagement;


public class AuditDataThread implements Runnable
{

    private BatchOrderRequestHandlerManagement batchOrderRequestHandlerManagement;

    private String upfile;

    private String auditnum;

    public AuditDataThread(String upfile, String auditnum)
    {
        this.upfile = upfile;
        this.auditnum = auditnum;
    }

    public void run()
    {
        List borhList = batchOrderRequestHandlerManagement.queryBatchOrderRequestHandler(upfile,
            0L);
        if (borhList != null && !"".equals(auditnum))
        {
            int auditnumint = Integer.parseInt(auditnum);
            int i = 0;
            for (Iterator iterator = borhList.iterator(); iterator.hasNext();)
            {
                BatchOrderRequestHandler borh = (BatchOrderRequestHandler)iterator.next();
                if (i < auditnumint)
                    batchOrderRequestHandlerManagement.update(1L, borh.getId().longValue());
                i++ ;
            }

        }
    }
}
