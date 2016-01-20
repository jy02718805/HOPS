package com.yuecheng.hops.injection.service;

public interface ErrorCodeService
{

    /**
     * 获取返回码信息
     * 
     * @param code
     *            返回码
     * @return
     */
    public String getErrorCode(String code);
}
