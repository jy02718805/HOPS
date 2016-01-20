package com.yuecheng.hops.gateway.communication.netty;

import org.jboss.netty.handler.codec.frame.DelimiterBasedFrameDecoder;
import org.jboss.netty.handler.codec.frame.Delimiters;

public class LineDelimiterBasedFrameDecoder extends DelimiterBasedFrameDecoder {
	public LineDelimiterBasedFrameDecoder(int maxFrameLength) {
		super(maxFrameLength,Delimiters.lineDelimiter());
	}
	
	public LineDelimiterBasedFrameDecoder()
	{
		super(8192,Delimiters.lineDelimiter());
	}
}
