package com.yuecheng.hops.privilege.service.impl;


import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.rpc.RpcException;
import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.enump.IdentityType;
import com.yuecheng.hops.common.exception.ApplicationException;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.common.utils.Collections3;
import com.yuecheng.hops.privilege.entity.IdentityRole;
import com.yuecheng.hops.privilege.entity.IdentityRoleSelect;
import com.yuecheng.hops.privilege.entity.Role;
import com.yuecheng.hops.privilege.repository.IdentityRoleDao;
import com.yuecheng.hops.privilege.service.IdentityRoleQueryService;
import com.yuecheng.hops.privilege.service.IdentityRoleSelectLogic;
import com.yuecheng.hops.privilege.service.RoleService;


@Service("identityRoleQueryService")
public class IdentityRoleQueryServiceImpl implements IdentityRoleQueryService
{
    @Autowired
    IdentityRoleDao identityRoleDao;

    @Autowired
    RoleService roleService;

    @Autowired
    IdentityRoleSelectLogic identityRoleSelectLogic;

    private static final Logger logger = LoggerFactory.getLogger(IdentityRoleQueryServiceImpl.class);

    /**
     * 根据角色ID获取所有的用户角色关联列表
     */
    @Override
    public List<IdentityRole> queryIdentityRoleByRId(Long roleId)
    {
        logger.debug("[IdentityRoleServiceImpl:queryIdentityRoleByRId(" + roleId + ")]");
        if (null != roleId)
        {
            List<IdentityRole> identityRoleList = identityRoleDao.getIdentityRoleListByRole(roleId);
            logger.debug("[IdentityRoleServiceImpl:queryIdentityRoleByRId(" + (BeanUtils.isNotNull(identityRoleList) ? Collections3.convertToString(
                identityRoleList, Constant.Common.SEPARATOR) :"") + ")][返回信息]");
            return identityRoleList;
        }
        else
        {
            logger.debug("[IdentityRoleServiceImpl:queryIdentityRoleByRId(角色Id为空)][返回信息]");
            String[] msgParams = new String[] {"queryIdentityRoleByRId"};
            ApplicationException e = new ApplicationException("identity001057", msgParams);
            throw ExceptionUtil.throwException(e);
        }
    }

    /**
     * 根据用户ID获取所有的用户角色关联列表
     */
    @Override
    public List<IdentityRole> queryIdentityRoleByIdentityId(Long identityId)
    {
        logger.debug("[IdentityRoleServiceImpl:queryIdentityRoleByIdentityId(" + identityId + ")]");
        if (null != identityId)
        {
            List<IdentityRole> identityRoleList = identityRoleDao.getIdentityRoleListByIdentity(identityId);
            logger.debug("[IdentityRoleServiceImpl:queryIdentityRoleByIdentityId("
                         + (BeanUtils.isNotNull(identityRoleList) ? Collections3.convertToString(
                identityRoleList, Constant.Common.SEPARATOR) :"") + ")][返回信息]");
            return identityRoleList;
        }
        else
        {
            logger.debug("[IdentityRoleServiceImpl:queryIdentityRoleByIdentityId(用户Id为空)][返回信息]");
            String[] msgParams = new String[] {"queryIdentityRoleByIdentityId"};
            ApplicationException e = new ApplicationException("identity001058", msgParams);
            throw ExceptionUtil.throwException(e);
        }
    }

    /**
     * 根据用户ID和用户类型获取所有的用户角色关联列表
     */
    @Override
    public List<IdentityRole> queryIdentityRole(Long identityId, IdentityType identityType)
    {
        logger.debug("[IdentityRoleServiceImpl:queryIdentityRole(" + identityId + ","
                     + (BeanUtils.isNotNull(identityType) ? identityType.toString() :"") + ")]");
        List<IdentityRole> identityRoleList = identityRoleDao.getIdentityRoleList(identityId,
            identityType);
        logger.debug("[IdentityRoleServiceImpl:queryIdentityRole(" + (BeanUtils.isNotNull(identityRoleList) ? Collections3.convertToString(
            identityRoleList, Constant.Common.SEPARATOR) :"") + ")][返回信息]");
        return identityRoleList;
    }

    /**
     * 根据用户ID和用户类型获取用户所有已开通的角色列表
     */
    @Override
    public List<Role> queryRoleByIdentity(Long identityId, IdentityType identityType)
    {
        try
        {
            logger.debug("[IdentityRoleServiceImpl:queryRoleByIdentity(" + identityId + ","
                         + (BeanUtils.isNotNull(identityType) ? identityType.toString() :"") + ")]");
            List<IdentityRole> irList = queryIdentityRole(identityId, identityType);
            List<Role> roleList = new ArrayList<Role>();
            int i = 0;
            while (i < irList.size())
            {
                IdentityRole ir = irList.get(i);
                if (ir.getStatus().equals(Constant.IdentityStatus.OPEN_STATUS))
                {
                    Role role = ir.getRole();
                    if (role.getStatus().endsWith(Constant.RoleStatus.OPEN_STATUS))
                    {
                        roleList.add(role);
                    }
                }
                i++ ;
            }
            logger.debug("[IdentityRoleServiceImpl:queryRoleByIdentity(" + (BeanUtils.isNotNull(roleList) ? Collections3.convertToString(
                roleList, Constant.Common.SEPARATOR) :"") + ")][返回信息]");
            return roleList;
        }
        catch (Exception e)
        {
            logger.error("[IdentityRoleServiceImpl:queryRoleByIdentity(" + ExceptionUtil.getStackTraceAsString(e) + ")]");
            String[] msgParams = new String[] {"queryRoleByIdentity"};
            ApplicationException ae = new ApplicationException("identity001010", msgParams);
            throw ExceptionUtil.throwException(ae);
        }
    }

    /**
     * 根据用户ID和用户类型获取用户所有已开通的用户角色关联列表
     */
    @Override
    public List<IdentityRoleSelect> queryIdentityRoleSelect(Long identityId,
                                                            IdentityType identityType)
    {
        try
        {
            logger.debug("[IdentityRoleServiceImpl:queryIdentityRoleSelect(" + identityId + ","
                         + (BeanUtils.isNotNull(identityType) ? identityType.toString() :"") + ")]");
            List<Role> roleList = roleService.queryRoleListByStatus(Constant.RoleStatus.OPEN_STATUS);
            List<IdentityRole> identityRoleList = queryIdentityRole(identityId, identityType);
            List<IdentityRoleSelect> irSelectlist = identityRoleSelectLogic.queryAllIdentityRoleSelect(
                roleList, identityRoleList);
            logger.debug("[IdentityRoleServiceImpl:queryIdentityRoleSelect(" + (BeanUtils.isNotNull(irSelectlist) ? Collections3.convertToString(
                irSelectlist, Constant.Common.SEPARATOR) :"") + ")][返回信息]");
            return irSelectlist;
        }catch(RpcException e){
            throw ExceptionUtil.throwException(e);
        }
        catch (Exception e)
        {
            logger.error("[IdentityRoleSelectLogicImpl:queryIdentityRoleSelect(" + ExceptionUtil.getStackTraceAsString(e)
                         + ")]");
            String[] msgParams = new String[] {"queryIdentityRoleSelect"};
            ApplicationException ae = new ApplicationException("identity001011", msgParams);
            throw ExceptionUtil.throwException(ae);
        }
    }

    /**
     * 根据用户ID和用户类型以及角色类型获取用户所有已开通的用户角色关联列表
     */
    @Override
    public List<IdentityRoleSelect> queryIRSelectByRoleType(Long identityId,
                                                            IdentityType identityType,
                                                            String roleType)
    {
        try
        {
            logger.debug("[IdentityRoleServiceImpl:queryIRSelectByRoleType(" + identityId + ","
                         + (BeanUtils.isNotNull(identityType) ? identityType.toString() :"") + ")]");
            List<Role> roleList = roleService.queryRoleListByRoleType(roleType);
            List<IdentityRole> identityRoleList = queryIdentityRole(identityId, identityType);
            List<IdentityRoleSelect> irSelectlist = identityRoleSelectLogic.queryAllIdentityRoleSelect(
                roleList, identityRoleList);
            logger.debug("[IdentityRoleServiceImpl:queryIRSelectByRoleType(" + (BeanUtils.isNotNull(irSelectlist) ? Collections3.convertToString(
                irSelectlist, Constant.Common.SEPARATOR) :"") + ")][返回信息]");
            return irSelectlist;
        }catch(RpcException e){
            throw ExceptionUtil.throwException(e);
        }
        catch (Exception e)
        {
            logger.error("[IdentityRoleServiceImpl:queryIRSelectByRoleType(" + ExceptionUtil.getStackTraceAsString(e)
                         + ")]");
            String[] msgParams = new String[] {"queryIRSelectByRoleType"};
            ApplicationException ae = new ApplicationException("identity001012", msgParams);
            throw ExceptionUtil.throwException(ae);
        }
    }

}