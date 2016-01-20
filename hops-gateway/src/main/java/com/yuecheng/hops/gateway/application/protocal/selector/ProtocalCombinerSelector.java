package com.yuecheng.hops.gateway.application.protocal.selector;

import com.yuecheng.hops.gateway.application.protocal.ProtocalCombiner;

public interface ProtocalCombinerSelector {
	public ProtocalCombiner select(String packetType);
}
