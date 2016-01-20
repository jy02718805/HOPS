package com.yuecheng.hops.common.event;


import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;


@Component("hopsResponseListener")
public class HopsResponseListener implements ApplicationListener<HopsResponseEvent>
{

    @Override
    public void onApplicationEvent(HopsResponseEvent event)
    {
        HopsResponseContext context = event.getHopsResponseContext();
    }

}
