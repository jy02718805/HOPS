package com.yuecheng.hops.blacklist.service.impl;


import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuecheng.hops.blacklist.entity.Blacklist;
import com.yuecheng.hops.blacklist.service.BlacklistService;
import com.yuecheng.hops.blacklist.service.CheckBlacklistService;


@Service("checkBlacklistService")
@Transactional
public class CheckBlacklistServiceImpl implements CheckBlacklistService
{
    private static Logger logger = LoggerFactory.getLogger(CheckBlacklistServiceImpl.class);

    @Autowired
    private BlacklistService blacklistService;

    @Override
    public Blacklist checkNum(String blacklistNo, String businessType)
    {
        logger.info("[CheckBlacklistServiceImpl:checkNum(" + blacklistNo + ","
                    + businessType + ")]");
        if (StringUtils.isNotBlank(blacklistNo)
            && StringUtils.isNumeric(blacklistNo))
        {
            Blacklist blacklist = blacklistService.findOne(blacklistNo,
                    businessType);
            if (blacklist != null)
            {
                logger.debug("查询号码在黑名单列表之中，详细信息：" + blacklist.toString());
                return blacklist;
            }
        }
        logger.debug("查询号码不在黑名单列表之中，返回 null");
        return null;
    }

}
