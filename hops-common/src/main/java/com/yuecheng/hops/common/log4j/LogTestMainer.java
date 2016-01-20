package com.yuecheng.hops.common.log4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogTestMainer
{
	private static final Logger logger = LoggerFactory.getLogger(LogTestMainer.class);
    
    @LogAnnotation(servicename = "LogTestMainer", methodname = "executeMainLogic", description = " here is description")
    public void executeMainLogic()
    {
    	logger.info("This is in main Login!");
    }
}
