/*
 * 文件名：InterfaceParamService.java
 * 版权：Copyright by www.365haoyou.com
 * 描述：
 * 修改人：Administrator
 * 修改时间：2014年12月13日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.yuecheng.hops.injection.service;

import java.util.List;

import com.yuecheng.hops.injection.entity.InterfaceParam;

public interface InterfaceParamService
{
    public List<InterfaceParam> getInterfaceParamByParams(Long interfaceDefinitionId, String connectionModule, String responseResult);

    public List<InterfaceParam> saveAll(Long definitionId, String requestOrResponse, List<InterfaceParam> interfaceParams);
    
    public void deleteAll(Long definitionId, String requestOrResponse, List<InterfaceParam> interfaceParams);
}
