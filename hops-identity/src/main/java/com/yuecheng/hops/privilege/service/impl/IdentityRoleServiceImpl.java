package com.yuecheng.hops.privilege.service.impl;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springside.modules.persistence.SearchFilter;

import com.alibaba.dubbo.rpc.RpcException;
import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.Constant.PrivilegeConstants;
import com.yuecheng.hops.common.enump.IdentityType;
import com.yuecheng.hops.common.exception.ApplicationException;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.query.BSort;
import com.yuecheng.hops.common.query.PageUtil;
import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.common.utils.Collections3;
import com.yuecheng.hops.common.utils.StringUtil;
import com.yuecheng.hops.privilege.entity.IdentityRole;
import com.yuecheng.hops.privilege.entity.Role;
import com.yuecheng.hops.privilege.repository.IdentityRoleDao;
import com.yuecheng.hops.privilege.repository.RoleDao;
import com.yuecheng.hops.privilege.service.IdentityRoleQueryService;
import com.yuecheng.hops.privilege.service.IdentityRoleSelectLogic;
import com.yuecheng.hops.privilege.service.IdentityRoleService;


/**
 * 用户角色表逻辑访问层
 * 
 * @author：Jinger
 * @date：2013-09-27
 */

@Service("identityRoleService")
public class IdentityRoleServiceImpl implements IdentityRoleService
{
    @Autowired
    IdentityRoleDao identityRoleDao;

    @Autowired
    RoleDao roleDao;

    @Autowired
    IdentityRoleQueryService identityRoleQueryService;

    static Logger logger = LoggerFactory.getLogger(IdentityRoleServiceImpl.class);

    @Autowired
    IdentityRoleSelectLogic irService;

    /**
     * 增加角色权限
     */
    @Override
    public IdentityRole saveIdentityRole(IdentityRole identityRole)
    {
        logger.debug("[IdentityRoleServiceImpl:saveIdentityRole(" + (BeanUtils.isNotNull(identityRole) ? identityRole.toString() :"")
                                                                                                                     + ")]");
        if (BeanUtils.isNotNull(identityRole))
        {
            identityRole = identityRoleDao.save(identityRole);
            logger.debug("[IdentityRoleServiceImpl:saveIdentityRole(" + (BeanUtils.isNotNull(identityRole) ? identityRole.toString() :"")
                                                                                                                         + ")][返回信息]");
        }
        return identityRole;
    }

    /**
     * 批量增加角色权限
     */
    @Override
    public String saveIdentityRole(List<IdentityRole> identityRoleList)
    {
        try
        {
            logger.debug("[IdentityRoleServiceImpl:saveIdentityRole(List:" + (BeanUtils.isNotNull(identityRoleList) ? Collections3.convertToString(
                identityRoleList, Constant.Common.SEPARATOR) :"") + ")]");
            if (BeanUtils.isNotNull(identityRoleList) && 0 < identityRoleList.size())
            {
                int i = 0;
                while (i < identityRoleList.size())
                {
                    IdentityRole identityRole = identityRoleList.get(i);
                    this.saveIdentityRole(identityRole);
                    i++ ;
                }
                logger.debug("[IdentityRoleServiceImpl:saveIdentityRole(批量保存成功)]");
                return PrivilegeConstants.SUCCESS;
            }
            logger.debug("[IdentityRoleServiceImpl:saveIdentityRole(批量保存失败)]");
            return PrivilegeConstants.FAIL;
        }
        catch (Exception e)
        {
            logger.error("[IdentityRoleSelectLogicImpl:getAllIdentityRole(" + ExceptionUtil.getStackTraceAsString(e)
                         + ")]");
            String[] msgParams = new String[] {"saveIdentityRole"};
            ApplicationException ae = new ApplicationException("identity001009", msgParams);
            throw ExceptionUtil.throwException(ae);
        }
    }

    /**
     * 删除角色权限
     */
    @Override
    public void deleteIdentityRole(Long identityRoleId)
    {
        logger.debug("[IdentityRoleServiceImpl:deleteIdentityRole(" + identityRoleId + ")]");
        if (null != identityRoleId)
        {
            identityRoleDao.delete(identityRoleId);
        }
    }

    /**
     * 查找角色权限
     */
    @Override
    public IdentityRole queryIdentityRoleById(Long identityRoleId)
    {
        logger.debug("[IdentityRoleServiceImpl:queryIdentityRoleById(" + identityRoleId + ")]");
        if (null != identityRoleId)
        {
            IdentityRole identityRole = identityRoleDao.findOne(identityRoleId);
            logger.debug("[IdentityRoleServiceImpl:queryIdentityRoleById(" + (BeanUtils.isNotNull(identityRole) ? identityRole.toString() :"")
                                                                                                                              + ")][返回信息]");
            return identityRole;
        }
        else
        {
            logger.error("[IdentityRoleSelectLogicImpl:queryIdentityRoleById(identityRoleId为空)]");
            String[] msgParams = new String[] {"queryIdentityRoleById"};
            ApplicationException ae = new ApplicationException("identity001059", msgParams);
            throw ExceptionUtil.throwException(ae);
        }
    }

    public IdentityRole queryIdentityRoleByIdentity(Long identityRoleId, Long identityId,
                                                    IdentityType identityType)
    {
        logger.debug("[IdentityRoleServiceImpl:queryIdentityRoleByIdentity(" + identityRoleId
                     + "," + identityId + "," + (BeanUtils.isNotNull(identityType) ? identityType.toString() :"")
                                                                                                 + ")]");
        IdentityRole identityRole = identityRoleDao.findOneByMany(identityRoleId, identityId,
            identityType);
        logger.debug("[IdentityRoleServiceImpl:queryIdentityRoleByIdentity(" + (BeanUtils.isNotNull(identityRole) ? identityRole.toString() :"")
                                                                                                                                + ")][返回信息]");
        return identityRole;
    }

    /**
     * 查询角色权限列表
     */
    @Override
    public List<IdentityRole> queryAllIdentityRole()
    {
        List<IdentityRole> identityRoleList = identityRoleDao.selectAll();
        logger.debug("[IdentityRoleServiceImpl:queryAllIdentityRole(" + (BeanUtils.isNotNull(identityRoleList) ? Collections3.convertToString(
            identityRoleList, Constant.Common.SEPARATOR) :"") + ")][返回信息]");
        return identityRoleList;
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
    public YcPage<IdentityRole> queryIdentityRole(Map<String, Object> searchParams,
                                                  int pageNumber, int pageSize, BSort bsort)
    {
        logger.debug("[IdentityRoleServiceImpl:queryIdentityRole(" + (BeanUtils.isNotNull(searchParams) ? searchParams.toString() :"")
                                                                                                                      + ","
                                                                                                                      + pageNumber
                                                                                                                      + ","
                                                                                                                      + pageSize
                                                                                                                      + ")]");
        Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
        String orderCloumn = bsort == null ? Constant.Sort.ID : bsort.getCloumn();
        String orderDirect = bsort == null ? Constant.Sort.DESC : bsort.getDirect().toString();
        Sort sort = new Sort(Direction.valueOf(Direction.class, orderDirect), orderCloumn);
        YcPage<IdentityRole> ycPage = PageUtil.queryYcPage(identityRoleDao, filters, pageNumber,
            pageSize, sort, IdentityRole.class);
        List<IdentityRole> identityRoleList = ycPage.getList();
        logger.debug("[IdentityRoleServiceImpl:queryIdentityRole(" + (BeanUtils.isNotNull(identityRoleList) ? Collections3.convertToString(
            identityRoleList, Constant.Common.SEPARATOR) :"") + ")][返回信息]");
        return ycPage;
    }

    /**
     * 根据用户ID和用户类型以及角色列表保存相应的用户角色关联数据
     */
    @Override
    public String saveIdentityRoleList(String[] roleId, Long identityId,
                                       IdentityType identityType, String updateName)
    {
        try
        {
            logger.debug("[IdentityRoleServiceImpl:saveIdentityRoleList(" + StringUtil.getToString(roleId,Constant.Common.SEPARATOR) + ","
                         + identityId + "," + (BeanUtils.isNotNull(identityType) ? identityType.toString() :"")
                                                                                               + ","
                                                                                               + updateName
                                                                                               + ")]");
            if (BeanUtils.isNotNull(identityId))
            {
                if (BeanUtils.isNotNull(roleId) && 0 < roleId.length)
                {
                    List<IdentityRole> oldirList = identityRoleQueryService.queryIdentityRole(
                        identityId, identityType);
                    List<IdentityRole> newirList = new ArrayList<IdentityRole>();
                    int i = 0;
                    while (i < roleId.length)
                    {
                        if (BeanUtils.isNotNull(roleId[i]))
                        {
                            Role role = roleDao.findOne(Long.parseLong(roleId[i].replace(",",
                                StringUtil.initString())));
                            Date dt = new Date();
                            IdentityRole identityRole = new IdentityRole();
                            identityRole.setIdentityId(identityId);
                            identityRole.setIdentityType(identityType);
                            identityRole.setRole(role);
                            identityRole.setStatus(Constant.IdentityStatus.OPEN_STATUS);
                            identityRole.setCreateTime(dt);
                            identityRole.setUpdateName(updateName);
                            identityRole.setUpdateTime(dt);
                            newirList.add(identityRole);
                        }
                        i++ ;
                    }
                    List<IdentityRole> identityRoleList = irService.queryAllUpdateIdentityRole(
                        newirList, oldirList, updateName);
                    String result = saveIdentityRole(identityRoleList);
                    return result;
                }
            }
            return null;
        }catch(RpcException e){
            throw ExceptionUtil.throwException(e);
        }
        catch (Exception e)
        {
            logger.error("[IdentityRoleSelectLogicImpl:getIRSelectListByRoleType("
                         + ExceptionUtil.getStackTraceAsString(e) + ")]");
            String[] msgParams = new String[] {"getIRSelectListByRoleType"};
            ApplicationException ae = new ApplicationException("identity001013", msgParams);
            throw ExceptionUtil.throwException(ae);
        }
    }

}
