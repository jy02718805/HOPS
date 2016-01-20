package com.yuecheng.hops.gateway.application.protocal.resolver;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Service;

import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.exception.ApplicationException;
import com.yuecheng.hops.common.utils.XmlUtils;
import com.yuecheng.hops.gateway.application.protocal.ProtocalResolver;


@Service("textXmlResolver")
public class TextXmlResolver implements ProtocalResolver
{
    private static final String SPLITE_CHAR = ".";

    @Override
    public Map<String, Object> decode(String messageContent, String encoding)
    {
        Map<String, Object> result = new HashMap<String, Object>();
        Map<String, Object> fields = new HashMap<String, Object>();
        Document doc = null;
        try
        {
            doc = DocumentHelper.parseText(messageContent);
            fields = XmlUtils.Dom2Map(doc);
        }
        catch (Exception e)
        {
            throw new ApplicationException(Constant.ErrorCode.PARAM_IS_ERROR);
        }
        for (Map.Entry<String, Object> entry : fields.entrySet())
        {
            List<String[]> entryString = resolveMap(entry);
            for (String[] keyValue : entryString)
            {
                result.put(keyValue[0], keyValue[1]);
            }
        }
      //遍历所有节点的属性值
        try{
        	Element root = doc.getRootElement();
            List<Element> elmlist = root.elements() ;
            Map<String,Object> map = foreachAtribute(elmlist);
            result.putAll(map);
        }
        catch(Exception e){
        	
        }
        return result;
    }
    
    private Map<String, Object> foreachAtribute(List<Element> elmlist)
    {
    	Map<String,Object> result = new HashMap<String,Object>();
         for (int i= 0;i<elmlist.size();i++)
         {
        	Element e = elmlist.get(i);
         	List<Attribute> list = e.attributes();
         	for (int j =0;j<list.size();j++)
         	{
         		Attribute a = list.get(j);
         		result.put(a.getName(), a.getValue());
         	}
         	List<Element> elmlist2 = e.elements();
         	if (elmlist2.size() > 0)
         	{
         		result.putAll(foreachAtribute(elmlist2));
         	}
         }
    	return result;
    }
   
    public List<String[]> resolveMap(Map.Entry<String, Object> entry)
    {
        List<String[]> keyValuePair = new ArrayList<String[]>();
        if (entry.getValue() instanceof Map)
        {
            Map<String, Object> temp = (Map<String, Object>)entry.getValue();
            for (Map.Entry<String, Object> entry2 : temp.entrySet())
            {
                List<String[]> keyValuePairTemp = resolveMap(entry2);
                for (String[] keyValuePairTempString : keyValuePairTemp)
                {
                    String[] keyValue = new String[2];
                    keyValue[0] = entry.getKey().concat(SPLITE_CHAR).concat(
                        keyValuePairTempString[0]);
                    keyValue[1] = keyValuePairTempString[1];
                    keyValuePair.add(keyValue);
                }
            }
        }
        else
        {
            String[] keyValue = new String[2];
            keyValue[0] = entry.getKey();
            keyValue[1] = entry.getValue().toString();
            keyValuePair.add(keyValue);
        }
        return keyValuePair;
    }
}
