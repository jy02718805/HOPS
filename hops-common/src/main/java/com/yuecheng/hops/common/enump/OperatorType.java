package com.yuecheng.hops.common.enump;


import java.io.Serializable;

import com.yuecheng.hops.common.CodedEnum;


public enum OperatorType implements CodedEnum<OperatorType>, Serializable {
    SP_OPERATOR("SP_OPERATOR"), MERCHANT_OPERATOR("MERCHANT_OPERATOR");

    private String code;

    private OperatorType(String code)
    {
        this.code = code;
    }

    @Override
    public String getCode(OperatorType t)
    {
        if (null != t)
        {
            for (OperatorType operatorType : OperatorType.values())
            {
                if (operatorType.equals(t))
                {
                    return operatorType.code;
                }
            }
        }
        return null;
    }

    @Override
    public OperatorType getEnum(String code)
    {
        if (null != code)
        {
            for (OperatorType operatorType : OperatorType.values())
            {
                if (operatorType.code.equals(code))
                {
                    return operatorType;
                }
            }
        }
        return null;
    }
}
