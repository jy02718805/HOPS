package com.yuecheng.hops.gateway.application.protocal.combiner;

import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.common.utils.StringUtil;
import com.yuecheng.hops.gateway.application.protocal.ProtocalCombiner;
import com.yuecheng.hops.injection.entity.InterfaceParam;

@Service("textPlainCombiner")
public class TextPlainCombiner implements ProtocalCombiner  {
	
    /**
     * fields 
     * {interfaceMerchantId=6351, sign=null, tsp=20141013155637, oid=634006, cid=test, key=N6L2N4JVBZ0JR8B026RN880Z6TDN468B}
     */
	@Override
	public String encoder(List<InterfaceParam> interfaceParams,Map<String, Object> fields, String encoding) throws Exception {
	    String packets = StringUtil.initString();
	    if(BeanUtils.isNotNull(interfaceParams) && interfaceParams.size() > 0){
	        packets = paramsToPlainText(interfaceParams,fields,encoding);
	    }
        //转码
        packets = new String(packets.getBytes(), encoding);
        return packets;
	}
	
	public String paramsToPlainText(List<InterfaceParam> interfaceParams,Map<String, Object> fields,String encoding) throws Exception{
        StringBuffer sb = new StringBuffer();
        String name = StringUtil.initString();
        Object value = StringUtil.initString();
        for(InterfaceParam interfaceParam : interfaceParams){
            if(interfaceParam.getInBody().equals(Constant.TrueOrFalse.TRUE))
            {
                name = interfaceParam.getOutParamName();
                
                if(interfaceParam.getParamType().equalsIgnoreCase(Constant.Interface.INTERFACE_PARAM_PARAMTYPE_CONSTANT))
                {
                    name = name.split("\\*")[0];
                }
                
                value = fields.get(name);
                try{
                	//对中文做转码处理
                	if (((String)value).length() != ((String)value).getBytes().length)
                	{
                		value = URLEncoder.encode(((String)value),encoding);
                	}
                }catch(Exception e){
                	
                }
                
                if(interfaceParam.getIsCapital()!=null && interfaceParam.getIsCapital().equalsIgnoreCase(Constant.Interface.INTERFACE_IS_CAPITAL_DOWN)){
                    value = value.toString().toLowerCase();
                }else if(interfaceParam.getIsCapital()!=null && interfaceParam.getIsCapital().equalsIgnoreCase(Constant.Interface.INTERFACE_IS_CAPITAL_UP)){
                    value = value.toString().toUpperCase();
                }
                sb.append(name + "=" + value + "&");
            }
        }
        String result = sb.toString();
        result = result.substring(0, sb.length() - 1);
        return result;
    }
}
