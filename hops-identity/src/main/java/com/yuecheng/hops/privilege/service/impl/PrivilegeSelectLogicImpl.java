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
import com.yuecheng.hops.privilege.entity.RolePrivilege;
import com.yuecheng.hops.privilege.service.PrivilegeSelectLogic;


@Service("privilegeSelectLogic")
public class PrivilegeSelectLogicImpl implements PrivilegeSelectLogic
{
    private static final Logger logger = LoggerFactory.getLogger(PrivilegeSelectLogicImpl.class);

    @Override
    public List<RolePrivilege> queryAllUpdateRolePrivilege(List<RolePrivilege> newRPList,
                                                           List<RolePrivilege> oldRPList,
                                                           String updateName)
    {
        try
        {
            logger.debug("[PrivilegeSelectLogicImpl:queryAllUpdateRolePrivilege(" + (BeanUtils.isNotNull(newRPList) ? Collections3.convertToString(
                newRPList, Constant.Common.SEPARATOR) :"") + "," + (BeanUtils.isNotNull(oldRPList) ? Collections3.convertToString(
                oldRPList, Constant.Common.SEPARATOR) :"") + "," + updateName + ")]");
            List<RolePrivilege> rolePrivilegeList = queryNewRolePrivilegeList(newRPList,
                oldRPList, updateName);
            // 将newRPList中不存在oldRPList中的新角色菜单数据追加到最新数据中rolePrivilegeList
            for (RolePrivilege newRolePrivilege : newRPList)
            {
                boolean temp = true;
                for (RolePrivilege oldRolePrivilege : oldRPList)
                {
                    Long newPrivilegeId = newRolePrivilege.getPrivilege().getPrivilegeId();
                    Long oldPrivilegeId = oldRolePrivilege.getPrivilege().getPrivilegeId();
                    if (newPrivilegeId.equals(oldPrivilegeId))
                    {
                        temp = false;
                        break;
                    }
                }
                if (temp)
                {
                    rolePrivilegeList.add(newRolePrivilege);
                }
            }
            logger.debug("[PrivilegeSelectLogicImpl:queryAllUpdateRolePrivilege("
                         + (BeanUtils.isNotNull(rolePrivilegeList) ? Collections3.convertToString(
                rolePrivilegeList, Constant.Common.SEPARATOR) :"") + ")][返回信息]");
            return rolePrivilegeList;
        }
        catch (Exception e)
        {
            logger.error("[PrivilegeSelectLogicImpl:queryAllUpdateRolePrivilege(" + ExceptionUtil.getStackTraceAsString(e)
                         + ")]");
            String[] msgParams = new String[] {"queryAllUpdateRolePrivilege"};
            ApplicationException ae = new ApplicationException("identity001019", msgParams);
            throw ExceptionUtil.throwException(ae);
        }
    }

    public List<RolePrivilege> queryNewRolePrivilegeList(List<RolePrivilege> newRPList,
                                                         List<RolePrivilege> oldRPList,
                                                         String updateName)
    {
        List<RolePrivilege> rolePrivilegeList = new ArrayList<RolePrivilege>();
        Date dt = new Date();
        // 判断原始角色权限数据是否在新增加的角色权限数据中，如果有则将状态启用，没有将状态禁用，获得一个最新的角色权限数据roleMenuList
        for (RolePrivilege oldRolePrivilege : oldRPList)
        {
            boolean temp = true;
            for (RolePrivilege newRolePrivilege : newRPList)
            {
                Long newPrivilegeId = newRolePrivilege.getPrivilege().getPrivilegeId();
                Long oldPrivilegeId = oldRolePrivilege.getPrivilege().getPrivilegeId();
                if (oldPrivilegeId.equals(newPrivilegeId))
                {
                    oldRolePrivilege.setStatus(Constant.MenuStatus.OPEN_STATUS);
                    temp = false;
                    break;
                }
            }
            if (temp)
            {
                oldRolePrivilege.setStatus(Constant.MenuStatus.CLOSE_STATUS);
            }
            oldRolePrivilege.setUpdateUser(updateName);
            oldRolePrivilege.setUpdateTime(dt);
            rolePrivilegeList.add(oldRolePrivilege);
        }
        logger.debug("[PrivilegeSelectLogicImpl:queryNewRolePrivilegeList(" + (BeanUtils.isNotNull(rolePrivilegeList) ? Collections3.convertToString(
            rolePrivilegeList, Constant.Common.SEPARATOR) :"") + ")][返回信息]");
        return rolePrivilegeList;
    }
}
