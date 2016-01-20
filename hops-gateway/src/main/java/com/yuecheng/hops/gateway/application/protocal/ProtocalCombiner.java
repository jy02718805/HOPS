package com.yuecheng.hops.gateway.application.protocal;

import java.util.List;
import java.util.Map;

import com.yuecheng.hops.injection.entity.InterfaceParam;


public interface ProtocalCombiner {    
    /**
     * Map(oid)-> responseStr
     * @param ipd
     * @param fields
     * @return
     * @throws Exception 
     * @see
     */
    String encoder(List<InterfaceParam> interfaceParams,Map<String,Object> fields,String encoding) throws Exception;
}
