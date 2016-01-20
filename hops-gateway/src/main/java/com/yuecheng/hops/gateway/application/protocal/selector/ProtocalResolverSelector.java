package com.yuecheng.hops.gateway.application.protocal.selector;

import com.yuecheng.hops.gateway.application.protocal.ProtocalResolver;

public interface ProtocalResolverSelector {
	public ProtocalResolver select(String packetType);
}
