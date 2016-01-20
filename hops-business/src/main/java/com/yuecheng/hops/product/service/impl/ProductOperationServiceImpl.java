package com.yuecheng.hops.product.service.impl;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.persistence.SearchFilter;
import org.springside.modules.persistence.SearchFilter.Operator;

import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.enump.IdentityType;
import com.yuecheng.hops.common.enump.MerchantType;
import com.yuecheng.hops.common.query.PageUtil;
import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.common.utils.StringUtil;
import com.yuecheng.hops.product.entity.history.ProductDiscountHistory;
import com.yuecheng.hops.product.entity.history.ProductOperationDetail;
import com.yuecheng.hops.product.entity.history.ProductOperationHistory;
import com.yuecheng.hops.product.entity.history.ProductOperationRule;
import com.yuecheng.hops.product.entity.history.assist.ProductOperationHistoryAssist;
import com.yuecheng.hops.product.entity.relation.AgentProductRelation;
import com.yuecheng.hops.product.entity.relation.ProductRelation;
import com.yuecheng.hops.product.entity.relation.SupplyProductRelation;
import com.yuecheng.hops.product.repository.ProductDiscountHistoryDao;
import com.yuecheng.hops.product.repository.ProductOperationHistoryDao;
import com.yuecheng.hops.product.service.AgentProductRelationService;
import com.yuecheng.hops.product.service.ProductManagement;
import com.yuecheng.hops.product.service.ProductOperationDetailService;
import com.yuecheng.hops.product.service.ProductOperationRuleService;
import com.yuecheng.hops.product.service.ProductOperationService;
import com.yuecheng.hops.product.service.ProductPageQuery;
import com.yuecheng.hops.product.service.SupplyProductRelationService;


@Service("productOperationService")
public class ProductOperationServiceImpl implements ProductOperationService
{
    @Autowired
    private ProductOperationHistoryDao productOperationHistoryDao;

//    @Autowired
//    private ProductOperationDetailDao productOperationDetailDao;
    
    @Autowired
    private ProductDiscountHistoryDao productDiscountHistoryDao;

    @Autowired
    private ProductOperationDetailService productOperationDetailService;
    
    @Autowired
    private ProductPageQuery productPageQuery;

    @Autowired
    private AgentProductRelationService agentProductRelationService;

    @Autowired
    private SupplyProductRelationService supplyProductRelationService;
    @Autowired
    private ProductOperationRuleService productOperationRuleService;
    
    @Autowired
    private ProductManagement productManagement;

    @Override
    public YcPage<ProductOperationHistory> queryProductOperationHistory(Map<String, Object> searchParams,
                                                                        int pageNumber,
                                                                        int pageSize,
                                                                        String sortType)
    {
        Map<String, SearchFilter> filters = new HashMap<String, SearchFilter>();
        YcPage<ProductOperationHistory> ycPage = PageUtil.queryYcPage(productOperationHistoryDao,
            filters, pageNumber, pageSize, new Sort(Direction.DESC, sortType),
            ProductOperationHistory.class);
        return ycPage;
    }

    @Override
    @Transactional
    public ProductOperationHistory saveProductOperationHistory(ProductOperationHistory productOperationHistory,String merchantIds,String updateUser)
    {
        String parValue = StringUtil.initString();
        if (productOperationHistory.getParValue().compareTo(new BigDecimal(0)) > 0)
        {
            parValue = productOperationHistory.getParValue().toString();
        }
        else
        {
            parValue = StringUtil.initString();
        }
//        List<AirtimeProduct> products = productPageQuery.fuzzyQueryAirtimeProductsByParams(
//            productOperationHistory.getProvince(), parValue,
//            productOperationHistory.getCarrierName(), productOperationHistory.getCity(), StringUtil.initString());
        productOperationHistory = productOperationHistoryDao.save(productOperationHistory);
        //保存任务规则
        List<ProductOperationRule> porList = new ArrayList<ProductOperationRule>();
        if (!productOperationHistory.getMerchantType().equalsIgnoreCase(Constant.Common.MERCHANTTYPE_ALL))
        {
            porList=productOperationRuleService.saveProductOperationRules(productOperationHistory, merchantIds, updateUser);
        }
//        for (AirtimeProduct product : products)
//        {
//            String status = StringUtil.initString();
//            if (productOperationHistory.getOperationFlag().equalsIgnoreCase(Constant.ProductOperationHistory.OPEN_STATUS))
//            {
//                status = Constant.SupplyProductStatus.CLOSE_STATUS;
//            }
//            else
//            {
//                status = Constant.SupplyProductStatus.OPEN_STATUS;
//            }
//            
//            if (productOperationHistory.getMerchantType().equalsIgnoreCase(Constant.Common.MERCHANTTYPE_AGENT))
//            {
//        		List<AgentProductRelation> dprs = new ArrayList<AgentProductRelation>();
//        		//循环获取所有选中商户对应的产品	
//            	for (ProductOperationRule productOperationRule : porList) {
//            		AgentProductRelation agentProductRelation=agentProductRelationService.queryAgentProductRelationByParams(product.getProductId(), productOperationRule.getMerchantId(), status);
//            		if(agentProductRelation!=null)
//            		{
//            			dprs.add(agentProductRelation);
//            		}
//				}
//                saveAgentProductOperationHistorys(productOperationHistory.getId(), dprs);
//                
//            }
//            else if (productOperationHistory.getMerchantType().equalsIgnoreCase(Constant.Common.MERCHANTTYPE_SUPPLY))
//            {
//            	//循环获取所有选中商户对应的产品	
//                List<SupplyProductRelation> uprs = new ArrayList<SupplyProductRelation>();
//                for (ProductOperationRule productOperationRule : porList) {
//	                SupplyProductRelation supplyProductRelation=supplyProductRelationService.querySupplyProductRelationByParams(product.getProductId(), productOperationRule.getMerchantId(), status);
//	                if(supplyProductRelation!=null)
//	                {
//	                	uprs.add(supplyProductRelation);
//	                }
//                }
//                saveSupplyProductOperationHistorys(productOperationHistory.getId(), uprs);
//            }
//            else if (productOperationHistory.getMerchantType().equalsIgnoreCase(Constant.Common.MERCHANTTYPE_ALL))
//            {
//                List<AgentProductRelation> dprs = agentProductRelationService.getAgentProductRelationByParams(product.getProductId(), status);
//                List<SupplyProductRelation> uprs = supplyProductRelationService.getSupplyProductRelationByParams(product.getProductId(), status);
//                saveAgentProductOperationHistorys(productOperationHistory.getId(), dprs);
//                saveSupplyProductOperationHistorys(productOperationHistory.getId(), uprs);
//            }
//        }
        return productOperationHistory;
    }

    public void saveAgentProductOperationHistorys(Long productOperationHistoryId,
                                                  List<AgentProductRelation> agentProductRelations)
    {
        for (AgentProductRelation agentProductRelation : agentProductRelations)
        {
            ProductOperationDetail poh = new ProductOperationDetail();
            poh.setMerchantType(MerchantType.AGENT.toString());
            poh.setProductOperationHistoryId(productOperationHistoryId);
            poh.setProductRelationId(agentProductRelation.getId());
            poh.setProductRelationStatus(agentProductRelation.getStatus());
            productOperationDetailService.saveProductOperationDetail(poh);
        }
    }

    public void saveSupplyProductOperationHistorys(Long productOperationHistoryId,
                                                   List<SupplyProductRelation> supplyProductRelations)
    {
        for (SupplyProductRelation supplyProductRelation : supplyProductRelations)
        {
            ProductOperationDetail poh = new ProductOperationDetail();
            poh.setMerchantType(MerchantType.SUPPLY.toString());
            poh.setProductOperationHistoryId(productOperationHistoryId);
            poh.setProductRelationId(supplyProductRelation.getId());
            poh.setProductRelationStatus(supplyProductRelation.getStatus());
            productOperationDetailService.saveProductOperationDetail(poh);
        }
    }

    @Override
    @Transactional
    public ProductOperationHistoryAssist doProductOperationHistory(ProductOperationHistory productOperationHistory)
    {
        ProductOperationHistoryAssist productOperationHistoryAssist=new ProductOperationHistoryAssist();

        String excepitonProductName="";

        String status = StringUtil.initString();
        status=productOperationHistory.getOperationFlag().equalsIgnoreCase(
            Constant.ProductOperationHistory.OPEN_STATUS)?Constant.AgentProductStatus.OPEN_STATUS:Constant.AgentProductStatus.CLOSE_STATUS;
        
        Long historyId=productOperationHistory.getId();
        List<ProductOperationRule> listRule=productOperationRuleService.queryProductOperationRuleByHisId(historyId);
        String carrierName=StringUtil.isNullOrEmpty(productOperationHistory.getCarrierName())?StringUtil.initString():productOperationHistory.getCarrierName();
        String province=StringUtil.isNullOrEmpty(productOperationHistory.getProvince())?StringUtil.initString():productOperationHistory.getProvince();
        String city=StringUtil.isNullOrEmpty(productOperationHistory.getCity())?StringUtil.initString():productOperationHistory.getCity();
        
        
        if (productOperationHistory.getMerchantType().equalsIgnoreCase(
            Constant.Common.MERCHANTTYPE_AGENT))
        {
            for (ProductOperationRule productOperationRule : listRule)
            {
                agentProductRelationService.updateStatusByParamAll(status, carrierName, province, city, IdentityType.MERCHANT.toString(), productOperationRule.getMerchantId(),Constant.BusinessType.BUSINESS_TYPE_HF);
            }
            
        }
        else if (productOperationHistory.getMerchantType().equalsIgnoreCase(
            Constant.Common.MERCHANTTYPE_SUPPLY))
        {
            for (ProductOperationRule productOperationRule : listRule)
            {
                supplyProductRelationService.updateStatusByParamAll(status, carrierName, province, city, IdentityType.MERCHANT.toString(), productOperationRule.getMerchantId(),Constant.BusinessType.BUSINESS_TYPE_HF);
            }
        }
        else if (productOperationHistory.getMerchantType().equalsIgnoreCase(
            Constant.Common.MERCHANTTYPE_ALL))
        {
            agentProductRelationService.updateStatusByParamAll(status, carrierName, province, city, IdentityType.MERCHANT.toString(), null,Constant.BusinessType.BUSINESS_TYPE_HF);
            supplyProductRelationService.updateStatusByParamAll(status,carrierName, province, city, IdentityType.MERCHANT.toString(), null,Constant.BusinessType.BUSINESS_TYPE_HF);
        }
        productOperationHistory.setStatus(Constant.ProductOperationHistory.STATUS_DONE);
        productOperationHistoryDao.save(productOperationHistory);
        productOperationHistoryAssist.setProductOperationHistory(productOperationHistory);
        productOperationHistoryAssist.setNoUpdateProductName(excepitonProductName);
        return productOperationHistoryAssist;
    }

    @Override
    public ProductOperationHistory refProductOperationHistory(ProductOperationHistory productOperationHistory)
    {
        List<ProductOperationDetail> downProductOperationDetails = productOperationDetailService.getProductOperationDetailList(productOperationHistory.getId(), MerchantType.AGENT.toString());

        for (ProductOperationDetail productOperationDetail : downProductOperationDetails)
        {
            agentProductRelationService.updateAgentProductRelationStatus(
                productOperationDetail.getProductRelationId(),
                productOperationDetail.getProductRelationStatus());
        }

        List<ProductOperationDetail> upProductOperationDetails = productOperationDetailService.getProductOperationDetailList(productOperationHistory.getId(), MerchantType.SUPPLY.toString());

        for (ProductOperationDetail productOperationDetail : upProductOperationDetails)
        {
            supplyProductRelationService.updateSupplyProductRelationStatus(
                productOperationDetail.getProductRelationId(),
                productOperationDetail.getProductRelationStatus());
        }

        productOperationHistory.setStatus(Constant.ProductOperationHistory.STATUS_CLOSE);
        productOperationHistoryDao.save(productOperationHistory);
        return productOperationHistory;
    }

    @Override
    public void deleteProductOperationHistory(Long id)
    {
        List<ProductOperationDetail> downProductOperationDetails = productOperationDetailService.getProductOperationDetailList(id, null);
        productOperationDetailService.deleteProductOperationDetails(downProductOperationDetails);
        productOperationHistoryDao.delete(id);
        productOperationRuleService.delProductOperationRule(id);
    }

    @Override
    public ProductOperationHistory queryProductOperationHistoryById(Long id)
    {
        ProductOperationHistory productOperationHistory = productOperationHistoryDao.findOne(id);
        return productOperationHistory;
    }
    
    @Override
    public ProductDiscountHistory buildProductDiscountHistory(ProductRelation productRelation, String productSide, BigDecimal oldValue, BigDecimal newValue, String operatorName, String action){
        ProductDiscountHistory pdh = new ProductDiscountHistory();
        String carrierName = StringUtil.initString();
        String city = StringUtil.initString();
        String identityId = StringUtil.initString();
        String identityName = StringUtil.initString();
        String parValue = StringUtil.initString();
        String productName = StringUtil.initString();
        String businessType = StringUtil.initString();
        String province = StringUtil.initString();
        if(Constant.Product.PRODUCT_TYPE_AGENT.equalsIgnoreCase(productSide)){
            AgentProductRelation agentProductRelation = (AgentProductRelation)productRelation;
            carrierName = agentProductRelation.getCarrierName();
            city = agentProductRelation.getCity();
            identityId = agentProductRelation.getIdentityId().toString();
            identityName = agentProductRelation.getIdentityName();
            parValue = agentProductRelation.getParValue().toString();
            productName = agentProductRelation.getProductName();
            businessType = agentProductRelation.getBusinessType().toString();
            province = agentProductRelation.getProvince();
        }
        else if (Constant.Product.PRODUCT_TYPE_SUPPLY.equalsIgnoreCase(productSide))
        {
            SupplyProductRelation supplyProductRelation = (SupplyProductRelation)productRelation;
            carrierName = supplyProductRelation.getCarrierName();
            city = supplyProductRelation.getCity();
            identityId = supplyProductRelation.getIdentityId().toString();
            identityName = supplyProductRelation.getIdentityName();
            parValue = supplyProductRelation.getParValue().toString();
            productName = supplyProductRelation.getProductName();
            businessType = supplyProductRelation.getBusinessType().toString();
            province = supplyProductRelation.getProvince();
        }
        else
        {
            return null;
        }
        pdh.setBusinessType(businessType);
        pdh.setCarrierName(carrierName);
        pdh.setProvince(province);
        pdh.setCity(city);
        pdh.setCreateDate(new Date());
        pdh.setIdentityId(identityId);
        pdh.setIdentityName(identityName);
        pdh.setAction(action);
        if(Constant.Product.PRODUCT_DISCOUNT_HISTORY_UPDATE.equalsIgnoreCase(action))
        {
            pdh.setNewValue(newValue);
            pdh.setOldValue(oldValue);
        }
        pdh.setOperatorName(operatorName);
        pdh.setParValue(parValue);
        pdh.setProductName(productName);
        pdh.setProductSide(productSide);
        return pdh;
    }

    @Override
    public ProductDiscountHistory saveProductDiscountHistory(ProductDiscountHistory productDiscountHistory)
    {
        productDiscountHistory = productDiscountHistoryDao.save(productDiscountHistory);
        return productDiscountHistory;
    }

    @Override
    public YcPage<ProductDiscountHistory> queryProductDiscountHistory(Map<String, Object> searchParams,int pageNumber,int pageSize,String sortType,Date beginDate)
    {
        Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
        filters.put("createDate", new SearchFilter(
            "createDate", Operator.GTE, beginDate));
        YcPage<ProductDiscountHistory> ycPage = PageUtil.queryYcPage(productDiscountHistoryDao,
            filters, pageNumber, pageSize, new Sort(Direction.DESC, sortType),
            ProductDiscountHistory.class);
        return ycPage;
    }
}
