package com.yuecheng.hops.gateway.application.protocal;


import java.util.Map;


public interface ProtocalResolver
{
    Map<String, Object> decode(String messageContent, String encoding);

}
