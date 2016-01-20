package com.yuecheng.hops.mportal.web.privilege;


/**
 * 权限表控制层
 * 
 * @author：Jinger
 * @date：2014-08-29
 */
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
import com.yuecheng.hops.common.EntityConstant;
import com.yuecheng.hops.common.query.BSort;
import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.common.utils.StringUtil;
import com.yuecheng.hops.mportal.constant.PageConstant;
import com.yuecheng.hops.mportal.web.BaseControl;
import com.yuecheng.hops.privilege.entity.Privilege;
import com.yuecheng.hops.privilege.service.PrivilegeService;


@Controller
@RequestMapping(value = "/privilege")
public class PrivilegeController extends BaseControl
{
    @Autowired
    public PrivilegeService privilegeService;

    private static final Logger logger = LoggerFactory.getLogger(PrivilegeController.class);

    /**
     * 进入权限添加页面
     * 
     * @return
     */
    @RequestMapping(value = "/privilege_add", method = RequestMethod.GET)
    public String addPrivilege(ModelMap model)
    {
        try
        {
            List<Privilege> parentPrivilegeList = privilegeService.queryPrivilegeByPId(0l);
            model.addAttribute("parentList", parentPrivilegeList);
            return PageConstant.PAGE_PRIVILEGE_ADD;
        }
        catch (RpcException e)
        {
            logger.debug("[PrivilegeController:addPrivilege(GET)]" + e.getMessage());
            model.put("message", "操作失败");
            model.put("canback", true);
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
    }

    /**
     * 添加权限
     * 
     * @param role
     * @param result
     * @return
     */
    @RequestMapping(value = "/privilege_add", method = RequestMethod.POST)
    public String savePrivilege(@ModelAttribute("privilege") Privilege privilege, ModelMap model,
                                BindingResult result)
    {
        try
        {
            logger.debug("[PrivilegeController:savePrivilege(" + privilege != null ? privilege.toString() : null
                                                                                                           + ")]");
            com.yuecheng.hops.identity.entity.operator.Operator loginPerson = getLoginUser();
            privilege.setCreateUser(loginPerson.getOperatorName());
            privilege.setCreateTime(new Date());
            privilege.setUpdateUser(loginPerson.getOperatorName());
            privilege.setUpdateTime(new Date());
            privilege = privilegeService.savaPrivilege(privilege);
            if (privilege != null)
            {
                model.put("message", "操作成功");
                model.put("canback", false);
                model.put("next_url", "privilege/privilege_list");
                model.put("next_msg", "权限列表");
            }
            else
            {
                model.put("message", "操作失败");
                model.put("canback", true);
            }
        }
        catch (RpcException e)
        {
            logger.debug("[PrivilegeController:savePrivilege()]" + e.getMessage());
            model.put("message", "操作失败");
            model.put("canback", true);
        }
        return PageConstant.PAGE_COMMON_NOTIFY;
    }

    /**
     * 查看权限
     * 
     * @param role
     * @param result
     * @return
     */
    @RequestMapping(value = "/privilege_view", method = RequestMethod.GET)
    public String viewPrivilege(@RequestParam("id") String id, ModelMap model)
    {
        try
        {
            logger.debug("[PrivilegeController:viewPrivilege(" + id + ")]");
            Privilege privilege = privilegeService.queryPrivilegeById(Long.parseLong(id));
            model.addAttribute("privilege", privilege);
            return PageConstant.PAGE_PRIVILEGE_VIEW;
        }
        catch (RpcException e)
        {
            logger.debug("[PrivilegeController:viewPrivilege()]" + e.getMessage());
            model.put("message", "操作失败");
            model.put("canback", true);
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
    }

    /**
     * 删除权限
     * 
     * @param role
     * @param result
     * @return
     */
    @RequestMapping(value = "/privilege_delete", method = RequestMethod.GET)
    public String deletePrivilege(@RequestParam("id") String id, ModelMap model)
    {
        try
        {
            logger.debug("[PrivilegeController:deletePrivilege(" + id + ")]");
         // 判断是否还有子节点:Long parentPrivilegeId
            List<Privilege> privilegelist = privilegeService.queryPrivilegeByPId(Long.parseLong(id));
            // 还有子节点
            if (privilegelist.size() > 0)
            {
                model.put("message", "当前父节点下还有子节点未删除，请先删除子节点后再操作~~~！");
            }
            else
            {
            	Long privilegeId=Long.parseLong(id);
                
                privilegeService.delPrivilege(privilegeId);
                model.put("message", "删除操作成功");
            }
            model.put("canback", false);
            model.put("next_url", "privilege/privilege_list");
            model.put("next_msg", "权限列表");
        }
        catch (RpcException e)
        {
            logger.debug("[PrivilegeController:deletePrivilege()]" + e.getMessage());
            model.put("message", "操作失败");
            model.put("canback", true);
        }
        return PageConstant.PAGE_COMMON_NOTIFY;
    }

    /**
     * 进入权限编辑界面
     * 
     * @param role
     * @param result
     * @return
     */
    @RequestMapping(value = "/privilege_edit", method = RequestMethod.GET)
    public String editPrivilege(@RequestParam("id") String id, ModelMap model)
    {
        try
        {
            logger.debug("[PrivilegeController:editPrivilege(" + id + ")]");
            Privilege privilege = privilegeService.queryPrivilegeById(Long.parseLong(id));
            model.addAttribute("privilege", privilege);
            List<Privilege> parentPrivilegeList = privilegeService.queryPrivilegeByPId(0l);
            model.addAttribute("parentList", parentPrivilegeList);
            return PageConstant.PAGE_PRIVILEGE_EDIT;
        }
        catch (RpcException e)
        {
            logger.debug("[PrivilegeController:editPrivilege()]" + e.getMessage());
            model.put("message", "操作失败");
            model.put("canback", true);
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
    }

    /**
     * 编辑权限后保存
     * 
     * @param role
     * @param result
     * @return
     */
    @RequestMapping(value = "/privilege_edit", method = RequestMethod.POST)
    public String editPrivilegeDo(@ModelAttribute("privilege") Privilege privilege, ModelMap model)
    {
        try
        {
            logger.debug("[PrivilegeController:editPrivilegeDo(" + privilege != null ? privilege.toString() : null+ ")]");
            com.yuecheng.hops.identity.entity.operator.Operator loginPerson = getLoginUser();
            Privilege privilege1 = privilegeService.queryPrivilegeById(privilege.getPrivilegeId());
            privilege.setCreateUser(privilege1.getCreateUser());
            privilege.setCreateTime(privilege1.getCreateTime());
            privilege.setUpdateTime(new Date());
            privilege.setUpdateUser(loginPerson.getOperatorName());
            privilege = privilegeService.savaPrivilege(privilege);
            if (privilege != null)
            {
                model.put("message", "操作成功");
                model.put("canback", false);
                model.put("next_url", "privilege/privilege_list");
                model.put("next_msg", "权限列表");
            }
            else
            {
                model.put("message", "操作失败");
                model.put("canback", true);
            }
        }
        catch (RpcException e)
        {
            logger.debug("[PrivilegeController:editPrivilegeDo()]" + e.getMessage());
            model.put("message", "操作失败");
            model.put("canback", true);
        }
        return PageConstant.PAGE_COMMON_NOTIFY;
    }

    /**
     * 进入权限列表页面
     * 
     * @return
     */
    @RequestMapping(value = "/privilege_list", method = RequestMethod.GET)
    public String listPrivilege(@RequestParam(value = "privilegeName", defaultValue = "") String privilegeName,
                                @RequestParam(value = "permissionName", defaultValue = "") String permissionName,
                                @RequestParam(value = "parentPId", defaultValue = "") String parentPId,
                                @RequestParam(value = "page", defaultValue = "1") int pageNumber,
                                @RequestParam(value = "pageSize", defaultValue = PageConstant.LIST_PAGE_SIZE) int pageSize,
                                @RequestParam(value = "sortType", defaultValue = PageConstant.LIST_ORDER_SORT) String sortType,
                                ModelMap model, ServletRequest request)
    {
        try
        {
            privilegeName = new String(privilegeName.getBytes("ISO-8859-1"), "UTF-8");
            permissionName = new String(permissionName.getBytes("ISO-8859-1"), "UTF-8");
            logger.debug("[PrivilegeController:listdPrivilege(" + privilegeName + ","+ permissionName + ")]");
            Map<String, Object> searchParams = new HashMap<String, Object>();
            if (StringUtil.isNotBlank(privilegeName))
            {
                searchParams.put(Operator.LIKE + "_" + EntityConstant.Privilege.PRIVILEGE_NAME,
                    privilegeName);
            }
            if (StringUtil.isNotBlank(permissionName))
            {
                searchParams.put(Operator.LIKE + "_" + EntityConstant.Privilege.PERMISSION_NAME,
                    permissionName);
            }
            if (StringUtil.isNotBlank(parentPId))
            {
                searchParams.put(Operator.EQ + "_" + EntityConstant.Privilege.PARENT_PRIVILEGE_ID,
                    parentPId);
            }
            BSort bsort = new BSort(BSort.Direct.DESC, EntityConstant.Role.CREATE_TIME);
            YcPage<Privilege> page_list = privilegeService.queryPagePrivilege(searchParams,
                pageNumber, pageSize, bsort);
            List<Privilege> list = page_list.getList();
            String pagetotal = page_list.getPageTotal() + "";
            String countTotal = page_list.getCountTotal() + "";
            List<Privilege> parentPrivilegeList = privilegeService.queryPrivilegeByPId(0l);
            model.addAttribute("parentList", parentPrivilegeList);
            model.addAttribute("plist", list);
            model.addAttribute("page", pageNumber);
            model.addAttribute("pageSize", pageSize);
            model.addAttribute("pagetotal", pagetotal);
            model.addAttribute("counttotal", countTotal);
            model.addAttribute("privilegeName", privilegeName);
            model.addAttribute("permissionName", permissionName);
            if (StringUtil.isNullOrEmpty(parentPId))
            {
                model.addAttribute("parentPId", new Long(0l));
            }
            else
            {
                model.addAttribute("parentPId", new Long(parentPId));
            }
            return PageConstant.PAGE_PRIVILEGE_LIST;
        }
        catch (RpcException e)
        {
            logger.debug("[PrivilegeController:listdPrivilege()]" + e.getMessage());
            model.put("message", "操作失败");
            model.put("canback", true);
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
        catch (Exception e)
        {
            logger.debug("[PrivilegeController:listdPrivilege()]" + e.getMessage());
            model.put("message", "操作失败");
            model.put("canback", true);
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
    }


    /********* 树 ************/
    // 进入权限列表树形图页面
    @RequestMapping(value = "/privilege_list_tree")
    public String listPrivilege(ModelMap model, ServletRequest request)
    {
        // 树的代码集合
        StringBuffer sb = new StringBuffer();
        // 拼接树
        List<Privilege> privilegeList = privilegeService.queryAllPrivilege();
        for (Privilege privilege : privilegeList)
        {
            if (sb.length() > 0) sb.append(",");
            sb.append("{ key:" + privilege.getPrivilegeId().longValue() + ", pkey:"
                      + privilege.getParentPrivilegeId() + ", name:\""
                      + privilege.getPrivilegeName() + "\", title:\"" + privilege.getPrivilegeName()
                      + "\"}");
        }
        model.addAttribute("tree", sb.toString());
        return "identity/privilege/privilege_list_tree";

    }

    // 进入添加[同级]页面
    @RequestMapping(value = "/privilege_add_treepage", method = RequestMethod.GET)
    public String addPrivilegeTree(
    @RequestParam(value = "pid", defaultValue = "0") String pid, ModelMap model)
    {
        try
        {
            if (Constant.Common.ZERO.equals(pid))
            {
                model.addAttribute("privilegename", Constant.Common.ZERO);
                model.addAttribute("parentprivilegeId", Constant.Common.ZERO);
            }
            else
            {
                Privilege privilege = privilegeService.queryPrivilegeById(Long.parseLong(pid));
                model.addAttribute("privilegename", privilege.getPrivilegeName());
                model.addAttribute("parentprivilegeId", privilege.getParentPrivilegeId());
            }

            return "identity/privilege/privilege_add_tree.ftl";
        }
        catch (RpcException e)
        {
            logger.debug("[PrivilegeController:addPrivilege(GET)]" + e.getMessage());
            model.put("message", "操作失败");
            model.put("canback", true);
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
    }

    // 进入添加[子级]页面
    @RequestMapping(value = "/privilege_addChildren_treepage", method = RequestMethod.GET)
    public String addChildrenPrivilegeTree(
    @RequestParam(value = "id", defaultValue = "0") String id, ModelMap model)
    {
        try
        {
            Privilege privilege = privilegeService.queryPrivilegeById(Long.parseLong(id));
            model.addAttribute("privilegename", privilege.getPrivilegeName());
            model.addAttribute("parentprivilegeId", id);

            return "identity/privilege/privilege_add_tree.ftl";
        }
        catch (RpcException e)
        {
            logger.debug("[PrivilegeController:addPrivilege(GET)]" + e.getMessage());
            model.put("message", "操作失败");
            model.put("canback", true);
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
    }

    // 添加保存
    @RequestMapping(value = "/privilege_add_tree", method = RequestMethod.POST)
    public String savePrivilegeTree(@ModelAttribute("privilege") Privilege privilege,
                                    ModelMap model, BindingResult result)
    {
        try
        {
            logger.debug("[PrivilegeController:savePrivilege(" + privilege != null ? privilege.toString() : null+ ")]");
            com.yuecheng.hops.identity.entity.operator.Operator loginPerson = getLoginUser();
            privilege.setCreateUser(loginPerson.getOperatorName());
            privilege.setCreateTime(new Date());
            privilege.setUpdateUser(loginPerson.getOperatorName());
            privilege.setUpdateTime(new Date());
            privilege = privilegeService.savaPrivilege(privilege);
            model.addAttribute("privilege", privilege);
        }
        catch (RpcException e)
        {
            logger.debug("[PrivilegeController:savePrivilege()]" + e.getMessage());
            model.put("message", "操作失败["+e.getMessage()+"]");
            model.put("canback", true);
        }
        return "redirect:/privilege/privilege_list_tree";
    }

    // 进入修改页面
    @RequestMapping(value = "/privilege_edit_treepage", method = RequestMethod.GET)
    public String editPrivilegeTree(@RequestParam("id") String id, ModelMap model)
    {
        try
        {
            logger.debug("[PrivilegeController:editPrivilege(" + id + ")]");
            Privilege privilege = privilegeService.queryPrivilegeById(Long.parseLong(id));
            model.addAttribute("privilege", privilege);
            if (0l != privilege.getParentPrivilegeId())
            {
                Privilege pprivilege = privilegeService.queryPrivilegeById(privilege.getParentPrivilegeId());
                model.addAttribute("privilegename", pprivilege.getPrivilegeName());
            }
            else
            {
                model.addAttribute("privilegename", Constant.Common.ZERO);
            }

            return "identity/privilege/privilege_edit_tree.ftl";
        }
        catch (RpcException e)
        {
                logger.debug("[PrivilegeController:editPrivilege()]" + e.getMessage());
            model.put("message", "操作失败");
            model.put("canback", true);
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
    }

    // 提交修改
    @RequestMapping(value = "/privilege_edit_tree", method = RequestMethod.POST)
    public String editPrivilegeDoTree(@ModelAttribute("privilege") Privilege privilege,
                                      ModelMap model)
    {
        try
        {
            logger.debug("[PrivilegeController:editPrivilegeDo(" + privilege != null ? privilege.toString() : null+ ")]");
            com.yuecheng.hops.identity.entity.operator.Operator loginPerson = getLoginUser();
            Privilege privilege1 = privilegeService.queryPrivilegeById(privilege.getPrivilegeId());
            privilege.setCreateUser(privilege1.getCreateUser());
            privilege.setCreateTime(privilege1.getCreateTime());
            privilege.setUpdateTime(new Date());
            privilege.setUpdateUser(loginPerson.getOperatorName());
            privilege = privilegeService.savaPrivilege(privilege);
            model.addAttribute("privilege", privilege);
        }
        catch (RpcException e)
        {
            logger.debug("[PrivilegeController:editPrivilegeDo()]" + e.getMessage());
            model.put("message", "操作失败");
            model.put("canback", true);
        }
        return "redirect:/privilege/privilege_list_tree";
    }

    // 删除
    @RequestMapping(value = "/privilege_delete_tree", method = RequestMethod.GET)
    public String deletePrivilegeTree(@RequestParam("id") String id, ModelMap model)
    {
        try
        {
            logger.debug("[PrivilegeController:deletePrivilege(" + id + ")]");
            // 判断是否还有子节点:Long parentPrivilegeId
            List<Privilege> privilegelist = privilegeService.queryPrivilegeByPId(Long.parseLong(id));
            // 还有子节点
            if (privilegelist.size() > 0)
            {
                model.put("message", "当前父节点下还有子节点未删除，请先删除子节点后再操作~~~！");
            }
            else
            {
            	Long privilegeId=Long.parseLong(id);
                privilegeService.delPrivilege(privilegeId);
                model.put("message", "删除操作成功");
            }
            model.put("canback", false);
            model.put("next_url", "privilege/privilege_list_tree");
            model.put("next_msg", "权限列表树形图");
        }
        catch (RpcException e)
        {
            logger.debug("[PrivilegeController:deletePrivilege()]" + e.getMessage());
            model.put("message", "操作失败");
            model.put("canback", true);
        }
        return PageConstant.PAGE_COMMON_NOTIFY;
    }
}
