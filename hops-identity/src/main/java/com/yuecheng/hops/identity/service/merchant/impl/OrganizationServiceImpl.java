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

import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.EntityConstant;
import com.yuecheng.hops.common.exception.ApplicationException;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.query.BSort;
import com.yuecheng.hops.common.query.PageUtil;
import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.common.utils.Collections3;
import com.yuecheng.hops.identity.entity.merchant.Merchant;
import com.yuecheng.hops.identity.entity.mirror.Organization;
import com.yuecheng.hops.identity.repository.OrganizationDao;
import com.yuecheng.hops.identity.service.merchant.MerchantService;
import com.yuecheng.hops.identity.service.merchant.OrganizationService;


@Service("organizationService")
public class OrganizationServiceImpl implements OrganizationService
{

    @Autowired
    private OrganizationDao organizationDao;

    @Autowired
    private MerchantService merchantService;

    private static Logger logger = LoggerFactory.getLogger(OrganizationServiceImpl.class);

    /*
     * 新增组织结构
     * @see
     * com.yuecheng.hops.identity.service.MerchantService#saveOrganization(com.yuecheng.hops.identity
     * .entity.mirror.Organization)
     */
    public Organization saveOrganization(Organization organization)
    {

        logger.debug("OrganizationServiceImpl:saveOrganization(" + (BeanUtils.isNotNull(organization) ? organization.toString() :"")
                                                                                                                    + ")");
        organization = organizationDao.save(organization);
        logger.debug("OrganizationServiceImpl:saveOrganization(" + (BeanUtils.isNotNull(organization) ? organization.toString() :"")
                                                                                                                    + ")[返回信息]");
        return organization;
    }

    /*
     * 获取组织结构详情
     * @see com.yuecheng.hops.identity.service.MerchantService#getOrganizationById(java.lang.Long)
     */
    public Organization queryOrganizationById(Long id)
    {
        try
        {
            logger.debug("OrganizationServiceImpl:queryOrganizationById(" + id + ")");
            Organization organization = organizationDao.findOne(id);
            if (BeanUtils.isNotNull(organization))
            {
                List<Merchant> merchants = merchantService.queryMerchantByOrganizationId(
                    organization.getOrganizationId(), null);
                organization.setMerchants(merchants);
            }
            logger.debug("OrganizationServiceImpl:queryOrganizationById(" + (BeanUtils.isNotNull(organization) ? organization.toString() :"")
                                                                                                                             + ")[返回信息]");
            return organization;
        }
        catch (Exception e)
        {
            logger.error("[OrganizationServiceImpl:queryOrganizationById(" + ExceptionUtil.getStackTraceAsString(e) + ")]");
            String[] msgParams = new String[] {"queryOrganizationById"};
            ApplicationException ae = new ApplicationException("identity001035", msgParams);
            throw ExceptionUtil.throwException(ae);
        }
    }

    /*
     * 获取组织机构列表
     * @see com.yuecheng.hops.identity.service.MerchantService#queryOrganization(java.util.Map,
     * int, int, java.lang.String)
     */
    public YcPage<Organization> queryOrganization(Map<String, Object> searchParams,
                                                  int pageNumber, int pageSize, BSort bsort)
    {
        logger.debug("OrganizationServiceImpl:queryOrganization(" + (BeanUtils.isNotNull(searchParams) ? searchParams.toString() :"")
                                                                                                                     + ")");
        Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);;
        String orderCloumn = bsort == null ? EntityConstant.Organization.ORGANIZATION_ID : bsort.getCloumn();
        String orderDirect = bsort == null ? Constant.Sort.DESC : bsort.getDirect().toString();
        Sort sort = new Sort(Direction.valueOf(Direction.class, orderDirect), orderCloumn);
        YcPage<Organization> ycPage = PageUtil.queryYcPage(organizationDao, filters, pageNumber,
            pageSize, sort, Organization.class);
        List<Organization> list = ycPage.getList();
        logger.debug("OrganizationServiceImpl:queryOrganization(" + (BeanUtils.isNotNull(list) ? Collections3.convertToString(
            list, Constant.Common.SEPARATOR) :"") + ")[返回信息]");
        return ycPage;
    }

    @Override
    public Organization queryOrganizationByNo(String organizationNo)
    {
        logger.debug("OrganizationServiceImpl:queryOrganizationByNo(" + organizationNo + ")");
        Organization organization = organizationDao.getOrganizationByNo(organizationNo);
        logger.debug("OrganizationServiceImpl:queryOrganizationByNo(" + (BeanUtils.isNotNull(organization) ? organization.toString() :"")
                                                                                                                         + ")[返回信息]");
        return organization;
    }

    @Override
    public Organization queryOrganizationByName(String organizationName)
    {
        logger.debug("OrganizationServiceImpl:queryOrganizationByName(" + organizationName + ")");
        Organization organization = organizationDao.getOrganizationByName(organizationName);
        logger.debug("OrganizationServiceImpl:queryOrganizationByName(" + (BeanUtils.isNotNull(organization) ? organization.toString() :"")
                                                                                                                           + ")[返回信息]");
        return organization;
    }

}
