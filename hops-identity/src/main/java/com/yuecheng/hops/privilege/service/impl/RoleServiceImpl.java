package com.yuecheng.hops.privilege.service.impl;


/**
 * 角色表逻辑访问层
 * 
 * @author：Jinger
 * @date：2013-09-17
 */

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
import com.yuecheng.hops.privilege.entity.Role;
import com.yuecheng.hops.privilege.repository.RoleDao;
import com.yuecheng.hops.privilege.service.RoleMenuService;
import com.yuecheng.hops.privilege.service.RolePrivilegeService;
import com.yuecheng.hops.privilege.service.RoleService;


@Service("roleService")
public class RoleServiceImpl implements RoleService
{

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private RolePrivilegeService rolePrivilegeService;

    @Autowired
    private RoleMenuService roleMenuService;

    private static Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);

    /**
     * 添加角色
     */
    @Override
    @Transactional
    public Role saveRole(Role role)
    {
        logger.debug("[RoleServiceImpl:saveRole(" + (BeanUtils.isNotNull(role) ? role.toString() :"") + ")]");
        if (BeanUtils.isNotNull(role))
        {
            role = roleDao.save(role);
        }
        logger.debug("[RoleServiceImpl:saveRole(" + (BeanUtils.isNotNull(role) ? role.toString() :"")
                                                                                     + ")][返回信息]");
        return role;
    }

    /**
     * 删除角色
     */
    @Override
    @Transactional
    public void deleteRole(Long roleId)
    {
        logger.debug("[RoleServiceImpl:deleteRole(" + roleId + ")]");
        if (BeanUtils.isNotNull(roleId))
        {
        	roleMenuService.deleteRoleMenuByRoleId(roleId);
            rolePrivilegeService.deleteByRoleId(roleId);
            roleDao.delete(roleId);
        }
    }

    /**
     * 更新角色
     */
    @Override
    @Transactional
    public void updateRole(Long roleId, String status)
    {
        logger.debug("[RoleServiceImpl:updateRole(" + roleId + "," + status + ")]");
        Role role = roleDao.findOne(roleId);
        role.setStatus(status);
        roleDao.save(role);
    }

    /**
     * 根据ID获取角色信息
     */
    @Override
    public Role queryRoleById(Long roleId)
    {
        logger.debug("[RoleServiceImpl:queryRoleById(" + roleId + ")]");
        if (BeanUtils.isNotNull(roleId))
        {
            Role role = roleDao.findOne(roleId);
            logger.debug("[RoleServiceImpl:queryRoleById(" + (BeanUtils.isNotNull(role) ? role.toString() :"")
                                                                                              + ")][返回信息]");
            return role;
        }
        else
        {
            logger.error("[RoleServiceImpl:queryRoleById(根据角色ID获取角色失败,角色ID为空)]");
            String[] msgParams = new String[] {"queryRoleById"};
            ApplicationException ae = new ApplicationException("identity001073", msgParams);
            throw ExceptionUtil.throwException(ae);
        }
    }

    /**
     * 获取角色所有列表信息
     */
    @Override
    public List<Role> queryRoleListByStatus(String status)
    {
        logger.debug("[RoleServiceImpl:queryRoleListByStatus(" + status + ")]");
        List<Role> roleList = roleDao.getRoleListByStatus(status);
        logger.debug("[RoleServiceImpl:getRoleListByStatus(" + (BeanUtils.isNotNull(roleList) ? Collections3.convertToString(
            roleList, Constant.Common.SEPARATOR) :"") + ")][返回信息]");
        return roleList;
    }

    /**
     * 分页获取角色列表信息
     */
    @Override
    public YcPage<Role> queryRole(Map<String, Object> searchParams, int pageNumber, int pageSize,
                                  BSort bsort)
    {
        logger.debug("[RoleServiceImpl:queryRole(" + (BeanUtils.isNotNull(searchParams) ? searchParams.toString() :"")
                                                                                                      + ","
                                                                                                      + pageNumber
                                                                                                      + ","
                                                                                                      + pageSize
                                                                                                      + ")]");
        Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
        String orderCloumn = bsort == null ? Constant.Sort.ID : bsort.getCloumn();
        String orderDirect = bsort == null ? Constant.Sort.DESC : bsort.getDirect().toString();
        Sort sort = new Sort(Direction.valueOf(Direction.class, orderDirect), orderCloumn);
        YcPage<Role> ycPage = PageUtil.queryYcPage(roleDao, filters, pageNumber, pageSize, sort,
            Role.class);
        List<Role> roleList = ycPage.getList();
        logger.debug("[RoleServiceImpl:queryRole(" + (BeanUtils.isNotNull(roleList) ? Collections3.convertToString(
            roleList, Constant.Common.SEPARATOR) :"") + ")][返回信息]");
        return ycPage;
    }

    @Override
    public List<Role> queryRoleListByRoleType(String roleType)
    {
        logger.debug("[RoleServiceImpl:queryRoleListByRoleType(" + roleType + ")]");
        List<Role> roleList = roleDao.getRoleListByRoleType(roleType);
        logger.debug("[RoleServiceImpl:queryRoleListByRoleType(" + (BeanUtils.isNotNull(roleList) ? Collections3.convertToString(
            roleList, Constant.Common.SEPARATOR) :"") + ")][返回信息]");
        return roleList;
    }
}
