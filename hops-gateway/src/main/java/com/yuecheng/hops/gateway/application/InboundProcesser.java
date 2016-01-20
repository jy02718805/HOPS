/*
 * 文件名：InboundProcesser.java 版权：Copyright by www.365haoyou.com 描述： 修改人：yangyi 修改时间：2014年10月14日
 * 跟踪单号： 修改单号： 修改内容：
 */

package com.yuecheng.hops.gateway.application;


import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.EntityConstant;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.exception.HopsException;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.gateway.GatewayConstant;
import com.yuecheng.hops.gateway.GatewayContextUtil;
import com.yuecheng.hops.gateway.application.protocal.ProtocalResolver;
import com.yuecheng.hops.gateway.application.protocal.selector.ProtocalResolverSelector;
import com.yuecheng.hops.gateway.application.tranfer.BeanDecoderTransfer;
import com.yuecheng.hops.gateway.security.authentication.Verification;
import com.yuecheng.hops.gateway.security.authentication.VerificationComponent;
import com.yuecheng.hops.injection.entity.InterfacePacketTypeConf;
import com.yuecheng.hops.injection.entity.InterfacePacketsDefinitionBo;
import com.yuecheng.hops.injection.entity.InterfaceParam;

@Service("inboundProcesser")
public class InboundProcesser
{
    private static Logger logger = LoggerFactory.getLogger(InboundProcesser.class);
    
    @Autowired
    private ProtocalResolverSelector protocalResolverSelector;
    
    @Autowired
    private BeanDecoderTransfer beanDecoderTransfer;
    
    @Autowired
    private Verification verification;
    
    private static final Set<String> interfacePacketsDefinitionSet = new HashSet<String>();
    
    static{
        interfacePacketsDefinitionSet.add(Constant.Interface.INTERFACE_TYPE_RECIEVER_ORDER);
        interfacePacketsDefinitionSet.add(Constant.Interface.INTERFACE_TYPE_RECIEVER_TBORDER);
        interfacePacketsDefinitionSet.add(Constant.Interface.INTERFACE_TYPE_AGENT_QUERY_ORDER);
        interfacePacketsDefinitionSet.add(Constant.Interface.INTERFACE_TYPE_AGENT_QUERY_TBORDER);
        interfacePacketsDefinitionSet.add(Constant.Interface.INTERFACE_TYPE_AGENT_PRODUCT_STATUS);
    }
    
    /**
     * 外部返回报文处理
     * @param packets  外部报文
     * @param direction  request(系统是服务端)   response(系统是客户端)
     * @return
     * @throws Exception 
     * @see
     */
    public Map<String,Object> resoleMessage(String packets,String direction)
        throws Exception
    {
        try
        {
            InterfacePacketsDefinitionBo ipd = (InterfacePacketsDefinitionBo)GatewayContextUtil.getParameter(GatewayConstant.INTERFACE_PACKETS_DEFINITION);
            List<InterfaceParam> interfaceParams = null;
            InterfacePacketTypeConf packetTypeConf = null;
            if(Constant.Interface.INTERFACE_CONNECTION_MODULE_REQUEST.equalsIgnoreCase(direction))
            {
                interfaceParams = ipd.getRequestParams();
                packetTypeConf = ipd.getRequestInterfacePacketTypeConf();
            }
            else
            {
                interfaceParams = ipd.getResponseSuccessParams();
                packetTypeConf = ipd.getResponseInterfacePacketTypeConf();
            }
            
            ProtocalResolver messageDecoder = protocalResolverSelector.select(packetTypeConf.getPacketType());
            //2.request -> Map   oid=xxx      sign=下游加密待验证
            Map<String,Object> packetMap = messageDecoder.decode(packets, ipd.getEncoding());
            logger.debug("request转换Map对象 request_fields=["+packetMap+"]");
            //3.Map translate oid-->OrderNo !!!!password
            Map<String,Object> orderMap = beanDecoderTransfer.decode(packetMap, interfaceParams, ipd.getMerchantId());
            logger.debug("Map翻译成orderMap orderMap=["+orderMap+"]");
            
            String merchantCode = (String)orderMap.get(EntityConstant.Merchant.MERCHANT_CODE);
            if(BeanUtils.isNull(merchantCode)){
                merchantCode = (String)GatewayContextUtil.getParameter(EntityConstant.Merchant.MERCHANT_CODE);
            }
            else
            {
                GatewayContextUtil.setParameter(EntityConstant.Merchant.MERCHANT_CODE, merchantCode);
            }
            logger.debug("merchantCode=["+merchantCode+"]");
            //4.将需要加密的字段 加密 Map -> Map oid=xxx key = 密钥   sign=下游加密待验证
            Map<String,Object> keySignMap = verification.signDecode(packetMap, merchantCode,ipd.getInterfaceType());
            //5.验证 sign 对应表达式 测试屏蔽，上生产需要打开!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            VerificationComponent.verification(interfaceParams, keySignMap, packets,ipd.getEncoding());
            GatewayContextUtil.setParameter("encoding",ipd.getEncoding());
            GatewayContextUtil.setParameter("packetType",packetTypeConf.getPacketType());
            return orderMap;
        }
        catch (HopsException e)
        {
            logger.error("failed to resolver the recieved message!"+ExceptionUtil.getStackTraceAsString(e));
            throw e;
        }
        catch (Exception e)
        {
            logger.error("failed to resolver the recieved message!"+ExceptionUtil.getStackTraceAsString(e));
            throw e;
        }
    }
}