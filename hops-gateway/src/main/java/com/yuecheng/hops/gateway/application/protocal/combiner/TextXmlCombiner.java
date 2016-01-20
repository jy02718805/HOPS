package com.yuecheng.hops.gateway.application.protocal.combiner;

import java.util.List;
import java.util.Map;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.common.utils.StringUtil;
import com.yuecheng.hops.gateway.application.protocal.ProtocalCombiner;
import com.yuecheng.hops.injection.entity.InterfaceParam;

@Service("textXmlCombiner")
public class TextXmlCombiner implements ProtocalCombiner  {
	
    private static Logger logger = LoggerFactory.getLogger(TextXmlCombiner.class);
    
    public String printList(List objs)
    {
        StringBuffer sb = new StringBuffer();
        for(Object obj : objs)
        {
            sb.append(String.valueOf(obj).toString());
        }
        return sb.toString();
    }
    
	@Override
	public String encoder(List<InterfaceParam> interfaceParams,Map<String, Object> fields, String encoding) throws Exception{
	    logger.info("TextXmlCombiner interfaceParams["+ printList(interfaceParams) +"]");
        Document document = DocumentHelper.createDocument();
        document.setXMLEncoding(encoding);
        Element rootElement = null;
        Element curentElement = null;
        if(BeanUtils.isNotNull(interfaceParams)){
        
            for(InterfaceParam interfaceParam : interfaceParams){
                String nodeString =interfaceParam.getOutParamName();
                String[] nodeStrings = nodeString.split("\\*");
                nodeString = nodeStrings[0];
                if(interfaceParam.getParamType().equalsIgnoreCase(Constant.Interface.INTERFACE_PARAM_PARAMTYPE_CONSTANT))
                {
                    nodeString = nodeString.split("_")[0];
                }
                
                if(nodeString.indexOf(".") < 0){
                    rootElement = document.addElement(nodeString);
                    curentElement = rootElement;
                }else if(interfaceParam.getInBody().equals(Constant.TrueOrFalse.TRUE)){
                    String[] nodes_str = nodeString.split("\\.");
                    
                    for (int j = 0; j < nodes_str.length; j++) {
                        String key=nodes_str[j];
                        @SuppressWarnings("unchecked")
                        List<Node> nodes = document.selectNodes("//"+key);
                        if(nodes.size() > 1)
                        {
                            break;
                        }
                        else if(nodes.size() == 1)
                        {
                            curentElement = (Element)nodes.get(0);
                            String[] keys = key.split("\\|");
                            if (keys.length > 1)
                            {
                            	curentElement.addAttribute(keys[1], String.valueOf(fields.get(nodeString)));
                            }
                        }
                        else
                        {
                            if(rootElement == null){
                                rootElement=document.addElement(key);
                                curentElement = rootElement;
                                if(j == nodes_str.length-1){
                                    curentElement.setText(valueResolver(fields,interfaceParam,nodeString));
                                }
                            }else{
                                if(curentElement == null){
                                    curentElement = rootElement;
                                    if(j == nodes_str.length-1){
                                        curentElement.setText(valueResolver(fields,interfaceParam,nodeString));
                                    }
                                }else{
                                	String[] keys = key.split("\\|");
                                	if (keys.length <= 1)
                                	{
                                		curentElement = curentElement.addElement(key);
                                        if(j == nodes_str.length-1){
                                            curentElement.setText(valueResolver(fields,interfaceParam,nodeString));
                                        }
                                	}
                                	else
                                	{
                                		curentElement = curentElement.addElement(keys[0]);
                                		curentElement.addAttribute(keys[1],String.valueOf(fields.get(nodeString)) );
                                	}
                                }
                            }
                        }
                        
                    }
                }
            }
        
        }
        return document.asXML();
    }
    
	/**
	 * map取值，根据参数类型，转换成对应值
	 * @param map
	 * @param interfaceParam
	 * @param key
	 * @return
	 * @throws Exception 
	 * @see
	 */
    public String valueResolver(Map<String,Object> map,InterfaceParam interfaceParam,String key) throws Exception {
        String value = StringUtil.initString();
        
        if(BeanUtils.isNull(map.get(key))){
            value = StringUtil.initString();
        }
        else
        {
            value = map.get(key).toString();
        }
        
        if(interfaceParam.getIsCapital()!=null && interfaceParam.getIsCapital().equalsIgnoreCase(Constant.Interface.INTERFACE_IS_CAPITAL_DOWN)){
            value = value.toString().toLowerCase();
        }else if(interfaceParam.getIsCapital()!=null && interfaceParam.getIsCapital().equalsIgnoreCase(Constant.Interface.INTERFACE_IS_CAPITAL_UP)){
            value = value.toString().toUpperCase();
        }
        return value;
    }
}
