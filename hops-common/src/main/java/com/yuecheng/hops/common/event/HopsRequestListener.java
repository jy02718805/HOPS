package com.yuecheng.hops.common.event;


import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;


@Component("hopsRequestListener")
public class HopsRequestListener implements ApplicationListener<HopsRequestEvent>
{

    @Override
    public void onApplicationEvent(HopsRequestEvent event)
    {
        HopsRequestContext context = event.getHopsRequestContext();
    }

}
