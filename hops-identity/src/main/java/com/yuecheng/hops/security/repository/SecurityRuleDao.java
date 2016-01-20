/**
 * @Title: SecurityRuleDao.java
 * @Package com.yuecheng.hops.identity.repository
 * @Description: TODO Copyright: Copyright (c) 2011 Company:湖南跃程网络科技有限公司
 * @author 肖进
 * @date 2014年9月1日 下午3:01:10
 * @version V1.0
 */

package com.yuecheng.hops.security.repository;


import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.security.entity.SecurityCredentialRule;


/**
 * @ClassName: SecurityRuleDao
 * @Description: TODO
 * @author 肖进
 * @date 2014年9月1日 下午3:01:10
 */

public interface SecurityRuleDao
{

    /**
     * 查询-分页
     * 
     * @param securityrulename
     * @param letter
     * @param figure
     * @param specialcharacter
     * @param status
     * @param pageNumber
     * @param pageSize
     * @return
     */
    public YcPage<SecurityCredentialRule> querySecurityRuleList(String securityrulename,
                                                                String letter, String figure,
                                                                String specialcharacter,
                                                                Long status, int pageNumber,
                                                                int pageSize);
}
