package com.yuecheng.hops.common.exception;

public class SystemException extends HopsException
{

    private static final long serialVersionUID = 3082381601074912007L;
    
    public SystemException(String code, Throwable t)
    {
        super(code, new String[0], new String[0], t);
    }
    
    public SystemException(String code, String msgParam, Throwable t)
    {
        super(code, new String[]{msgParam}, new String[0], t);
    }
    
    public SystemException(String code, String[] msgParams, Throwable t)
    {
        super(code, msgParams, new String[0], t);
    }

    public SystemException(String code, String msgParam, String viewMsg, Throwable t)
    {
        super(code, new String[]{msgParam}, new String[]{viewMsg}, new Throwable());
    }
    
    public SystemException(String code, String[] msgParams, String[] viewMsg, Throwable t)
    {
        super(code, msgParams, viewMsg, t);
    }
    
    public SystemException(String code, String[] msgParams, String[] viewMsg)
    {
        super(code, msgParams, viewMsg, new Throwable());
    }
}
