package com.yuecheng.hops.aportal.web.product;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.ui.ModelMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springside.modules.persistence.SearchFilter.Operator;

import com.yuecheng.hops.aportal.constant.PageConstant;
import com.yuecheng.hops.aportal.web.BaseControl;
import com.yuecheng.hops.common.EntityConstant;
import com.yuecheng.hops.common.query.BSort;
import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.common.utils.StringUtil;
import com.yuecheng.hops.identity.service.merchant.MerchantPageQuery;
import com.yuecheng.hops.numsection.entity.CarrierInfo;
import com.yuecheng.hops.numsection.entity.Province;
import com.yuecheng.hops.numsection.service.CarrierInfoService;
import com.yuecheng.hops.numsection.service.CityService;
import com.yuecheng.hops.numsection.service.ProvinceService;
import com.yuecheng.hops.product.entity.relation.AgentProductRelation;
import com.yuecheng.hops.product.service.AgentProductRelationService;
import com.yuecheng.hops.product.service.ProductPropertyService;
import com.yuecheng.hops.product.service.ProductService;
import com.yuecheng.hops.product.service.ProductTypeService;
import com.yuecheng.hops.product.service.SupplyProductRelationService;


@Controller
@RequestMapping(value = "/product")
public class ProductController extends BaseControl
{
    private static final String PAGE_SIZE = "10";

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductTypeService productTypeService;

    @Autowired
    private ProductPropertyService productPropertyService;

    @Autowired
    private MerchantPageQuery merchantService;

    @Autowired
    private ProvinceService provinceService;

    @Autowired
    private CityService cityService;

    @Autowired
    private CarrierInfoService carrierInfoService;

    @Autowired
    private SupplyProductRelationService supplyProductRelationService;

    @Autowired
    private AgentProductRelationService agentProductRelationService;

    @Autowired
    HttpSession session;

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 条件查询
     * 
     * @param faceValue
     * @param carrierInfo
     * @param province
     * @param city
     * @param pageNumber
     * @param pageSize
     * @param sortType
     */
    @RequestMapping(value = "/agentProductRelationInfoList")
    public String agentProductRelationInfoList(@RequestParam(value = "faceValue", defaultValue = "")
                                               String faceValue,
                                               @RequestParam(value = "carrierInfo", defaultValue = "")
                                               String carrierInfo,
                                               @RequestParam(value = "province", defaultValue = "")
                                               String province,
                                               @RequestParam(value = "page", defaultValue = "1")
                                               int pageNumber,
                                               @RequestParam(value = "pageSize", defaultValue = PAGE_SIZE)
                                               int pageSize,
                                               @RequestParam(value = "sortType", defaultValue = "auto")
                                               String sortType, ModelMap model, ServletRequest request)
    {
        LOGGER.debug("[ProductController :agentProductRelationInfoList()]");
        YcPage<AgentProductRelation> page_list = new YcPage<AgentProductRelation>();

        com.yuecheng.hops.identity.entity.operator.Operator loginPerson = BaseControl.getLoginUser();
        Long identityId = loginPerson.getOwnerIdentityId();
        List<Province> provinces = new ArrayList<Province>();
        List<CarrierInfo> carrierInfos = carrierInfoService.getAllCarrierInfo();
        try
        {
            if (identityId != null)
            {
                Map<String, Object> searchParams = new HashMap<String, Object>();
                if (!StringUtil.isNullOrEmpty(faceValue.trim()))
                {
                    searchParams.put(EntityConstant.AgentProductRelation.PAR_VALUE, faceValue.trim());
                }
                if (!StringUtil.isNullOrEmpty(carrierInfo))
                {
                    searchParams.put(EntityConstant.AgentProductRelation.CARRIER_NAME,
                        carrierInfo);
                }
                if (!StringUtil.isNullOrEmpty(province))
                {
                    searchParams.put(EntityConstant.AgentProductRelation.PROVINCE, province);
                }

                searchParams.put(EntityConstant.AgentProductRelation.IDENTITY_ID,
                    identityId.toString());
                BSort bsort = new BSort(BSort.Direct.DESC,
                    EntityConstant.AirtimeProduct.PRODUCT_ID);
                page_list = agentProductRelationService.queryAgentProductRelation(searchParams,
                    pageNumber, pageSize, bsort);
            }
            provinces = provinceService.getAllProvince();
        }
        catch (Exception e)
        {
            LOGGER.error("[ProductController :agentProductRelationInfoList()] " + e.getMessage());
            model.put("message", "操作失败");
            model.put("canback", true);
            return PageConstant.PAGE_COMMON_NOTIFY;

        }
        model.addAttribute("faceValue", faceValue);
        model.addAttribute("carrierInfoVal", carrierInfo);
        model.addAttribute("provinceVal", province);
        model.addAttribute("provinces", provinces);
        model.addAttribute("carrierInfos", carrierInfos);

        model.addAttribute("mlist", page_list.getList());
        model.addAttribute("page", pageNumber);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("counttotal", page_list.getCountTotal() + "");
        model.addAttribute("pagetotal", page_list.getPageTotal() + "");
        return "product/agentProductRelationInfoList";
    }
}
