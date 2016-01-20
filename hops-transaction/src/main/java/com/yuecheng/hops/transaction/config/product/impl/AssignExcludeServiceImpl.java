package com.yuecheng.hops.transaction.config.product.impl;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.persistence.DynamicSpecifications;
import org.springside.modules.persistence.SearchFilter;
import org.springside.modules.persistence.SearchFilter.Operator;

import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.EntityConstant;
import com.yuecheng.hops.common.query.BSort;
import com.yuecheng.hops.common.query.PageUtil;
import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.product.entity.relation.AgentProductRelation;
import com.yuecheng.hops.product.entity.relation.SupplyProductRelation;
import com.yuecheng.hops.transaction.config.entify.product.AssignExclude;
import com.yuecheng.hops.transaction.config.product.AssignExcludeService;
import com.yuecheng.hops.transaction.config.repository.AssignExcludeDao;
import com.yuecheng.hops.transaction.config.repository.AssignExcludeSqlDao;


/**
 * 指定排除规则服务层
 * 
 * @author Jinger 2014-03-26
 */

@Service("assignExcludeService")
public class AssignExcludeServiceImpl implements AssignExcludeService
{
    private static Logger    logger = LoggerFactory.getLogger(AssignExcludeServiceImpl.class);

    @Autowired
    private AssignExcludeDao assignExcludeDao;

    @Autowired
    private AssignExcludeSqlDao assignExcludeSqlDao;
    @Override
    public List<AssignExclude> getAllAssignExclude()
    {
        logger.info("[AssignExcludeServiceImpl:getAllAssignExclude()]");
        List<AssignExclude> assignExcludeList = assignExcludeDao.queryAssignExcludeList();
        return assignExcludeList;
    }

    @Override
    @Transactional
    public void deleteAssignExclude(Long assignExcludeId)
    {
        logger.info("[AssignExcludeServiceImpl:deleteAssignExclude(" + assignExcludeId + ")]");
        if (assignExcludeId != null)
        {
            assignExcludeDao.delete(assignExcludeId);
        }
    }

    @Override
    public AssignExclude getAssignExcludeById(Long assignExcludeId)
    {
        logger.info("[AssignExcludeServiceImpl:getAssignExcludeById(" + assignExcludeId + ")]");
        if (assignExcludeId != null)
        {
            return assignExcludeDao.findOne(assignExcludeId);
        }
        return null;
    }

    @Override
    public YcPage<AssignExclude> queryAssignExclude(Map<String, Object> searchParams,
                                                    int pageNumber, int pageSize, BSort bsort)
    {
        logger.info("[AssignExcludeServiceImpl:queryAssignExclude(" + searchParams != null ? searchParams.toString() : null
                                                                                                                           + ","
                                                                                                                           + pageNumber
                                                                                                                           + ","
                                                                                                                           + pageSize
                                                                                                                           + ")]");
        Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
        String orderCloumn = bsort == null ? "id" : bsort.getCloumn();
        String orderDirect = bsort == null ? "DESC" : bsort.getDirect().toString();
        Sort sort = new Sort(Direction.valueOf(Direction.class, orderDirect), orderCloumn);
        Page<AssignExclude> page = PageUtil.queryPage(assignExcludeDao, filters, pageNumber,
            pageSize, sort, AssignExclude.class);
        YcPage<AssignExclude> ycPage = new YcPage<AssignExclude>();
        ycPage.setList(page.getContent());
        ycPage.setPageTotal(page.getTotalPages());
        ycPage.setCountTotal((int)page.getTotalElements());
        return ycPage;
    }

    @Override
    public List<AssignExclude> getAllAssignExclude(String merchantType, Long merchantId,
                                                   Long ruleType, Long productNo)
    {
        logger.info("[AssignExcludeServiceImpl:getAllAssignExclude(" + merchantType + ","
                        + merchantId + "," + ruleType + "," + productNo + ")]");
        Map<String, SearchFilter> filters = new HashMap<String, SearchFilter>();
        if (merchantType != null)
        {
            filters.put(EntityConstant.AssignExclude.MERCHANT_TYPE, new SearchFilter(
                EntityConstant.AssignExclude.MERCHANT_TYPE, Operator.EQ, merchantType));
        }
        if (merchantId != null)
        {
            filters.put(EntityConstant.AssignExclude.MERCHANT_ID, new SearchFilter(
                EntityConstant.AssignExclude.MERCHANT_ID, Operator.EQ, merchantId));
        }
        if (ruleType != null)
        {
            filters.put(EntityConstant.AssignExclude.RULE_TYPE, new SearchFilter(
                EntityConstant.AssignExclude.RULE_TYPE, Operator.EQ, ruleType));
        }
        if (productNo != null)
        {
            filters.put(EntityConstant.AssignExclude.PRODUCT_NO, new SearchFilter(
                EntityConstant.AssignExclude.PRODUCT_NO, Operator.EQ, productNo));
        }
        Specification<AssignExclude> spec_AssignExclude = DynamicSpecifications.bySearchFilter(
            filters.values(), AssignExclude.class);
        List<AssignExclude> dprs = assignExcludeDao.findAll(spec_AssignExclude);
        return dprs;
    }

    @Override
    public List<String[]> getAllAgentMerchant(List<AgentProductRelation> agentProductList,
                                              List<AssignExclude> assignExcludeList)
    {
        logger.info("[AssignExcludeServiceImpl:getAllMerchantSelectDown(" + agentProductList != null ? agentProductList.toString() : null
                                                                                                                                         + ","
                                                                                                                                         + assignExcludeList != null ? assignExcludeList.toString() : null
                                                                                                                                                                                                      + ")]");
        List<String[]> strList = new ArrayList<String[]>();
        for (int i = 0; i < agentProductList.size(); i++ )
        {
            AgentProductRelation downProduct = agentProductList.get(i);
            String[] resultObj = new String[3];
            resultObj[0] = downProduct.getIdentityId().toString();
            resultObj[1] = downProduct.getIdentityName();
            resultObj[2] = Constant.MerchantStatus.DISABLE;
            for (int j = 0; j < assignExcludeList.size(); j++ )
            {
                AssignExclude AssignExclude = assignExcludeList.get(j);
                if (downProduct.getIdentityId().compareTo(AssignExclude.getMerchantId()) == 0)
                {
                    resultObj[2] = Constant.MerchantStatus.ENABLE;
                    break;
                }
            }
            strList.add(resultObj);
        }
        return strList;
    }

    @Override
    public List<String[]> getAllSupplyMerchant(List<SupplyProductRelation> supplyProductList,
                                               List<AssignExclude> assignExcludeList)
    {
        logger.info("[AssignExcludeServiceImpl:getAllMerchantSelectUp(" + supplyProductList != null ? supplyProductList.toString() : null
                                                                                                                                         + ","
                                                                                                                                         + assignExcludeList != null ? assignExcludeList.toString() : null
                                                                                                                                                                                                      + ")]");
        List<String[]> strList = new ArrayList<String[]>();
        for (int i = 0; i < supplyProductList.size(); i++ )
        {
            SupplyProductRelation upProduct = supplyProductList.get(i);
            String[] resultObj = new String[3];
            resultObj[0] = upProduct.getIdentityId().toString();
            resultObj[1] = upProduct.getIdentityName();
            resultObj[2] = Constant.MerchantStatus.DISABLE;
            for (int j = 0; j < assignExcludeList.size(); j++ )
            {
                AssignExclude AssignExclude = assignExcludeList.get(j);
                if (upProduct.getIdentityId().compareTo(AssignExclude.getObjectMerchantId()) == 0)
                {
                    resultObj[2] = Constant.MerchantStatus.ENABLE;
                    break;
                }
            }
            strList.add(resultObj);
        }
        return strList;
    }

    @Override
    @Transactional
    public List<AssignExclude> saveAssignExcludeList(List<AssignExclude> assignExcludeList)
    {
        logger.info("[AssignExcludeServiceImpl:saveAssignExcludeList(" + assignExcludeList != null ? assignExcludeList.toString() : null
                                                                                                                                        + ")]");
        List<AssignExclude> backDEList = new ArrayList<AssignExclude>();
        if (BeanUtils.isNotNull(assignExcludeList))
        {
            for(AssignExclude assignExclude : assignExcludeList){
                assignExclude = assignExcludeDao.save(assignExclude);
                backDEList.add(assignExclude);
            }
        }
        return backDEList;
    }

    @Override
    @Transactional
    public void deleteAssignExcludeList(String merchantType, Long merchantId, Long ruleType,
                                        Long productNo)
    {
        logger.info("[AssignExcludeServiceImpl:deleteAssignExcludeList(" + merchantType + ","
                        + merchantId + "," + ruleType + "," + productNo + ")]");
        if (merchantType != null && !merchantType.isEmpty() && merchantId != null
            && ruleType != null && productNo != null)
        {
            List<AssignExclude> dprs = getAllAssignExclude(merchantType, merchantId, ruleType,
                productNo);
            assignExcludeDao.delete(dprs);
        }
    }

    @Override
    public List<AssignExclude> findAssignExcludeByParams(Long merchantId, String rule)
    {
        logger.info("[AssignExcludeServiceImpl:findAssignExcludeByParams(" + merchantId + ","
                        + rule + ")]");
        Map<String, SearchFilter> filters = new HashMap<String, SearchFilter>();
        filters.put(EntityConstant.AssignExclude.MERCHANT_ID, new SearchFilter(
            EntityConstant.AssignExclude.MERCHANT_ID, Operator.EQ, merchantId));
        filters.put(EntityConstant.AssignExclude.RULE_TYPE, new SearchFilter(
            EntityConstant.AssignExclude.RULE_TYPE, Operator.EQ, rule));
        Specification<AssignExclude> spec_AssignExclude = DynamicSpecifications.bySearchFilter(
            filters.values(), AssignExclude.class);
        List<AssignExclude> designMerchants = assignExcludeDao.findAll(spec_AssignExclude);
        return designMerchants;
    }

    @Override
    public List<AssignExclude> getAllAssignExclude(Map<String, Object> searchParams)
    {
        Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
        Specification<AssignExclude> spec_AssignExclude = DynamicSpecifications.bySearchFilter(
            filters.values(), AssignExclude.class);
        List<AssignExclude> dprs = assignExcludeDao.findAll(spec_AssignExclude);
        return dprs;
    }

    @Override
    public List<String[]> getAllAgentProduct(List<AgentProductRelation> agentProductList,
                                             List<AssignExclude> assignExcludeList)
    {
        logger.info("[AssignExcludeServiceImpl:getAllMerchantSelectDown(" + agentProductList != null ? agentProductList.toString() : null
            + ","
            + assignExcludeList != null ? assignExcludeList.toString() : null
                                                                         + ")]");
        List<String[]> strList = new ArrayList<String[]>();
        for (AgentProductRelation downProduct : agentProductList)
        {
            String[] resultObj = new String[3];
            resultObj[0] = downProduct.getProductId().toString();
            resultObj[1] = downProduct.getProductName();
            resultObj[2] = Constant.MerchantStatus.DISABLE;
            for (AssignExclude assignExclude : assignExcludeList)
            {
                if (downProduct.getProductId().compareTo(assignExclude.getProductNo()) == 0)
                {
                    resultObj[2] = Constant.MerchantStatus.ENABLE;
                    break;
                }
            }
            strList.add(resultObj);
        }
        return strList;
    }

    @Override
    public List<String[]> getAllSupplyProduct(List<SupplyProductRelation> supplyProductList,
                                              List<AssignExclude> assignExcludeList)
    {
        logger.info("[AssignExcludeServiceImpl:getAllMerchantSelectUp(" + supplyProductList != null ? supplyProductList.toString() : null
            + ","
            + assignExcludeList != null ? assignExcludeList.toString() : null
                                                                         + ")]");
        List<String[]> strList = new ArrayList<String[]>();
        for(SupplyProductRelation upProduct : supplyProductList)
        {
            String[] resultObj = new String[3];
            resultObj[0] = upProduct.getProductId().toString();
            resultObj[1] = upProduct.getProductName();
            resultObj[2] = Constant.MerchantStatus.DISABLE;
            for (AssignExclude assignExclude : assignExcludeList)
            {
                if (upProduct.getProductId().compareTo(assignExclude.getProductNo()) == 0)
                {
                    resultObj[2] = Constant.MerchantStatus.ENABLE;
                    break;
                }
            }
            strList.add(resultObj);
        }
        return strList;
    }

    @Override
    public void deleteAssignExcludeList(List<AssignExclude> assignExcludeList)
    {
        logger.info("[AssignExcludeServiceImpl:deleteAssignExcludeList(" +assignExcludeList != null ? assignExcludeList.toString() : null
                                                                         + ")]");
        assignExcludeDao.delete(assignExcludeList);
    }

    @Override
    public List<AssignExclude> getAllAssignExcludeRelation(Map<String, Object> searchParams)
    {
        return assignExcludeSqlDao.getAssignExcludeByParams(searchParams);
    }
}
