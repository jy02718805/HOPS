/*
 * 文件名：Verification.java 版权：Copyright by www.365haoyou.com 描述： 修改人：Administrator 修改时间：2014年10月12日
 * 跟踪单号： 修改单号： 修改内容：
 */

package com.yuecheng.hops.gateway.security.authentication;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.enump.IdentityType;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.common.utils.StringUtil;
import com.yuecheng.hops.gateway.GatewayConstant;
import com.yuecheng.hops.gateway.GatewayContextUtil;
import com.yuecheng.hops.identity.entity.merchant.Merchant;
import com.yuecheng.hops.identity.service.merchant.MerchantService;
import com.yuecheng.hops.security.service.SecurityCredentialService;

@Service("verification")
public class Verification
{
    private static Logger logger = LoggerFactory.getLogger(Verification.class);
    
    @Autowired 
    private SecurityCredentialService securityCredentialService;
    
    @Autowired 
    MerchantService merchantService;

    public Map<String, Object> signDecode(Map<String, Object> map, String merchantCode, String interfaceType)
        throws Exception
    {
        try
        {
            Object signValue = null;
            String securityCredentialTypeName = getSecurityCredentialType(interfaceType);
            Merchant merchant = null;
            if(BeanUtils.isNotNull(GatewayContextUtil.getParameter(GatewayConstant.INTERFACE_MERCHANT_ID))){
                Long merchantId = (Long)GatewayContextUtil.getParameter(GatewayConstant.INTERFACE_MERCHANT_ID);
                merchant = merchantService.queryMerchantById(merchantId);
            }else{
                merchant = merchantService.queryMerchantByMerchantCode(merchantCode);
            }
            Assert.notNull(merchant,"merchant not found!");
            signValue = securityCredentialService.querySecurityCredentialValueByParams(
            		merchant.getId(), IdentityType.MERCHANT,securityCredentialTypeName,Constant.SecurityCredentialStatus.ENABLE_STATUS);
            map.put(Constant.Common.KEY, signValue);
            return map;
        }
        catch (Exception e)
        {
            logger.error("failed to decode Sign casued by:" + ExceptionUtil.getStackTraceAsString(e));
            throw new Exception(Constant.ErrorCode.SIGN_ERROR);
        }
    }

    public Map<String, Object> signEncode(Map<String, Object> fieldsMap,String interfaceType,Map<String, Object> responseFields) throws Exception {
        try
        {
            String securityCredentialTypeName = getSecurityCredentialType(interfaceType);
            Long merchantId = (Long)responseFields.get(GatewayConstant.INTERFACE_MERCHANT_ID);
            if(BeanUtils.isNotNull(merchantId)){
            	String signValue = securityCredentialService.querySecurityCredentialValueByParams(
            			merchantId, IdentityType.MERCHANT,securityCredentialTypeName,Constant.SecurityCredentialStatus.ENABLE_STATUS);
                fieldsMap.put(Constant.Common.KEY, signValue);   
            }
            return fieldsMap;
        }
        catch (Exception e)
        {
            logger.error("failed to encode Sign casued by:" + ExceptionUtil.getStackTraceAsString(e));
            throw new Exception(Constant.ErrorCode.SIGN_ERROR);
        }
    }
    
    public static String getSecurityCredentialType(String interfaceType){
        String securityCredentialType = StringUtil.initString();
        if (interfaceType.equalsIgnoreCase(Constant.Interface.INTERFACE_TYPE_RECIEVER_ORDER)
            || interfaceType.equalsIgnoreCase(Constant.Interface.INTERFACE_TYPE_RECIEVER_TBORDER)
            || interfaceType.equalsIgnoreCase(Constant.Interface.INTERFACE_TYPE_AGENT_QUERY_ORDER)
            || interfaceType.equalsIgnoreCase(Constant.Interface.INTERFACE_TYPE_AGENT_QUERY_TBORDER)
            || interfaceType.equalsIgnoreCase(Constant.Interface.INTERFACE_TYPE_AGENT_NOTIFY_ORDER)
            || interfaceType.equalsIgnoreCase(Constant.Interface.INTERFACE_TYPE_AGENT_QUERY_ACCOUNT)
            || interfaceType.equalsIgnoreCase(Constant.Interface.INTERFACE_TYPE_AGENT_PRODUCT_STATUS)
            || interfaceType.equalsIgnoreCase(Constant.Interface.INTERFACE_TYPE_AGENT_NOTIFY_TBORDER)
            || interfaceType.equalsIgnoreCase(Constant.Interface.INTERFACE_TYPE_AGENT_QUERY_ORDER_PAIPAI)
            || interfaceType.equalsIgnoreCase(Constant.Interface.INTERFACE_TYPE_RECIEVER_ORDER_PAIPAI)
            || interfaceType.equalsIgnoreCase(Constant.Interface.INTERFACE_TYPE_AGENT_NOTIFY_ORDER_FAIL_PAIPAI)
            || interfaceType.equalsIgnoreCase(Constant.Interface.INTERFACE_TYPE_AGENT_NOTIFY_ORDER_SUCCESS_PAIPAI)
            || interfaceType.equalsIgnoreCase(Constant.Interface.INTERFACE_TYPE_SYN_PRODUCT_STATUS_TO_AGENT))
        {
            securityCredentialType = Constant.SecurityCredentialType.AGENTMD5KEY;
        }
        else
        {
            securityCredentialType = Constant.SecurityCredentialType.SUPPLYMD5KEY;
        }
        return securityCredentialType;
    }
}