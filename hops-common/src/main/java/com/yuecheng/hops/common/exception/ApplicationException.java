package com.yuecheng.hops.common.exception;

public class ApplicationException extends HopsException
{

    private static final long serialVersionUID = 8576960307065239487L;
    
    public ApplicationException(String code, Throwable t)
    {
        super(code, new String[0], new String[0], t);
    }
    
    public ApplicationException(String code)
    {
        super(code, new String[0], new String[0], new Throwable());
    }

    public ApplicationException(String code, String[] msgParams, Throwable t)
    {
        super(code, msgParams, new String[0], t);
    }
    
    public ApplicationException(String code, String[] msgParams)
    {
        super(code, msgParams, new String[0], new Throwable());
    }
    
    public ApplicationException(String code, String msgParam, Throwable t)
    {
        super(code, new String[]{msgParam}, new String[0], t);
    }

    public ApplicationException(String code, String msgParam, String viewMsg, Throwable t)
    {
        super(code, new String[]{msgParam}, new String[]{viewMsg}, t);
    }

    public ApplicationException(String code, String[] msgParams, String[] viewMsg, Throwable t)
    {
        super(code, msgParams, viewMsg, t);
    }
    
    public ApplicationException(String code, String[] msgParams, String[] viewMsg)
    {
        super(code, msgParams, viewMsg, new Throwable());
    }
}
