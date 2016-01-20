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
import com.yuecheng.hops.privilege.entity.IdentityRole;
import com.yuecheng.hops.privilege.entity.IdentityRoleSelect;
import com.yuecheng.hops.privilege.entity.Role;
import com.yuecheng.hops.privilege.service.IdentityRoleSelectLogic;


/**
 * 查询用户订购角色逻辑访问层
 * 
 * @author：Jinger
 * @date：2013-09-17
 */
@Service("identityRoleSelectLogic")
public class IdentityRoleSelectLogicImpl implements IdentityRoleSelectLogic
{

    private static Logger logger = LoggerFactory.getLogger(IdentityRoleSelectLogicImpl.class);

    @Override
    public List<IdentityRoleSelect> queryAllIdentityRoleSelect(List<Role> roleList,
                                                               List<IdentityRole> identityRoleList)
    {
        try
        {
            logger.debug("[IdentityRoleSelectLogicImpl:queryAllIdentityRoleSelect(" + (BeanUtils.isNotNull(roleList) ? Collections3.convertToString(
                roleList, Constant.Common.SEPARATOR) :"") + "," + (BeanUtils.isNotNull(identityRoleList) ? Collections3.convertToString(
                identityRoleList, Constant.Common.SEPARATOR) :"") + ")]");
            List<IdentityRoleSelect> identityRoleSelectList = new ArrayList<IdentityRoleSelect>();
            for (Role role : roleList)
            {
                IdentityRoleSelect identityRoleSelect = new IdentityRoleSelect();
                identityRoleSelect.setRole(role);
                identityRoleSelect.setStatus(Constant.IdentityStatus.CLOSE_STATUS);
                for (IdentityRole identityRole : identityRoleList)
                {
                    if (identityRole.getRole().getRoleId().equals(role.getRoleId())
                        && identityRole.getStatus().equals(Constant.IdentityStatus.OPEN_STATUS))
                    {
                        identityRoleSelect.setStatus(Constant.IdentityStatus.OPEN_STATUS);
                        break;
                    }
                }
                identityRoleSelectList.add(identityRoleSelect);
            }
            logger.debug("[IdentityRoleSelectLogicImpl:queryAllIdentityRoleSelect()return:"
                         + (BeanUtils.isNotNull(identityRoleSelectList) ? identityRoleSelectList.toString() :"")
                                                                                                + "][返回信息]");
            return identityRoleSelectList;
        }
        catch (Exception e)
        {
            logger.error("[IdentityRoleSelectLogicImpl:queryAllIdentityRoleSelect("
                         + ExceptionUtil.getStackTraceAsString(e) + ")]");
            String[] msgParams = new String[] {"queryAllIdentityRoleSelect"};
            ApplicationException ae = new ApplicationException("identity001007", msgParams);
            throw ExceptionUtil.throwException(ae);
        }
    }

    @Override
    public List<IdentityRole> queryAllUpdateIdentityRole(List<IdentityRole> newIRList,
                                                         List<IdentityRole> oldIRList,
                                                         String updateName)
    {
        try
        {
            logger.debug("[IdentityRoleSelectLogicImpl:queryAllUpdateIdentityRole(" + (BeanUtils.isNotNull(newIRList) ? Collections3.convertToString(
                newIRList, Constant.Common.SEPARATOR) :"") + "," + (BeanUtils.isNotNull(oldIRList) ? Collections3.convertToString(
                oldIRList, Constant.Common.SEPARATOR) :"") + "," + updateName + ")]");
            List<IdentityRole> identityRoleList = queryNewIdentityRoleList(newIRList, oldIRList,
                updateName);
            // 将newIRList中不存在oldIRList中的新角色菜单数据追加到最新数据中identityRoleList
            for (IdentityRole newIdentityRole : newIRList)
            {
                boolean temp = true;
                for (IdentityRole oldIdentityRole : oldIRList)
                {
                    Long newRoleId = newIdentityRole.getRole().getRoleId();
                    Long oldRoleId = oldIdentityRole.getRole().getRoleId();
                    if (newRoleId.equals(oldRoleId))
                    {
                        temp = false;
                        break;
                    }
                }
                if (temp)
                {
                    identityRoleList.add(newIdentityRole);
                }
            }
            logger.debug("[IdentityRoleSelectLogicImpl:queryAllUpdateIdentityRole()return:"
                         + (BeanUtils.isNotNull(identityRoleList) ? identityRoleList.toString() :"")
                                                                                    + "][返回信息]");
            return identityRoleList;
        }
        catch (Exception e)
        {
            logger.error("[IdentityRoleSelectLogicImpl:queryAllUpdateIdentityRole("
                         + ExceptionUtil.getStackTraceAsString(e) + ")]");
            String[] msgParams = new String[] {"queryAllUpdateIdentityRole"};
            ApplicationException ae = new ApplicationException("identity001008", msgParams);
            throw ExceptionUtil.throwException(ae);
        }
    }

    public List<IdentityRole> queryNewIdentityRoleList(List<IdentityRole> newIRList,
                                                       List<IdentityRole> oldIRList,
                                                       String updateName)
    {
        List<IdentityRole> identityRoleList = new ArrayList<IdentityRole>();
        Date dt = new Date();
        // 判断原始用户角色数据是否在新增加的用户角色数据中，如果有则将状态启用，没有将状态禁用，获得一个最新的用户角色数据identityRoleList
        for (IdentityRole oldIdentityRole : oldIRList)
        {
            boolean temp = true;
            for (IdentityRole newIdentityRole : newIRList)
            {
                Long newRoleId = newIdentityRole.getRole().getRoleId();
                Long oldRoleId = oldIdentityRole.getRole().getRoleId();
                if (oldRoleId.equals(newRoleId))
                {
                    oldIdentityRole.setStatus(Constant.IdentityStatus.OPEN_STATUS);
                    temp = false;
                    break;
                }
            }
            if (temp)
            {
                oldIdentityRole.setStatus(Constant.IdentityStatus.CLOSE_STATUS);
            }
            oldIdentityRole.setUpdateName(updateName);
            oldIdentityRole.setUpdateTime(dt);
            identityRoleList.add(oldIdentityRole);
        }
        logger.debug("[IdentityRoleSelectLogicImpl:queryNewIdentityRoleList()return:"
                     + (BeanUtils.isNotNull(identityRoleList) ? identityRoleList.toString() :"") + "][返回信息]");
        return identityRoleList;
    }
}
