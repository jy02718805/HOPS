package com.yuecheng.hops.gateway.application.protocal.combiner;


import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.protocol.HTTP;
import org.aspectj.weaver.patterns.ThisOrTargetAnnotationPointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.utils.StringUtil;
import com.yuecheng.hops.gateway.application.protocal.ProtocalCombiner;
import com.yuecheng.hops.injection.entity.InterfaceParam;


@Service("applicationJsonCombiner")
public class ApplicationJsonCombiner implements ProtocalCombiner
{

    private static Logger logger = LoggerFactory.getLogger(ApplicationJsonCombiner.class);

    @Override
    public String encoder(List<InterfaceParam> interfaceParams, Map<String, Object> fields,
                          String encoding)
        throws Exception
    {
        String name = StringUtil.initString();
        Object value = StringUtil.initString();
        Map<String, Object> fieldsToJosnMap = new HashMap<String, Object>();
        for (InterfaceParam interfaceParam : interfaceParams)
        {
            name = interfaceParam.getOutParamName();
            value = fields.get(name);

            if (name.indexOf(".") > -1)
            {
                Map<String, Object> temp = new HashMap<String, Object>();
                String[] names = name.split("\\.");
                if (fieldsToJosnMap.get(names[0]) instanceof Map)
                {
                    temp = (Map<String, Object>)fieldsToJosnMap.get(names[0]);
                }
                temp.put(names[1], value);
                fieldsToJosnMap.put(names[0], temp);

            }
            else
            {
                fieldsToJosnMap.put(name, value);
            }

        }

        Gson gson = new Gson();
        String jsonStr = gson.toJson(fieldsToJosnMap);
        jsonStr = new String(jsonStr.getBytes(), encoding);
        return jsonStr;
    }

}
