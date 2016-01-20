package com.yuecheng.hops.common.event;


import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;


@Component("publisher")
public class HopsPublisher implements ApplicationContextAware
{
    private static ApplicationContext ctx;

    public void setApplicationContext(ApplicationContext applicationContext)
    {
        ctx = applicationContext;
    }

    public static void publishResponseEvent(HopsResponseEvent event)
    {
        ctx.publishEvent(event);
    }

    public static void publishRequestEvent(HopsRequestEvent event)
    {
        ctx.publishEvent(event);
    }

}
