package com.yuecheng.hops.identity.service.merchant;


import java.util.Map;

import com.yuecheng.hops.common.query.BSort;
import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.identity.entity.mirror.Organization;


public interface OrganizationService
{

    /**
     * 新增组织机构
     * 
     * @param organization
     *            组织机构实体
     * @return
     */
    public Organization saveOrganization(Organization organization);

    /**
     * 根据ID查询组织机构详细信息
     * 
     * @param identityId
     *            组织机构Identity ID
     * @return
     */
    public Organization queryOrganizationById(Long organizationId);

    /**
     * 根据编号查询组织机构详细信息
     * 
     * @param organizationNo
     *            组织机构编号
     * @return
     */
    public Organization queryOrganizationByNo(String organizationNo);

    /**
     * 根据名称查询组织机构详细信息
     * 
     * @param organizationName
     *            组织机构名称
     * @return
     */
    public Organization queryOrganizationByName(String organizationName);

    /**
     * 获取组织机构列表
     * 
     * @param searchParams
     *            查询条件
     * @param pageNumber
     *            当前页码
     * @param pageSize
     *            每页条数
     * @param sortType
     *            排序字段
     * @return
     */
    public YcPage<Organization> queryOrganization(Map<String, Object> searchParams,
                                                  int pageNumber, int pageSize, BSort sort);

}
