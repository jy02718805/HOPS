package com.yuecheng.hops.mportal.web.privilege;


/**
 * 角色界面控制层
 * 
 * @author：Jinger
 * @date：2013-10-04
 */
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springside.modules.persistence.SearchFilter.Operator;

import com.alibaba.dubbo.rpc.RpcException;
import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.Constant.RoleType;
import com.yuecheng.hops.common.EntityConstant;
import com.yuecheng.hops.common.enump.PortalType;
import com.yuecheng.hops.common.query.BSort;
import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.common.utils.StringUtil;
import com.yuecheng.hops.mportal.constant.PageConstant;
import com.yuecheng.hops.mportal.web.BaseControl;
import com.yuecheng.hops.privilege.entity.MenuSelect;
import com.yuecheng.hops.privilege.entity.Privilege;
import com.yuecheng.hops.privilege.entity.Role;
import com.yuecheng.hops.privilege.entity.RoleMenu;
import com.yuecheng.hops.privilege.entity.RolePrivilege;
import com.yuecheng.hops.privilege.service.MenuService;
import com.yuecheng.hops.privilege.service.PrivilegeService;
import com.yuecheng.hops.privilege.service.RoleMenuQueryService;
import com.yuecheng.hops.privilege.service.RoleMenuService;
import com.yuecheng.hops.privilege.service.RolePrivilegeQueryService;
import com.yuecheng.hops.privilege.service.RolePrivilegeService;
import com.yuecheng.hops.privilege.service.RoleService;


@Controller
@RequestMapping(value = "/Role")
public class RoleController extends BaseControl
{
    @Autowired
    private RoleService roleService;

    @Autowired
    private RolePrivilegeService rolePrivilegeService;

    @Autowired
    private RolePrivilegeQueryService rolePrivilegeQueryService;

    @Autowired
    private RoleMenuService roleMenuService;

    @Autowired
    private RoleMenuQueryService roleMenuQueryService;

    @Autowired
    private MenuService menuService;

    @Autowired
    public PrivilegeService privilegeService;

    private static Logger logger = LoggerFactory.getLogger(RoleController.class);

    /**
     * 进入角色添加页面
     * 
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addRole(ModelMap model)
    {
        try
        {
            return PageConstant.PAGE_ROLE_ADD;
        }
        catch (RpcException e)
        {
            logger.debug("[RoleController:addRole(GET)]" + e.getMessage());
            model.put("message", "操作失败");
            model.put("canback", true);
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
    }

    /**
     * 添加角色
     * 
     * @param role
     * @param result
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String saveRole(@ModelAttribute("role")
    Role role, ModelMap model, BindingResult result)
    {
        try
        {
            logger.info("[RoleController:saveRole(" + role != null ? role.toString() : null + ")]");
            com.yuecheng.hops.identity.entity.operator.Operator loginPerson = getLoginUser();
            role.setCreateTime(new Date());
            role.setUpdateUser(loginPerson.getOperatorName());
            role.setUpdateTime(new Date());
            role = roleService.saveRole(role);
            if (role != null)
            {
                model.put("message", "操作成功");
                model.put("canback", false);
                model.put("next_url", "Role/list");
                model.put("next_msg", "角色列表");
            }
            else
            {
                model.put("message", "操作失败");
                model.put("canback", true);
            }
        }
        catch (RpcException e)
        {
            logger.debug("[RoleController:saveRole()]" + e.getMessage());
            model.put("message", "操作失败");
            model.put("canback", true);
        }
        return PageConstant.PAGE_COMMON_NOTIFY;
    }

    /**
     * 查看角色
     * 
     * @param role
     * @param result
     * @return
     */
    @RequestMapping(value = "/role_view", method = RequestMethod.GET)
    public String viewRole(@RequestParam("id")
    String id, ModelMap model)
    {
        try
        {
            logger.info("[RoleController:viewRole(" + id + ")]");
            Role role = roleService.queryRoleById(Long.parseLong(id));
            model.addAttribute("role", role);
            return PageConstant.PAGE_ROLE_VIEW;
        }
        catch (RpcException e)
        {
            logger.debug("[RoleController:viewRole()]" + e.getMessage());
            model.put("message", "操作失败");
            model.put("canback", true);
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
    }

    /**
     * 删除角色
     * 
     * @param role
     * @param result
     * @return
     */
    @RequestMapping(value = "/role_delete", method = RequestMethod.GET)
    public String deleteRole(@RequestParam("id")
    String id, ModelMap model)
    {
        try
        {
            logger.info("[RoleController:deleteRole(" + id + ")]");
            roleService.deleteRole(Long.parseLong(id));
            model.put("message", "操作成功");
            model.put("canback", false);
            model.put("next_url", "Role/list");
            model.put("next_msg", "角色列表");
        }
        catch (RpcException e)
        {
            logger.debug("[RoleController:deleteRole()]" + e.getMessage());
            model.put("message", "操作失败");
            model.put("canback", true);
        }
        return PageConstant.PAGE_COMMON_NOTIFY;
    }

    /**
     * 进入角色编辑界面
     * 
     * @param role
     * @param result
     * @return
     */
    @RequestMapping(value = "/role_edit", method = RequestMethod.GET)
    public String editRole(@RequestParam("id")
    String id, ModelMap model)
    {
        try
        {
            logger.info("[RoleController:editRole(" + id + ")]");
            Role role = roleService.queryRoleById(Long.parseLong(id));
            model.addAttribute("role", role);
            return PageConstant.PAGE_ROLE_EDIT;
        }
        catch (RpcException e)
        {
            logger.debug("[RoleController:editRole()]" + e.getMessage());
            model.put("message", "操作失败");
            model.put("canback", true);
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
    }

    /**
     * 编辑角色后保存
     * 
     * @param role
     * @param result
     * @return
     */
    @RequestMapping(value = "/role_edit", method = RequestMethod.POST)
    public String editRoleDo(@ModelAttribute("role")
    Role role, ModelMap model)
    {
        try
        {
            logger.info("[RoleController:editRoleDo(" + role != null ? role.toString() : null
                                                                                         + ")]");
            com.yuecheng.hops.identity.entity.operator.Operator loginPerson = getLoginUser();
            Role role1 = roleService.queryRoleById(role.getRoleId());
            role.setCreateTime(role1.getCreateTime());
            role.setUpdateTime(new Date());
            role.setUpdateUser(loginPerson.getOperatorName());
            role = roleService.saveRole(role);
            if (role != null)
            {
                model.put("message", "操作成功");
                model.put("canback", false);
                model.put("next_url", "Role/list");
                model.put("next_msg", "角色列表");
            }
            else
            {
                model.put("message", "操作失败");
                model.put("canback", true);
            }
        }
        catch (RpcException e)
        {
            logger.debug("[RoleController:editRoleDo()]" + e.getMessage());
            model.put("message", "操作失败");
            model.put("canback", true);
        }
        return PageConstant.PAGE_COMMON_NOTIFY;
    }

    /**
     * 进入角色列表页面
     * 
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String listdRole(@RequestParam(value = "roleName", defaultValue = "")
    String roleName, @RequestParam(value = "roleType", defaultValue = "")
    String roleType, @RequestParam(value = "page", defaultValue = "1")
    int pageNumber, @RequestParam(value = "pageSize", defaultValue = PageConstant.LIST_PAGE_SIZE)
    int pageSize, @RequestParam(value = "sortType", defaultValue = PageConstant.LIST_ORDER_SORT)
    String sortType, ModelMap model, ServletRequest request)
    {
        try
        {
            roleName = new String(roleName.getBytes("ISO-8859-1"), "UTF-8");
            logger.info("[RoleController:listdRole(" + roleName + "," + roleType + ")]");
            Map<String, Object> searchParams = new HashMap<String, Object>();
            if (StringUtil.isNotBlank(roleName))
            {
                searchParams.put(Operator.LIKE + "_" + EntityConstant.Role.ROLE_NAME, roleName);
            }
            if (StringUtil.isNotBlank(roleType))
            {
                searchParams.put(Operator.EQ + "_" + EntityConstant.Role.ROLE_TYPE, roleType);
            }
            BSort bsort = new BSort(BSort.Direct.DESC, EntityConstant.Role.CREATE_TIME);
            YcPage<Role> page_list = roleService.queryRole(searchParams, pageNumber, pageSize,
                bsort);
            List<Role> list = page_list.getList();
            String pagetotal = page_list.getPageTotal() + "";
            String countTotal = page_list.getCountTotal() + "";
            model.addAttribute("mlist", list);
            model.addAttribute("page", pageNumber);
            model.addAttribute("pageSize", pageSize);
            model.addAttribute("pagetotal", pagetotal);
            model.addAttribute("counttotal", countTotal);
            model.addAttribute("roleName", roleName);
            return PageConstant.PAGE_ROLE_LIST;
        }
        catch (RpcException e)
        {
            logger.debug("[RoleController:listdRole()]" + e.getMessage());
            model.put("message", "操作失败");
            model.put("canback", true);
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
        catch (Exception e)
        {
            logger.debug("[RoleController:listdRole()]" + e.getMessage());
            model.put("message", "操作失败");
            model.put("canback", true);
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
    }

    /**
     * 进入角色菜单权限界面
     * 
     * @return
     */
    @RequestMapping(value = "/role_addmenu", method = RequestMethod.GET)
    public String listRoleMenu(@RequestParam("id")
    String id, ModelMap model)
    {
        try
        {
            logger.info("[RoleController:listRoleMenu(" + id + ")]");
            Role role = roleService.queryRoleById(Long.parseLong(id));
            List<Role> roleList = new ArrayList<Role>();
            roleList.add(role);
            List<MenuSelect> menuSelectList0 = roleMenuQueryService.queryMenuSelectByParams(
                Constant.MenuLevel.ZERO, roleList);
            List<MenuSelect> menuSelectList1 = roleMenuQueryService.queryMenuSelectByParams(
                Constant.MenuLevel.ONE, roleList);
            List<MenuSelect> menuSelectList2 = roleMenuQueryService.queryMenuSelectByParams(
                Constant.MenuLevel.TWO, roleList);
            List<MenuSelect> menuSelectList3 = roleMenuQueryService.queryMenuSelectByParams(
                Constant.MenuLevel.THREE, roleList);
            model.addAttribute("role", role);
            model.addAttribute("menulist0", menuSelectList0);
            model.addAttribute("menulist1", menuSelectList1);
            model.addAttribute("menulist2", menuSelectList2);
            model.addAttribute("menulist3", menuSelectList3);
            return PageConstant.PAGE_ROLE_ADDMENU;
        }
        catch (RpcException e)
        {
            logger.debug("[RoleController:listRoleMenu()]" + e.getMessage());
            model.put("message", "操作失败");
            model.put("canback", true);
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
    }

    /**
     * 进入角色菜单权限界面
     * 
     * @return
     */
    @RequestMapping(value = "/role_addmenu", method = RequestMethod.POST)
    public String listRoleMenu(@RequestParam("rid")
    String rid, @RequestParam("menuidstr")
    String menuidstr, ModelMap model)
    {
        try
        {
            logger.info("[RoleController:listRoleMenu(" + rid + "," + menuidstr + ")]");
            com.yuecheng.hops.identity.entity.operator.Operator loginPerson = getLoginUser();
            if (StringUtil.isNotBlank(rid))
            {
                Role role = roleService.queryRoleById(Long.parseLong(rid));
                if (StringUtil.isNotBlank(menuidstr))
                {
                    roleMenuQueryService.saveRoleMenuList(menuidstr, role,
                        loginPerson.getOperatorName());
                }
            }
            return listRoleMenu(rid, model);
        }
        catch (RpcException e)
        {
            logger.debug("[RoleController:listRoleMenu()]" + e.getMessage());
            model.put("message", "操作失败");
            model.put("canback", true);
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
    }

    /**
     * 进入角色菜单权限界面
     * 
     * @return
     */
    @RequestMapping(value = "/role_addPrivilege", method = RequestMethod.POST)
    public String listRolePrivilege(@RequestParam("rid")
    String rid, @RequestParam("privilegeidstr")
    String privilegeidstr, ModelMap model)
    {
        try
        {
            logger.info("[RoleController:listRoleMenu(" + rid + "," + privilegeidstr + ")]");
            com.yuecheng.hops.identity.entity.operator.Operator loginPerson = getLoginUser();
            if (StringUtil.isNotBlank(rid))
            {
                Role role = roleService.queryRoleById(Long.parseLong(rid));
                if (StringUtil.isNotBlank(privilegeidstr))
                {
                    rolePrivilegeQueryService.saveRolePrivilegeList(privilegeidstr, role,
                        loginPerson.getOperatorName());
                }
            }
            return listRoleMenu(rid, model);
        }
        catch (RpcException e)
        {
            logger.debug("[RoleController:listRoleMenu()]" + e.getMessage());
            model.put("message", "操作失败");
            model.put("canback", true);
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
    }

    // 进入菜单树形界面
    @RequestMapping(value = "/role_addtreemenu")
    public String listRoleTreeMenu(@RequestParam("id")
    String id, ModelMap model)
    {
        // MPORTAL树的代码集合
        StringBuffer mpsb = new StringBuffer();
        // APORTAL树的代码集合
        StringBuffer apsb = new StringBuffer();

        // 得到权限集合
        logger.info("[进入角色菜单权限树形界面(id=" + id + ")]");
        Role role = roleService.queryRoleById(Long.parseLong(id));
        List<Role> roleList = new ArrayList<Role>();
        roleList.add(role);
        List<MenuSelect> menuSelectList0 = roleMenuQueryService.queryMenuSelectByParams(
            Constant.MenuLevel.ZERO, roleList);
        List<MenuSelect> menuSelectList1 = roleMenuQueryService.queryMenuSelectByParams(
            Constant.MenuLevel.ONE, roleList);

        // MPORTAL和APORTAL拼接树ZERO节点
        for (MenuSelect ms0 : menuSelectList0)
        {
            String checked0 = Constant.TrueOrFalse.FALSE;
            List<RoleMenu> roleMenuList0 = roleMenuQueryService.queryRoleMenuByParams(
                ms0.getMenu().getMenuId(), Long.parseLong(id));
            if (roleMenuList0 != null && roleMenuList0.size() >= 1)
                checked0 = Constant.TrueOrFalse.TRUE;
            String protalType0 = ms0.getMenu().getPortaltype();
            if (StringUtil.isNotBlank(protalType0)
                && protalType0.equals(PortalType.PORTAL.toString()))
            {
                mpsb = getPortalStr(mpsb, ms0.getMenu().getMenuId(), ms0.getMenu().getMenuId(),
                    ms0.getMenu().getMenuName(), checked0, Constant.TrueOrFalse.TRUE);
                apsb = getPortalStr(apsb, ms0.getMenu().getMenuId(), ms0.getMenu().getMenuId(),
                    ms0.getMenu().getMenuName(), checked0, Constant.TrueOrFalse.TRUE);
            }
            else if (StringUtil.isNotBlank(protalType0)
                     && protalType0.equals(PortalType.MPORTAL.toString()))
            {
                mpsb = getPortalStr(mpsb, ms0.getMenu().getMenuId(), ms0.getMenu().getMenuId(),
                    ms0.getMenu().getMenuName(), checked0, Constant.TrueOrFalse.TRUE);
            }
            else if (StringUtil.isNotBlank(protalType0)
                     && protalType0.equals(PortalType.APORTAL.toString()))
            {
                apsb = getPortalStr(apsb, ms0.getMenu().getMenuId(), ms0.getMenu().getMenuId(),
                    ms0.getMenu().getMenuName(), checked0, Constant.TrueOrFalse.TRUE);
            }
            for (MenuSelect ms1 : menuSelectList1)
            {
                String checked1 = Constant.TrueOrFalse.FALSE;
                if (ms0.getMenu().getMenuId().longValue() == ms1.getMenu().getParentMenuId().longValue())
                {
                    List<RoleMenu> roleMenuList1 = roleMenuQueryService.queryRoleMenuByParams(
                        ms1.getMenu().getMenuId(), Long.parseLong(id));
                    if (roleMenuList1 != null && roleMenuList1.size() >= 1)
                        checked1 = Constant.TrueOrFalse.TRUE;
                    String protalType1 = ms1.getMenu().getPortaltype();
                    if (StringUtil.isNotBlank(protalType1)
                        && protalType1.equals(PortalType.PORTAL.toString()))
                    {
                        mpsb = getPortalStr(mpsb, ms1.getMenu().getMenuId(),
                            ms0.getMenu().getMenuId(), ms1.getMenu().getMenuName(), checked1,
                            Constant.TrueOrFalse.TRUE);
                        apsb = getPortalStr(apsb, ms1.getMenu().getMenuId(),
                            ms0.getMenu().getMenuId(), ms1.getMenu().getMenuName(), checked1,
                            Constant.TrueOrFalse.TRUE);
                    }
                    else if (StringUtil.isNotBlank(protalType1)
                             && protalType1.equals(PortalType.MPORTAL.toString()))
                    {
                        mpsb = getPortalStr(mpsb, ms1.getMenu().getMenuId(),
                            ms0.getMenu().getMenuId(), ms1.getMenu().getMenuName(), checked1,
                            Constant.TrueOrFalse.TRUE);
                    }
                    else if (StringUtil.isNotBlank(protalType1)
                             && protalType1.equals(PortalType.APORTAL.toString()))
                    {
                        apsb = getPortalStr(apsb, ms1.getMenu().getMenuId(),
                            ms0.getMenu().getMenuId(), ms1.getMenu().getMenuName(), checked1,
                            Constant.TrueOrFalse.TRUE);
                    }

                }
            }
        }

        // 通过role中的ROLE_TYPE来区分菜单树的显示
        // SP时只显示MPORTAL树
        // 其它显示-》APORTAL树

        if (RoleType.ROLE_TYPE_SP.equalsIgnoreCase(role.getRoleType()))
        {
//            apsb.setLength(0);
            model.addAttribute("tree", mpsb.toString());
            model.addAttribute("titlename", "hops-mportal");
        }
        else
        {
//            mpsb.setLength(0);
            model.addAttribute("tree", apsb.toString());
            model.addAttribute("titlename", "hops-aportal");
        }

        logger.info("进入角色菜单权限树形界面----MPORTAL-tree=" + mpsb.toString());
        logger.info("进入角色菜单权限树形界面----APORTAL-tree=" + apsb.toString());

        model.addAttribute("role", role);
        model.addAttribute("id", id);
        return "identity/role/role_addmenu_tree";
    }

    // 菜单提交
    @RequestMapping(value = "/role_addupdatetreemenu")
    public String listRoleTreeMenuUpdate(@RequestParam("id")
    String id, @RequestParam("menuidstr")
    String menuidstr, ModelMap model)
    {
    	try{
    		logger.debug("权限更新提交---listRoleTreeMenuUpdate---id=" + id + " ; menuidstr="
                    + menuidstr);
    		com.yuecheng.hops.identity.entity.operator.Operator loginPerson = getLoginUser();
	    	if (StringUtil.isNotBlank(id))
	        {
	            Role role = roleService.queryRoleById(Long.parseLong(id));
	            if (StringUtil.isNotBlank(menuidstr))
	            {
	                roleMenuQueryService.saveRoleMenuList(menuidstr, role,
	                    loginPerson.getOperatorName());
	            }
		    	model.addAttribute("role", role);
		        model.addAttribute("id", id);
	        }
	        model.put("message", "操作成功");
	        model.put("canback", false);
	        model.put("next_url", "Role/role_addtreemenu?id=" + id);
	        model.put("next_msg", "进入菜单树形图");

	        return PageConstant.PAGE_COMMON_NOTIFY;
	    }
	    catch (RpcException e)
	    {
	        logger.debug("[RoleController:listRoleMenu()]" + e.getMessage());
	        model.put("message", "操作失败");
	        model.put("canback", true);
	        return PageConstant.PAGE_COMMON_NOTIFY;
	    }
    }

    // 进入权限树界面
    @RequestMapping(value = "/rivilege_addtreemenu")
    public String listPrivilegeTreeMenu(@RequestParam("id")
    String roleid, ModelMap model)
    {
    	try{
	        // 树的代码集合
	        StringBuffer sb = new StringBuffer();
	        // 拼接树
	        List<Privilege> privilegeList = privilegeService.queryAllPrivilege();
	
	        for (Privilege privilege : privilegeList)
	        {
	            String checked = Constant.TrueOrFalse.FALSE;
	            List<RolePrivilege> rolePrivilegeList = rolePrivilegeQueryService.queryRolePrivilegeByParams(
	                privilege.getPrivilegeId().longValue(), Long.parseLong(roleid));
	            if (rolePrivilegeList.size() >= 1)
	            {
	                checked = Constant.TrueOrFalse.TRUE;
	            }
	            sb = getPortalStr(sb, privilege.getPrivilegeId().longValue(),
	                privilege.getParentPrivilegeId(), privilege.getPrivilegeName(), checked,
	                Constant.TrueOrFalse.FALSE);
	        }
	        model.addAttribute("tree", sb.toString());
	        model.addAttribute("id", roleid);
	
	        return "identity/role/privilege_addmenu_tree";
	    }
	    catch (RpcException e)
	    {
	        logger.debug("[RoleController:listRoleMenu()]" + e.getMessage());
	        model.put("message", "操作失败");
	        model.put("canback", true);
	        return PageConstant.PAGE_COMMON_NOTIFY;
	    }
    }

    // 权限更新提交
    @RequestMapping(value = "/rivilege_addupdatetreemenu")
    public String listRivilegeTreeMenuUpdate(@RequestParam("id")
    String roleid, @RequestParam("menuidstr")
    String menuidstr, ModelMap model)
    {
        logger.debug("权限更新提交---listRivilegeTreeMenuUpdate---id=" + roleid + " ; menuidstr="
                     + menuidstr);
        com.yuecheng.hops.identity.entity.operator.Operator loginPerson = getLoginUser();
        String result=Constant.Common.FAIL;
        if (StringUtil.isNotBlank(roleid))
        {
            Role role = roleService.queryRoleById(Long.parseLong(roleid));
            if (StringUtil.isNotBlank(menuidstr))
            {
            	result=rolePrivilegeQueryService.saveRolePrivilegeList(menuidstr, role,
                    loginPerson.getOperatorName());
            }
        }
        if(result.equals(Constant.Common.SUCCESS))
        {
	        model.put("message", "操作成功");
	        model.put("canback", false);
	        model.put("next_url", "Role/rivilege_addtreemenu?id=" + roleid);
	        // 进入权限树界面
	        model.put("next_msg", "进入菜单树形图");
    	}else{
	    	model.put("message", "操作失败");
	        model.put("canback", true);
    	}
        model.addAttribute("id", roleid);
        return PageConstant.PAGE_COMMON_NOTIFY;


    }

    public StringBuffer getPortalStr(StringBuffer sb, Long id, Long pid, String name,
                                     String checked, String openType)
    {
        if (sb.length() > 1) sb.append(",");
        sb.append("{ id:" + id + ", pId:" + pid + ", name:\"" + name + "\", checked:" + checked
                  + ",open:" + openType + "}");
        return sb;
    }
}
