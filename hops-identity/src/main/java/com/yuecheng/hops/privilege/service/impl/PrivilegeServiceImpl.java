package com.yuecheng.hops.privilege.service.impl;


import java.util.ArrayList;
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
import com.yuecheng.hops.privilege.entity.Menu;
import com.yuecheng.hops.privilege.entity.Privilege;
import com.yuecheng.hops.privilege.entity.RolePrivilege;
import com.yuecheng.hops.privilege.repository.PrivilegeDao;
import com.yuecheng.hops.privilege.service.PrivilegeService;
import com.yuecheng.hops.privilege.service.RolePrivilegeQueryService;
import com.yuecheng.hops.privilege.service.RolePrivilegeService;


/**
 * 权限表逻辑访问层
 * 
 * @author：Jinger
 * @date：2014-08-29
 */

@Service("privilegeService")
public class PrivilegeServiceImpl implements PrivilegeService
{

    @Autowired
    PrivilegeDao privilegeDao;

    @Autowired
    private RolePrivilegeService rolePrivilegeService;
    @Autowired
    private RolePrivilegeQueryService rolePrivilegeQueryService;
    private static Logger logger = LoggerFactory.getLogger(PrivilegeServiceImpl.class);

    @Override
    @Transactional
    public Privilege savaPrivilege(Privilege privilege)
    {
        logger.debug("[PrivilegeServiceImpl:savaPrivilege(" + (BeanUtils.isNotNull(privilege) ? privilege.toString() :"")
                                                                                                         + ")]");
        if (BeanUtils.isNotNull(privilege))
        {
            privilege = privilegeDao.save(privilege);
        }
        logger.debug("[PrivilegeServiceImpl:savaPrivilege(" + (BeanUtils.isNotNull(privilege) ? privilege.toString() :"")
                                                                                                         + ")][返回信息]");
        return privilege;
    }

    @Override
    @Transactional
    public void delPrivilege(Long privilegeId)
    {
        logger.debug("[PrivilegeServiceImpl:delPrivilege(" + privilegeId + ")]");
        if (null != privilegeId)
        {
	        List<RolePrivilege> rolePrivilegeList=rolePrivilegeQueryService.queryRolePrivilegeByPriId(privilegeId);
	        if(BeanUtils.isNotNull(rolePrivilegeList)&&rolePrivilegeList.size()>0)
	        {
	            //同步删除角色权限表中的数据
	        	rolePrivilegeService.deleteRolePrivilegeList(rolePrivilegeList);
	        }
            privilegeDao.delete(privilegeId);
        }
    }

    @Override
    @Transactional
    public Privilege updatePrivilege(Privilege privilege)
    {
        logger.debug("[PrivilegeServiceImpl:updatePrivilege(" + (BeanUtils.isNotNull(privilege) ? privilege.toString() :"")
                                                                                                           + ")]");
        if (BeanUtils.isNotNull(privilege))
        {
            privilege = privilegeDao.save(privilege);
        }
        logger.debug("[PrivilegeServiceImpl:updatePrivilege(" + (BeanUtils.isNotNull(privilege) ? privilege.toString() :"")
                                                                                                           + ")][返回信息]");
        return privilege;
    }

    @Override
    public Privilege queryPrivilegeById(Long privilegeId)
    {
        logger.debug("[PrivilegeServiceImpl:queryPrivilegeById(" + privilegeId + ")]");
        if (null != privilegeId)
        {
            Privilege privilege = privilegeDao.findOne(privilegeId);
            logger.debug("[PrivilegeServiceImpl:queryPrivilegeById(" + (BeanUtils.isNotNull(privilege) ? privilege.toString() :"")
                                                                                                                  + ")][返回信息]");
            return privilege;
        }
        else
        {
            logger.error("[PrivilegeServiceImpl:queryPrivilegeById(权限ID为空)]");
            String[] msgParams = new String[] {"queryPrivilegeById"};
            ApplicationException ae = new ApplicationException("identity001063", msgParams);
            throw ExceptionUtil.throwException(ae);
        }
    }

    @Override
    public List<Privilege> queryPrivilegeByPId(Long parentPrivilegeId)
    {
        logger.debug("[PrivilegeServiceImpl:queryPrivilegeByPId(" + parentPrivilegeId + ")]");
        if (null != parentPrivilegeId)
        {
            List<Privilege> privilegeList = privilegeDao.getPrivilegeList(parentPrivilegeId);
            logger.debug("[PrivilegeServiceImpl:queryPrivilegeByPId(" + (BeanUtils.isNotNull(privilegeList) ? Collections3.convertToString(
                privilegeList, Constant.Common.SEPARATOR) :"") + ")][返回信息]");
            return privilegeList;
        }
        else
        {
            logger.error("[PrivilegeServiceImpl:queryPrivilegeByPId(父级权限ID为空)]");
            String[] msgParams = new String[] {"queryPrivilegeByPId"};
            ApplicationException ae = new ApplicationException("identity001064", msgParams);
            throw ExceptionUtil.throwException(ae);
        }
    }

    @Override
    public YcPage<Privilege> queryPagePrivilege(Map<String, Object> searchParams, int pageNumber,
                                                int pageSize, BSort bsort)
    {
        logger.debug("[PrivilegeServiceImpl:queryPagePrivilege(," + pageNumber + "," + pageSize
                     + ")]");
        Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
        String orderCloumn = bsort == null ? Constant.Sort.ID : bsort.getCloumn();
        String orderDirect = bsort == null ? Constant.Sort.DESC : bsort.getDirect().toString();
        Sort sort = new Sort(Direction.valueOf(Direction.class, orderDirect), orderCloumn);
        YcPage<Privilege> ycPage = PageUtil.queryYcPage(privilegeDao, filters, pageNumber,
            pageSize, sort, Menu.class);
        List<Privilege> privilegeList = ycPage.getList();
        privilegeList=getParentName(privilegeList);
        logger.debug("[PrivilegeServiceImpl:queryPagePrivilege(" + (BeanUtils.isNotNull(privilegeList) ? Collections3.convertToString(
            privilegeList, Constant.Common.SEPARATOR) :"") + ")][返回信息]");
        return ycPage;
    }
    
    public List<Privilege> getParentName(List<Privilege> privileges)
    {
    	List<Privilege> backList=new ArrayList<Privilege>();
    	for (Privilege privilege : privileges) {
    		Privilege pri=privilegeDao.findOne(privilege.getParentPrivilegeId());
    		if(BeanUtils.isNotNull(pri))
    		{
    			privilege.setParentPrivilegeName(pri.getPrivilegeName());
    		}else
    		{
    			privilege.setParentPrivilegeName(privilege.getPrivilegeName());
    		}
			backList.add(privilege);
		}
    	return backList;
    }

    // 得到全部权限信息
    public List<Privilege> queryAllPrivilege()
    {
        List<Privilege> privilegeList = privilegeDao.getAllPrivilegeList();
        logger.debug("[PrivilegeServiceImpl:queryAllPrivilege(" + (BeanUtils.isNotNull(privilegeList) ? Collections3.convertToString(
            privilegeList, Constant.Common.SEPARATOR) :"") + ")][返回信息]");
        return privilegeList;
    }

}
