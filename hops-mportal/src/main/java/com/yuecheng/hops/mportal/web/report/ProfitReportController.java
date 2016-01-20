package com.yuecheng.hops.mportal.web.report;


import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.EntityConstant;
import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.common.utils.StringUtil;
import com.yuecheng.hops.mportal.vo.report.ProfitReportVo;
import com.yuecheng.hops.numsection.service.CarrierInfoService;
import com.yuecheng.hops.numsection.service.CityService;
import com.yuecheng.hops.numsection.service.ProvinceService;
import com.yuecheng.hops.report.entity.ProfitReportInfo;
import com.yuecheng.hops.report.entity.ReportProperty;
import com.yuecheng.hops.report.entity.ReportType;
import com.yuecheng.hops.report.entity.po.ProfitReportPo;
import com.yuecheng.hops.report.service.ProfitReportService;
import com.yuecheng.hops.report.service.ReportPropertyService;
import com.yuecheng.hops.report.service.ReportTypeService;


/**
 * 利润统计
 * 
 * @author Administrator
 * @version 2014年10月12日
 * @see ProfitReportController
 * @since
 */
@Controller
@RequestMapping(value = "/report")
public class ProfitReportController
{
    @Autowired
    private ProvinceService provinceService;

    @Autowired
    private ProfitReportService profitReportsService;

    @Autowired
    private ReportTypeService reportTypeService;

    @Autowired
    private ReportPropertyService reportPropertyService;

    @Autowired
    private CityService cityService;

    @Autowired
    private CarrierInfoService carrierInfoService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ProfitReportController.class);

    private static final String PAGE_SIZE = "10";

    /**
     * 利润统计列表
     * 
     * @param province
     * @param merchantName
     * @param merchantType
     * @param reportType
     * @param parValue
     * @param city
     * @param carrierInfo
     * @param reportsStatus
     * @param beginTime
     * @param endTime
     * @param pageNumber
     * @param pageSize
     * @param model
     * @param request
     * @return
     * @see
     */
    @RequestMapping(value = "/profitReports")
    public String profitReports(ProfitReportVo profitReportVo, String endTime,
                                @RequestParam(value = "page", defaultValue = "1")
                                int pageNumber,
                                @RequestParam(value = "pageSize", defaultValue = PAGE_SIZE)
                                int pageSize, Model model, ServletRequest request)
    {
        LOGGER.debug("[进入查询利润报表方法][ProfitReportsController:profitReports(profitReportVo:"
                     + profitReportVo != null ? profitReportVo.toString() : null + ")] ");

        if (StringUtil.isBlank(profitReportVo.getBeginTime())
            && StringUtil.isBlank(profitReportVo.getEndTime()))
        {
            profitReportVo.setBeginTime(ReportTool.getDefaultDate());
            profitReportVo.setEndTime(ReportTool.getDefaultDate());
        }
        ReportType rtype = new ReportType();
        StringBuffer html = new StringBuffer();
        YcPage<ProfitReportPo> pagelist = new YcPage<ProfitReportPo>();
        try
        {
            LOGGER.debug("[查询出利润报表分页数据][ProfitReportsController:profitReports()]  [调用profitReportsService中queryProfitReports()方法，获得报表数据]");

            if (StringUtil.isNotBlank(profitReportVo.getReportType()))
            {
                rtype = reportTypeService.getReportType(Long.valueOf(profitReportVo.getReportType()));
                List<ReportProperty> rpList = reportPropertyService.queryReportPropertysByTypeId(Long.valueOf(profitReportVo.getReportType()));
                Map<String, Object> searchParams = BeanUtils.transBean2Map(profitReportVo);
                pagelist = profitReportsService.queryProfitReports(searchParams, rpList,
                    profitReportVo.getBeginTime(), profitReportVo.getEndTime(), pageNumber,
                    pageSize, EntityConstant.ProfitReports.BEGIN_TIME);
                if(rpList.size()>0)
                {
                    html = ReportTool.getReportTypeHtml(html, rpList);
                    html = ReportTool.getReportPropertyHtml(html,pageNumber,pageSize, rpList, pagelist.getList());
                }else
                {
                    html=html.append("<tr><td>您还没有配置属性，请先配置。</td></tr>");
                }
            }
        }
        catch (Exception e)
        {
            // TODO: handle exception
            LOGGER.error("[查询利润报表报错][ProfitReportsController:profitReports()] " + e.getMessage());
        }

        model.addAttribute("reportTermList",
            ReportTool.getReportTermList(reportTypeService, rtype));
        model.addAttribute("reportTypeList",
            ReportTool.getReportTypeList(reportTypeService, Constant.ReportType.PROFIT_REPORTS));
        model.addAttribute("provinces", ReportTool.getProvince(provinceService));
        model.addAttribute("profits", html);

        model.addAttribute("provinces", ReportTool.getProvince(provinceService));
        model.addAttribute("citys", ReportTool.getcitys(cityService));
        model.addAttribute("carrierInfos", ReportTool.getcarrierInfos(carrierInfoService));
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("profitReportVo", profitReportVo);
        model.addAttribute("profitList", pagelist.getList());
        model.addAttribute("page", pageNumber);
        model.addAttribute("counttotal", pagelist.getCountTotal() + "");
        model.addAttribute("pagetotal", pagelist.getPageTotal() + "");
        LOGGER.debug("[结束查询利润报表][ProfitReportsController:profitReports()]  [调用profitReportsService中queryProfitReports()方法，获得报表数据]");
        return "/report/profitReports.ftl";
    }

    /**
     * 设置响应
     * 
     * @param province
     * @param merchantName
     * @param merchantType
     * @param reportType
     * @param parValue
     * @param city
     * @param carrierInfo
     * @param reportsStatus
     * @param beginTime
     * @param endTime
     * @param pageNumber
     * @param model
     * @param rtype
     * @param html
     * @param page_list
     * @see
     */
    protected void setModelToProfltReports(String province, String merchantName,
                                           String merchantType, String reportType,
                                           String parValue, String city, String carrierInfo,
                                           String reportsStatus, String beginTime, String endTime,
                                           int pageNumber, Model model, ReportType rtype,
                                           StringBuffer html, YcPage<ProfitReportInfo> page_list)
    {
        LOGGER.debug("[设置利润报表响应数据][ProfitReportsController: setModelToProfltReports()]");
        model.addAttribute("reportTermList",
            ReportTool.getReportTermList(reportTypeService, rtype));
        model.addAttribute("reportTypeList",
            ReportTool.getReportTypeList(reportTypeService, Constant.ReportType.PROFIT_REPORTS));
        model.addAttribute("provinces", ReportTool.getProvince(provinceService));
        model.addAttribute("profits", html);
        model.addAttribute("reportType", reportType);
        model.addAttribute("reportsStatus", reportsStatus);
        model.addAttribute("merchantName", merchantName);
        model.addAttribute("merchantType", merchantType);

        model.addAttribute("provinces", ReportTool.getProvince(provinceService));
        model.addAttribute("citys", ReportTool.getcitys(cityService));
        model.addAttribute("carrierInfos", ReportTool.getcarrierInfos(carrierInfoService));
        model.addAttribute("province", province);
        model.addAttribute("carrierInfo", carrierInfo);
        model.addAttribute("city", city);

        model.addAttribute("parValue", parValue);
        model.addAttribute("reportType", reportType);
        model.addAttribute("beginTime", beginTime);
        model.addAttribute("endTime", endTime);
        model.addAttribute("profitList", page_list.getList());
        model.addAttribute("page", pageNumber);
        model.addAttribute("counttotal", page_list.getCountTotal() + "");
        model.addAttribute("pagetotal", page_list.getPageTotal() + "");
    }

    /**
     * 测试
     */
    @RequestMapping(value = "/testprofit")
    public String testprofit(@RequestParam(value = "beginTime", defaultValue = "")
    String beginTime, @RequestParam(value = "endTime", defaultValue = "")
    String endTime, Model model, ServletRequest request)
    {
        profitReportsService.testReport(beginTime, endTime);
        return null;
    }
}
