package com.yuecheng.hops.privilege.service.impl;


import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.persistence.SearchFilter;

import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.exception.ApplicationException;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.query.BSort;
import com.yuecheng.hops.common.query.PageUtil;
import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.common.utils.Collections3;
import com.yuecheng.hops.privilege.entity.Menu;
import com.yuecheng.hops.privilege.entity.PageResource;
import com.yuecheng.hops.privilege.entity.RoleMenu;
import com.yuecheng.hops.privilege.repository.PageResourceDao;
import com.yuecheng.hops.privilege.service.MenuService;
import com.yuecheng.hops.privilege.service.PageResourceService;
import com.yuecheng.hops.privilege.service.RoleMenuQueryService;
import com.yuecheng.hops.privilege.service.RoleMenuService;


/**
 * 页面资源权限表逻辑访问层
 * 
 * @author：Jinger
 * @date：2013-09-17
 */

@Service("pageService")
public class PageResourceServiceImpl implements PageResourceService
{
    @Autowired
    private PageResourceDao pageResourceDao;

    @Autowired
    private RoleMenuService roleMenuService;
    @Autowired
    private RoleMenuQueryService roleMenuQueryService;
    @Autowired
    private MenuService menuService;

    private static Logger logger = LoggerFactory.getLogger(PageResourceServiceImpl.class);

    /**
     * 添加页面资源
     */
    @Override
    @Transactional
    public PageResource savePageResource(PageResource pageResource)
    {
        logger.debug("[PageResourceServiceImpl:savePageResource(" + (BeanUtils.isNotNull(pageResource) ? pageResource.toString() :"")
                                                                                                                     + ")]");
        if (BeanUtils.isNotNull(pageResource))
        {
            pageResource = pageResourceDao.save(pageResource);
        }
        logger.debug("[PageResourceServiceImpl:savePageResource(" + (BeanUtils.isNotNull(pageResource) ? pageResource.toString() :"")
                                                                                                                     + ")][返回信息]");
        return pageResource;
    }

    /**
     * 删除页面资源
     */
    @Override
    @Transactional
    public void deletePageResource(Long pageResourceId)
    {
        logger.debug("[PageResourceServiceImpl:deletePageResource(" + pageResourceId + ")]");
        if (null != pageResourceId)
        {
        	Menu menu=menuService.queryMenuByPageId(pageResourceId);
            if(BeanUtils.isNotNull(menu)&&BeanUtils.isNotNull(menu.getMenuId()))
            {
	            // 先删除角色权限表中的数据再删除菜单
            	Long menuId=menu.getMenuId();
            	List<RoleMenu> roleMenuList=roleMenuQueryService.queryRoleMenuByMenuId(menuId);
            	if(BeanUtils.isNotNull(roleMenuList)&&roleMenuList.size()>0)
            	{
            		roleMenuService.deleteRoleMenuList(roleMenuList);
            	}
	            menuService.deleteMenu(menuId);
            }
            pageResourceDao.delete(pageResourceId);
        }
    }

    /**
     * 查询页面资源信息
     */
    @Override
    public PageResource queryPageResourceById(Long resourceId)
    {
        logger.debug("[PageResourceServiceImpl:queryPageResourceById(" + resourceId + ")]");
        if (null != resourceId)
        {
            PageResource pageResource = pageResourceDao.findOne(resourceId);
            logger.debug("[PageResourceServiceImpl:queryPageResourceById(" + (BeanUtils.isNotNull(pageResource) ? pageResource.toString() :"")
                                                                                                                              + ")][返回信息]");
            return pageResource;
        }
        else
        {
            logger.error("[PageResourceServiceImpl:queryPageResourceById(页面资源ID为空)]");
            String[] msgParams = new String[] {"queryPageResourceById"};
            ApplicationException ae = new ApplicationException("identity001062", msgParams);
            throw ExceptionUtil.throwException(ae);
        }
    }

    /**
     * 查询页面资源信息列表
     */
    @Override
    public List<PageResource> queryAllPageResource()
    {
        List<PageResource> pageResourceList = pageResourceDao.selectAll();
        logger.debug("[PageResourceServiceImpl:queryAllPageResource(" + (BeanUtils.isNotNull(pageResourceList) ? Collections3.convertToString(
            pageResourceList, Constant.Common.SEPARATOR) :"") + ")][返回信息]");
        return pageResourceList;
    }

    /**
     * 分页查询
     * 
     * @param searchParams
     * @param pageNumber
     * @param pageSize
     * @param sortType
     * @return
     */
    @Override
    public YcPage<PageResource> queryPageResource(Map<String, Object> searchParams,
                                                  int pageNumber, int pageSize, BSort bsort)
    {
        logger.debug("[PageResourceServiceImpl:queryPageResource(" + (BeanUtils.isNotNull(searchParams) ? searchParams.toString() :"")
                                                                                                                      + ","
                                                                                                                      + pageNumber
                                                                                                                      + ","
                                                                                                                      + pageSize
                                                                                                                      + ")]");
        Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
        String orderCloumn = bsort == null ? Constant.Sort.ID : bsort.getCloumn();
        String orderDirect = bsort == null ? Constant.Sort.DESC : bsort.getDirect().toString();
        Sort sort = new Sort(Direction.valueOf(Direction.class, orderDirect), orderCloumn);
        YcPage<PageResource> ycPage = PageUtil.queryYcPage(pageResourceDao, filters, pageNumber,
            pageSize, sort, PageResource.class);
        List<PageResource> pageResourceList = ycPage.getList();
        logger.debug("[PageResourceServiceImpl:queryPageResource(" + (BeanUtils.isNotNull(pageResourceList) ? Collections3.convertToString(
            pageResourceList, Constant.Common.SEPARATOR) :"") + ")][返回信息]");
        return ycPage;
    }

    @Override
    public List<PageResource> queryPageResourceByStatus(String status)
    {
        logger.debug("[PageResourceServiceImpl:queryPageResourceByStatus(" + status + ")]");
        List<PageResource> pageResourceList = pageResourceDao.queryPageResourceByStatus(status);
        logger.debug("[PageResourceServiceImpl:queryPageResourceByStatus(" + (BeanUtils.isNotNull(pageResourceList) ? Collections3.convertToString(
            pageResourceList, Constant.Common.SEPARATOR) :"") + ")][返回信息]");
        return pageResourceList;
    }

    @Override
    public PageResource queryPageResourceByUrl(String pageUrl)
    {
        logger.debug("[PageResourceServiceImpl:getPageResourceByUrl(" + pageUrl + ")]");
        PageResource pageResource = pageResourceDao.getPageResourceByUrl(pageUrl);
        logger.debug("[PageResourceServiceImpl:queryPageResourceByStatus(" + (BeanUtils.isNotNull(pageResource) ? pageResource.toString() : "")
                                                                                                                              + ")][返回信息]");
        return pageResource;
    }
}
