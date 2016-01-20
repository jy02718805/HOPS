package com.yuecheng.hops.gateway.application.protocal.selector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.gateway.application.protocal.ProtocalResolver;

@Service("protocalResolverSelector")
public class ProtocalResolverSelectorImpl implements ProtocalResolverSelector{
	
	@Autowired@Qualifier("textPlainResolver")
	private ProtocalResolver textPlainResolver;
	
	@Autowired@Qualifier("textXmlResolver")
	private ProtocalResolver textXmlResolver;
	
	@Autowired@Qualifier("applicationJsonResolver")
    private ProtocalResolver applicationJsonResolver;
	@Override
	public ProtocalResolver select(String packetType) {
		ProtocalResolver messageDecoder = null;
		if(packetType.equalsIgnoreCase(Constant.Interface.PACKET_TYPE_XML)){
			messageDecoder = textXmlResolver;
		}else if(packetType.equalsIgnoreCase(Constant.Interface.PACKET_TYPE_TEXT)){
			messageDecoder = textPlainResolver;
		}else if(packetType.equalsIgnoreCase(Constant.Interface.PACKET_TYPE_JSON)){
            messageDecoder = applicationJsonResolver;
        }
		
		return messageDecoder;
	}
}
