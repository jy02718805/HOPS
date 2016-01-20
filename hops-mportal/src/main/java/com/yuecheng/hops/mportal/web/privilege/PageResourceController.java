package com.yuecheng.hops.mportal.web.privilege;


/**
 * 页面资源界面控制层
 * 
 * @author：Jinger
 * @date：2013-10-10
 */
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;

import org.apache.commons.lang3.StringUtils;
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
import com.yuecheng.hops.privilege.entity.PageResource;
import com.yuecheng.hops.privilege.service.PageResourceService;


@Controller
@RequestMapping(value = "/PageResource")
public class PageResourceController extends BaseControl
{
    @Autowired
    private PageResourceService pageResourceService;

    private static Logger logger = LoggerFactory.getLogger(PageResourceController.class);

    /**
     * 进入角色添加页面
     * 
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addPageResource(ModelMap model)
    {
        try
        {
            return PageConstant.PAGE_RESOURCE_ADD;
        }
        catch (RpcException e)
        {
            logger.debug("[PageResourceController:addPageResource(GET)]" + e.getMessage());
            model.put("message", "操作失败" + e.getMessage());
            model.put("canback", true);
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
    }

    /**
     * 添加用户
     * 
     * @param pageResource
     * @param result
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String savePageResource(@ModelAttribute("pageResource")
    PageResource pageResource, ModelMap model, BindingResult result)
    {
        try
        {
            logger.debug("[PageResourceController:savePageResource(" + pageResource != null ? pageResource.toString() : null
                                                                                                                       + ")]");
            com.yuecheng.hops.identity.entity.operator.Operator loginPerson = getLoginUser();
            pageResource.setCreateName(loginPerson.getOperatorName());
            pageResource.setCreateTime(new Date());
            pageResource.setStatus(Constant.PageResourceStatus.OPEN_STATUS);
            pageResource.setUpdateName(loginPerson.getOperatorName());
            pageResource.setUpdateTime(new Date());
            pageResource = pageResourceService.savePageResource(pageResource);
            if (pageResource != null)
            {
                model.put("message", "操作成功");
                model.put("canback", false);
                model.put("next_url", "PageResource/list");
                model.put("next_msg", "页面资源列表");
            }
            else
            {
                model.put("message", "操作失败");
                model.put("canback", true);
            }
        }
        catch (RpcException e)
        {
            logger.debug("[PageResourceController:savePageResource()]" + e.getMessage());
            model.put("message", "操作失败" + e.getMessage());
            model.put("canback", true);
        }
        return PageConstant.PAGE_COMMON_NOTIFY;
    }

    /**
     * 查看用户
     * 
     * @param pageResource
     * @param result
     * @return
     */
    @RequestMapping(value = "/pageresource_view", method = RequestMethod.GET)
    public String viewPageResource(@RequestParam("id")
    String id, ModelMap model)
    {
        try
        {
            logger.debug("[PageResourceController:viewPageResource(" + id + ")]");
            PageResource pageResource = pageResourceService.queryPageResourceById(Long.parseLong(id));
            model.addAttribute("pageResource", pageResource);
            return PageConstant.PAGE_RESOURCE_VIEW;
        }
        catch (RpcException e)
        {
            logger.debug("[PageResourceController:viewPageResource()]" + e.getMessage());
            model.put("message", "操作失败" + e.getMessage());
            model.put("canback", true);
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
    }

    /**
     * 删除用户
     * 
     * @param pageResource
     * @param result
     * @return
     */
    @RequestMapping(value = "/pageresource_delete", method = RequestMethod.GET)
    public String deletePageResource(@RequestParam("id")
    String id, ModelMap model)
    {
        try
        {
            logger.debug("[PageResourceController:deletePageResource(" + id + ")]");
            if(StringUtils.isNotBlank(id))
            {
            	Long pageResourceId=Long.parseLong(id);
	            pageResourceService.deletePageResource(pageResourceId);
	            model.put("message", "操作成功");
	            model.put("canback", false);
	            model.put("next_url", "PageResource/list");
	            model.put("next_msg", "页面资源列表");
            }else{
            	model.put("message", "操作失败[资源ID为空]" );
                model.put("canback", true);
            }
        }
        catch (RpcException e)
        {
            logger.debug("[PageResourceController:deletePageResource()]" + e.getMessage());
            model.put("message", "操作失败" + e.getMessage());
            model.put("canback", true);
        }
        return PageConstant.PAGE_COMMON_NOTIFY;
    }

    /**
     * 进入用户编辑界面
     * 
     * @param pageResource
     * @param result
     * @return
     */
    @RequestMapping(value = "/pageresource_edit", method = RequestMethod.GET)
    public String editPageResource(@RequestParam("id")
    String id, ModelMap model)
    {
        try
        {
            logger.debug("[PageResourceController:editPageResource(" + id + ")]");
            PageResource pageResource = pageResourceService.queryPageResourceById(Long.parseLong(id));
            model.addAttribute("pageResource", pageResource);
            model.addAttribute("id", pageResource.getResourceId().toString());
            return PageConstant.PAGE_RESOURCE_EDIT;
        }
        catch (RpcException e)
        {
            logger.debug("[PageResourceController:editPageResource()]" + e.getMessage());
            model.put("message", "操作失败" + e.getMessage());
            model.put("canback", true);
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
    }

    /**
     * 编辑用户后保存
     * 
     * @param pageResource
     * @param result
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String editPageResourceDo(@ModelAttribute("pageResource")
    PageResource pageResource, ModelMap model)
    {
        try
        {
            logger.debug("[PageResourceController:editPageResourceDo(" + pageResource != null ? pageResource.toString() : null
                                                                                                                         + ")]");
            com.yuecheng.hops.identity.entity.operator.Operator loginPerson = getLoginUser();
            PageResource pageResource1 = pageResourceService.queryPageResourceById(pageResource.getResourceId());
            pageResource.setCreateName(pageResource1.getCreateName());
            pageResource.setCreateTime(pageResource1.getCreateTime());
            pageResource.setStatus(pageResource1.getStatus());
            pageResource.setUpdateName(loginPerson.getOperatorName());
            pageResource.setUpdateTime(new Date());
            pageResource = pageResourceService.savePageResource(pageResource);
            if (pageResource != null)
            {
                model.put("message", "操作成功");
                model.put("canback", false);
                model.put("next_url", "PageResource/list");
                model.put("next_msg", "页面资源列表");
            }
            else
            {
                model.put("message", "操作失败");
                model.put("canback", true);
            }
        }
        catch (RpcException e)
        {
            logger.debug("[PageResourceController:editPageResourceDo()]" + e.getMessage());
            model.put("message", "操作失败" + e.getMessage());
            model.put("canback", true);
        }
        return PageConstant.PAGE_COMMON_NOTIFY;
    }

    /**
     * 进入用户列表页面
     * 
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String listdPageResource(@RequestParam(value = "pageResourceName", defaultValue = "")
    String pageResourceName, @RequestParam(value = "status", defaultValue = "")
    String status, @RequestParam(value = "page", defaultValue = "1")
    int pageNumber, @RequestParam(value = "pageSize", defaultValue = PageConstant.LIST_PAGE_SIZE)
    int pageSize, @RequestParam(value = "sortType", defaultValue = PageConstant.LIST_ORDER_SORT)
    String sortType, ModelMap model, ServletRequest request)
    {
        try
        {
            pageResourceName = new String(pageResourceName.getBytes("ISO-8859-1"), "UTF-8");
            logger.debug("[PageResourceController:listdPageResource(" + pageResourceName + ")]");
            Map<String, Object> searchParams = new HashMap<String, Object>();
            if (StringUtil.isNotBlank(pageResourceName))
            {
                searchParams.put(Operator.LIKE + "_"
                                 + EntityConstant.PageResource.PAGE_RESOURCE_NAME,
                    pageResourceName);
            }
            if (StringUtil.isNotBlank(status))
            {
                searchParams.put(Operator.EQ + "_" + EntityConstant.PageResource.STATUS, status);
            }
            BSort bsort = new BSort(BSort.Direct.DESC, EntityConstant.PageResource.CREATE_TIME);
            YcPage<PageResource> page_list = pageResourceService.queryPageResource(searchParams,
                pageNumber, pageSize, bsort);
            List<PageResource> list = page_list.getList();
            String pagetotal = page_list.getPageTotal() + "";
            String countTotal = page_list.getCountTotal() + "";
            model.addAttribute("mlist", list);
            model.addAttribute("page", pageNumber);
            model.addAttribute("pageSize", pageSize);
            model.addAttribute("pagetotal", pagetotal);
            model.addAttribute("counttotal", countTotal);
            model.addAttribute("pageResourceName", pageResourceName);
            model.addAttribute("status", status);
            return PageConstant.PAGE_RESOURCE_LIST;
        }
        catch (RpcException e)
        {
            logger.debug("[PageResourceController:listdPageResource()]" + e.getMessage());
            model.put("message", "操作失败" + e.getMessage());
            model.put("canback", true);
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
        catch (Exception e)
        {
            logger.debug("[PageResourceController:listdPageResource()]" + e.getMessage());
            model.put("message", "操作失败" + e.getMessage());
            model.put("canback", true);
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
    }
}
