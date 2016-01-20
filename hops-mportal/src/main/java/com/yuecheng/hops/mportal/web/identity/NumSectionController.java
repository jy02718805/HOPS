package com.yuecheng.hops.mportal.web.identity;


/**
 * 号段管理界面控制层
 * 
 * @author：Jinger
 * @date：2013-10-10
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springside.modules.persistence.SearchFilter.Operator;

import com.alibaba.dubbo.rpc.RpcException;
import com.yuecheng.hops.common.EntityConstant;
import com.yuecheng.hops.common.query.BSort;
import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.common.utils.StringUtil;
import com.yuecheng.hops.mportal.constant.PageConstant;
import com.yuecheng.hops.numsection.entity.CarrierInfo;
import com.yuecheng.hops.numsection.entity.City;
import com.yuecheng.hops.numsection.entity.NumSection;
import com.yuecheng.hops.numsection.entity.Province;
import com.yuecheng.hops.numsection.service.CarrierInfoService;
import com.yuecheng.hops.numsection.service.CityService;
import com.yuecheng.hops.numsection.service.NumSectionService;
import com.yuecheng.hops.numsection.service.ProvinceService;
import com.yuecheng.hops.privilege.service.MenuService;


@Controller
@RequestMapping(value = "/NumSection")
public class NumSectionController
{
    @Autowired
    private NumSectionService numSectionService;

    @Autowired
    private ProvinceService provinceService;

    @Autowired
    private CityService cityService;

    @Autowired
    private CarrierInfoService carrierInfoService;

    @Autowired
    private MenuService menuService;

    private static Logger logger = LoggerFactory.getLogger(NumSectionController.class);

    /**
     * 进入号段添加页面
     * 
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addNumSection(ModelMap model)
    {
        try
        {
            List<Province> province = provinceService.getAllProvince();
            List<CarrierInfo> carrierInfo = carrierInfoService.getAllCarrierInfo();
            model.addAttribute("province", province);
            model.addAttribute("carrierInfo", carrierInfo);
            return PageConstant.PAGE_NUMSECTION_ADD;
        }
        catch (RpcException e)
        {
            logger.debug("[NumSectionController:addNumSection(GET)]" + e.getMessage());
            model.put("message", "操作失败[" + e.getMessage() + "]");
            model.put("canback", true);
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
    }

    /**
     * 进入号段添加页面
     * 
     * @return
     */
    @RequestMapping(value = "/addCity", method = RequestMethod.POST)
    @ResponseBody
    public String addCity(@RequestParam("id")
    String id, ModelMap model)
    {
        try
        {
            logger.debug("[NumSectionController:addCity(" + id + ")]");
            List<City> citylist = cityService.getCityByProvince(id);
            int i = 0;
            String result = "";
            while (i < citylist.size())
            {
                City city = citylist.get(i);
                result = result + city.getCityId() + "*" + city.getCityName() + "|";
                i++ ;
            }
            return result;
        }
        catch (RpcException e)
        {
            logger.debug("[NumSectionController:addCity()]" + e.getMessage());
            return null;
        }
    }

    /**
     * 添加号段
     * 
     * @param numSection
     * @param result
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String saveNumSection(@ModelAttribute("numSection")
    NumSection numSection, ModelMap model, BindingResult result)
    {
        try
        {
            logger.debug("[NumSectionController:saveNumSection(" + numSection != null ? numSection.toString() : null
                                                                                                               + ")]");
            numSection.setCreateTime(new Date());
            numSection = numSectionService.saveNumSection(numSection);
            if (numSection != null)
            {
                model.put("message", "操作成功");
                model.put("canback", false);
                model.put("next_url", "NumSection/list");
                model.put("next_msg", "号段列表");
            }
            else
            {
                model.put("message", "操作失败");
                model.put("canback", true);
            }
        }
        catch (RpcException e)
        {
            logger.debug("[NumSectionController:saveNumSection()]" + e.getMessage());
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
    @RequestMapping(value = "/view", method = RequestMethod.GET)
    public String viewNumSection(@RequestParam("id")
    String id, ModelMap model)
    {
        try
        {
            logger.debug("[NumSectionController:viewNumSection(" + id + ")]");
            NumSection numSection = numSectionService.findOne(id);
            model.addAttribute("numSection", numSection);
            return PageConstant.PAGE_NUMSECTION_VIEW;
        }
        catch (RpcException e)
        {
            logger.debug("[NumSectionController:viewNumSection()]" + e.getMessage());
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
    @RequestMapping(value = "/numsection_delete", method = RequestMethod.GET)
    public String deleteNumSection(@RequestParam("id")
    String id, ModelMap model)
    {
        try
        {
            logger.debug("[NumSectionController:deleteNumSection(" + id + ")]");
            numSectionService.deleteNumSection(id);
            model.put("message", "操作成功");
            model.put("canback", false);
            model.put("next_url", "NumSection/list");
            model.put("next_msg", "号段列表");
        }
        catch (RpcException e)
        {
            logger.debug("[NumSectionController:deleteNumSection()]" + e.getMessage());
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
    @RequestMapping(value = "/numsection_edit", method = RequestMethod.GET)
    public String editNumSection(@RequestParam("id")
    String id, ModelMap model)
    {
        try
        {
            logger.debug("[NumSectionController:editNumSection(" + id + ")]");
            NumSection numSection = numSectionService.findOne(id);
            model.addAttribute("numSection", numSection);
            List<Province> province = provinceService.getAllProvince();
            List<CarrierInfo> carrierInfo = carrierInfoService.getAllCarrierInfo();
            model.addAttribute("province", province);
            model.addAttribute("carrierInfo", carrierInfo);
            return PageConstant.PAGE_NUMSECTION_EDIT;
        }
        catch (RpcException e)
        {
            logger.debug("[NumSectionController:editNumSection()]" + e.getMessage());
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
    public String editNumSectionDo(@ModelAttribute("numSection")
    NumSection numSection, ModelMap model)
    {
        try
        {
            logger.debug("[NumSectionController:editNumSectionDo(" + numSection != null ? numSection.toString() : null
                                                                                                                 + ")]");
            NumSection numSection1 = numSectionService.findOne(numSection.getSectionId());
            CarrierInfo carrierInfo=carrierInfoService.findOne(numSection.getCarrierInfo().getCarrierNo());
            Province province=provinceService.findOne(numSection.getProvince().getProvinceId());
            City city=cityService.findOne(numSection.getCity().getCityId());
            numSection.setCreateTime(numSection1.getCreateTime());
            numSection.setCarrierInfo(carrierInfo);
            numSection.setProvince(province);
            numSection.setCity(city);
            numSection = numSectionService.saveNumSection(numSection);
            if (numSection != null)
            {
                model.put("message", "操作成功");
                model.put("canback", false);
                model.put("next_url", "NumSection/list");
                model.put("next_msg", "号段列表");
            }
            else
            {
                model.put("message", "操作失败");
                model.put("canback", true);
            }
        }
        catch (RpcException e)
        {
            logger.debug("[NumSectionController:editNumSectionDo()]" + e.getMessage());
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
    public String listdNumSection(@RequestParam(value = "id", defaultValue = "")
    String id, @RequestParam(value = "page", defaultValue = "1")
    int pageNumber, @RequestParam(value = "pageSize", defaultValue = PageConstant.LIST_PAGE_SIZE)
    int pageSize, @RequestParam(value = "sortType", defaultValue = PageConstant.LIST_ORDER_SORT)
    String sortType, ModelMap model, ServletRequest request)
    {
        try
        {
            logger.debug("[NumSectionController:listdNumSection(" + id + ")]");
            Map<String, Object> searchParams = new HashMap<String, Object>();
            if (StringUtil.isNotBlank(id))
            {
                searchParams.put(Operator.EQ + "_" + EntityConstant.NumSection.SECTION_ID, id);
            }
            BSort bsort = new BSort(BSort.Direct.DESC, "createTime");
            YcPage<NumSection> page_list = numSectionService.queryNumSection(searchParams,
                pageNumber, pageSize, bsort);
            List<NumSection> list = page_list.getList();
            String pagetotal = page_list.getPageTotal() + "";
            String countTotal = page_list.getCountTotal() + "";
            model.addAttribute("mlist", list);
            model.addAttribute("page", pageNumber);
            model.addAttribute("pageSize", pageSize);
            model.addAttribute("pagetotal", pagetotal);
            model.addAttribute("counttotal", countTotal);
            model.addAttribute("id", id);
            return PageConstant.PAGE_NUMSECTION_LIST;
        }
        catch (RpcException e)
        {
            logger.debug("[NumSectionController:listdNumSection()]" + e.getMessage());
            model.put("message", "操作失败" + e.getMessage());
            model.put("canback", true);
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
    }
}
