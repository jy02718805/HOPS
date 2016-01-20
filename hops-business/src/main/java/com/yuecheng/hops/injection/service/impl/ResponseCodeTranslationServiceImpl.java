package com.yuecheng.hops.injection.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springside.modules.persistence.DynamicSpecifications;
import org.springside.modules.persistence.SearchFilter;
import org.springside.modules.persistence.SearchFilter.Operator;

import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.EntityConstant;
import com.yuecheng.hops.common.hopscache.HopsCacheUtil;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.common.utils.StringUtil;
import com.yuecheng.hops.injection.entity.ResponseCodeTranslation;
import com.yuecheng.hops.injection.repository.ResponseCodeTranslationDao;
import com.yuecheng.hops.injection.service.ResponseCodeTranslationService;

@Service("responseCodeTranslationService")
public class ResponseCodeTranslationServiceImpl implements ResponseCodeTranslationService {

	@Autowired
	private ResponseCodeTranslationDao responseCodeTranslationDao;
	
	@Override
	public Map<String,Object> translationMapToResponse(String interfaceType,Map<String,Object> response_fields){
		String errorCode = (String)response_fields.get(Constant.TransactionCode.ERROR_CODE);//Constant.ErrorCode.BALANCE_NOT_ENOUGH
		
		ResponseCodeTranslation responseCodeTranslation = (ResponseCodeTranslation)HopsCacheUtil.get(Constant.Common.BUSINESS_CACHE, Constant.CacheKey.RESPONSE_CODE_TRANSLATION + Constant.StringSplitUtil.ENCODE + interfaceType + Constant.StringSplitUtil.ENCODE + errorCode);
		
		if(BeanUtils.isNull(responseCodeTranslation))
		{
		    Map<String, SearchFilter> filters = new HashMap<String, SearchFilter>();
	        filters.put(EntityConstant.ResponseCodeTranslation.ERROR_CODE, new SearchFilter(EntityConstant.ResponseCodeTranslation.ERROR_CODE, Operator.EQ, errorCode));
	        filters.put(EntityConstant.ResponseCodeTranslation.INTERFACE_TYPE, new SearchFilter(EntityConstant.ResponseCodeTranslation.INTERFACE_TYPE, Operator.EQ, interfaceType));
	        Specification<ResponseCodeTranslation> spec = DynamicSpecifications.bySearchFilter(filters.values(), ResponseCodeTranslation.class);
	        responseCodeTranslation = responseCodeTranslationDao.findOne(spec);
	        HopsCacheUtil.put(Constant.Common.BUSINESS_CACHE, Constant.CacheKey.RESPONSE_CODE_TRANSLATION + Constant.StringSplitUtil.ENCODE + interfaceType + Constant.StringSplitUtil.ENCODE + errorCode, responseCodeTranslation);
		}
		if(responseCodeTranslation!=null){
			response_fields.put(EntityConstant.Order.RESULT, responseCodeTranslation.getCoopOrderStatus());
			if(StringUtil.isNotBlank(responseCodeTranslation.getFailedCode())){
			    response_fields.put(EntityConstant.Order.ERROR_CODE, responseCodeTranslation.getFailedCode());
			}else{
			    response_fields.put(EntityConstant.Order.ERROR_CODE, StringUtil.initString());
			}
			response_fields.put(Constant.TransactionCode.MSG, responseCodeTranslation.getMsg());
		}else{
			response_fields.put(EntityConstant.Order.RESULT, StringUtil.initString());
			response_fields.put(EntityConstant.Order.ERROR_CODE, StringUtil.initString());
			response_fields.put(Constant.TransactionCode.MSG, StringUtil.initString());
		}
		
		return response_fields;
	}
}
