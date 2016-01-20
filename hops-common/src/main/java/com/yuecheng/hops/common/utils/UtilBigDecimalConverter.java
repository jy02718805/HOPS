package com.yuecheng.hops.common.utils;

import java.math.BigDecimal;

import org.apache.commons.beanutils.Converter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * beanUtil转换BigDecimal类型帮助类
 */
public class UtilBigDecimalConverter implements Converter {
    /*
     * (non-Javadoc)
     * 
     * @see org.apache.commons.beanutils.Converter#convert(java.lang.Class,
     * java.lang.Object)
     */

	private static final Logger logger = LoggerFactory.getLogger(UtilBigDecimalConverter.class);
    @Override
    public Object convert(Class arg0, Object arg1) {

        Object obj = null;
        if (java.math.BigDecimal.class == arg0) {
            if (arg1 != null) {
                try {
                    obj = new BigDecimal(arg1.toString());
                } catch (Exception e) {
                	logger.error("UtilBigDecimalConverter:[convert]["+e.getMessage()+"]");
                }
            }
        }

        return obj;
    }

}
