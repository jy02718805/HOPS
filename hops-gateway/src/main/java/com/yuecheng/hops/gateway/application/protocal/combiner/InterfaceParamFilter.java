/*
 * 文件名：InterfaceParamFilter.java
 * 版权：Copyright by www.365haoyou.com
 * 描述：
 * 修改人：Administrator
 * 修改时间：2014年10月18日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.yuecheng.hops.gateway.application.protocal.combiner;

import java.util.ArrayList;
import java.util.List;

import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.injection.entity.InterfaceParam;

public class InterfaceParamFilter
{
    public static List<InterfaceParam> execute(List<InterfaceParam> interfaceParams){
        List<InterfaceParam> result = new ArrayList<InterfaceParam>();
        if(BeanUtils.isNotNull(interfaceParams)){
            for (InterfaceParam interfaceParam : interfaceParams)
            {
                if(interfaceParam.getInBody().equals(Constant.TrueOrFalse.TRUE))
                {
                    result.add(interfaceParam);
                }
            }
        }
        return result;
    }
}
