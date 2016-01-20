package com.yuecheng.hops.gateway.application.protocal.resolver;


import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.yuecheng.hops.gateway.application.protocal.ProtocalResolver;


@Service("textPlainResolver")
public class TextPlainResolver implements ProtocalResolver
{
    private static String SPLITE_AND = "&";

    private static String SPLITE_EQUAL = "=";

    @Override
    public Map<String, Object> decode(String messageContent, String encoding)
    {
        Map<String, Object> fields = new HashMap<String, Object>();
        try
        {
        	messageContent = URLDecoder.decode(messageContent,encoding);
            //messageContent = new String(messageContent.getBytes(encoding));
        }
        catch (UnsupportedEncodingException e)
        {
            
        }
        String[] tempFields = messageContent.split(SPLITE_AND);
        for (String tempfield : tempFields)
        {
            String[] fieldsValue = tempfield.split(SPLITE_EQUAL);
            if (fieldsValue.length == 2)
            {
                fields.put(fieldsValue[0], fieldsValue[1]);
            }
        }
        return fields;
    }
}
