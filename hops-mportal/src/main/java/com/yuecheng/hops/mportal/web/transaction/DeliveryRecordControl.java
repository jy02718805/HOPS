package com.yuecheng.hops.mportal.web.transaction;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;

import org.apache.commons.lang.StringUtils;
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
import com.google.gson.Gson;
import com.yuecheng.hops.account.service.TransactionHistoryService;
import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.enump.MerchantType;
import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.common.utils.DateUtil;
import com.yuecheng.hops.identity.entity.merchant.Merchant;
import com.yuecheng.hops.identity.service.merchant.MerchantService;
import com.yuecheng.hops.mportal.constant.PageConstant;
import com.yuecheng.hops.mportal.web.BaseControl;
import com.yuecheng.hops.numsection.entity.CarrierInfo;
import com.yuecheng.hops.numsection.entity.Province;
import com.yuecheng.hops.numsection.service.CarrierInfoService;
import com.yuecheng.hops.numsection.service.CityService;
import com.yuecheng.hops.numsection.service.ProvinceService;
import com.yuecheng.hops.transaction.basic.entity.Delivery;
import com.yuecheng.hops.transaction.basic.entity.vo.DeliveryStatisticsVo;
import com.yuecheng.hops.transaction.service.delivery.DeliveryManagement;
import com.yuecheng.hops.transaction.service.order.OrderManagement;


@Controller
@RequestMapping(value = "/transaction")
public class DeliveryRecordControl extends BaseControl
{

    private static final Logger LOGGER = LoggerFactory.getLogger(DeliveryRecordControl.class);

    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private OrderManagement orderService;

    @Autowired
    private DeliveryManagement deliveryService;

    @Autowired
    private ProvinceService provinceService;

    @Autowired
    private CityService cityService;

    @Autowired
    private CarrierInfoService carrierInfoService;

    @Autowired
    private MerchantService merchantService;

    @Autowired
    private TransactionHistoryService transactionHistoryService;

    @Autowired
    private DeliveryManagement deliveryManagement;

    private static final String PAGE_SIZE = "10";

    @RequestMapping(value = "/deliveryRecordList", method = RequestMethod.GET)
    public String deliveryRecordList(@RequestParam(value = "page", defaultValue = "1")
    int pageNumber, @RequestParam(value = "pageSize", defaultValue = PAGE_SIZE)
    int pageSize, @RequestParam(value = "sortType", defaultValue = "auto")
    String sortType, @RequestParam(value = "beginDate", defaultValue = "")
    String beginDate, @RequestParam(value = "endDate", defaultValue = "")
    String endDate, @RequestParam(value = "delivery_status", defaultValue = "")
    String delivery_status, @RequestParam(value = "supplymerchant", defaultValue = "")
    String supplymerchant, @RequestParam(value = "agentmerchant", defaultValue = "")
    String agentmerchant, @RequestParam(value = "carrier_no", defaultValue = "")
    String carrier_no, @RequestParam(value = "province_id", defaultValue = "")
    String province_id, @RequestParam(value = "numbertype", defaultValue = "")
    String numbertype, @RequestParam(value = "numbervalue", defaultValue = "")
    String numbervalue, @RequestParam(value = "statisticsOrder", defaultValue = "")
    String statisticsOrder, @RequestParam(value = "changeDate", defaultValue = "")
    String changeDate, @RequestParam(value = "parValue", defaultValue = "")
    String parValue, @RequestParam(value = "usercode", defaultValue = "")
    String usercode, @RequestParam(value = "orderNo", defaultValue = "")
    String orderNo, @RequestParam(value = "businessType", defaultValue = "")
    String businessType, ModelMap model, ServletRequest request)
    {

        if (StringUtils.isBlank(beginDate) && StringUtils.isBlank(endDate))
        {
            beginDate = DateUtil.toDateMinute(new Date());
            endDate = DateUtil.getDateTime();
        }
        List<Province> provinces = provinceService.getAllProvince();
        List<CarrierInfo> carrierInfos = carrierInfoService.getAllCarrierInfo();
        List<Merchant> supplylist = merchantService.queryAllMerchant(MerchantType.SUPPLY, null);
        List<Merchant> agentlist = merchantService.queryAllMerchant(MerchantType.AGENT, null);

        String supply_no = "", agent_no = "";
        if (!StringUtils.isBlank(numbertype))
        {
            if (numbertype.equals("supply_no"))
            {
                supply_no = numbervalue;
            }
            else if (numbertype.equals("agent_no"))
            {
                agent_no = numbervalue;
            }
        }

        YcPage<Map<String, Object>> deliveryList = deliveryManagement.queryDeliveryList(beginDate,
            endDate, delivery_status, carrier_no, province_id, supplymerchant, agentmerchant,
            supply_no, agent_no, orderNo, parValue, usercode, pageNumber, pageSize, null,
            businessType);

        model.addAttribute("provincelist", provinces);
        model.addAttribute("carrierlist", carrierInfos);
        model.addAttribute("supplylist", supplylist);
        model.addAttribute("agentlist", agentlist);

        model.addAttribute("beginDate", beginDate);
        model.addAttribute("delivery_status", delivery_status);
        model.addAttribute("supplymerchant", supplymerchant);
        model.addAttribute("agentmerchant", agentmerchant);

        model.addAttribute("endDate", endDate);
        model.addAttribute("numbertype", numbertype);
        model.addAttribute("numbervalue", numbervalue);
        model.addAttribute("parValue", parValue);
        model.addAttribute("usercode", usercode);
        model.addAttribute("orderNo", orderNo);

        model.addAttribute("carrier_no", carrier_no);
        model.addAttribute("province_id", province_id);

        model.addAttribute("businessType", businessType);

        model.addAttribute("changeDate", changeDate);
        model.addAttribute("statisticsOrder", statisticsOrder);

        setDefaultStaticModel(model, new Class[] {Constant.Delivery.class});

        model.addAttribute("pageSize", pageSize);
        model.addAttribute("deliverylist", deliveryList.getList());
        model.addAttribute("page", pageNumber);
        model.addAttribute("counttotal", deliveryList.countTotal);
        model.addAttribute("pagetotal", deliveryList.pageTotal);
        return "transaction/deliverylist.ftl";
    }

    @RequestMapping(value = "/deliveryresult")
    public String deliveryresult(@RequestParam(value = "deliveryId", defaultValue = "")
    String deliveryId, ModelMap model)
    {
        LOGGER.info("[" + format.format(new Date())
                    + "][DeliveryRecordControl :deliveryresult()] ");
        Delivery delivery = new Delivery();
        try
        {
            delivery = deliveryService.findDeliveryById(Long.valueOf(deliveryId));
        }
        catch (NullPointerException e)
        {
            LOGGER.error("[" + format.format(new Date())
                         + "][DeliveryRecordControl :deliveryresult() deliveryId为空] "
                         + e.getMessage());
        }
        catch (NumberFormatException e)
        {
            LOGGER.error("["
                         + format.format(new Date())
                         + "][DeliveryRecordControl :deliveryresult() deliveryId] [Long.valueOf(deliveryId)转换失败!]"
                         + e.getMessage());
        }
        String deliveryResult = delivery.getDeliveryResult() == ""
                                || delivery.getDeliveryResult() == null ? "" : delivery.getDeliveryResult().replaceAll(
            "\"", "\'").replaceAll("，", ",").trim();
        model.addAttribute("deliveryResult", deliveryResult);
        return "transaction/deliveryresult";
    }

    public static String replaceString(String strData, String regex, String replacement)
    {
        if (strData == null)
        {
            return null;
        }
        int index;
        index = strData.indexOf(regex);
        String strNew = "";
        if (index >= 0)
        {
            while (index >= 0)
            {
                strNew += strData.substring(0, index) + replacement;
                strData = strData.substring(index + regex.length());
                index = strData.indexOf(regex);
            }
            strNew += strData;
            return strNew;
        }
        return strData;
    }

    public static String encodeString(String strData)
    {
        if (strData == null)
        {
            return "";
        }
        strData = replaceString(strData, "&", "&amp;");
        strData = replaceString(strData, "<", "&lt;");
        strData = replaceString(strData, ">", "&gt;");
        strData = replaceString(strData, "&apos;", "&apos;");
        strData = replaceString(strData, "\"", "&quot;");
        return strData;
    }

    @RequestMapping(value = "/statisticsDeliveryInfo")
    @ResponseBody
    public String statisticsOrderInfo(@RequestParam(value = "carrierInfo", defaultValue = "")
    String carrierInfo, @RequestParam(value = "province", defaultValue = "")
    String province, @RequestParam(value = "numbervalue", defaultValue = "")
    String numbervalue, @RequestParam(value = "parValue", defaultValue = "")
    String parValue, @RequestParam(value = "usercode", defaultValue = "")
    String usercode, @RequestParam(value = "orderNo", defaultValue = "")
    String orderNo, @RequestParam(value = "businessType", defaultValue = "")
    String businessType, @RequestParam(value = "numbertype", defaultValue = "")
    String numbertype, @RequestParam(value = "agentMerchant", defaultValue = "")
    String agentmerchant, @RequestParam(value = "supplyMerchant", defaultValue = "")
    String supplymerchant, @RequestParam(value = "deliveryStatus", defaultValue = "")
    String deliveryStatus, @RequestParam(value = "beginDate", defaultValue = "")
    String beginDate, @RequestParam(value = "endDate", defaultValue = "")
    String endDate, ModelMap model, ServletRequest request)
    {
        try
        {
            String supply_no = "", agent_no = "";
            if (!StringUtils.isBlank(numbertype))
            {
                if (numbertype.equals("supply_no"))
                {
                    supply_no = numbervalue;
                }
                else if (numbertype.equals("agent_no"))
                {
                    agent_no = numbervalue;
                }
            }

            DeliveryStatisticsVo deliveryStatisticsVo = deliveryManagement.statisticsDeliveryInfo(
                beginDate, endDate, deliveryStatus, carrierInfo, province, supplymerchant,
                agentmerchant, supply_no, agent_no, parValue, usercode, orderNo, businessType);
            Gson gson = new Gson();
            String deliveryStatisticsVoStr = gson.toJson(deliveryStatisticsVo);
            return deliveryStatisticsVoStr;
        }
        catch (RpcException e)
        {
            LOGGER.error("[OrderController:getProducts()]" + e.getMessage());
            return null;
        }
    }

    @RequestMapping(value = "/checkReissueDelivery")
    public String checkReissueOrderList(@RequestParam(value = "deliveryId")
    String deliveryIds, ModelMap model, ServletRequest request)
    {
        try
        {
            List<Long> deliverys = deliveryManagement.batchUpdateQueryFlag(deliveryIds,
                Constant.Delivery.QUERY_FLAG_WAIT_QUERY);

            model.put("message", "操作成功");
            model.put("canback", false);
            model.put("next_url", "transaction/reissueDeliveryRecordList");
            model.put("next_msg", "返回列表");
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
        catch (RpcException e)
        {
            LOGGER.debug("[OrderController:checkSuccessOrder()]" + e.getMessage());
            LOGGER.error("批量补发查询发生异常：" + e.getMessage());
            model.put("canback", true);
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
    }

    @RequestMapping(value = "/reissueDeliveryRecordList", method = RequestMethod.GET)
    public String reissueDeliveryRecordList(@RequestParam(value = "page", defaultValue = "1")
    int pageNumber, @RequestParam(value = "pageSize", defaultValue = PAGE_SIZE)
    int pageSize, @RequestParam(value = "queryFlag", defaultValue = "")
    String queryFlag, @RequestParam(value = "beginDate", defaultValue = "")
    String beginDate, @RequestParam(value = "endDate", defaultValue = "")
    String endDate, @RequestParam(value = "delivery_status", defaultValue = "")
    String delivery_status, @RequestParam(value = "supplymerchant", defaultValue = "")
    String supplymerchant, @RequestParam(value = "agentmerchant", defaultValue = "")
    String agentmerchant, @RequestParam(value = "carrier_no", defaultValue = "")
    String carrier_no, @RequestParam(value = "province_id", defaultValue = "")
    String province_id, @RequestParam(value = "numbertype", defaultValue = "")
    String numbertype, @RequestParam(value = "numbervalue", defaultValue = "")
    String numbervalue, @RequestParam(value = "statisticsOrder", defaultValue = "")
    String statisticsOrder, @RequestParam(value = "changeDate", defaultValue = "")
    String changeDate, @RequestParam(value = "parValue", defaultValue = "")
    String parValue, @RequestParam(value = "usercode", defaultValue = "")
    String usercode, @RequestParam(value = "orderNo", defaultValue = "")
    String orderNo, @RequestParam(value = "businessType", defaultValue = "")
    String businessType, ModelMap model, ServletRequest request)
    {

        if (StringUtils.isBlank(beginDate) && StringUtils.isBlank(endDate))
        {
            beginDate = DateUtil.toDateMinute(new Date());
            endDate = DateUtil.getDateTime();
        }
        List<Province> provinces = provinceService.getAllProvince();
        List<CarrierInfo> carrierInfos = carrierInfoService.getAllCarrierInfo();
        List<Merchant> supplylist = merchantService.queryAllMerchant(MerchantType.SUPPLY, null);
        List<Merchant> agentlist = merchantService.queryAllMerchant(MerchantType.AGENT, null);

        String supply_no = "", agent_no = "";
        if (!StringUtils.isBlank(numbertype))
        {
            if (numbertype.equals("supply_no"))
            {
                supply_no = numbervalue;
            }
            else if (numbertype.equals("agent_no"))
            {
                agent_no = numbervalue;
            }
        }

        YcPage<Map<String, Object>> deliveryList = deliveryManagement.queryDeliveryList(beginDate,
            endDate, delivery_status, carrier_no, province_id, supplymerchant, agentmerchant,
            supply_no, agent_no, orderNo, parValue, usercode, pageNumber, pageSize,
            Constant.Delivery.QUERY_FLAG_NEED_QUERY + "", businessType);

        model.addAttribute("provincelist", provinces);
        model.addAttribute("carrierlist", carrierInfos);
        model.addAttribute("supplylist", supplylist);
        model.addAttribute("agentlist", agentlist);

        model.addAttribute("beginDate", beginDate);
        model.addAttribute("delivery_status", delivery_status);
        model.addAttribute("supplymerchant", supplymerchant);
        model.addAttribute("agentmerchant", agentmerchant);

        model.addAttribute("endDate", endDate);
        model.addAttribute("numbertype", numbertype);
        model.addAttribute("numbervalue", numbervalue);
        model.addAttribute("parValue", parValue);
        model.addAttribute("usercode", usercode);
        model.addAttribute("orderNo", orderNo);

        model.addAttribute("carrier_no", carrier_no);
        model.addAttribute("province_id", province_id);

        model.addAttribute("businessType", businessType);

        model.addAttribute("changeDate", changeDate);
        model.addAttribute("statisticsOrder", statisticsOrder);

        setDefaultStaticModel(model, new Class[] {Constant.Delivery.class});

        model.addAttribute("pageSize", pageSize);
        model.addAttribute("deliverylist", deliveryList.getList());
        model.addAttribute("page", pageNumber);
        model.addAttribute("counttotal", deliveryList.countTotal);
        model.addAttribute("pagetotal", deliveryList.pageTotal);
        return "transaction/reissueDeliveryRecordList.ftl";
    }
}
