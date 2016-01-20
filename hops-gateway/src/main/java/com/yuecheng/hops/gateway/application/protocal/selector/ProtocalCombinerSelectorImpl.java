package com.yuecheng.hops.gateway.application.protocal.selector;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.gateway.application.protocal.ProtocalCombiner;


@Service("protocalCombinerSelector")
public class ProtocalCombinerSelectorImpl implements ProtocalCombinerSelector
{

    @Autowired
    @Qualifier("textXmlCombiner")
    private ProtocalCombiner textXmlCombiner;

    @Autowired
    @Qualifier("textPlainCombiner")
    private ProtocalCombiner textPlainCombiner;

    @Autowired
    @Qualifier("applicationJsonCombiner")
    private ProtocalCombiner applicationJsonCombiner;

    @Override
    public ProtocalCombiner select(String packetType)
    {
        ProtocalCombiner protocalCombiner = null;
        if (packetType.equalsIgnoreCase(Constant.Interface.PACKET_TYPE_XML))
        {
            protocalCombiner = textXmlCombiner;
        }
        else if (packetType.equalsIgnoreCase(Constant.Interface.PACKET_TYPE_TEXT)
                 || packetType.equalsIgnoreCase(Constant.Interface.PACKET_TYPE_FORM))
        {
            protocalCombiner = textPlainCombiner;
        }
        else if (packetType.equalsIgnoreCase(Constant.Interface.PACKET_TYPE_JSON))
        {
            protocalCombiner = applicationJsonCombiner;
        }
        return protocalCombiner;
    }
}
