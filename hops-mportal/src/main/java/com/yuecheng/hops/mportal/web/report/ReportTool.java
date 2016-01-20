package com.yuecheng.hops.mportal.web.report;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springside.modules.persistence.SearchFilter.Operator;

import com.yuecheng.hops.common.EntityConstant;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.common.utils.StringUtil;
import com.yuecheng.hops.mportal.vo.report.AccountReportVo;
import com.yuecheng.hops.mportal.vo.report.ProfitReportVo;
import com.yuecheng.hops.mportal.vo.report.TransactionReportVo;
import com.yuecheng.hops.numsection.entity.CarrierInfo;
import com.yuecheng.hops.numsection.entity.City;
import com.yuecheng.hops.numsection.entity.Province;
import com.yuecheng.hops.numsection.service.CarrierInfoService;
import com.yuecheng.hops.numsection.service.CityService;
import com.yuecheng.hops.numsection.service.ProvinceService;
import com.yuecheng.hops.report.entity.ReportProperty;
import com.yuecheng.hops.report.entity.ReportTerm;
import com.yuecheng.hops.report.entity.ReportType;
import com.yuecheng.hops.report.service.ReportTypeService;


/**
 * 报表公共
 * 
 * @author Administrator
 * @version 2014年10月12日
 * @see ReportTool
 * @since
 */
@Controller
@RequestMapping(value = "/reportTool")
public class ReportTool
{
    @Autowired
    private CityService cityService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ReportTool.class);

    /**
     * 默认时间
     * 
     * @return
     * @see
     */
    public static String getDefaultDate()
    {
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        String formatsStr = formatDate.format(cal.getTime());
        return formatsStr;
    }

    /**
     * 拼装html
     * 
     * @param html
     * @param rpList
     * @return
     * @see
     */
    public static StringBuffer getReportTypeHtml(StringBuffer html, List<ReportProperty> rpList)
    {
        if (!BeanUtils.isNullOrEmpty(rpList))
        {
            html.append("<tr>");
            html.append("<th>序列</th>");
            for (ReportProperty rp : rpList)
            {
                html.append("<th>" + rp.getReportPropertyName() + "</th>");
            }
            html.append("</tr>");
        }
        else
        {
            html.append("");
        }
        return html;
    }

    /**
     * 拼装html
     * 
     * @param html
     * @param rpList
     * @param content
     * @return
     * @see
     */
    public static StringBuffer getReportPropertyHtml(StringBuffer html, int pageNumber,
                                                     int pageSize, List<ReportProperty> rpList,
                                                     List<?> content)
    {
        if (!BeanUtils.isNullOrEmpty(content))
        {
            int i = 0;
            for (Object o : content)
            {
                BeanWrapper bw = new BeanWrapperImpl(o);
                ++i;
                int count = (pageNumber - 1) * pageSize + i;
                html.append("<tr>");
                html.append("<td>" + count + "</td>");
                for (ReportProperty rp : rpList)
                {

                    Object propertyValue = bw.getPropertyValue(rp.getReportPropertyFieldName());
                    // StringUtil.isNullOrEmpty(rp.getReportPropertyType()
                    // if (propertyValue != null
                    // && propertyValue.getClass().getName().equals(
                    // Constant.ReportType.REPORT_PROPERTY_DATE))
                    // {
                    // propertyValue = formatDate.format(propertyValue);
                    // }
                    if (propertyValue == null)
                    {
                        propertyValue = "";
                    }
                    if ("BUSINESSTYPE".equalsIgnoreCase(rp.getReportPropertyFieldName()))
                    {
                    	if ((Long)propertyValue == 0L)
                    	{
                    		propertyValue = "话费";
                    	}
                    	else if ((Long)propertyValue == 1L)
                    	{
                    		propertyValue = "流量";
                    	}
                    }
                    html.append("<td>" + propertyValue + "</td>");
                }
                html.append("</tr>");
            }
        }
        else
        {
            html.append("<tr>");
            html.append("<td colspan=" + (rpList.size() + 1) + ">" + "没数据" + "</td>");
            html.append("</tr>");
        }
        return html;
    }

    /**
     * 报表条件
     * 
     * @param reportTypeService
     * @param rtype
     * @return
     * @see
     */
    public static List<ReportTerm> getReportTermList(ReportTypeService reportTypeService,
                                                     ReportType rtype)
    {
        List<ReportTerm> reportTermList = new ArrayList<ReportTerm>();
        try
        {
            reportTermList = reportTypeService.getReportTermsByReportTypeId(rtype.getReportTypeId());;
        }
        catch (Exception e)
        {
            if (LOGGER.isErrorEnabled())
            {
                LOGGER.error("[ReportConstant:getReportTermList()]" + e.getMessage());
            }
        }
        return reportTermList;
    }

    /**
     * 报表类型
     * 
     * @param reportTypeService
     * @param reportType
     * @return
     * @see
     */
    public static List<ReportType> getReportTypeList(ReportTypeService reportTypeService,
                                                     String reportType)
    {
        List<ReportType> reportTypeList = new ArrayList<ReportType>();
        try
        {
            reportTypeList = reportTypeService.getReportTypeByType(reportType);
        }
        catch (Exception e)
        {
            if (LOGGER.isErrorEnabled())
            {
                LOGGER.error("[ReportConstant:getReportTypeList()]" + e.getMessage());
            }
        }
        return reportTypeList;
    }

    /**
     * 省份
     * 
     * @param provinceService
     * @return
     * @see
     */
    public static List<Province> getProvince(ProvinceService provinceService)
    {
        List<Province> provinces = new ArrayList<Province>();
        try
        {
            provinces = provinceService.getAllProvince();
        }
        catch (Exception e)
        {
            if (LOGGER.isErrorEnabled())
            {
                LOGGER.error("[ReportConstant:getProvince()]" + e.getMessage());
            }
        }
        return provinces;
    }

    /**
     * 城市
     * 
     * @param cityService
     * @return
     * @see
     */
    public static List<City> getcitys(CityService cityService)
    {
        List<City> citys = new ArrayList<City>();
        try
        {
            citys = cityService.selectAll();
        }
        catch (Exception e)
        {
            if (LOGGER.isErrorEnabled())
            {
                LOGGER.error("[ReportConstant: getcitys()]" + e.getMessage());
            }
        }
        return citys;
    }

    /**
     * 运营商
     * 
     * @param carrierInfoService
     * @return
     * @see
     */
    public static List<CarrierInfo> getcarrierInfos(CarrierInfoService carrierInfoService)
    {
        List<CarrierInfo> carrierInfos = new ArrayList<CarrierInfo>();
        try
        {
            carrierInfos = carrierInfoService.getAllCarrierInfo();
        }
        catch (Exception e)
        {
            if (LOGGER.isErrorEnabled())
            {
                LOGGER.error("[ReportConstant: getcarrierInfos()]" + e.getMessage());
            }
        }
        return carrierInfos;
    }

    /**
     * 判断报表实体是否为空
     * 
     * @param rt
     * @return
     * @see
     */
    public static boolean reportTypeisNotNull(ReportType rt)
    {
        if (rt == null || rt.getReportTypeId() == null)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    /**
     * 设置交易量响应
     * 
     * @param province
     * @param merchantName
     * @param merchantType
     * @param parValue
     * @param city
     * @param carrierInfo
     * @param searchParams
     * @see
     */
    public static void getTransactionReportsMap(String province, String merchantName,
                                                String merchantType, String parValue, String city,
                                                String carrierInfo,
                                                Map<String, Object> searchParams)
    {
        if (!StringUtil.isNullOrEmpty(province))
        {
            searchParams.put(Operator.EQ + "_" + EntityConstant.TransactionReports.PROVINCE,
                province);
        }
        if (!StringUtil.isNullOrEmpty(merchantName))
        {
            searchParams.put(Operator.EQ + "_" + EntityConstant.TransactionReports.MERCHANT_NAME,
                merchantName);
        }

        if (!StringUtil.isNullOrEmpty(merchantType))
        {
            searchParams.put(Operator.EQ + "_" + EntityConstant.TransactionReports.MERCHANT_TYPE,
                merchantType);
        }

        if (!StringUtil.isNullOrEmpty(parValue))
        {
            searchParams.put(Operator.EQ + "_" + EntityConstant.TransactionReports.PAR_VALUE,
                parValue);
        }

        if (!StringUtil.isNullOrEmpty(carrierInfo))
        {
            searchParams.put(Operator.EQ + "_" + EntityConstant.TransactionReports.CARRIER_NO,
                carrierInfo);
        }
        if (!StringUtil.isNullOrEmpty(city))
        {
            searchParams.put(Operator.EQ + "_" + EntityConstant.TransactionReports.CITY, city);
        }
    }

    /**
     * 设置利润统计响应
     * 
     * @param province
     * @param merchantName
     * @param merchantType
     * @param parValue
     * @param city
     * @param carrierInfo
     * @param searchParams
     * @see
     */
    public static void getProfitReportsMap(String province, String merchantName,
                                           String merchantType, String parValue, String city,
                                           String carrierInfo, Map<String, Object> searchParams)
    {
        if (!StringUtil.isNullOrEmpty(province))
        {
            searchParams.put(Operator.EQ + "_" + EntityConstant.ProfitReports.PROVINCE, province);
        }

        if (!StringUtil.isNullOrEmpty(merchantName))
        {
            searchParams.put(Operator.EQ + "_" + EntityConstant.ProfitReports.MERCHANT_NAME,
                merchantName);
        }

        if (!StringUtil.isNullOrEmpty(merchantType))
        {
            searchParams.put(Operator.EQ + "_" + EntityConstant.ProfitReports.MERCHANT_TYPE,
                merchantType);
        }

        if (!StringUtil.isNullOrEmpty(parValue))
        {
            searchParams.put(Operator.EQ + "_" + EntityConstant.ProfitReports.PAR_VALUE, parValue);
        }

        if (!StringUtil.isNullOrEmpty(carrierInfo))
        {
            searchParams.put(Operator.EQ + "_" + EntityConstant.ProfitReports.CARRIER_NO,
                carrierInfo);
        }
        if (!StringUtil.isNullOrEmpty(city))
        {
            searchParams.put(Operator.EQ + "_" + EntityConstant.TransactionReports.CITY, city);
        }
    }

    /**
     * 设置利润统计响应
     * 
     * @param province
     * @param identityName
     * @param identityType
     * @param parValue
     * @param city
     * @param carrierInfo
     * @param searchParams
     * @see
     */
    public static void getAccountReportMap(String identityName, Long accountId,
                                           String identityType, Long accountTypeId,
                                           Map<String, Object> searchParams)
    {
        if (accountId != null)
        {
            searchParams.put(Operator.EQ + "_" + EntityConstant.AccountReport.ACCOUNT_ID,
                accountId);
        }

        if (!StringUtil.isNullOrEmpty(identityName))
        {
            searchParams.put(Operator.EQ + "_" + EntityConstant.AccountReport.IDENTITY_NAME,
                identityName);
        }

        if (!StringUtil.isNullOrEmpty(identityType))
        {
            searchParams.put(Operator.EQ + "_" + EntityConstant.AccountReport.IDENTITY_TYPE,
                identityType);
        }
        if (accountTypeId != null)
        {
            searchParams.put(Operator.EQ + "_" + EntityConstant.AccountReport.ACCOUNT_TYPE_ID,
                accountTypeId);
        }
    }

    public static boolean transactionReportVoisNotNull(TransactionReportVo transactionReportVo)
    {
        if (StringUtil.isNotBlank(transactionReportVo.getCarrierNo())
            || StringUtil.isNotBlank(transactionReportVo.getProvince())
            || StringUtil.isNotBlank(transactionReportVo.getCity())
            || StringUtil.isNotBlank(transactionReportVo.getMerchantType()))
        {
            return true;
        }
        return false;
    }

    public static boolean profitReportVoisNotNull(ProfitReportVo profitReportVo)
    {
        if (StringUtil.isNotBlank(profitReportVo.getCarrierNo())
            || StringUtil.isNotBlank(profitReportVo.getProvince())
            || StringUtil.isNotBlank(profitReportVo.getCity())
            || StringUtil.isNotBlank(profitReportVo.getMerchantType()))
        {
            return true;
        }
        return false;
    }

    public static boolean accountReportVoisNotNull(AccountReportVo accountReportVo)
    {
        if (StringUtil.isNotBlank(accountReportVo.getAccountTypeId())
            || StringUtil.isNotBlank(accountReportVo.getIdentityType()))
        {
            return true;
        }
        return false;
    }

    @RequestMapping(value = "/getCityByProvince")
    @ResponseBody
    public String getCityByProvince(@RequestParam(value = "provinceId")
    String provinceId, Model model)
    {
        StringBuffer city_sb = new StringBuffer();
        List<City> citys = cityService.getCityByProvince(provinceId);
        city_sb.append("<option value=\'\'>请选择</option>");
        if (citys.size() > 0)
        {
            for (Iterator<City> iterator2 = citys.iterator(); iterator2.hasNext();)
            {
                City city = iterator2.next();
                city_sb.append("<option value=\'" + city.getCityId() + "\'>" + city.getCityName()
                               + "</option>");
            }
        }
        return city_sb.toString();
    }
}
