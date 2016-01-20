package com.yuecheng.hops.common.exception;


import java.io.PrintWriter;
import java.io.StringWriter;

import com.alibaba.dubbo.rpc.RpcException;


public class ExceptionUtil
{
    public static RpcException throwException(Exception e)
        throws RpcException
    {
        return new RpcException(e.getMessage(), unchecked(e));
    }

    /**
     * 将CheckedException转换为UncheckedException.
     */
    public static RuntimeException unchecked(Exception e)
    {
        if (e instanceof RuntimeException)
        {
            return (RuntimeException)e;
        }
        else
        {
            return new RuntimeException(e);
        }
    }

    /**
     * 将ErrorStack转化为String.
     */
    public static String getStackTraceAsString(Exception e)
    {
        StringWriter stringWriter = new StringWriter();
        e.printStackTrace(new PrintWriter(stringWriter));
        return stringWriter.toString();
    }

    /**
     * 判断异常是否由某些底层的异常引起.
     */
    public static boolean isCausedBy(Exception ex,
                                     Class<? extends Exception>... causeExceptionClasses)
    {
        Throwable cause = ex.getCause();
        while (cause != null)
        {
            for (Class<? extends Exception> causeClass : causeExceptionClasses)
            {
                if (causeClass.isInstance(cause))
                {
                    return true;
                }
            }
            cause = cause.getCause();
        }
        return false;
    }

    public static void wrappedByHopsException(Exception e, String exceptionCode)
        throws HopsException
    {
        String[] msgParams = new String[] {};
        String[] viewParams = new String[] {};
        wrappedByHopsException(e, exceptionCode, msgParams, viewParams);
    }

    public static void wrappedByHopsException(Exception e, String exceptionCode, String param)
        throws HopsException
    {
        String[] msgParams = new String[] {param};
        String[] viewParams = new String[] {};
        wrappedByHopsException(e, exceptionCode, msgParams, viewParams);
    }

    public static void wrappedByHopsException(Exception e, String exceptionCode, String[] params)
        throws HopsException
    {
        String[] viewParams = new String[] {};
        wrappedByHopsException(e, exceptionCode, params, viewParams);
    }

    public static void wrappedByHopsException(Exception e, String exceptionCode, String param,
                                              String viewParam)
        throws HopsException
    {
        String[] msgParams = new String[] {param};
        String[] viewParams = new String[] {viewParam};
        wrappedByHopsException(e, exceptionCode, msgParams, viewParams);
    }

    public static void wrappedByHopsException(Exception e, String exceptionCode, String[] params,
                                              String[] viewParams)
        throws HopsException
    {
        HopsException outException = null;
        if (e instanceof HopsException)
        {
            outException = (HopsException)e;
        }
        else
        {
            outException = new SystemException(exceptionCode, params, viewParams, e.getCause());
        }
        throw outException;
    }
}
