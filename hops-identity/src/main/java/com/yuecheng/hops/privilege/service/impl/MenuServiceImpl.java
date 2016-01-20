package com.yuecheng.hops.privilege.service.impl;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springside.modules.persistence.SearchFilter;

import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.exception.ApplicationException;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.query.BSort;
import com.yuecheng.hops.common.query.PageUtil;
import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.common.utils.Collections3;
import com.yuecheng.hops.common.utils.StringUtil;
import com.yuecheng.hops.privilege.entity.Menu;
import com.yuecheng.hops.privilege.entity.PageResource;
import com.yuecheng.hops.privilege.entity.RoleMenu;
import com.yuecheng.hops.privilege.repository.MenuDao;
import com.yuecheng.hops.privilege.service.MenuService;
import com.yuecheng.hops.privilege.service.PageResourceService;
import com.yuecheng.hops.privilege.service.RoleMenuQueryService;
import com.yuecheng.hops.privilege.service.RoleMenuService;


/**
 * 菜单权限表逻辑访问层
 * 
 * @author：Jinger
 * @date：2013-09-17
 */

@Service("menuService")
public class MenuServiceImpl implements MenuService
{

    @Autowired
    private MenuDao menuDao;

    @Autowired
    private PageResourceService pageResourceService;

    @Autowired
    private RoleMenuService roleMenuService;

    @Autowired
    private RoleMenuQueryService roleMenuQueryService;
    
    private static Logger logger = LoggerFactory.getLogger(MenuServiceImpl.class);

    /**
     * 添加菜单
     */
    @Override
    @Transactional
    public Menu saveMenu(Menu menu)
    {
        logger.debug("[MenuServiceImpl:saveMenu(" + (BeanUtils.isNotNull(menu) ? menu.toString() :"")
                                                                                                       + ")]");
        try
        {
            Assert.notNull(menu, "");
            menu = menuDao.save(menu);
            logger.debug("[MenuServiceImpl:saveMenu(" + (BeanUtils.isNotNull(menu) ? menu.toString() :"")
                                                                                                           + ")][返回信息]");
            return menu;

        }
        catch (Exception e)
        {
            // ExceptionUtil.throwException(e); 重新修改

            logger.error("[MenuServiceImpl:queryMenuById(菜单ID为空)]");
            String[] msgParams = new String[] {"queryMenuById"};
            ApplicationException ae = new ApplicationException("identity001060", msgParams);
            throw ExceptionUtil.throwException(ae);

        }
    }

    /**
     * 删除菜单
     */
    @Override
    @Transactional
    public void deleteMenu(Long menuId)
    {
        logger.debug("[MenuServiceImpl:deleteMenu(" + menuId + ")]");
        if (null != menuId)
        {
        	List<RoleMenu> roleMenuList=roleMenuQueryService.queryRoleMenuByMenuId(menuId);
        	if(BeanUtils.isNotNull(roleMenuList)&&roleMenuList.size()>0)
        	{
        		roleMenuService.deleteRoleMenuList(roleMenuList);
        	}
            menuDao.delete(menuId);
        }
    }

    /**
     * 查询菜单基本信息
     */
    @Override
    public Menu queryMenuById(Long menuId)
    {
        logger.debug("[MenuServiceImpl:queryMenuById(" + menuId + ")]");
        if (null != menuId)
        {
            Menu menu = menuDao.findOne(menuId);
            logger.debug("[MenuServiceImpl:queryMenuById(" + (BeanUtils.isNotNull(menu) ? menu.toString() :"")
                                                                                              + ")][返回信息]");
            return menu;
        }
        else
        {
            logger.error("[MenuServiceImpl:queryMenuById(菜单ID为空)]");
            String[] msgParams = new String[] {"queryMenuById"};
            ApplicationException ae = new ApplicationException("identity001060", msgParams);
            throw ExceptionUtil.throwException(ae);
        }
    }

    /**
     * 查询菜单信息列表
     */
    @Override
    public List<Menu> queryAllMenu()
    {
        List<Menu> menuList = menuDao.selectAll();
        logger.debug("[MenuServiceImpl:queryMenuById(" + (BeanUtils.isNotNull(menuList) ? Collections3.convertToString(
            menuList, Constant.Common.SEPARATOR) :"") + ")][返回信息]");
        return menuList;
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
    public YcPage<Menu> queryPageMenu(Map<String, Object> searchParams, int pageNumber,
                                      int pageSize, BSort bsort)
    {
        logger.debug("[MenuServiceImpl:queryPageMenu(," + pageNumber + "," + pageSize + ")]");
        Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
        String orderCloumn = bsort == null ? Constant.Sort.ID : bsort.getCloumn();
        String orderDirect = bsort == null ? Constant.Sort.DESC : bsort.getDirect().toString();
        Sort sort = new Sort(Direction.valueOf(Direction.class, orderDirect), orderCloumn);
        YcPage<Menu> ycPage = PageUtil.queryYcPage(menuDao, filters, pageNumber, pageSize, sort,
            Menu.class);
        List<Menu> menuList = ycPage.getList();
        logger.debug("MenuServiceImpl:queryPageMenu(" + (BeanUtils.isNotNull(menuList) ? Collections3.convertToString(
            menuList, Constant.Common.SEPARATOR) :"") + ")[返回信息]");
        return ycPage;
    }

    @Override
    public List<Menu> queryMenuByLevel(String menuLevel)
    {
        logger.debug("[MenuServiceImpl:queryMenuByLevel(" + (BeanUtils.isNotNull(menuLevel) ? menuLevel.toString() :"")
                                                                                                       + ")]");
        List<Menu> menuList = new ArrayList<Menu>();
        if (StringUtil.isNotBlank(menuLevel)) menuList = menuDao.selectList(menuLevel);
        logger.debug("[MenuServiceImpl:queryMenuByLevel(" + (BeanUtils.isNotNull(menuList) ? menuList.toString() :"")
                                                                                                     + ")[返回信息]");
        return menuList;
    }

    @Override
    public List<Menu> queryMenuByParams(Long parentId, String menuLevel)
    {
        logger.debug("[MenuServiceImpl:queryMenuByParams(" + parentId + "," + (BeanUtils.isNotNull(menuLevel) ? menuLevel.toString() :"")
                                                                                                                         + ")]");
        List<Menu> menuList = menuDao.selectList(parentId, menuLevel,
            Constant.MenuStatus.OPEN_STATUS);
        logger.debug("[MenuServiceImpl:queryMenuByParams(" + (BeanUtils.isNotNull(menuList) ? Collections3.convertToString(
            menuList, Constant.Common.SEPARATOR) :"") + ")][返回信息]");
        return menuList;
    }

    @Override
    public Menu queryMenuByPageUrl(String pageUrl)
    {
        logger.debug("[MenuServiceImpl:queryMenuByPageUrl(" + pageUrl + ")]");
        PageResource page = pageResourceService.queryPageResourceByUrl(pageUrl);
        if (BeanUtils.isNotNull(page))
        {
            Menu menu = menuDao.getMenuByPageUrl(page.getResourceId());
            logger.debug("[MenuServiceImpl:queryMenuByPageUrl(" + (BeanUtils.isNotNull(menu) ? menu.toString() :"")
                                                                                                   + ")][返回信息]");
            return menu;
        }
        else
        {
            logger.debug("[MenuServiceImpl:queryMenuByPageUrl(未找到该页面资源：" + (BeanUtils.isNotNull(pageUrl)?pageUrl:"") + ")]");
//            String[] msgParams = new String[] {"queryMenuByPageUrl", pageUrl};
//            ApplicationException ae = new ApplicationException("identity001061", msgParams);
//            throw ExceptionUtil.throwException(ae);
            return null;
        }
    }
    
    @Override
    public Menu queryMenuByPageId(Long pageId)
    {
        logger.debug("[MenuServiceImpl:queryMenuByPageId(" + pageId + ")]");
        if (BeanUtils.isNotNull(pageId))
        {
            Menu menu = menuDao.getMenuByPageUrl(pageId);
            logger.debug("[MenuServiceImpl:queryMenuByPageId(" + (BeanUtils.isNotNull(menu) ? menu.toString() :"")
                                                                                                   + ")][返回信息]");
            return menu;
        }
        else
        {
            return null;
        }
    }
    
}
