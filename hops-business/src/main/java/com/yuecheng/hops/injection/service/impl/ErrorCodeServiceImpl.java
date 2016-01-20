package com.yuecheng.hops.injection.service.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuecheng.hops.common.utils.StringUtil;
import com.yuecheng.hops.injection.entity.ErrorCode;
import com.yuecheng.hops.injection.repository.ErrorCodeDao;
import com.yuecheng.hops.injection.service.ErrorCodeService;


@Service("errorCodeService")
public class ErrorCodeServiceImpl implements ErrorCodeService
{

    @Autowired
    private ErrorCodeDao errorCodeDao;

    private static Logger logger = LoggerFactory.getLogger(ErrorCodeServiceImpl.class);

    @Override
    public String getErrorCode(String code)
    {
        logger.debug("[ErrorCodeServiceImpl:getErrorCode(" + code + ")]");
        String msg = StringUtil.initString();
        if (StringUtil.isNullOrEmpty(code))
        {
            return StringUtil.initString();
        }
        ErrorCode errorCode = errorCodeDao.findOne(code);
        if (errorCode != null)
        {
            msg = errorCode.getMsg();
        }
        else
        {
            msg = StringUtil.initString();
        }
        return msg;
    }

}
