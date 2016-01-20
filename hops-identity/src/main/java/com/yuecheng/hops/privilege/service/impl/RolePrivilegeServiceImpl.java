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
import com.yuecheng.hops.privilege.entity.RolePrivilege;
import com.yuecheng.hops.privilege.repository.RolePrivilegeDao;
import com.yuecheng.hops.privilege.service.RolePrivilegeQueryService;
import com.yuecheng.hops.privilege.service.RolePrivilegeService;


/**
 * 角色菜单权限表逻辑访问层
 * 
 * @author：Jinger
 * @date：2013-09-23
 */
@Service("rolePrivilegeService")
public class RolePrivilegeServiceImpl implements RolePrivilegeService
{
    @Autowired
    RolePrivilegeDao rolePrivilegeDao;

    @Autowired
    RolePrivilegeQueryService rolePrivilegeQueryService;

    static Logger logger = LoggerFactory.getLogger(RolePrivilegeServiceImpl.class);

    /**
     * 添加角色菜单权限
     */
    @Override
    @Transactional
    public RolePrivilege saveRolePrivilege(RolePrivilege rolePrivilege)
    {
        logger.debug("[RolePrivilegeServiceImpl:saveRolePrivilege(" + (BeanUtils.isNotNull(rolePrivilege) ? rolePrivilege.toString() :"")
                                                                                                                                 + ")]");
        if (BeanUtils.isNotNull(rolePrivilege))
        {
        	rolePrivilege = rolePrivilegeDao.save(rolePrivilege);
        }
        logger.debug("[RolePrivilegeServiceImpl:saveRolePrivilege(" + (BeanUtils.isNotNull(rolePrivilege) ? rolePrivilege.toString() :"")
                                                                                                                                 + ")][返回信息]");
        return rolePrivilege;
    }

    @Override
    @Transactional
    public String saveRolePrivilege(List<RolePrivilege> rolePrivilegeList)
    {
        try
        {
            logger.debug("[RolePrivilegeServiceImpl:saveRolePrivilege(List:"
                         + (BeanUtils.isNotNull(rolePrivilegeList) ? rolePrivilegeList.toString() :"")
                                                                                              + ")]");
            if (BeanUtils.isNotNull(rolePrivilegeList) && rolePrivilegeList.size() > 0)
            {
                int i = 0;
                while (i < rolePrivilegeList.size())
                {
                    RolePrivilege roleMenuPrivilege = rolePrivilegeList.get(i);
//                    this.saveRolePrivilege(roleMenuPrivilege);
                    rolePrivilegeDao.save(roleMenuPrivilege);
                    i++ ;
                }
                logger.debug("[RolePrivilegeServiceImpl:saveRolePrivilege()List-return:SUCCESS]");
                return PrivilegeConstants.SUCCESS;
            }
            logger.debug("[RolePrivilegeServiceImpl:saveRolePrivilege()List-return:FAIL]");
            return PrivilegeConstants.FAIL;
        }
        catch (Exception e)
        {
            logger.error("[RolePrivilegeServiceImpl:saveRolePrivilege(" + ExceptionUtil.getStackTraceAsString(e) + ")]");
            String[] msgParams = new String[] {"saveRolePrivilege"};
            ApplicationException ae = new ApplicationException("identity001036", msgParams);
            throw ExceptionUtil.throwException(ae);
        }
    }

    /**
     * 删除角色菜单权限
     */
    @Override
    @Transactional
    public void deleteRolePrivilege(Long rolePrivilegeId)
    {
        logger.debug("[RolePrivilegeServiceImpl:deleteRolePrivilege(" + rolePrivilegeId + ")]");
        if (null != rolePrivilegeId)
        {
            rolePrivilegeDao.delete(rolePrivilegeId);
        }
    }

    /**
     * 查找角色菜单权限
     */
    @Override
    public RolePrivilege queryRolePrivilegeById(Long rolePrivilegeId)
    {
        logger.debug("[RolePrivilegeServiceImpl:queryRolePrivilegeById(" + rolePrivilegeId + ")]");
        if (null != rolePrivilegeId)
        {
            RolePrivilege rolePrivilege = rolePrivilegeDao.findOne(rolePrivilegeId);
            logger.debug("[RolePrivilegeServiceImpl:queryRolePrivilegeById(" + (BeanUtils.isNotNull(rolePrivilege) ? rolePrivilege.toString() :"")
                                                                                                                                  + ")]");
            return rolePrivilege;
        }
        else
        {
            logger.error("[RolePrivilegeServiceImpl:queryRolePrivilegeById(根据角色权限ID获取角色权限失败,角色权限ID为空)]");
            String[] msgParams = new String[] {"queryRolePrivilegeById"};
            ApplicationException ae = new ApplicationException("identity001072", msgParams);
            throw ExceptionUtil.throwException(ae);
        }
    }

    /**
     * 查找角色菜单权限列表
     */
    @Override
    public List<RolePrivilege> queryAllRolePrivilege()
    {
        List<RolePrivilege> rolePrivilegeList = rolePrivilegeDao.selectAll();
        logger.debug("[RolePrivilegeServiceImpl:queryAllRolePrivilege(" + (BeanUtils.isNotNull(rolePrivilegeList) ? Collections3.convertToString(
            rolePrivilegeList, Constant.Common.SEPARATOR) :"") + ")][返回信息]");
        return rolePrivilegeList;
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
    public YcPage<RolePrivilege> queryRolePrivilege(Map<String, Object> searchParams,
                                                    int pageNumber, int pageSize, BSort bsort)
    {
        logger.debug("[RolePrivilegeServiceImpl:queryRolePrivilege(" + (BeanUtils.isNotNull(searchParams) ? searchParams.toString() :"")
                                                                                                                        + ","
                                                                                                                        + pageNumber
                                                                                                                        + ","
                                                                                                                        + pageSize
                                                                                                                        + ")]");
        Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
        String orderCloumn = bsort == null ? Constant.Sort.ID : bsort.getCloumn();
        String orderDirect = bsort == null ? Constant.Sort.DESC : bsort.getDirect().toString();
        Sort sort = new Sort(Direction.valueOf(Direction.class, orderDirect), orderCloumn);
        YcPage<RolePrivilege> ycPage = PageUtil.queryYcPage(rolePrivilegeDao, filters, pageNumber,
            pageSize, sort, RolePrivilege.class);
        List<RolePrivilege> rolePrivilegeList = ycPage.getList();
        logger.debug("[RolePrivilegeServiceImpl:queryRolePrivilege(" + (BeanUtils.isNotNull(rolePrivilegeList) ? Collections3.convertToString(
            rolePrivilegeList, Constant.Common.SEPARATOR) :"") + ")][返回信息]");
        return ycPage;
    }

    @Override
    @Transactional
    public void deleteByRoleId(Long roleId)
    {
        logger.debug("[RolePrivilegeServiceImpl:deleteByRoleId(" + roleId + ")]");
        List<RolePrivilege> rolePrivilegeList = rolePrivilegeQueryService.queryRolePrivilegeByRoleId(roleId);
        deleteRolePrivilegeList(rolePrivilegeList);

    }
    @Override
    @Transactional
    public void deleteByPrivilegeId(Long privilegeId)
    {
        logger.debug("[RolePrivilegeServiceImpl:deleteByPrivilegeId(" + privilegeId + ")]");
        List<RolePrivilege> rolePrivilegeList = rolePrivilegeQueryService.queryRolePrivilegeByPriId(privilegeId);
        deleteRolePrivilegeList(rolePrivilegeList);
    }
    @Override
    @Transactional
    public void deleteRolePrivilegeList(List<RolePrivilege> rolePrivilegeList)
    {
    	if (BeanUtils.isNotNull(rolePrivilegeList))
        {
            int i = 0;
            while (i < rolePrivilegeList.size())
            {
                RolePrivilege rolePrivilege = rolePrivilegeList.get(i);
//                this.deleteRolePrivilege(rolePrivilege.getId());
                rolePrivilegeDao.delete(rolePrivilege.getId());
                i++ ;
            }
        }
    }
    
    
    
}
