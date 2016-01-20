package com.yuecheng.hops.mportal.web.identity;


import java.io.UnsupportedEncodingException;
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
import com.yuecheng.hops.common.EntityConstant;
import com.yuecheng.hops.common.enump.MerchantType;
import com.yuecheng.hops.common.query.BSort;
import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.common.utils.StringUtil;
import com.yuecheng.hops.identity.entity.mirror.Organization;
import com.yuecheng.hops.identity.service.merchant.OrganizationService;
import com.yuecheng.hops.mportal.constant.PageConstant;
import com.yuecheng.hops.mportal.web.BaseControl;


@Controller
@RequestMapping(value = "/Organization")
public class OrganizationController extends BaseControl
{

    @Autowired
    private OrganizationService organizationService;

    private static Logger logger = LoggerFactory.getLogger(OrganizationController.class);

    /**
     * 进入新增机构页面
     * 
     * @return
     */
    @RequestMapping(value = "/organization_add", method = RequestMethod.GET)
    public String addOrganization()
    {

        return PageConstant.PAGE_ORGANIZATIONT_ADD;

    }

    /**
     * 进入组织机构列表页面
     * 
     * @return
     * @throws UnsupportedEncodingException
     *             organizationList
     */
    @RequestMapping(value = "/organizationList")
    public String listdOrganization(@RequestParam(value = "organizationName", defaultValue = "")
    String organizationName, @RequestParam(value = "page", defaultValue = "1")
    int pageNumber, @RequestParam(value = "pageSize", defaultValue = PageConstant.LIST_PAGE_SIZE)
    int pageSize, @RequestParam(value = "sortType", defaultValue = PageConstant.LIST_ORDER_SORT)
    String sortType, ModelMap model, ServletRequest request)
        throws UnsupportedEncodingException
    {
        try
        {
            Map<String, Object> searchParams = new HashMap<String, Object>();
            if (StringUtil.isNotBlank(organizationName))
            {
                searchParams.put(Operator.LIKE + "_"
                                 + EntityConstant.Organization.ORGANIZATION_NAME, organizationName);
            }
            BSort bsort = new BSort(BSort.Direct.DESC, "organizationName");
            YcPage<Organization> page = organizationService.queryOrganization(searchParams,
                pageNumber, pageSize, bsort);
            List<Organization> list = page.getList();
            String pagetotal = page.getPageTotal() + "";
            String countTotal = page.getCountTotal() + "";

            model.addAttribute("mlist", list);
            model.addAttribute("page", pageNumber);
            model.addAttribute("pageSize", pageSize);
            model.addAttribute("pagetotal", pagetotal);
            model.addAttribute("counttotal", countTotal);
            model.addAttribute("organizationName", organizationName);
            return PageConstant.PAGE_ORGANIZATIONT_LIST;
        }
        catch (RpcException e)
        {
            logger.debug("[OrganizationController:organizationList()]" + e.getMessage());
            model.put("message", "操作失败" + e.getMessage());
            model.put("canback", true);
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
    }

    /**
     * 添加机构
     * 
     * @param merchant
     * @param result
     * @return
     */
    @RequestMapping(value = "/organization_add", method = RequestMethod.POST)
    public String saveOrganization(@ModelAttribute("organization")
    Organization organization, ModelMap model, BindingResult result)
    {
        try
        {
            // 判断注册号是否已存在
            Organization org1 = organizationService.queryOrganizationByNo(organization.getOrganizationRegistrationNo());
            if (org1 != null && org1.getOrganizationId() != null)
            {
                model.put("message", "操作失败,注册号已存在");
                model.put("canback", true);
                return PageConstant.PAGE_COMMON_NOTIFY;
            }
            // 判断组织机构名称是否已存在
            Organization org2 = organizationService.queryOrganizationByName(organization.getOrganizationName());
            if (org2 != null && org2.getOrganizationId() != null)
            {
                model.put("message", "操作失败,组织机构名称已存在");
                model.put("canback", true);
                return PageConstant.PAGE_COMMON_NOTIFY;
            }

            Organization org = organizationService.saveOrganization(organization);
            if (org != null)
            {
                model.put("message", "操作成功");
                model.put("canback", false);
                model.put("next_url", "Organization/organizationList");
                model.put("next_msg", "组织机构列表");
            }
            else
            {
                model.put("message", "操作失败");
                model.put("canback", true);
            }
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
        catch (RpcException e)
        {
            logger.debug("[OrganizationController:organization_add()]" + e.getMessage());
            model.put("message", "操作失败" + e.getMessage());
            model.put("canback", true);
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
    }

    @RequestMapping(value = "/organization_view", method = RequestMethod.GET)
    public String viewMerchant(@RequestParam("id")
    String id, ModelMap model)
    {
        try
        {
            Organization org = organizationService.queryOrganizationById(Long.parseLong(id));
            model.addAttribute("organization", org);
            model.addAttribute("mlist", org.getMerchants());
            setDefaultEnumModel(model, new Class[] {MerchantType.class});
            return PageConstant.PAGE_MERCHANT_VIEW;
        }
        catch (RpcException e)
        {
            logger.debug("[OrganizationController:organization_view()]" + e.getMessage());
            model.put("message", "操作失败" + e.getMessage());
            model.put("canback", true);
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
    }

    @RequestMapping(value = "/organization_edit", method = RequestMethod.GET)
    public String editMerchant(@RequestParam("id")
    String id, ModelMap model)
    {
        try
        {
            Organization org = organizationService.queryOrganizationById(Long.parseLong(id));
            model.addAttribute("organization", org);
            return PageConstant.PAGE_ORGANIZATIONT_EDIT;
        }
        catch (RpcException e)
        {
            logger.debug("[OrganizationController:organization_edit()]" + e.getMessage());
            model.put("message", "操作失败" + e.getMessage());
            model.put("canback", true);
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
    }

    @RequestMapping(value = "/organization_edit", method = RequestMethod.POST)
    public String editMerchantDo(@ModelAttribute("organization")
    Organization organization, ModelMap model)
    {
        try
        {
        	Organization org = organizationService.queryOrganizationById(organization.getOrganizationId());
        	if(org!=null)
        	{
	        	// 判断注册号是否已存在
	            Organization org1 = organizationService.queryOrganizationByNo(organization.getOrganizationRegistrationNo());
	            if (org1 != null && org1.getOrganizationId() != null)
	            {
	            	if(!org.getOrganizationId().equals(org1.getOrganizationId()))
	            	{
		                model.put("message", "操作失败,注册号已存在");
		                model.put("canback", true);
		                return PageConstant.PAGE_COMMON_NOTIFY;
	            	}
	            }
	            // 判断组织机构名称是否已存在
	            Organization org2 = organizationService.queryOrganizationByName(organization.getOrganizationName());
	            if (org2 != null && org2.getOrganizationId() != null)
	            {
	            	if(!org.getOrganizationId().equals(org2.getOrganizationId()))
	            	{
		                model.put("message", "操作失败,组织机构名称已存在");
		                model.put("canback", true);
		                return PageConstant.PAGE_COMMON_NOTIFY;
	            	}
	            }
	            org = organizationService.saveOrganization(organization);
	            if (org != null)
	            {
	                model.put("message", "操作成功");
	                model.put("canback", false);
	                model.put("next_url", "Organization/organizationList");
	                model.put("next_msg", "组织机构列表");
	            }
	            else
	            {
	                model.put("message", "操作失败");
	                model.put("canback", true);
	            }
        	}
            else
            {
                model.put("message", "操作失败");
                model.put("canback", true);
            }
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
        catch (RpcException e)
        {
            logger.debug("[OrganizationController:organization_edit()]" + e.getMessage());
            model.put("message", "操作失败" + e.getMessage());
            model.put("canback", true);
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
    }
}
