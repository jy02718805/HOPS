package com.yuecheng.hops.mportal.web.privilege;


/**
 * 菜单界面控制层
 * 
 * @author：Jinger
 * @date：2013-10-10
 */
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.rpc.RpcException;
import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.enump.PortalType;
import com.yuecheng.hops.common.utils.MenuLevelUtil;
import com.yuecheng.hops.common.utils.StringUtil;
import com.yuecheng.hops.identity.entity.operator.Operator;
import com.yuecheng.hops.mportal.constant.PageConstant;
import com.yuecheng.hops.mportal.web.BaseControl;
import com.yuecheng.hops.privilege.entity.Menu;
import com.yuecheng.hops.privilege.entity.PageResource;
import com.yuecheng.hops.privilege.service.MenuService;
import com.yuecheng.hops.privilege.service.PageResourceService;


@Controller
@RequestMapping(value = "/Menu")
public class MenuController extends BaseControl
{
    @Autowired
    private MenuService menuService;

    @Autowired
    private PageResourceService pageResourceService;

    private static Logger logger = LoggerFactory.getLogger(MenuController.class);

    /**
     * 进入角色菜单权限界面
     * 
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String listMenu(ModelMap model)
    {
        try
        {
            List<Menu> menulist0 = menuService.queryMenuByLevel(Constant.MenuLevel.ZERO);
            List<Menu> menulist1 = menuService.queryMenuByLevel(Constant.MenuLevel.ONE);
            List<Menu> menulist2 = menuService.queryMenuByLevel(Constant.MenuLevel.TWO);
            List<Menu> menulist3 = menuService.queryMenuByLevel(Constant.MenuLevel.THREE);
            model.addAttribute("menulist0", menulist0);
            model.addAttribute("menulist1", menulist1);
            model.addAttribute("menulist2", menulist2);
            model.addAttribute("menulist3", menulist3);
            return PageConstant.PAGE_MENU_LIST;
        }
        catch (RpcException e)
        {
            logger.debug("[MenuController:listMenu(GET)]" + e.getMessage());
            model.put("message", "操作失败[" + e.getMessage() + "]");
            model.put("canback", true);
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
    }

    /**
     * 进入角色菜单树权限界面
     * 
     * @Title: listMenuTree
     * @Description: 进入角色菜单树权限界面
     * @param @param model
     * @param @return 设定文件
     * @return String 返回类型
     * @throws
     */
    @RequestMapping(value = "/listtree", method = RequestMethod.GET)
    public String listMenuTree(ModelMap model)
    {
        // 树的代码集合
        StringBuffer sb = new StringBuffer();
        // 拼接树ZERO节点
        sb = getTreeStr();
        model.addAttribute("tree", sb.toString());
        return "identity/menu/menu_list_tree";
    }

    public StringBuffer getTreeStr()
    {
        StringBuffer sb = new StringBuffer();
        List<Menu> menulist0 = menuService.queryMenuByLevel(Constant.MenuLevel.ZERO);
        List<Menu> menulist1 = menuService.queryMenuByLevel(Constant.MenuLevel.ONE);
        List<Menu> menulist2 = menuService.queryMenuByLevel(Constant.MenuLevel.TWO);
        List<Menu> menulist3 = menuService.queryMenuByLevel(Constant.MenuLevel.THREE);
        for (Menu m0 : menulist0)
        {
            sb = getMenuStr(sb, m0.getMenuId(), m0.getMenuId(), m0.getMenuName(),
                m0.getMenuName(), m0.getPortaltype());
            for (Menu m1 : menulist1)
            {
                if (m0.getMenuId().equals(m1.getParentMenuId()))
                {
                    sb = getMenuStr(sb, m1.getMenuId(), m0.getMenuId(), m1.getMenuName(),
                        m0.getMenuName(), m1.getPortaltype());
                    for (Menu m2 : menulist2)
                    {
                        if (m1.getMenuId().equals(m2.getParentMenuId()))
                        {
                            sb = getMenuStr(sb, m2.getMenuId(), m1.getMenuId(), m2.getMenuName(),
                                m1.getMenuName(), m2.getPortaltype());
                            for (Menu m3 : menulist3)
                            {
                                if (m2.getMenuId().equals(m3.getParentMenuId()))
                                {
                                    sb = getMenuStr(sb, m3.getMenuId(), m2.getMenuId(),
                                        m3.getMenuName(), m2.getMenuName(), m3.getPortaltype());
                                }
                            }
                        }
                    }
                }
            }
        }
        return sb;
    }

    public StringBuffer getMenuStr(StringBuffer sb, Long id, Long pid, String name, String pname,
                                   String portalName)
    {
        if (sb.length() > 0) sb.append(",");
        sb.append("{ key:" + id + ", pkey:" + pid + ", name:\"" + name + "["
                  + getFirstLetter(portalName) + "]" + "\", title:\"" + pname + "\"}");
        return sb;
    }

    // 返回首字母
    public static String getFirstLetter(String str)
    {
        return str != null ? str.substring(0, 1) : "";

    }

    /**
     * 跳转操作页面
     */
    @RequestMapping(value = "/operatepage")
    public String operatePage(@RequestParam(value = "id", defaultValue = "")
    String id, @RequestParam(value = "pid", defaultValue = "")
    String pid, ModelMap model)
    {
        model.addAttribute("id", id);
        model.addAttribute("pid", pid);
        return "identity/menu/menu_main";
    }

    /**
     * 进入添加角色菜单权限界面
     * 
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addMenu(@RequestParam(value = "id", defaultValue = "")
    String id, ModelMap model)
    {
        try
        {
            logger.info("[MenuController:addMenu(" + id + ")]");
            String parentId = "";
            String parentName = "";
            if (StringUtil.isNotBlank(id) && !"null".equals(id) && !Constant.Common.ZERO.equals(id))
            {
                Menu menu = menuService.queryMenuById(Long.parseLong(id.replace(",", "")));
                if (menu != null)
                {
                    parentId = menu.getMenuId().toString();
                    parentName = menu.getMenuName();
                }
            }
            model.addAttribute("parentid", parentId);
            model.addAttribute("parentname", parentName);
            List<PageResource> pageResource = pageResourceService.queryPageResourceByStatus(Constant.PageResourceStatus.OPEN_STATUS);
            model.addAttribute("pageResource", pageResource);

            // 后台类型
            List<String> portaltypelist = new ArrayList<String>();
            for (PortalType c : PortalType.values())
            {
                portaltypelist.add(c.toString());
            }
            model.addAttribute("portaltypelist", portaltypelist);
            return PageConstant.PAGE_MENU_ADD;

        }
        catch (RpcException e)
        {
            logger.debug("[MenuController:addMenu()]" + e.getMessage());
            model.put("message", "操作失败[" + e.getMessage() + "]");
            model.put("canback", true);
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
    }

    /**
     * 添加角色菜单权限
     * 
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addMenu(@RequestParam(value = "parentMenuId", defaultValue = "")
    String parentMenuId, @RequestParam(value = "menuName", defaultValue = "")
    String menuName, @RequestParam(value = "displayOrder")
    int displayOrder, @RequestParam(value = "pageResource.resourceId", defaultValue = "")
    String pageResourceid, @RequestParam(value = "status", defaultValue = "")
    String status, @RequestParam(value = "remark", defaultValue = "")
    String remark, @RequestParam(value = "portaltype", defaultValue = "")
    String portaltype, ModelMap model)
    {
        try
        {
            logger.info("[MenuController:addMenu(" + parentMenuId + "," + menuName + ","
                        + displayOrder + "," + pageResourceid + "," + status + "," + remark
                        + ")]");
            Operator loginPerson = getLoginUser();
            Menu menu = new Menu();
            PageResource pageResource = pageResourceService.queryPageResourceById(Long.parseLong(pageResourceid.replace(
                ",", "")));
            menu.setMenuName(menuName);
            menu.setDisplayOrder(displayOrder);
            menu.setRemark(remark);
            menu.setPortaltype(portaltype);
            if (StringUtil.isNotBlank(parentMenuId) && !"null".equals(parentMenuId) )
            {
                menu.setParentMenuId(Long.parseLong(parentMenuId));
            }
            else
            {
                menu.setParentMenuId(1l);
            }
            menu.setStatus(status);
            menu.setPageResource(pageResource);
            if (null != menu.getParentMenuId())
            {
                Date dt = new Date();
                if (menu.getParentMenuId().equals(1l))
                {
                    menu.setMenuLevel(Constant.MenuLevel.ZERO);
                }
                else
                {
                    Menu parent = menuService.queryMenuById(menu.getParentMenuId());
                    if (null != parent)
                    {
                        String ml = MenuLevelUtil.getChildMenuLevel(parent.getMenuLevel());
                        menu.setMenuLevel(ml);
                    }
                }
                menu.setCreateTime(dt);
                menu.setUpdateTime(dt);
                menu.setUpdateName(loginPerson.getOperatorName());
                menu = menuService.saveMenu(menu);
                pageResource.setStatus(Constant.MenuStatus.CLOSE_STATUS);
                pageResourceService.savePageResource(pageResource);
            }
            return "redirect:/Menu/listtree";
        }
        catch (RpcException e)
        {
            logger.debug("[MenuController:addMenu()]" + e.getMessage());
            model.put("message", "操作失败[" + e.getMessage() + "]");
            model.put("canback", true);
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
    }

    /**
     * 进入编辑角色菜单权限界面
     * 
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String editMenu(@RequestParam(value = "id", defaultValue = "")
    String id, ModelMap model)
    {
        try
        {
            logger.debug("[MenuController:editMenu(" + id + ")]");
            Menu menu =new Menu();
            if(StringUtils.isNotBlank(id) && !"null".equals(id))
            {
            	menu = menuService.queryMenuById(Long.parseLong(id.replace(",", "")));
            }
            String parentId = "";
            String parentName = "";
            Long parentid = menu.getParentMenuId();
            if (parentid != null && !parentid.equals(new Long(Constant.Common.ZERO)))
            {
                Menu parentMenu = menuService.queryMenuById(parentid);
                if (parentMenu != null)
                {
                    parentId = parentMenu.getMenuId().toString();
                    parentName = parentMenu.getMenuName();
                }
            }
            model.addAttribute("parentid", parentId);
            model.addAttribute("parentname", parentName);
            model.addAttribute("menu", menu);
            PageResource pageResource = pageResourceService.queryPageResourceById(menu.getPageResource().getResourceId());
            List<PageResource> pageResourcelist = pageResourceService.queryPageResourceByStatus(Constant.PageResourceStatus.OPEN_STATUS);
            model.addAttribute("pageResourcelist", pageResourcelist);
            model.addAttribute("pageResource", pageResource);

            // 后台类型
            List<String> portaltypelist = new ArrayList<String>();
            for (PortalType c : PortalType.values())
            {
                portaltypelist.add(c.toString());
            }
            model.addAttribute("portaltypelist", portaltypelist);
            return PageConstant.PAGE_MENU_EDIT;
        }
        catch (RpcException e)
        {
            logger.debug("[MenuController:editMenu()]" + e.getMessage());
            model.put("message", "操作失败[" + e.getMessage() + "]");
            model.put("canback", true);
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
    }

    /**
     * 添加角色菜单权限
     * 
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String editMenu(@RequestParam(value = "menuId", defaultValue = "")
    String id, @RequestParam(value = "parentMenuId", defaultValue = "")
    String parentMenuId, @RequestParam(value = "menuName", defaultValue = "")
    String menuName, @RequestParam(value = "displayOrder")
    int displayOrder, @RequestParam(value = "pageResource.resourceId", defaultValue = "")
    String pageResourceid, @RequestParam(value = "status", defaultValue = "")
    String status, @RequestParam(value = "remark", defaultValue = "")
    String remark, @RequestParam(value = "portaltype", defaultValue = "")
    String portaltype, ModelMap model)
    {

        try
        {
            logger.info("[MenuController:editMenu(id=" + id + ",parentMenuId=" + parentMenuId
                        + ",menuName=" + menuName + ",displayOrder=" + displayOrder
                        + ",pageResourceid=" + pageResourceid + ",status=" + status
                        + ",functionDesc=" + remark + ",portaltype=" + portaltype + ")]");
            Operator loginPerson = getLoginUser();
            Menu menu = menuService.queryMenuById(Long.parseLong(id.replace(",", "")));
            PageResource newpageResource = pageResourceService.queryPageResourceById(Long.parseLong(pageResourceid.replace(
                ",", "")));
            PageResource oldpageResource = pageResourceService.queryPageResourceById(menu.getPageResource().getResourceId());
            menu.setMenuName(menuName);
            menu.setDisplayOrder(displayOrder);
            menu.setRemark(remark);
            menu.setPortaltype(portaltype);
            menu.setParentMenuId(Long.parseLong(parentMenuId));
            menu.setStatus(status);
            menu.setPageResource(newpageResource);
            Date dt = new Date();
            menu.setUpdateTime(dt);
            menu.setUpdateName(loginPerson.getOperatorName());
            menu = menuService.saveMenu(menu);

        	updatePageResource(oldpageResource,Constant.PageResourceStatus.OPEN_STATUS);
        	updatePageResource(newpageResource,Constant.PageResourceStatus.CLOSE_STATUS);
            return "redirect:/Menu/listtree";
        }
        catch (RpcException e)
        {
            logger.debug("[MenuController:editMenu()]" + e.getMessage());
            model.put("message", "操作失败[" + e.getMessage() + "]");
            model.put("canback", true);
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
    }

    /**
     * new 删除菜单
     */
    @RequestMapping(value = "/deletemenuh")
    public String deleteMenuH(@RequestParam("id")
    String id, ModelMap model)
    {
        try
        {
            logger.debug("[MenuController:deleteMenu(" + id + ")]");
            // 检查登录是否超时
            if (StringUtil.isNotBlank(id) && !"null".equals(id))
            {
                Long menuid = Long.parseLong(id.replace(",", ""));
                Menu menu = menuService.queryMenuById(menuid);
                if (menu != null)
                {
                    if (menu.getMenuLevel().equals(Constant.MenuLevel.TWO))
                    {
                        // 先删除角色全校表中的数据再删除菜单
                    	deleteAssociation(menu);
                    }
                    else
                    {
                        String ml = MenuLevelUtil.getChildMenuLevel(menu.getMenuLevel());
                        List<Menu> menulist = menuService.queryMenuByParams(menu.getMenuId(), ml);
                        if (menulist.size() > 0)
                        {
                            model.put("message", "操作失败[当前父节点下还有子节点未删除，请先删除子节点后再操作~~~！]");
                            model.put("canback", true);
                            return PageConstant.PAGE_COMMON_NOTIFY;
                        }
                        else
                        {
                            // 先删除角色全校表中的数据再删除菜单
                        	deleteAssociation(menu);
                        }
                    }
                }
            }
            return "redirect:/Menu/listtree";
        }
        catch (RpcException e)
        {
            logger.debug("[MenuController:deleteMenu()]" + e.getMessage());
            model.put("message", "操作失败[" + e.getMessage() + "]");
            model.put("canback", true);
            return PageConstant.PAGE_COMMON_NOTIFY;
        }

    }

    /**
     * 删除关联关系数据
     * @param menu
     */
    public void deleteAssociation(Menu menu)
    {
    	Long menuid=menu.getMenuId();
    	menuService.deleteMenu(menuid);
    	updatePageResource(menu.getPageResource(),Constant.PageResourceStatus.OPEN_STATUS);
    }
    
    public void updatePageResource(PageResource pageResource,String status)
    {
    	Operator operator=getLoginUser();
    	pageResource.setStatus(status);
    	pageResource.setUpdateName(operator.getOperatorName());
    	pageResource.setUpdateTime(new Date());
    	pageResourceService.savePageResource(pageResource);
    }
    
    /**
     * 删除菜单
     * 
     * @param customer
     * @param result
     * @return
     */
    // /@RequestMapping(value = "/delete", method = RequestMethod.POST)
    @RequestMapping(value = "/delete")
    @ResponseBody
    public String deleteMenu(@RequestParam("id")
    String id, ModelMap model)
    {
        try
        {
            logger.debug("[MenuController:deleteMenu(" + id + ")]");
            // 检查登录是否超时
            if (StringUtil.isNotBlank(id) && !"null".equals(id))
            {
                Long menuid = Long.parseLong(id.replace(",", ""));
                Menu menu = menuService.queryMenuById(menuid);
                if (menu != null)
                {
                    if (menu.getMenuLevel().equals(Constant.MenuLevel.TWO))
                    {
                        // 先删除角色全校表中的数据再删除菜单
                    	deleteAssociation(menu);
                        return Constant.TrueOrFalse.TRUE;
                    }
                    else
                    {
                        String ml = MenuLevelUtil.getChildMenuLevel(menu.getMenuLevel());
                        List<Menu> menulist = menuService.queryMenuByParams(menu.getMenuId(), ml);
                        if (menulist.size() > 0)
                        {
                            return Constant.Common.CHILD;
                        }
                        else
                        {
                            // 先删除角色全校表中的数据再删除菜单
                        	deleteAssociation(menu);
                            return Constant.TrueOrFalse.TRUE;
                        }
                    }
                }
            }
            return Constant.TrueOrFalse.FALSE;
        }
        catch (RpcException e)
        {
            logger.debug("[MenuController:deleteMenu()]" + e.getMessage());
            model.put("message", "操作失败[" + e.getMessage() + "]");
            model.put("canback", true);
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
    }
}
