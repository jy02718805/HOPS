package com.yuecheng.hops.security.repository;


import java.util.Map;

import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.security.entity.vo.SecurityCredentialVo;


/**
 * @author：Jinger
 * @date：2013-09-26
 */

public interface SecurityCredentialDao
{
    YcPage<SecurityCredentialVo> queryPageSecurityCredential(Map<String, Object> searchParams,
                                                             int pageNumber, int pageSize);

}
