package com.yuecheng.hops.gateway.application.protocal.resolver;


import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yuecheng.hops.gateway.application.protocal.ProtocalResolver;


@Service("applicationJsonResolver")
public class ApplicationJsonResolver implements ProtocalResolver
{

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationJsonResolver.class);

    public Map<String, Object> decode(String messageContent, String encoding)
    {
        try
        {
            messageContent = URLDecoder.decode(messageContent, encoding);
        }
        catch (UnsupportedEncodingException e)
        {
            LOGGER.error("URLDecoder.decode 报错 :" + e.getMessage());
        }

        Map<String, Object> result = new HashMap<String, Object>();
        try
        {
            Map<String, Object> map = new HashMap<String, Object>();
            Gson gson = new Gson();
            map = gson.fromJson(messageContent, new TypeToken<Map<String, Object>>()
            {
                private static final long serialVersionUID = 1L;
            }.getType());

            for (Map.Entry<String, Object> entry : map.entrySet())
            {
                List<Object[]> entryString = resolveMap(entry);
                for (Object[] keyValue : entryString)
                {
                    result.put(keyValue[0].toString(), keyValue[1]);
                }
            }
        }
        catch (Exception e)
        {
            LOGGER.error("JSON to Map decode 报错 :" + e.getMessage());
        }

        return result;
    }

    private static List<Object[]> resolveMap(Map.Entry<String, Object> entry)
    {
        List<Object[]> keyValuePair = new ArrayList<Object[]>();
        if (entry.getValue() instanceof Map)
        {
            Map<String, Object> temp = (Map<String, Object>)entry.getValue();
            for (Map.Entry<String, Object> entry2 : temp.entrySet())
            {
                keyValuePair.addAll(resolveMap(entry2));
            }
        }
        else
        {
            Object[] keyValue = new Object[2];
            keyValue[0] = entry.getKey();
            keyValue[1] = (Object)entry.getValue();
            keyValuePair.add(keyValue);
        }
        return keyValuePair;
    }

}
