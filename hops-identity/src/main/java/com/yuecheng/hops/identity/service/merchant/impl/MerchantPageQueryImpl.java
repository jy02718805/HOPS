package com.yuecheng.hops.identity.service.merchant.impl;


import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springside.modules.persistence.SearchFilter;
import org.springside.modules.persistence.SearchFilter.Operator;

import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.EntityConstant;
import com.yuecheng.hops.common.enump.MerchantType;
import com.yuecheng.hops.common.exception.ApplicationException;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.query.PageUtil;
import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.common.utils.Collections3;
import com.yuecheng.hops.common.utils.StringUtil;
import com.yuecheng.hops.identity.entity.merchant.Merchant;
import com.yuecheng.hops.identity.repository.MerchantDao;
import com.yuecheng.hops.identity.service.merchant.MerchantPageQuery;


@Service("merchantPageQuery")
public class MerchantPageQueryImpl implements MerchantPageQuery
{
    @Autowired
    private MerchantDao merchantDao;

    private static final Logger logger = LoggerFactory.getLogger(MerchantPageQueryImpl.class);

    @Override
    public YcPage<Merchant> queryPageMerchant(Map<String, Object> searchParams, int pageNumber,
                                              int pageSize, String sortType, MerchantType type,
                                              String name)
    {
        try{
            logger.debug("MerchantPageQueryImpl:queryPageMerchant(" + (BeanUtils.isNotNull(type) ? type.toString() :"")
                                                                                                       + ","
                                                                                                       + name
                                                                                                       + ")");
            Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
            if (StringUtil.isNotBlank(name))
            {
                filters.put(EntityConstant.Merchant.MERCHANT_NAME, new SearchFilter(
                    EntityConstant.Merchant.MERCHANT_NAME, Operator.EQ, name));
            }
            if (BeanUtils.isNotNull(type))
            {
                filters.put(EntityConstant.Merchant.MERCHANT_TYPE, new SearchFilter(
                    EntityConstant.Merchant.MERCHANT_TYPE, Operator.EQ, type));
            }
            Sort sort=new Sort(Direction.DESC, EntityConstant.Identity.IDENTITY_ID);
//          YcPage<Merchant> ycPage=merchantDao.queryPageMerchant(searchParams, pageNumber, pageSize, sortType, type, name);
            YcPage<Merchant> ycPage = PageUtil.queryYcPage(merchantDao, filters, pageNumber, pageSize,
                sort, Merchant.class);
            List<Merchant> list = ycPage.getList();
            logger.debug("MerchantPageQueryImpl:queryPageMerchant(" + (BeanUtils.isNotNull(list) ? Collections3.convertToString(
                list, Constant.Common.SEPARATOR) :"") + ")[返回信息]");
            return ycPage;
        }catch(Exception e)
        {
            logger.error("[MerchantPageQueryImpl:queryPageMerchant(" + ExceptionUtil.getStackTraceAsString(e) + ")]");
            String[] msgParams = new String[] {"queryPageMerchant"};
            ApplicationException ae = new ApplicationException("identity001112", msgParams);
            throw ExceptionUtil.throwException(ae);
        }
    }

}
