package com.yuecheng.hops.identity.service.merchant.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springside.modules.persistence.DynamicSpecifications;
import org.springside.modules.persistence.SearchFilter;
import org.springside.modules.persistence.SearchFilter.Operator;

import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.EntityConstant;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.common.utils.Collections3;
import com.yuecheng.hops.identity.entity.merchant.Merchant;
import com.yuecheng.hops.identity.repository.MerchantDao;
import com.yuecheng.hops.identity.service.IdentityStatusTransferService;
import com.yuecheng.hops.identity.service.merchant.MerchantStatusManagement;


@Service("merchantStatusManagement")
public class MerchantStatusManagementImpl implements MerchantStatusManagement
{

    private static final Logger logger = LoggerFactory.getLogger(MerchantStatusManagementImpl.class);

    @Autowired
    private MerchantDao merchantDao;

    @Autowired
    private IdentityStatusTransferService identityStatusDefendersService;

    @Override
    public List<Merchant> queryMerchantByIsRebate(boolean isRebate)
    {
        logger.debug("MerchantStatusManagementImpl:getMerchantByIsRebate(" + isRebate + ")");
        Map<String, SearchFilter> filters = new HashMap<String, SearchFilter>();
        filters.put(EntityConstant.Merchant.IS_REBATE, new SearchFilter(
            EntityConstant.Merchant.IS_REBATE, Operator.EQ, isRebate?Constant.RebateStatus.ENABLE:Constant.RebateStatus.DISABLE));
        Specification<Merchant> spec_Merchant = DynamicSpecifications.bySearchFilter(
            filters.values(), Merchant.class);
        List<Merchant> merchants = merchantDao.findAll(spec_Merchant);
        logger.debug("MerchantStatusManagementImpl:getMerchantByIsRebate(" + (BeanUtils.isNotNull(merchants) ? Collections3.convertToString(
            merchants, Constant.Common.SEPARATOR) :"") + ")[返回信息]");
        return merchants;
    }
    
}
