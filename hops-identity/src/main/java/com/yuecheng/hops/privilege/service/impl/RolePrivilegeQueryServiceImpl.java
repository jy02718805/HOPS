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
import com.yuecheng.hops.privilege.entity.Privilege;
import com.yuecheng.hops.privilege.entity.Role;
import com.yuecheng.hops.privilege.entity.RolePrivilege;
import com.yuecheng.hops.privilege.repository.MenuDao;
import com.yuecheng.hops.privilege.repository.RolePrivilegeDao;
import com.yuecheng.hops.privilege.service.PrivilegeSelectLogic;
import com.yuecheng.hops.privilege.service.PrivilegeService;
import com.yuecheng.hops.privilege.service.RolePrivilegeQueryService;
import com.yuecheng.hops.privilege.service.RolePrivilegeService;


@Service("rolePrivilegeQueryService")
public class RolePrivilegeQueryServiceImpl implements RolePrivilegeQueryService
{

    private static final Logger logger = LoggerFactory.getLogger(RolePrivilegeQueryServiceImpl.class);

    @Autowired
    RolePrivilegeDao rolePrivilegeDao;

    @Autowired
    MenuDao menuService;
    @Autowired
    PrivilegeService privilegeService;
    @Autowired
    PrivilegeSelectLogic privilegeSelectLogic;
    @Autowired
    RolePrivilegeService rolePrivilegeService;

    /**
     * 根据角色id和菜单级别查找权限列表
     */
    @Override
    public List<RolePrivilege> queryRolePrivilegeByRole(Role role)
    {
        logger.debug("[RolePrivilegeServiceImpl:queryRolePrivilegeByRole(" + (BeanUtils.isNotNull(role) ? role.toString() :"")
                                                                                                              + ")]");
        if (BeanUtils.isNotNull(role) && null != role.getRoleId())
        {
            List<RolePrivilege> rolePrivilegeList = rolePrivilegeDao.getRolePrivilegeByRoleId(role.getRoleId());
            logger.debug("[RolePrivilegeServiceImpl:queryRolePrivilegeByRole(" + (BeanUtils.isNotNull(rolePrivilegeList) ? Collections3.convertToString(
                rolePrivilegeList, Constant.Common.SEPARATOR) :"") + ")][返回信息]");
            return rolePrivilegeList;
        }
        else
        {
            logger.error("[RolePrivilegeServiceImpl:queryRolePrivilegeByRole(根据角色获取角色权限失败,角色为空)]");
            String[] msgParams = new String[] {"queryRolePrivilegeByRole"};
            ApplicationException ae = new ApplicationException("identity001069", msgParams);
            throw ExceptionUtil.throwException(ae);
        }
    }

    /**
     * 根据菜单id查找权限列表
     */
    @Override
    public List<RolePrivilege> queryRolePrivilegeByMenuId(Long privilegeId)
    {
        logger.debug("[RolePrivilegeServiceImpl:queryRolePrivilegeByMenuId(" + privilegeId + ")]");
        if (null != privilegeId)
        {
            List<RolePrivilege> rolePrivilegeList = rolePrivilegeDao.getRolePrivilegeByPriId(privilegeId);
            logger.debug("[RolePrivilegeServiceImpl:queryRolePrivilegeByMenuId("
                         + (BeanUtils.isNotNull(rolePrivilegeList) ? Collections3.convertToString(
                rolePrivilegeList, Constant.Common.SEPARATOR) :"") + ")][返回信息]");
            return rolePrivilegeList;
        }
        else
        {
            logger.error("[RolePrivilegeServiceImpl:queryRolePrivilegeByMenuId(根据权限ID获取角色权限失败,菜单ID为空)]");
            String[] msgParams = new String[] {"queryRolePrivilegeByMenuId"};
            ApplicationException ae = new ApplicationException("identity001070", msgParams);
            throw ExceptionUtil.throwException(ae);
        }
    }

    /**
     * 根据菜单id和角色id查找权限列表
     */
    @Override
    public List<RolePrivilege> queryRolePrivilegeByParams(Long privilegeId, Long roleId)
    {
        logger.debug("[RolePrivilegeServiceImpl:queryRolePrivilegeByParams(" + privilegeId + ","
                     + roleId + ")]");
        List<RolePrivilege> rolePrivilegeList = rolePrivilegeDao.getRolePrivilegeList(privilegeId,
            roleId);
        logger.debug("[RolePrivilegeServiceImpl:queryRolePrivilegeByParams(" + (BeanUtils.isNotNull(rolePrivilegeList) ? Collections3.convertToString(
            rolePrivilegeList, Constant.Common.SEPARATOR) :"") + ")][返回信息]");
        return rolePrivilegeList;
    }

    @Override
    public List<RolePrivilege> queryRolePrivilegeByRoleId(Long roleId)
    {
        logger.debug("[RolePrivilegeServiceImpl:queryRolePrivilegeByRoleId(" + roleId + ")]");
        List<RolePrivilege> rolePrivilegeList = rolePrivilegeDao.getRolePrivilegeByRoleId(roleId);
        logger.debug("[RolePrivilegeServiceImpl:queryRolePrivilegeByRoleId(" + (BeanUtils.isNotNull(rolePrivilegeList) ? Collections3.convertToString(
            rolePrivilegeList, Constant.Common.SEPARATOR) :"") + ")][返回信息]");
        return rolePrivilegeList;
    }

    @Override
    public List<RolePrivilege> queryRolePrivilegeByRoleList(List<Role> roleList)
    {
        logger.debug("[RolePrivilegeServiceImpl:queryRolePrivilegeByRoleList()]");
        if (BeanUtils.isNotNull(roleList))
        {
            List<RolePrivilege> rolePrivilegeLists = new ArrayList<RolePrivilege>();
            for (Role role : roleList)
            {
                List<RolePrivilege> rolePrivilegeList = new ArrayList<RolePrivilege>();
                rolePrivilegeList = queryRolePrivilegeByRoleId(role.getRoleId());
                if (BeanUtils.isNotNull(rolePrivilegeList))
                {
                    rolePrivilegeLists.addAll(rolePrivilegeList);
                }
            }
            logger.debug("[RolePrivilegeServiceImpl:queryRolePrivilegeByRoleList("
                         + (BeanUtils.isNotNull(rolePrivilegeLists) ? Collections3.convertToString(
                rolePrivilegeLists, Constant.Common.SEPARATOR) :"") + ")][返回信息]");
            return rolePrivilegeLists;
        }
        else
        {
            logger.error("[RolePrivilegeServiceImpl:queryRolePrivilegeByRoleList(根据角色列表获取角色权限失败,角色列表为空)]");
            String[] msgParams = new String[] {"queryRolePrivilegeByRoleList"};
            ApplicationException ae = new ApplicationException("identity001071", msgParams);
            throw ExceptionUtil.throwException(ae);
        }
    }

    @Override
    public List<String> queryPermissionsByRoleList(List<Role> roleList)
    {
        logger.debug("RolePrivilegeServiceImpl:queryPermissionsByRoleList(" + (BeanUtils.isNotNull(roleList) ? Collections3.convertToString(
            roleList, Constant.Common.SEPARATOR) :"") + ")");
        List<String> permissionList = new ArrayList<String>();
        List<RolePrivilege> rolePrivilegeLists = queryRolePrivilegeByRoleList(roleList);
        for (RolePrivilege rolePrivilege : rolePrivilegeLists)
        {
            // 获取privilege里面的permission值
            permissionList.add(rolePrivilege.getPrivilege().getPermissionName());
        }
        logger.debug("[RolePrivilegeServiceImpl:queryPermissionsByRoleList(" + (BeanUtils.isNotNull(permissionList) ? Collections3.convertToString(
            permissionList, Constant.Common.SEPARATOR) :"") + ")][返回信息]");
        return permissionList;
    }

    @Override
    public List<String> queryPermissionsByRoleId(Long roleId)
    {
        logger.debug("RolePrivilegeServiceImpl:queryPermissionsByRoleId(" + roleId + ")");
        List<String> permissionList = new ArrayList<String>();
        List<RolePrivilege> rolePrivilegeLists = queryRolePrivilegeByRoleId(roleId);
        for (RolePrivilege rolePrivilege : rolePrivilegeLists)
        {
            // 获取privilege里面的permission值
            permissionList.add(rolePrivilege.getPrivilege().getPermissionName());
        }
        logger.debug("[RolePrivilegeServiceImpl:queryPermissionsByRoleList(" + (BeanUtils.isNotNull(permissionList) ? Collections3.convertToString(
            permissionList, Constant.Common.SEPARATOR) :"") + ")][返回信息]");
        return permissionList;
    }

    @Override
    public List<RolePrivilege> queryRolePrivilegeByPriId(Long privilegeId)
    {
        logger.debug("[RolePrivilegeServiceImpl:queryRolePrivilegeByPriId(" + privilegeId + ")]");
        List<RolePrivilege> rolePrivilegeList = rolePrivilegeDao.getRolePrivilegeByPriId(privilegeId);
        logger.debug("[RolePrivilegeServiceImpl:queryRolePrivilegeByPriId(" + (BeanUtils.isNotNull(rolePrivilegeList) ? Collections3.convertToString(
            rolePrivilegeList, Constant.Common.SEPARATOR) :"") + ")][返回信息]");
        return rolePrivilegeList;
    }
    /**
     * 根据菜单id列表和角色保存相应的用户角色关联数据
     */
    @Override
    @Transactional
    public String saveRolePrivilegeList(String privilegeidstr, Role role, String updateName)
    {
        try
        {
            logger.debug("[RolePrivilegeServiceImpl:saveRolePrivilegeList(" + privilegeidstr + ","
                         + (BeanUtils.isNotNull(role) ? role.toString() :"") + "," + updateName + ")]");
            if(BeanUtils.isNotNull(role))
            {
	            // 删除原有的权限记录
	            rolePrivilegeService.deleteByRoleId(role.getRoleId());
	            if (StringUtil.isNotBlank(privilegeidstr))
	            {
	                String[] privilegeIdlist = privilegeidstr.split("\\|");
	                List<RolePrivilege> rolePrivilegeList = new ArrayList<RolePrivilege>();
	                int i = 0;
	                while (i < privilegeIdlist.length)
	                {
	                    Long pid = Long.parseLong(privilegeIdlist[i].replace(",", ""));
	                    Privilege privilege = privilegeService.queryPrivilegeById(pid);
	                    Date dt = new Date();
	                    RolePrivilege rp = new RolePrivilege();
	                    rp.setPrivilege(privilege);
	                    rp.setRole(role);
	                    rp.setStatus(Constant.RoleStatus.OPEN_STATUS);
	                    rp.setCreateTime(dt);
	                    rp.setUpdateUser(updateName);
	                    rp.setUpdateTime(dt);
	                    rolePrivilegeList.add(rp);
	                    i++ ;
	                }
	                String result = rolePrivilegeService.saveRolePrivilege(rolePrivilegeList);
	                logger.debug("[RolePrivilegeServiceImpl:saveRolePrivilegeList()return:" + result
	                             + "]");
	                return result;
	            }
            }
            return Constant.Common.FAIL;
        }catch(RpcException e){
            throw ExceptionUtil.throwException(e);
        }
        catch (Exception e)
        {
            logger.error("[RolePrivilegeServiceImpl:saveRolePrivilegeList(" + ExceptionUtil.getStackTraceAsString(e)
                         + ")]");
            String[] msgParams = new String[] {"saveRolePrivilegeList"};
            ApplicationException ae = new ApplicationException("identity001040", msgParams);
            throw ExceptionUtil.throwException(ae);
        }
    }

}