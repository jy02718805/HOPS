package com.yuecheng.hops.privilege.service.impl;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.rpc.RpcException;
import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.exception.ApplicationException;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.common.utils.Collections3;
import com.yuecheng.hops.common.utils.StringUtil;
import com.yuecheng.hops.privilege.entity.Menu;
import com.yuecheng.hops.privilege.entity.MenuSelect;
import com.yuecheng.hops.privilege.entity.Role;
import com.yuecheng.hops.privilege.entity.RoleMenu;
import com.yuecheng.hops.privilege.repository.RoleMenuDao;
import com.yuecheng.hops.privilege.service.MenuSelectLogic;
import com.yuecheng.hops.privilege.service.MenuService;
import com.yuecheng.hops.privilege.service.RoleMenuQueryService;
import com.yuecheng.hops.privilege.service.RoleMenuService;


@Service("roleMenuQueryService")
public class RoleMenuQueryServiceImpl implements RoleMenuQueryService
{
    private static final Logger logger = LoggerFactory.getLogger(RoleMenuQueryServiceImpl.class);

    @Autowired
    RoleMenuDao roleMenuDao;

    @Autowired
    MenuService menuService;

    @Autowired
    MenuSelectLogic menuLogic;
    
    @Autowired
    RoleMenuService roleMenuService;

    @Override
    public List<RoleMenu> queryRoleMenuByRole(Role role, String funcLevel)
    {
        logger.debug("[RoleMenuServiceImpl:queryRoleMenuByRole(" + (BeanUtils.isNotNull(role) ? role.toString() :"")
                                                                                                    + ","
                                                                                                    + funcLevel
                                                                                                    + ")]");
        if (BeanUtils.isNotNull(role) && BeanUtils.isNotNull(role.getRoleId()))
        {
            List<RoleMenu> roleMenuList = roleMenuDao.getRoleMenuByRoleId(role.getRoleId(),
                funcLevel);
            logger.debug("[RoleMenuServiceImpl:queryRoleMenuByRole(" + (BeanUtils.isNotNull(roleMenuList) ? Collections3.convertToString(
                roleMenuList, Constant.Common.SEPARATOR) :"") + ")][返回信息]");
            return roleMenuList;
        }
        else
        {
            logger.error("[RoleMenuServiceImpl:queryRoleMenuByRole(参数为空，请检查)]");
            String[] msgParams = new String[] {"queryRoleMenuByRole"};
            ApplicationException ae = new ApplicationException("identity001065", msgParams);
            throw ExceptionUtil.throwException(ae);
        }
    }

    @Override
    public List<RoleMenu> queryRoleMenuByRoleList(List<Role> roleList, String funcLevel)
    {
        try
        {
            logger.debug("[RoleMenuServiceImpl:queryRoleMenuByRoleList(" + (BeanUtils.isNotNull(roleList) ? roleList.toString() :"")
                                                                                                                    + ","
                                                                                                                    + funcLevel
                                                                                                                    + ")]");
            List<RoleMenu> rpList = new ArrayList<RoleMenu>();
            if (BeanUtils.isNotNull(roleList) && roleList.size() != 0)
            {
                if (roleList.size() == 1)
                {
                    Role role = roleList.get(0);
                    rpList = queryRoleMenuByRole(role, funcLevel);
                    logger.debug("[RoleMenuServiceImpl:queryRoleMenuByRoleList(" + (BeanUtils.isNotNull(rpList) ? Collections3.convertToString(
                        rpList, Constant.Common.SEPARATOR) :"") + ")][返回信息]");
                    return rpList;
                }
                else
                {
                    int i = 0;
                    while (i < roleList.size())
                    {
                        Role role = roleList.get(i);
                        List<RoleMenu> rplist = queryRoleMenuByRole(role, funcLevel);
                        rpList.addAll(rplist);
                        i++ ;
                    }
                    logger.debug("[RoleMenuServiceImpl:queryRoleMenuByRoleList(" + (BeanUtils.isNotNull(rpList) ? Collections3.convertToString(
                        rpList, Constant.Common.SEPARATOR) :"") + ")][返回信息]");
                    return rpList;
                }
            }
            else
            {
                logger.error("[RoleMenuServiceImpl:queryRoleMenuByRoleList(根据角色列表获取角色菜单列表失败，角色列表为空)]");
                String[] msgParams = new String[] {"queryRoleMenuByRoleList"};
                ApplicationException ae = new ApplicationException("identity001066", msgParams);
                throw ExceptionUtil.throwException(ae);
            }
        }catch(RpcException e){
            throw ExceptionUtil.throwException(e);
        }
        catch (Exception e)
        {
            logger.error("[RoleMenuServiceImpl:queryRoleMenuByRoleList(" + ExceptionUtil.getStackTraceAsString(e) + ")]");
            String[] msgParams = new String[] {"getRoleMenuByRoleList"};
            ApplicationException ae = new ApplicationException("identity001037", msgParams);
            throw ExceptionUtil.throwException(ae);
        }
    }

    @Override
    public List<RoleMenu> queryRoleMenuByMenuId(Long menuId)
    {
        logger.debug("[RoleMenuServiceImpl:getRoleMenuByMenuId(" + menuId + ")]");
        if (null != menuId)
        {
            List<RoleMenu> roleMenuList = roleMenuDao.getRoleMenuByMenuId(menuId);
            logger.debug("[RoleMenuServiceImpl:queryRoleMenuByMenuId(" + (BeanUtils.isNotNull(roleMenuList) ? Collections3.convertToString(
                roleMenuList, Constant.Common.SEPARATOR) :"") + ")][返回信息]");
            return roleMenuList;
        }
        else
        {
            logger.error("[RoleMenuServiceImpl:queryRoleMenuByRoleList(根据菜单ID获取角色菜单失败,菜单ID为空)]");
            String[] msgParams = new String[] {"queryRoleMenuByMenuId"};
            ApplicationException ae = new ApplicationException("identity001067", msgParams);
            throw ExceptionUtil.throwException(ae);
        }
    }

    @Override
    public List<RoleMenu> queryRoleMenuByParams(Long menuId, Long roleId)
    {
        logger.debug("[RoleMenuServiceImpl:queryRoleMenuByParams(" + menuId + "," + roleId + ")]");
        List<RoleMenu> roleMenuList = roleMenuDao.getRoleMenuList(menuId, roleId);
        logger.debug("[RoleMenuServiceImpl:queryRoleMenuByParams(" + (BeanUtils.isNotNull(roleMenuList) ? Collections3.convertToString(
            roleMenuList, Constant.Common.SEPARATOR) :"") + ")][返回信息]");
        return roleMenuList;
    }

    @Override
    public List<MenuSelect> queryMenuSelectByParams(String menuLevel, List<Role> roleList)
    {
        try
        {
            logger.debug("[RoleMenuServiceImpl:queryMenuSelect(" + menuLevel + "," + (BeanUtils.isNotNull(roleList) ? roleList.toString() :"")
                                                                                                                              + ")]");
            List<Menu> menulist = menuService.queryMenuByLevel(menuLevel);
            List<RoleMenu> rplist = queryRoleMenuByRoleList(roleList, menuLevel);
            List<MenuSelect> menuSelectList = menuLogic.queryAllMenuSelect(menulist, rplist);
            menuSelectList = queryMenuSelectList(menuSelectList);
            logger.debug("[RoleMenuServiceImpl:queryMenuSelect(" + (BeanUtils.isNotNull(menuSelectList) ? Collections3.convertToString(
                menuSelectList, Constant.Common.SEPARATOR) :"") + ")][返回信息]");
            return menuSelectList;
        }catch(RpcException e){
            throw ExceptionUtil.throwException(e);
        }
        catch (Exception e)
        {
            logger.error("[RoleMenuServiceImpl:queryMenuSelect(" + ExceptionUtil.getStackTraceAsString(e) + ")]");
            String[] msgParams = new String[] {"queryMenuSelect"};
            ApplicationException ae = new ApplicationException("identity001038", msgParams);
            throw ExceptionUtil.throwException(ae);
        }
    }

    @Override
    public List<RoleMenu> queryRoleMenuByRoleId(Long roleId)
    {
        logger.debug("[RolePrivilegeServiceImpl:queryRoleMenuByRoleId(" + roleId + ")]");
        List<RoleMenu> roleMenuList = roleMenuDao.getRoleMenuByRoleId(roleId);
        logger.debug("[RoleMenuServiceImpl:queryRoleMenuByParams(" + (BeanUtils.isNotNull(roleMenuList) ? Collections3.convertToString(
            roleMenuList, Constant.Common.SEPARATOR) :"") + ")][返回信息]");
        return roleMenuList;
    }

    /**
     * 去重
     */
    public List<MenuSelect> queryMenuSelectList(List<MenuSelect> menuSelectList2)
    {
        try
        {
            logger.debug("[RoleMenuServiceImpl:queryMenuSelectList(去重：" + (BeanUtils.isNotNull(menuSelectList2) ? menuSelectList2.toString() :"")
                                                                                                                                 + ")]");
            List<MenuSelect> menuSelectList = new ArrayList<MenuSelect>();
            int i = 0;
            while (i < menuSelectList2.size())
            {
                MenuSelect menuSelect = menuSelectList2.get(i);
                int j = 0;
                boolean flag = true;
                while (j < menuSelectList.size())
                {
                    MenuSelect ms = menuSelectList.get(j);
                    if (menuSelect.getMenu().getMenuId().equals(ms.getMenu().getMenuId()))
                    {
                        flag = false;
                        break;
                    }
                    j++ ;
                }
                if (flag)
                {
                    menuSelectList.add(menuSelect);
                }
                i++ ;
            }
            logger.debug("[RoleMenuServiceImpl:queryMenuSelectList(" + (BeanUtils.isNotNull(menuSelectList) ? Collections3.convertToString(
                menuSelectList, Constant.Common.SEPARATOR) :"") + ")][返回信息]");
            return menuSelectList;
        }
        catch (Exception e)
        {
            logger.error("[RoleMenuServiceImpl:queryMenuSelectList(" + ExceptionUtil.getStackTraceAsString(e) + ")]");
            String[] msgParams = new String[] {"queryMenuSelectList"};
            ApplicationException ae = new ApplicationException("identity001039", msgParams);
            throw ExceptionUtil.throwException(ae);
        }
    }
    @Override
    @Transactional
    public String saveRoleMenuList(String menuid, Role role, String updateName)
    {
        try
        {
            logger.debug("[RoleMenuServiceImpl:saveRoleMenuList(" + menuid + "," + (BeanUtils.isNotNull(role) ? role.toString() :"")
                                                                                                                    + ","
                                                                                                                    + updateName
                                                                                                                    + ")]");
            if(BeanUtils.isNotNull(role))
            {
	            // 删除原有的权限记录
		        roleMenuService.deleteRoleMenuByRoleId(role.getRoleId());
	            if (StringUtil.isNotBlank(menuid))
	            {
	                String[] menuidlist = menuid.split(Constant.StringSplitUtil.DECODE);
	                List<RoleMenu> roleMenuList = new ArrayList<RoleMenu>();
	                int i = 0;
	                while (i < menuidlist.length)
	                {
	                    Long mid = Long.parseLong(menuidlist[i].replace(",", StringUtil.initString()));
	                    Menu menu = menuService.queryMenuById(mid);
	                    Date dt = new Date();
	                    RoleMenu rp = new RoleMenu();
	                    rp.setMenu(menu);
	                    rp.setRole(role);
	                    rp.setStatus(Constant.RoleStatus.OPEN_STATUS);
	                    rp.setCreateTime(dt);
	                    rp.setUpdateUser(updateName);
	                    rp.setUpdateTime(dt);
	                    roleMenuList.add(rp);
	                    i++ ;
	                }
	                String result = roleMenuService.saveRoleMenu(roleMenuList);
	                logger.debug("[RoleMenuServiceImpl:saveRoleMenuList()return:" + result + "][返回信息]");
	                return result;
	            }
            }
            return null;
        }catch(RpcException e){
            throw ExceptionUtil.throwException(e);
        }
        catch (Exception e)
        {
            logger.error("[RoleMenuServiceImpl:saveRoleMenuList(" + ExceptionUtil.getStackTraceAsString(e) + ")]");
            String[] msgParams = new String[] {"saveRoleMenuList"};
            ApplicationException ae = new ApplicationException("identity001040", msgParams);
            throw ExceptionUtil.throwException(ae);
        }
    }
}
