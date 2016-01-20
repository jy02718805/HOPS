package com.yuecheng.hops.privilege.service.impl;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.exception.ApplicationException;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.common.utils.Collections3;
import com.yuecheng.hops.privilege.entity.Menu;
import com.yuecheng.hops.privilege.entity.MenuSelect;
import com.yuecheng.hops.privilege.entity.RoleMenu;
import com.yuecheng.hops.privilege.service.MenuSelectLogic;


@Service("menuSelectLogic")
public class MenuSelectLogicImpl implements MenuSelectLogic
{
    static Logger logger = LoggerFactory.getLogger(MenuSelectLogicImpl.class);

    @Override
    public List<MenuSelect> queryAllMenuSelect(List<Menu> menuList, List<RoleMenu> roleMenuList)
    {
        try
        {
            logger.debug("[MenuSelectLogicImpl:queryAllMenuSelect(" + (BeanUtils.isNotNull(menuList) ? Collections3.convertToString(
                menuList, Constant.Common.SEPARATOR) :"") + "," + (BeanUtils.isNotNull(roleMenuList) ? Collections3.convertToString(
                roleMenuList, Constant.Common.SEPARATOR) :"") + ")]");
            List<MenuSelect> menuSelectList = new ArrayList<MenuSelect>();
            for (Menu menu : menuList)
            {
                MenuSelect menuSelect = new MenuSelect();
                menuSelect.setMenu(menu);
                menuSelect.setStatus(Constant.MenuStatus.CLOSE_STATUS);
                for (RoleMenu roleMenu : roleMenuList)
                {
                    if (roleMenu.getMenu().getMenuId().equals(menu.getMenuId())
                        && roleMenu.getStatus().equals(Constant.MenuStatus.OPEN_STATUS))
                    {
                        menuSelect.setStatus(Constant.MenuStatus.OPEN_STATUS);
                        break;
                    }
                }
                menuSelectList.add(menuSelect);
            }
            logger.debug("[MenuSelectLogicImpl:queryAllMenuSelect(" + (BeanUtils.isNotNull(menuSelectList) ? Collections3.convertToString(
                menuSelectList, Constant.Common.SEPARATOR) :"") + ")][返回信息]");
            return menuSelectList;
        }
        catch (Exception e)
        {
            logger.error("[MenuSelectLogicImpl:queryAllMenuSelect(" + ExceptionUtil.getStackTraceAsString(e) + ")]");
            String[] msgParams = new String[] {"getAllSelect"};
            ApplicationException ae = new ApplicationException("identity001018", msgParams);
            throw ExceptionUtil.throwException(ae);
        }
    }

    @Override
    public List<RoleMenu> queryAllUpdateRoleMenu(List<RoleMenu> newRMList,
                                                 List<RoleMenu> oldRMList, String updateName)
    {
        try
        {
            logger.debug("[MenuSelectLogicImpl:queryAllUpdateRoleMenu(" + (BeanUtils.isNotNull(newRMList) ? Collections3.convertToString(
                newRMList, Constant.Common.SEPARATOR) :"") + "," + (BeanUtils.isNotNull(oldRMList) ? Collections3.convertToString(
                oldRMList, Constant.Common.SEPARATOR) :"") + "," + updateName + ")]");
            List<RoleMenu> roleMenuList = queryNewRoleMenuList(newRMList, oldRMList, updateName);
            // 将newRMList中不存在oldRMList中的新角色菜单数据追加到最新数据中roleMenuList
            for (RoleMenu newRoleMenu : newRMList)
            {
                boolean temp = true;
                for (RoleMenu oldRoleMenu : oldRMList)
                {
                    Long newMenuId = newRoleMenu.getMenu().getMenuId();
                    Long oldMenuId = oldRoleMenu.getMenu().getMenuId();
                    if (newMenuId.equals(oldMenuId))
                    {
                        temp = false;
                        break;
                    }
                }
                if (temp)
                {
                    roleMenuList.add(newRoleMenu);
                }
            }
            logger.debug("[MenuSelectLogicImpl:queryAllUpdateRoleMenu(" + (BeanUtils.isNotNull(roleMenuList) ? Collections3.convertToString(
                roleMenuList, Constant.Common.SEPARATOR) :"") + ")][返回信息]");
            return roleMenuList;

        }
        catch (Exception e)
        {
            logger.error("[MenuSelectLogicImpl:queryAllUpdateRoleMenu(" + ExceptionUtil.getStackTraceAsString(e) + ")]");
            String[] msgParams = new String[] {"queryAllUpdateRoleMenu"};
            ApplicationException ae = new ApplicationException("identity001019", msgParams);
            throw ExceptionUtil.throwException(ae);
        }
    }

    public List<RoleMenu> queryNewRoleMenuList(List<RoleMenu> newRMList, List<RoleMenu> oldRMList,
                                               String updateName)
    {
        List<RoleMenu> roleMenuList = new ArrayList<RoleMenu>();
        Date dt = new Date();
        // 判断原始角色菜单数据是否在新增加的角色菜单数据中，如果有则将状态启用，没有将状态禁用，获得一个最新的角色菜单数据roleMenuList
        for (RoleMenu oldRoleMenu : oldRMList)
        {
            boolean temp = true;
            for (RoleMenu newRoleMenu : newRMList)
            {
                Long newMenuId = newRoleMenu.getMenu().getMenuId();
                Long oldMenuId = oldRoleMenu.getMenu().getMenuId();
                if (oldMenuId.equals(newMenuId))
                {
                    oldRoleMenu.setStatus(Constant.MenuStatus.OPEN_STATUS);
                    temp = false;
                    break;
                }
            }
            if (temp)
            {
                oldRoleMenu.setStatus(Constant.MenuStatus.CLOSE_STATUS);
            }
            oldRoleMenu.setUpdateUser(updateName);
            oldRoleMenu.setUpdateTime(dt);
            roleMenuList.add(oldRoleMenu);
        }
        logger.debug("[MenuSelectLogicImpl:queryNewRoleMenuList(" + (BeanUtils.isNotNull(roleMenuList) ? Collections3.convertToString(
            roleMenuList, Constant.Common.SEPARATOR) :"") + ")][返回信息]");
        return roleMenuList;
    }
}
