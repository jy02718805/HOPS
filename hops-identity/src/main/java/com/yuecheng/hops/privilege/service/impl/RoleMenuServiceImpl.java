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
import com.yuecheng.hops.common.Constant.PrivilegeConstants;
import com.yuecheng.hops.common.exception.ApplicationException;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.query.BSort;
import com.yuecheng.hops.common.query.PageUtil;
import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.common.utils.Collections3;
import com.yuecheng.hops.privilege.entity.RoleMenu;
import com.yuecheng.hops.privilege.entity.RolePrivilege;
import com.yuecheng.hops.privilege.repository.RoleMenuDao;
import com.yuecheng.hops.privilege.service.MenuSelectLogic;
import com.yuecheng.hops.privilege.service.MenuService;
import com.yuecheng.hops.privilege.service.RoleMenuQueryService;
import com.yuecheng.hops.privilege.service.RoleMenuService;


@Service("roleMenuService")
public class RoleMenuServiceImpl implements RoleMenuService
{
    @Autowired
    RoleMenuDao roleMenuDao;

    @Autowired
    MenuService menuService;

    @Autowired
    MenuSelectLogic menuLogic;

    @Autowired
    RoleMenuQueryService roleMenuQueryService;

    static Logger logger = LoggerFactory.getLogger(RoleMenuServiceImpl.class);

    @Override
    @Transactional
    public RoleMenu saveRoleMenu(RoleMenu roleMenu)
    {
        logger.debug("[RoleMenuServiceImpl:saveRoleMenu(" + (BeanUtils.isNotNull(roleMenu) ? roleMenu.toString() :"")
                                                                                                     + ")]");
        if (BeanUtils.isNotNull(roleMenu))
        {
            roleMenu = roleMenuDao.save(roleMenu);
        }
        logger.debug("[RoleMenuServiceImpl:saveRoleMenu(" + (BeanUtils.isNotNull(roleMenu) ? roleMenu.toString() :"")
                                                                                                     + ")][返回信息]");
        return roleMenu;
    }

    @Override
    @Transactional
    public String saveRoleMenu(List<RoleMenu> roleMenuList)
    {
        try
        {
            logger.debug("[RoleMenuServiceImpl:saveRoleMenu(List:" + (BeanUtils.isNotNull(roleMenuList) ? roleMenuList.toString() :"")
                                                                                                                      + ")]");
            if (BeanUtils.isNotNull(roleMenuList) && 0 < roleMenuList.size())
            {
                int i = 0;
                while (i < roleMenuList.size())
                {
                    RoleMenu roleMenu = roleMenuList.get(i);
//                    this.saveRoleMenu(roleMenu);
                    roleMenuDao.save(roleMenu);
                    i++ ;
                }
                logger.debug("[RoleMenuServiceImpl:saveRoleMenu()List-return:SUCCESS]");
                return PrivilegeConstants.SUCCESS;
            }
            logger.debug("[RoleMenuServiceImpl:saveRoleMenu()List-return:FAIL]");
            return PrivilegeConstants.FAIL;
        }
        catch (Exception e)
        {
            logger.error("[RoleMenuServiceImpl:saveRoleMenu(" + ExceptionUtil.getStackTraceAsString(e) + ")]");
            String[] msgParams = new String[] {"saveRoleMenu"};
            ApplicationException ae = new ApplicationException("identity001036", msgParams);
            throw ExceptionUtil.throwException(ae);
        }
    }

    @Override
    @Transactional
    public void deleteRoleMenuById(Long roleMenuId)
    {
        logger.debug("[RoleMenuServiceImpl:deleteRoleMenu(" + roleMenuId + ")]");
        if (null != roleMenuId)
        {
            roleMenuDao.delete(roleMenuId);
        }

    }

    @Override
    public RoleMenu queryRoleMenuById(Long roleMenuId)
    {
        logger.debug("[RoleMenuServiceImpl:queryRoleMenuById(" + roleMenuId + ")]");
        if (null != roleMenuId)
        {
            RoleMenu roleMenu = roleMenuDao.findOne(roleMenuId);
            logger.debug("[RoleMenuServiceImpl:queryRoleMenuById(" + (BeanUtils.isNotNull(roleMenu) ? roleMenu.toString() :"")
                                                                                                              + ")][返回信息]");
            return roleMenu;
        }
        else
        {
            logger.error("[RoleMenuServiceImpl:queryRoleMenuById(根据角色菜单ID获取角色菜单失败,角色菜单ID为空)]");
            String[] msgParams = new String[] {"queryRoleMenuById"};
            ApplicationException ae = new ApplicationException("identity001068", msgParams);
            throw ExceptionUtil.throwException(ae);
        }
    }

    @Override
    public List<RoleMenu> queryAllRoleMenu()
    {
        List<RoleMenu> roleMenuList = roleMenuDao.selectAll();
        logger.debug("[RoleMenuServiceImpl:queryAllRoleMenu(" + (BeanUtils.isNotNull(roleMenuList) ? roleMenuList.toString() :"")
                                                                                                                 + ")][返回信息]");
        return roleMenuList;
    }

    @Override
    public YcPage<RoleMenu> queryRoleMenu(Map<String, Object> searchParams, int pageNumber,
                                          int pageSize, BSort bsort)
    {
        logger.debug("[RoleMenuServiceImpl:queryRoleMenu(" + (BeanUtils.isNotNull(searchParams) ? searchParams.toString() :"")
                                                                                                              + ","
                                                                                                              + pageNumber
                                                                                                              + ","
                                                                                                              + pageSize
                                                                                                              + ")]");
        Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
        String orderCloumn = bsort == null ? Constant.Sort.ID : bsort.getCloumn();
        String orderDirect = bsort == null ? Constant.Sort.DESC : bsort.getDirect().toString();
        Sort sort = new Sort(Direction.valueOf(Direction.class, orderDirect), orderCloumn);
        YcPage<RoleMenu> ycPage = PageUtil.queryYcPage(roleMenuDao, filters, pageNumber, pageSize,
            sort, RolePrivilege.class);
        List<RoleMenu> roleMenuList = ycPage.getList();
        logger.debug("[RoleMenuServiceImpl:queryRoleMenu(" + (BeanUtils.isNotNull(roleMenuList) ? roleMenuList.toString() :"")
                                                                                                              + ")][返回信息]");
        return ycPage;
    }

    

    @Override
    @Transactional
    public void deleteRoleMenuByRoleId(Long roleId)
    {
        logger.debug("[RoleMenuServiceImpl:deleteByRoleId(" + roleId + ")]");
        List<RoleMenu> roleMenuList = roleMenuQueryService.queryRoleMenuByRoleId(roleId);
        if (BeanUtils.isNotNull(roleMenuList))
        {
            int i = 0;
            while (i < roleMenuList.size())
            {
                RoleMenu roleMenu = roleMenuList.get(i);
//                this.deleteRoleMenuById(roleMenu.getRoleMenuId());
                roleMenuDao.delete(roleMenu.getRoleMenuId());
                i++ ;
            }
        }
    }

    @Override
    @Transactional
    public void deleteRoleMenuByMenuId(Long menuId)
    {
        logger.debug("[RoleMenuServiceImpl:deleteRoleMenuByMenuId(" + menuId + ")]");
        List<RoleMenu> roleMenuList = roleMenuQueryService.queryRoleMenuByMenuId(menuId);
        deleteRoleMenuList(roleMenuList);

    }

    @Override
    @Transactional
    public void deleteRoleMenuList(List<RoleMenu> roleMenuList)
    {
        logger.debug("[RoleMenuServiceImpl:deleteRoleMenuList(" + (BeanUtils.isNotNull(roleMenuList) ? Collections3.convertToString(
            roleMenuList, Constant.Common.SEPARATOR) :"") + ")]");
        for (RoleMenu roleMenu : roleMenuList)
        {
//            deleteRoleMenuById(roleMenu.getRoleMenuId());
            roleMenuDao.delete(roleMenu.getRoleMenuId());
        }

    }

}
