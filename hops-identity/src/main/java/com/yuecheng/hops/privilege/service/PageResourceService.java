package com.yuecheng.hops.privilege.service;


/**
 * 页面资源权限表逻辑访问层
 * 
 * @author：Jinger
 * @date：2013-09-17
 */
import java.util.List;
import java.util.Map;

import com.yuecheng.hops.common.query.BSort;
import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.privilege.entity.PageResource;


public interface PageResourceService
{
    /**
     * 保存页面资源
     * 
     * @param pageResource
     * @return
     */
    public PageResource savePageResource(PageResource pageResource);

    /**
     * 根据ID删除页面资源
     * 
     * @param resourceId
     */
    public void deletePageResource(Long resourceId);

    /**
     * 根据ID查找页面资源
     * 
     * @param resourceId
     * @return
     */
    public PageResource queryPageResourceById(Long resourceId);

    /**
     * 根据页面路径查找页面资源
     * 
     * @param pageUrl
     * @return
     */
    public PageResource queryPageResourceByUrl(String pageUrl);

    /**
     * 获取所有的页面资源
     * 
     * @return
     */
    public List<PageResource> queryAllPageResource();

    /**
     * 获取所有未使用的页面资源
     * 
     * @return
     */
    public List<PageResource> queryPageResourceByStatus(String status);

    /**
     * 分页查询页面资源
     * 
     * @param searchParams
     * @param pageNumber
     * @param pageSize
     * @param sort
     * @return
     */
    public YcPage<PageResource> queryPageResource(Map<String, Object> searchParams,
                                                  int pageNumber, int pageSize, BSort sort);
}
