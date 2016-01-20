package com.yuecheng.hops.common.exception;


import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yuecheng.hops.common.message.MessageResolver;


public class HopsException extends RuntimeException implements Serializable
{
    private static final Logger logger           = LoggerFactory.getLogger(HopsException.class);

    private static final long   serialVersionUID = 6788968994103609967L;

    public String               code;

    private String              viewMessage;

    public String getCode()
    {
        return code;
    }

    public String getViewMessage()
    {
        return viewMessage;
    }

    public void setViewMessage(String viewMessage)
    {
        this.viewMessage = viewMessage;
    }

    public HopsException(String code, String[] msgParams, String[] viewMsg, Throwable t)
    {
        super(MessageResolver.getMessage("view" + code, viewMsg));
        String msg = MessageResolver.getMessage("msg" + code, msgParams);
        this.viewMessage = MessageResolver.getMessage("view" + code, viewMsg);
        this.code = code;
    }

    public void printExceptionToLog(Throwable t,String msg)
    {
        StringWriter sw = null;
        PrintWriter pw = null;
        try {
            sw = new StringWriter();
            pw =  new PrintWriter(sw);
            //将出错的栈信息输出到printWriter中
            t.printStackTrace(pw);
            pw.flush();
            sw.flush();
        } finally {
            if (sw != null) {
                try {
                    sw.close();
                } catch (IOException e1) {
                	logger.error("HopsException:[printExceptionToLog]["+e1.getMessage()+"]");
                }
            }
            if (pw != null) {
                pw.close();
            }
        }
        logger.error(sw.toString(), t);
    }

    public HopsException(String msg, Throwable t)
    {
        super(msg);
        logger.error(msg, t);
    }

    public HopsException()
    {

    }

    public static void throwException(String code, String[] msgParams, String[] viewMsg,
                                      Throwable t)
    {
        throw new HopsException(code, msgParams, viewMsg, t);
    }

}
