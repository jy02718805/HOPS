package com.yuecheng.hops.product.service.impl;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springside.modules.persistence.DynamicSpecifications;
import org.springside.modules.persistence.SearchFilter;
import org.springside.modules.persistence.SearchFilter.Operator;

import com.yuecheng.hops.business.mq.producer.SynProductStatusProducerService;
import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.EntityConstant;
import com.yuecheng.hops.common.enump.IdentityType;
import com.yuecheng.hops.common.enump.MerchantType;
import com.yuecheng.hops.common.exception.ApplicationException;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.query.BSort;
import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.common.utils.StringUtil;
import com.yuecheng.hops.identity.entity.merchant.Merchant;
import com.yuecheng.hops.identity.service.IdentityService;
import com.yuecheng.hops.identity.service.merchant.MerchantStatusManagement;
import com.yuecheng.hops.product.entity.airtimes.AirtimeProduct;
import com.yuecheng.hops.product.entity.history.ProductDiscountHistory;
import com.yuecheng.hops.product.entity.relation.AgentProductRelation;
import com.yuecheng.hops.product.repository.AgentProductRelationDao;
import com.yuecheng.hops.product.repository.AgentProductRelationSqlDao;
import com.yuecheng.hops.product.repository.AirtimeProductDao;
import com.yuecheng.hops.product.service.AgentProductRelationService;
import com.yuecheng.hops.product.service.ProductManagement;
import com.yuecheng.hops.product.service.ProductOperationService;


@Service("agentProductRelationService")
public class AgentProductRelationServiceImpl implements AgentProductRelationService
{
    @Autowired
    private AgentProductRelationDao agentProductRelationDao;

    @Autowired
    private AirtimeProductDao airtimeProductDao;

    @Autowired
    private MerchantStatusManagement merchantStatusManagement;

    @Autowired
    private IdentityService identityService;
    
    @Autowired
    private SynProductStatusProducerService synProductStatusProducerService;
    
    @Autowired
    private ProductOperationService productOperationService;

    @Autowired
    private ProductManagement productManagement;

    @Autowired
    private AgentProductRelationSqlDao agentProductRelationSqlDao;
    
    private static Logger logger = LoggerFactory.getLogger(AgentProductRelationServiceImpl.class);
    @Override
    @Transactional
    public AgentProductRelation doSaveAgentProductRelation(AgentProductRelation dpr, String operatorName)
    {
        Map<String, SearchFilter> filters = new HashMap<String, SearchFilter>();
        filters.put(EntityConstant.AgentProductRelation.IDENTITY_ID, new SearchFilter(
            EntityConstant.AgentProductRelation.IDENTITY_ID, Operator.EQ, dpr.getIdentityId()));
        filters.put(EntityConstant.AgentProductRelation.IDENTITY_TYPE, new SearchFilter(
            EntityConstant.AgentProductRelation.IDENTITY_TYPE, Operator.EQ, dpr.getIdentityType()));
        filters.put(EntityConstant.AgentProductRelation.PRODUCT_ID, new SearchFilter(
            EntityConstant.AgentProductRelation.PRODUCT_ID, Operator.EQ, dpr.getProductId()));
        Specification<AgentProductRelation> spec = DynamicSpecifications.bySearchFilter(
            filters.values(), AgentProductRelation.class);
        AgentProductRelation agentProductRelation = agentProductRelationDao.findOne(spec);
        if (agentProductRelation == null)
        {
            ProductDiscountHistory productDiscountHistory = productOperationService.buildProductDiscountHistory(dpr, Constant.Product.PRODUCT_TYPE_AGENT, null, null, operatorName, Constant.Product.PRODUCT_DISCOUNT_HISTORY_SAVE);
            productOperationService.saveProductDiscountHistory(productDiscountHistory);
            dpr = agentProductRelationDao.save(dpr);
            updateIsRootByRootParams(dpr.getIdentityId(), dpr.getIdentityType(),
                dpr.getProductId());
        }
        else
        {
            String[] msgParams = new String[] {dpr.toString()};
            String[] viewParams = new String[] {};
            ApplicationException e = new ApplicationException("businesss000022", msgParams,
                viewParams);
            throw ExceptionUtil.throwException(e);
        }
        return dpr;
    }

    @Override
    public YcPage<AgentProductRelation> queryAgentProductRelation(Map<String, Object> searchParams,
                                                                  int pageNumber, int pageSize,
                                                                  BSort bsort)
    {
        YcPage<AgentProductRelation> ycPage=agentProductRelationSqlDao.queryPageAgentProductRelation(searchParams, pageNumber, pageSize);
        
        return ycPage;
    }

    @Override
    @Transactional
    public AgentProductRelation editAgentProductRelation(AgentProductRelation dpr,String operatorName)
    {
        AgentProductRelation oldAgentProduct = agentProductRelationDao.findOne(dpr.getId());
        if(!oldAgentProduct.getDiscount().equals(dpr.getDiscount()))
        {
            //折扣变更，记录
            ProductDiscountHistory productDiscountHistory = productOperationService.buildProductDiscountHistory(dpr, Constant.Product.PRODUCT_TYPE_AGENT, oldAgentProduct.getDiscount(), dpr.getDiscount(), operatorName, Constant.Product.PRODUCT_DISCOUNT_HISTORY_UPDATE);
            productOperationService.saveProductDiscountHistory(productDiscountHistory);
        }
        dpr = agentProductRelationDao.save(dpr);
        updateIsRootByRootParams(dpr.getIdentityId(), dpr.getIdentityType(), dpr.getProductId());
        return dpr;
    }

    @Override
    public AgentProductRelation queryAgentProductRelationById(Long id)
    {
        AgentProductRelation dpr = agentProductRelationDao.findOne(id);
        return dpr;
    }

    @Override
    public AgentProductRelation queryAgentProductRelationByParams(Long productId, Long merchantId,
                                                                  String status)
    {
        Map<String, SearchFilter> filters = new HashMap<String, SearchFilter>();
        if (productId != null)
        {
            filters.put(EntityConstant.AgentProductRelation.PRODUCT_ID, new SearchFilter(
                EntityConstant.AgentProductRelation.PRODUCT_ID, Operator.EQ, productId));
        }
        if (merchantId != null)
        {
            filters.put(EntityConstant.AgentProductRelation.IDENTITY_ID, new SearchFilter(
                EntityConstant.AgentProductRelation.IDENTITY_ID, Operator.EQ, merchantId));
            filters.put(EntityConstant.AgentProductRelation.IDENTITY_TYPE, new SearchFilter(
                EntityConstant.AgentProductRelation.IDENTITY_TYPE, Operator.EQ,
                EntityConstant.AgentProductRelation.MERCHANT));
        }
        if (StringUtils.isNotBlank(status))
        {
            filters.put(EntityConstant.AgentProductRelation.STATUS, new SearchFilter(
                EntityConstant.AgentProductRelation.STATUS, Operator.EQ, status));
        }
        Specification<AgentProductRelation> spec_DownProductRelation = DynamicSpecifications.bySearchFilter(
            filters.values(), AgentProductRelation.class);
        AgentProductRelation dpr = agentProductRelationDao.findOne(spec_DownProductRelation);
        return dpr;
    }
    
    @Override
    public AgentProductRelation queryAgentProductRelationByParams(String agentProdId, Long merchantId,
                                                                  String status)
    {
        Map<String, SearchFilter> filters = new HashMap<String, SearchFilter>();
        if (agentProdId != null)
        {
            filters.put(EntityConstant.AgentProductRelation.AGENT_PROD_ID, new SearchFilter(
                EntityConstant.AgentProductRelation.AGENT_PROD_ID, Operator.EQ, agentProdId));
        }
        if (merchantId != null)
        {
            filters.put(EntityConstant.AgentProductRelation.IDENTITY_ID, new SearchFilter(
                EntityConstant.AgentProductRelation.IDENTITY_ID, Operator.EQ, merchantId));
            filters.put(EntityConstant.AgentProductRelation.IDENTITY_TYPE, new SearchFilter(
                EntityConstant.AgentProductRelation.IDENTITY_TYPE, Operator.EQ,
                EntityConstant.AgentProductRelation.MERCHANT));
        }
        if (StringUtils.isNotBlank(status))
        {
            filters.put(EntityConstant.AgentProductRelation.STATUS, new SearchFilter(
                EntityConstant.AgentProductRelation.STATUS, Operator.EQ, status));
        }
        Specification<AgentProductRelation> spec_DownProductRelation = DynamicSpecifications.bySearchFilter(
            filters.values(), AgentProductRelation.class);
        AgentProductRelation dpr = agentProductRelationDao.findOne(spec_DownProductRelation);
        return dpr;
    }

    @Override
    public AgentProductRelation updateAgentProductRelationStatus(Long id, String status)
    {
        String productName="";
        try{
            AgentProductRelation dpr = agentProductRelationDao.findOne(id);
            productName=dpr.getProductName();
            dpr.setStatus(status);
            boolean isUpdate=productManagement.validateLockProductStatus(dpr.getProductId(), status);
            Assert.isTrue(isUpdate);
            if (isUpdate)
            {
                dpr = agentProductRelationDao.save(dpr);
                updateIsRootByRootParams(dpr.getIdentityId(), dpr.getIdentityType(),
                    dpr.getProductId());
                
            	//发送消息,同步产品状态到商户
				synProductStatusProducerService.sendMessage(dpr.getIdentityId(), dpr.getAgentProdId(), status,
						1000);
            }
            return dpr;
        }catch(Exception e)
        {
            logger.error("[AgentProductRelationServiceImpl:updateAgentProductRelationStatus(" + ExceptionUtil.getStackTraceAsString(e) + ")]");
            String[] msgParams = new String[] {"updateAgentProductRelationStatus"};
            String[] viewParams=new String[]{productName};
            ApplicationException ae = new ApplicationException("businesss100003", msgParams,viewParams);
            throw ExceptionUtil.throwException(ae);
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    private void updateIsRootByRootParams(Long identityId, String identityType, Long leafProductId)
    {
        Long rootProductId = getRootProductRelation(identityId, identityType, leafProductId);

        Map<String, SearchFilter> filters = new HashMap<String, SearchFilter>();
        filters.put(EntityConstant.AgentProductRelation.IDENTITY_ID, new SearchFilter(
            EntityConstant.AgentProductRelation.IDENTITY_ID, Operator.EQ, identityId));
        filters.put(EntityConstant.AgentProductRelation.IDENTITY_TYPE, new SearchFilter(
            EntityConstant.AgentProductRelation.IDENTITY_TYPE, Operator.EQ, identityType));
        if (rootProductId.compareTo(-1l) != 0)
        {
            filters.put(EntityConstant.AgentProductRelation.PRODUCT_ID, new SearchFilter(
                EntityConstant.AgentProductRelation.PRODUCT_ID, Operator.EQ, rootProductId));
            Specification<AgentProductRelation> spec_DownProductRelation = DynamicSpecifications.bySearchFilter(
                filters.values(), AgentProductRelation.class);
            AgentProductRelation dpr = agentProductRelationDao.findOne(spec_DownProductRelation);
            // 把根节点的is_root修改
            dpr.setIsRoot(true);
            agentProductRelationDao.save(dpr);

            // 修改根节点之后的所有节点都为false
            List<AgentProductRelation> dprs = agentProductRelationDao.getAllProductByMerchant(
                identityId, identityType, Constant.AgentProductStatus.OPEN_STATUS);
            List<Long> merchantProductTree = new ArrayList<Long>();
            for (Iterator<AgentProductRelation> iterator = dprs.iterator(); iterator.hasNext();)
            {
                AgentProductRelation downProductRelation = (AgentProductRelation)iterator.next();
                merchantProductTree.add(downProductRelation.getProductId());
            }
            getChildProduct(rootProductId, merchantProductTree, filters);
        }
    }

    public void getChildProduct(Long productId, List<Long> merchantProductTree,
                                Map<String, SearchFilter> filters)
    {
        List<AirtimeProduct> afterProducts = airtimeProductDao.getChildProductByParentId(productId);
        for (AirtimeProduct afterProduct : afterProducts)
        {
            if (merchantProductTree.contains(afterProduct.getProductId()))
            {
                filters.put(EntityConstant.AgentProductRelation.PRODUCT_ID,
                    new SearchFilter(EntityConstant.AgentProductRelation.PRODUCT_ID, Operator.EQ,
                        afterProduct.getProductId()));
                Specification<AgentProductRelation> spec_DownProductRelation = DynamicSpecifications.bySearchFilter(
                    filters.values(), AgentProductRelation.class);
                AgentProductRelation dpr = agentProductRelationDao.findOne(spec_DownProductRelation);
                // 把根节点的is_root修改
                dpr.setIsRoot(false);
                agentProductRelationDao.save(dpr);
            }
            afterProducts = airtimeProductDao.getChildProductByParentId(afterProduct.getProductId());
            if (afterProducts.size() > 0)
            {
                getChildProduct(afterProduct.getProductId(), merchantProductTree, filters);
            }
        }
    }

    /**
     * 获取此商户产品节点的根节点。
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    public Long getRootProductRelation(Long identityId, String identityType, Long leafProductId)
    {
        Long rootProductId = -1l;
        Map<String, SearchFilter> filters = new HashMap<String, SearchFilter>();
        filters.put(EntityConstant.AgentProductRelation.IDENTITY_ID, new SearchFilter(
            EntityConstant.AgentProductRelation.IDENTITY_ID, Operator.EQ, identityId));
        filters.put(EntityConstant.AgentProductRelation.IDENTITY_TYPE, new SearchFilter(
            EntityConstant.AgentProductRelation.IDENTITY_TYPE, Operator.EQ, identityType));
        filters.put(EntityConstant.AgentProductRelation.PRODUCT_ID, new SearchFilter(
            EntityConstant.AgentProductRelation.PRODUCT_ID, Operator.EQ, leafProductId));
        Specification<AgentProductRelation> spec_DownProductRelation = DynamicSpecifications.bySearchFilter(
            filters.values(), AgentProductRelation.class);
        AgentProductRelation dpr = agentProductRelationDao.findOne(spec_DownProductRelation);

        AirtimeProduct leafProduct = airtimeProductDao.findOne(dpr.getProductId());
        List<Long> productTree = new ArrayList<Long>();

        if (leafProduct.getParentProductId().compareTo(leafProduct.getProductId()) == 0)
        {
            productTree.add(leafProduct.getProductId());
            List<AgentProductRelation> afterDownProductRelations = getAfterRootAgentProductRelation(dpr.getId());
            for (AgentProductRelation downProductRelation : afterDownProductRelations)
            {
                productTree.add(0, downProductRelation.getProductId());
            }
        }
        else
        {
            productTree.add(leafProduct.getProductId());
            Long parentProductId = leafProduct.getParentProductId();
            Long productId = leafProduct.getProductId();
            while (parentProductId.compareTo(productId) != 0)
            {
                AirtimeProduct parentAirtimeProduct = airtimeProductDao.findOne(parentProductId);
                productTree.add(parentAirtimeProduct.getProductId());
                parentProductId = parentAirtimeProduct.getParentProductId();
                productId = parentAirtimeProduct.getProductId();
            }
        }

        List<AgentProductRelation> dprs = agentProductRelationDao.getAllProductByMerchant(
            identityId, identityType, Constant.AgentProductStatus.OPEN_STATUS);
        List<Long> merchantProductTree = new ArrayList<Long>();
        for (Iterator<AgentProductRelation> iterator = dprs.iterator(); iterator.hasNext();)
        {
            AgentProductRelation downProductRelation = (AgentProductRelation)iterator.next();
            merchantProductTree.add(downProductRelation.getProductId());
        }
        // 产品本身的树productTree

        // 商户管理的所有集合merchantProductTree
        if (productTree.size() > 0 && merchantProductTree.size() > 0)
        {
            for (int i = productTree.size(); i > 0; i-- )
            {
                if (merchantProductTree.contains(productTree.get(i - 1)))
                {
                    rootProductId = productTree.get(i - 1);
                    break;
                }
                else
                {
                    rootProductId = productTree.get(0);
                }
            }
        }
        return rootProductId;
    }

    @Override
    public List<AgentProductRelation> getAllRootAgentProductRelationService(Map<String, Object> searchParams,
                                                                            Long identityId,
                                                                            String identityType)
    {
        Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
        filters.put(EntityConstant.AgentProductRelation.IDENTITY_ID, new SearchFilter(
            EntityConstant.AgentProductRelation.IDENTITY_ID, Operator.EQ, identityId));
        filters.put(EntityConstant.AgentProductRelation.IDENTITY_TYPE, new SearchFilter(
            EntityConstant.AgentProductRelation.IDENTITY_TYPE, Operator.EQ, identityType));
        filters.put(EntityConstant.AgentProductRelation.STATUS, new SearchFilter(
            EntityConstant.AgentProductRelation.STATUS, Operator.EQ,
            Constant.AgentProductStatus.OPEN_STATUS));
        filters.put(EntityConstant.AgentProductRelation.ISROOT, new SearchFilter(
            EntityConstant.AgentProductRelation.ISROOT, Operator.EQ, true));
        Specification<AgentProductRelation> spec_DownProductRelation = DynamicSpecifications.bySearchFilter(
            filters.values(), AgentProductRelation.class);
        List<AgentProductRelation> dprs = agentProductRelationDao.findAll(spec_DownProductRelation);
        return dprs;
    }

    @Override
    public List<AgentProductRelation> getAllAgentProductRelationService(Map<String, Object> searchParams,
                                                                        Long identityId,
                                                                        String identityType)
    {
        Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
        filters.put(EntityConstant.AgentProductRelation.IDENTITY_ID, new SearchFilter(
            EntityConstant.AgentProductRelation.IDENTITY_ID, Operator.EQ, identityId));
        filters.put(EntityConstant.AgentProductRelation.IDENTITY_TYPE, new SearchFilter(
            EntityConstant.AgentProductRelation.IDENTITY_TYPE, Operator.EQ, identityType));
        Specification<AgentProductRelation> spec_DownProductRelation = DynamicSpecifications.bySearchFilter(
            filters.values(), AgentProductRelation.class);
        List<AgentProductRelation> dprs = agentProductRelationDao.findAll(spec_DownProductRelation);
        return dprs;
    }

    @Override
    public List<AgentProductRelation> getAllAgentProductRelationByUp(Long productId)
    {
        Map<String, SearchFilter> filters = new HashMap<String, SearchFilter>();
        filters.put(EntityConstant.AgentProductRelation.PRODUCT_ID, new SearchFilter(
            EntityConstant.AgentProductRelation.PRODUCT_ID, Operator.EQ, productId));
        Specification<AgentProductRelation> spec_DownProductRelation = DynamicSpecifications.bySearchFilter(
            filters.values(), AgentProductRelation.class);
        List<AgentProductRelation> dprs = agentProductRelationDao.findAll(spec_DownProductRelation);
        return dprs;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<AgentProductRelation> getAfterRootAgentProductRelation(Long id)
    {
        List<AgentProductRelation> result = new ArrayList<AgentProductRelation>();
        AgentProductRelation rootDpr = agentProductRelationDao.findOne(id);
        // !这可以做查询语句
        List<AgentProductRelation> dprs = agentProductRelationDao.getAllProductByMerchant(
            rootDpr.getIdentityId(), rootDpr.getIdentityType(),
            Constant.AgentProductStatus.OPEN_STATUS);
        List<Long> merchantProductTree = new ArrayList<Long>();
        for (Iterator<AgentProductRelation> iterator = dprs.iterator(); iterator.hasNext();)
        {
            AgentProductRelation downProductRelation = (AgentProductRelation)iterator.next();
            merchantProductTree.add(downProductRelation.getProductId());
        }

        Map<String, SearchFilter> filters = new HashMap<String, SearchFilter>();
        List<AirtimeProduct> afterProducts = airtimeProductDao.getChildProductByParentId(rootDpr.getProductId());
        for (AirtimeProduct afterProduct : afterProducts)
        {
            if (merchantProductTree.contains(afterProduct.getProductId()))
            {
                filters = new HashMap<String, SearchFilter>();
                filters.put(EntityConstant.AgentProductRelation.IDENTITY_ID,
                    new SearchFilter(EntityConstant.AgentProductRelation.IDENTITY_ID, Operator.EQ,
                        rootDpr.getIdentityId()));
                filters.put(EntityConstant.AgentProductRelation.IDENTITY_TYPE,
                    new SearchFilter(EntityConstant.AgentProductRelation.IDENTITY_TYPE,
                        Operator.EQ, rootDpr.getIdentityType()));
                filters.put(EntityConstant.AgentProductRelation.PRODUCT_ID,
                    new SearchFilter(EntityConstant.AgentProductRelation.PRODUCT_ID, Operator.EQ,
                        afterProduct.getProductId()));
                filters.put(EntityConstant.AgentProductRelation.STATUS, new SearchFilter(
                    EntityConstant.AgentProductRelation.STATUS, Operator.EQ,
                    Constant.AgentProductStatus.OPEN_STATUS));
                Specification<AgentProductRelation> spec_DownProductRelation = DynamicSpecifications.bySearchFilter(
                    filters.values(), AgentProductRelation.class);
                AgentProductRelation dpr = agentProductRelationDao.findOne(spec_DownProductRelation);
                dpr.setPid(afterProduct.getParentProductId());
                result.add(dpr);
            }
        }
        return result;
    }

    @Override
    @Transactional
    public void updateDefValueByIds(String changeNodes, Long merchantId)
    {
        if (changeNodes.length() > 0)
        {
            String[] downProductRelationIds = changeNodes.split(Constant.StringSplitUtil.DECODE);
            for (int i = 0; i < downProductRelationIds.length; i++ )
            {
                if (downProductRelationIds[i].length() > 0)
                {
                    Long id = Long.valueOf(downProductRelationIds[i]);
                    AgentProductRelation dpr = agentProductRelationDao.findOne(id);
                    if (!dpr.getIsRoot())
                    {
                        if (dpr.getDefValue())
                        {
                            dpr.setDefValue(false);
                        }
                        else
                        {
                            dpr.setDefValue(true);
                        }
                    }
                    else
                    {
                        // 根节点产品关系必须为默认！
                        String[] msgParams = new String[] {changeNodes, merchantId.toString()};
                        String[] viewParams = new String[] {};
                        ApplicationException e = new ApplicationException("businesss000022",
                            msgParams, viewParams);
                        throw ExceptionUtil.throwException(e);
                    }
                    agentProductRelationDao.save(dpr);
                }
            }
        }
        else
        {

        }
    }

    @Override
    public void cloneAgentMerchantProduct(Long newMerchantId, Long oldMerchantId, String productIds,String operatorName)
    {
        Map<String, SearchFilter> filters = null;
        Merchant newMerchant = (Merchant)identityService.findIdentityByIdentityId(newMerchantId,
            IdentityType.MERCHANT);
        Merchant oldMerchant = (Merchant)identityService.findIdentityByIdentityId(oldMerchantId,
            IdentityType.MERCHANT);
        String newMerchantType = newMerchant.getMerchantType().toString();
        String oldMerchantType = oldMerchant.getMerchantType().toString();
        if (!newMerchantType.equals(oldMerchantType)
            || !newMerchantType.equals(MerchantType.AGENT.toString()))
        {
            String[] msgParams = new String[] {newMerchantId.toString(), oldMerchantId.toString()};
            String[] viewParams = new String[] {};
            ApplicationException ae = new ApplicationException("businesss000020", msgParams,
                viewParams);
            throw ExceptionUtil.throwException(ae);
        }

        List<AgentProductRelation> result = new ArrayList<AgentProductRelation>();
        filters = new HashMap<String, SearchFilter>();
        filters.put(EntityConstant.AgentProductRelation.IDENTITY_ID, new SearchFilter(
            EntityConstant.AgentProductRelation.IDENTITY_ID, Operator.EQ, newMerchantId));
        filters.put(EntityConstant.AgentProductRelation.IDENTITY_TYPE,
            new SearchFilter(EntityConstant.AgentProductRelation.IDENTITY_TYPE, Operator.EQ,
                IdentityType.MERCHANT.toString()));
        Specification<AgentProductRelation> spec_DownProductRelation = DynamicSpecifications.bySearchFilter(
            filters.values(), AgentProductRelation.class);
        List<AgentProductRelation> newMerchantProducts = agentProductRelationDao.findAll(spec_DownProductRelation);
        List<Long> newMerchantProductIds = new ArrayList<Long>();
        for (AgentProductRelation newMerchantProduct : newMerchantProducts)
        {
            newMerchantProductIds.add(newMerchantProduct.getProductId());
        }

        List<AgentProductRelation> oldMerchantProducts = new ArrayList<AgentProductRelation>();
        String[] downProductRelations = productIds.split(",");
        for (int i = 0; i < downProductRelations.length; i++ )
        {
            AgentProductRelation dpr = agentProductRelationDao.findOne(Long.valueOf(downProductRelations[i]));
            oldMerchantProducts.add(dpr);
        }

        for (AgentProductRelation oldMerchantProduct : oldMerchantProducts)
        {
            if (newMerchantProductIds.contains(oldMerchantProduct.getProductId()))
            {
                filters = new HashMap<String, SearchFilter>();
                filters.put(EntityConstant.AgentProductRelation.IDENTITY_ID, new SearchFilter(
                    EntityConstant.AgentProductRelation.IDENTITY_ID, Operator.EQ, newMerchantId));
                filters.put(EntityConstant.AgentProductRelation.IDENTITY_TYPE, new SearchFilter(
                    EntityConstant.AgentProductRelation.IDENTITY_TYPE, Operator.EQ,
                    IdentityType.MERCHANT.toString()));
                filters.put(EntityConstant.AgentProductRelation.PRODUCT_ID, new SearchFilter(
                    EntityConstant.AgentProductRelation.PRODUCT_ID, Operator.EQ,
                    oldMerchantProduct.getProductId()));
                spec_DownProductRelation = DynamicSpecifications.bySearchFilter(filters.values(),
                    AgentProductRelation.class);
                AgentProductRelation sameDownProductRelation = agentProductRelationDao.findOne(spec_DownProductRelation);

                if (sameDownProductRelation != null)
                {
                    sameDownProductRelation.setProductId(oldMerchantProduct.getProductId());
                    sameDownProductRelation.setProductName(oldMerchantProduct.getProductName());
                    sameDownProductRelation.setProvince(oldMerchantProduct.getProvince());
                    sameDownProductRelation.setParValue(oldMerchantProduct.getParValue());
                    sameDownProductRelation.setCarrierName(oldMerchantProduct.getCarrierName());
                    sameDownProductRelation.setCity(oldMerchantProduct.getCity());
                    sameDownProductRelation.setDiscount(oldMerchantProduct.getDiscount());
                    sameDownProductRelation.setDiscountWeight(oldMerchantProduct.getDiscountWeight());
                    sameDownProductRelation.setQuality(oldMerchantProduct.getQuality());
                    sameDownProductRelation.setQualityWeight(oldMerchantProduct.getQualityWeight());
                    sameDownProductRelation.setPrice(oldMerchantProduct.getPrice());
                    sameDownProductRelation.setStatus(Constant.AgentProductStatus.CLOSE_STATUS);
                    sameDownProductRelation.setDefValue(oldMerchantProduct.getDefValue());
                    sameDownProductRelation.setAgentProdId(oldMerchantProduct.getAgentProdId());
                    sameDownProductRelation.setDisplayValue(oldMerchantProduct.getDisplayValue());
                    sameDownProductRelation.setBusinessType(oldMerchantProduct.getBusinessType());
                    result.add(sameDownProductRelation);
                }
            }
            else
            {
                AgentProductRelation sameDownProductRelation = new AgentProductRelation();
                sameDownProductRelation.setIdentityId(newMerchant.getId());
                sameDownProductRelation.setIdentityName(newMerchant.getMerchantName());
                sameDownProductRelation.setIdentityType(IdentityType.MERCHANT.toString());
                sameDownProductRelation.setProductId(oldMerchantProduct.getProductId());
                sameDownProductRelation.setProductName(oldMerchantProduct.getProductName());
                sameDownProductRelation.setProvince(oldMerchantProduct.getProvince());
                sameDownProductRelation.setParValue(oldMerchantProduct.getParValue());
                sameDownProductRelation.setCarrierName(oldMerchantProduct.getCarrierName());
                sameDownProductRelation.setCity(oldMerchantProduct.getCity());
                sameDownProductRelation.setDiscount(oldMerchantProduct.getDiscount());
                sameDownProductRelation.setDiscountWeight(oldMerchantProduct.getDiscountWeight());
                sameDownProductRelation.setQuality(oldMerchantProduct.getQuality());
                sameDownProductRelation.setQualityWeight(oldMerchantProduct.getQualityWeight());
                sameDownProductRelation.setPrice(oldMerchantProduct.getPrice());
                sameDownProductRelation.setStatus(Constant.AgentProductStatus.CLOSE_STATUS);
                sameDownProductRelation.setDefValue(oldMerchantProduct.getDefValue());
                sameDownProductRelation.setAgentProdId(oldMerchantProduct.getAgentProdId());
                sameDownProductRelation.setDisplayValue(oldMerchantProduct.getDisplayValue());
                sameDownProductRelation.setBusinessType(oldMerchantProduct.getBusinessType());
                result.add(sameDownProductRelation);
            }
        }

        for (Iterator<AgentProductRelation> iterator = result.iterator(); iterator.hasNext();)
        {
            AgentProductRelation downProductRelation = iterator.next();
            ProductDiscountHistory productDiscountHistory = productOperationService.buildProductDiscountHistory(downProductRelation, Constant.Product.PRODUCT_TYPE_AGENT, null, null, operatorName, Constant.Product.PRODUCT_DISCOUNT_HISTORY_SAVE);
            productOperationService.saveProductDiscountHistory(productDiscountHistory);
            agentProductRelationDao.save(downProductRelation);
            updateIsRootByRootParams(newMerchantId, IdentityType.MERCHANT.toString(),
                downProductRelation.getProductId());
        }
    }

    @Override
    public List<AgentProductRelation> getAllProductByAgentMerchantId(Long merchantId,
                                                                     String identityType,
                                                                     String status)
    {
        Map<String, SearchFilter> filters = new HashMap<String, SearchFilter>();
        filters.put(EntityConstant.AgentProductRelation.IDENTITY_ID, new SearchFilter(
            EntityConstant.AgentProductRelation.IDENTITY_ID, Operator.EQ, merchantId));
        filters.put(EntityConstant.AgentProductRelation.IDENTITY_TYPE, new SearchFilter(
            EntityConstant.AgentProductRelation.IDENTITY_TYPE, Operator.EQ, identityType));
        // Constant.UpProductStatus.OPEN_STATUS
        if (StringUtil.isNotBlank(status))
        {
            filters.put(EntityConstant.AgentProductRelation.STATUS, new SearchFilter(
                EntityConstant.AgentProductRelation.STATUS, Operator.EQ, status));
        }
        Specification<AgentProductRelation> spec = DynamicSpecifications.bySearchFilter(
            filters.values(), AgentProductRelation.class);
        List<AgentProductRelation> uprs = agentProductRelationDao.findAll(spec);
        return uprs;
    }

    @Override
    public void deleteAgentProductRelationById(Long id, String operatorName)
    {
        AgentProductRelation dpr = agentProductRelationDao.findOne(id);
        ProductDiscountHistory productDiscountHistory = productOperationService.buildProductDiscountHistory(dpr, Constant.Product.PRODUCT_TYPE_AGENT, null, null, operatorName, Constant.Product.PRODUCT_DISCOUNT_HISTORY_DELETE);
        productOperationService.saveProductDiscountHistory(productDiscountHistory);
        agentProductRelationDao.delete(id);
    }

    @Override
    public void deleteAgentProductRelationByProductId(Long productId, String operatorName)
    {
        Map<String, SearchFilter> filters = new HashMap<String, SearchFilter>();
        filters.put(EntityConstant.AgentProductRelation.PRODUCT_ID, new SearchFilter(
            EntityConstant.AgentProductRelation.PRODUCT_ID, Operator.EQ, productId));
        Specification<AgentProductRelation> spec = DynamicSpecifications.bySearchFilter(
            filters.values(), AgentProductRelation.class);
        List<AgentProductRelation> agentProductRelations = agentProductRelationDao.findAll(spec);
        
        for (int i = 0; i < agentProductRelations.size(); i++ )
        {
            ProductDiscountHistory productDiscountHistory = productOperationService.buildProductDiscountHistory(agentProductRelations.get(i), Constant.Product.PRODUCT_TYPE_AGENT, null, null, operatorName, Constant.Product.PRODUCT_DISCOUNT_HISTORY_DELETE);
            productOperationService.saveProductDiscountHistory(productDiscountHistory);
        }
        agentProductRelationDao.delete(agentProductRelations);
    }

    @Override
    public List<AgentProductRelation> getAgentProductRelationByParams(Long productId, String status)
    {
        Map<String, SearchFilter> filters = new HashMap<String, SearchFilter>();
        filters.put(EntityConstant.AgentProductRelation.PRODUCT_ID, new SearchFilter(
            EntityConstant.AgentProductRelation.PRODUCT_ID, Operator.EQ, productId));
        if (StringUtil.isNotBlank(status))
        {
            filters.put(EntityConstant.AgentProductRelation.STATUS, new SearchFilter(
                EntityConstant.AgentProductRelation.STATUS, Operator.EQ, status));
        }
        Specification<AgentProductRelation> spec = DynamicSpecifications.bySearchFilter(
            filters.values(), AgentProductRelation.class);
        List<AgentProductRelation> dprs = agentProductRelationDao.findAll(spec);
        return dprs;
    }

    @Override
    public void updateIsRootByProductId(boolean flag, Long productId)
    {
        agentProductRelationDao.updateIsRootByProductId(flag, productId);
    }

    @Override
    public void updateProductNameById(Long productId, String productName)
    {
        agentProductRelationDao.updateProductNameById(productId, productName);
    }

    @Override
    public void updateStatusByProductId(boolean isRoot, boolean isDefValue, Long productId)
    {
        agentProductRelationDao.updateStatusByProductId(isRoot, isDefValue, productId);
    }

    @Override
    public List<AgentProductRelation> getProductRelationByIdentityId(Long merchantId)
    {
        // TODO Auto-generated method stub
        Map<String, SearchFilter> filters = new HashMap<String, SearchFilter>();
        filters.put(EntityConstant.AgentProductRelation.IDENTITY_ID, new SearchFilter(
            EntityConstant.AgentProductRelation.IDENTITY_ID, Operator.EQ, merchantId));
        Specification<AgentProductRelation> spec_DownProductRelation = DynamicSpecifications.bySearchFilter(
            filters.values(), AgentProductRelation.class);
        List<AgentProductRelation> dprs = agentProductRelationDao.findAll(spec_DownProductRelation);
        return dprs;
    }

    @Override
    public List<AgentProductRelation> getProductRelationByParams(Map<String, Object> searchParams)
    {
        List<AgentProductRelation> aprs = agentProductRelationSqlDao.getProductRelationByParams(searchParams);
        return aprs;
    }

    @Override
    public List<AgentProductRelation> getAllAgentProductRelation(Map<String, Object> searchParams,
                                                                 Long identityId,
                                                                 String identityType)
    {
        List<AgentProductRelation> dprs = agentProductRelationSqlDao.getProductRelationByParams(searchParams, identityId, identityType);
        return dprs;
    }
 
    @Override
    public int updateStatusByParamAll(String status, String carrierName, String province,
                                      String city, String identityType, Long identityId,String businessType)
    {
       int result= agentProductRelationDao.updateStatusByParamAll(status, carrierName, province, city, identityType, identityId,Integer.valueOf(businessType));
       return result;
    }

}
