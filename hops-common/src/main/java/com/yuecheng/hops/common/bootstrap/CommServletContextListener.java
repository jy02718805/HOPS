package com.yuecheng.hops.common.bootstrap;


import java.util.Map;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import net.sf.ehcache.CacheManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.common.utils.SpringUtils;


public class CommServletContextListener implements ServletContextListener
{
    private static final Logger LOGGER = LoggerFactory.getLogger(CommServletContextListener.class);

    public void startup()
    {
        
    }
    
    public void contextInitialized(ServletContextEvent event)
    {
        try
        {
            LOGGER.info("================================================");
            LOGGER.info("=                   _ooOoo_                    =");
            LOGGER.info("=                  o8888888o                   =");
            LOGGER.info("=                  88\" . \"88                   =");
            LOGGER.info("=                  (| -_- |)                   =");
            LOGGER.info("=                  O\\  =  /O                   =");
            LOGGER.info("=               ____/`---'\\____                =");
            LOGGER.info("=             .'  \\\\|     |//  `.              =");
            LOGGER.info("=            /  \\\\|||  :  |||//  \\             =");
            LOGGER.info("=           /  _||||| -:- |||||-  \\            =");
            LOGGER.info("=           |   | \\\\\\  -  /// |   |            =");
            LOGGER.info("=           | \\_|  ''\\---/''  |   |            =");
            LOGGER.info("=           \\  .-\\__  `-`  ___/-. /            =");
            LOGGER.info("=         ___`. .'  /--.--\\  `. . __           =");
            LOGGER.info("=      .\"\" '<  `.___\\_<|>_/___.'  >'\"\".        =");
            LOGGER.info("=     | | :  `- \\`.;`\\ _ /`;.`/ - ` : | |      =");
            LOGGER.info("=     \\  \\ `-.   \\_ __\\ /__ _/   .-` /  /      =");
            LOGGER.info("=======`-.____`-.___\\_____/___.-`____.-'========");
            LOGGER.info("=                   `=---='                    =");
            LOGGER.info("=^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^ =");
            LOGGER.info("=              佛祖保佑       永无BUG                   =");
            LOGGER.info("=              老板发财       员工幸福                                                                    =");
            LOGGER.info("================================================");
            LOGGER.info("=                    The application [" + event.getServletContext().getServletContextName() + "] begining startup!       =");
            WebApplicationContext springContext = WebApplicationContextUtils.getWebApplicationContext(event.getServletContext());
            if(BeanUtils.isNull(SpringUtils.getApplicationContext()))
            {
                new SpringUtils().setApplicationContext(springContext);
            }
            if(BeanUtils.isNotNull(springContext)){
                Map<String, HopsBootstrap>  hopsBootstraps = springContext.getBeansOfType(HopsBootstrap.class);
                for (String key : hopsBootstraps.keySet())
                {
                    HopsBootstrap bootstrap = hopsBootstraps.get(key);
                    bootstrap.startup();
                    LOGGER.info("=       "+bootstrap.getClass().getName()+" has successfully executed!         =");
                }
            }

            
            LOGGER.info("=                    The application ["+ event.getServletContext().getServletContextName() + "] has started up successfully!=");
            LOGGER.info("====================================================================================================");
        }
        catch (Exception e)
        {
            LOGGER.error("The application ["
                         + event.getServletContext().getServletContextName()
                         + "] failed to start! caused by:" + ExceptionUtil.getStackTraceAsString(e));
            LOGGER.info("====================================================================================================");
        }
    }
    
    public static void main(String[] args)
    {
        System.out.println("================================================");
        System.out.println("=                   _ooOoo_                    =");
        System.out.println("=                  o8888888o                   =");
        System.out.println("=                  88\" . \"88                   =");
        System.out.println("=                  (| -_- |)                   =");
        System.out.println("=                  O\\  =  /O                   =");
        System.out.println("=               ____/`---'\\____                =");
        System.out.println("=             .'  \\\\|     |//  `.              =");
        System.out.println("=            /  \\\\|||  :  |||//  \\             =");
        System.out.println("=           /  _||||| -:- |||||-  \\            =");
        System.out.println("=           |   | \\\\\\  -  /// |   |            =");
        System.out.println("=           | \\_|  ''\\---/''  |   |            =");
        System.out.println("=           \\  .-\\__  `-`  ___/-. /            =");
        System.out.println("=         ___`. .'  /--.--\\  `. . __           =");
        System.out.println("=      .\"\" '<  `.___\\_<|>_/___.'  >'\"\".        =");
        System.out.println("=     | | :  `- \\`.;`\\ _ /`;.`/ - ` : | |      =");
        System.out.println("=     \\  \\ `-.   \\_ __\\ /__ _/   .-` /  /      =");
        System.out.println("=======`-.____`-.___\\_____/___.-`____.-'========");
        System.out.println("=                   `=---='                    =");
        System.out.println("=^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^ =");
        System.out.println("=              佛祖保佑       永无BUG                   =");
        System.out.println("=              老板发财       员工幸福                                                                    =");
        System.out.println("================================================");
    }
    
    public void contextDestroyed(ServletContextEvent event)
    {
        try
        {
            CacheManager.getInstance().shutdown();
                LOGGER.info("The application ["
                            + event.getServletContext().getServletContextName()
                            + "] has been shutted down successfully!");
        }
        catch (Exception e)
        {
                LOGGER.error("The application ["
                             + event.getServletContext().getServletContextName()
                             + "] failed to shut down smoothly!");
        }

    }
}
