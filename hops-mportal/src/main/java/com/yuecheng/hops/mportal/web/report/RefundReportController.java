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
import com.yuecheng.hops.mportal.vo.report.RefundReportVo;
import com.yuecheng.hops.numsection.service.CarrierInfoService;
import com.yuecheng.hops.numsection.service.CityService;
import com.yuecheng.hops.numsection.service.ProvinceService;
import com.yuecheng.hops.report.entity.RefundReportInfo;
import com.yuecheng.hops.report.entity.ReportProperty;
import com.yuecheng.hops.report.entity.ReportType;
import com.yuecheng.hops.report.entity.po.RefundReportPo;
import com.yuecheng.hops.report.service.RefundReportService;
import com.yuecheng.hops.report.service.ReportPropertyService;
import com.yuecheng.hops.report.service.ReportTypeService;


/**
 * 利润统计
 * 
 * @author Administrator
 * @version 2014年10月12日
 * @see RefundReportController
 * @since
 */
@Controller
@RequestMapping(value = "/report")
public class RefundReportController
{
    @Autowired
    private ProvinceService provinceService;

    @Autowired
    private RefundReportService refundReportService;

    @Autowired
    private ReportTypeService reportTypeService;

    @Autowired
    private ReportPropertyService reportPropertyService;

    @Autowired
    private CityService cityService;

    @Autowired
    private CarrierInfoService carrierInfoService;

    private static final Logger LOGGER = LoggerFactory.getLogger(RefundReportController.class);

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
    @RequestMapping(value = "/refundReports")
    public String refundReports(RefundReportVo refundReportVo, String endTime,
                                @RequestParam(value = "page", defaultValue = "1")
                                int pageNumber,
                                @RequestParam(value = "pageSize", defaultValue = PAGE_SIZE)
                                int pageSize, Model model, ServletRequest request)
    {
        LOGGER.debug("[进入查询利润报表方法][refundReportsController:refundReports(refundReportVo:"
                     + refundReportVo != null ? refundReportVo.toString() : null + ")] ");

        if (StringUtil.isBlank(refundReportVo.getBeginTime())
            && StringUtil.isBlank(refundReportVo.getEndTime()))
        {
            refundReportVo.setBeginTime(ReportTool.getDefaultDate());
            refundReportVo.setEndTime(ReportTool.getDefaultDate());
        }
        ReportType rtype = new ReportType();
        StringBuffer html = new StringBuffer();
        YcPage<RefundReportPo> pagelist = new YcPage<RefundReportPo>();
        try
        {
            LOGGER.debug("[查询出利润报表分页数据][refundReportsController:refundReports()]  [调用refundReportsService中queryrefundReports()方法，获得报表数据]");

            if (StringUtil.isNotBlank(refundReportVo.getReportType()))
            {
                rtype = reportTypeService.getReportType(Long.valueOf(refundReportVo.getReportType()));
                List<ReportProperty> rpList = reportPropertyService.queryReportPropertysByTypeId(Long.valueOf(refundReportVo.getReportType()));
                Map<String, Object> searchParams = BeanUtils.transBean2Map(refundReportVo);
                pagelist = refundReportService.queryRefundReports(searchParams, rpList,
                    refundReportVo.getBeginTime(), refundReportVo.getEndTime(), pageNumber,
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
            LOGGER.error("[查询利润报表报错][refundReportsController:refundReports()] " + e.getMessage());
        }

        model.addAttribute("reportTermList",
            ReportTool.getReportTermList(reportTypeService, rtype));
        model.addAttribute("reportTypeList",
            ReportTool.getReportTypeList(reportTypeService, Constant.ReportType.REFUND_REPORTS));
        model.addAttribute("provinces", ReportTool.getProvince(provinceService));
        model.addAttribute("refunds", html);

        model.addAttribute("provinces", ReportTool.getProvince(provinceService));
        model.addAttribute("citys", ReportTool.getcitys(cityService));
        model.addAttribute("carrierInfos", ReportTool.getcarrierInfos(carrierInfoService));
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("refundReportVo", refundReportVo);
        model.addAttribute("refundList", pagelist.getList());
        model.addAttribute("page", pageNumber);
        model.addAttribute("counttotal", pagelist.getCountTotal() + "");
        model.addAttribute("pagetotal", pagelist.getPageTotal() + "");
        LOGGER.debug("[结束查询利润报表][refundReportsController:refundReports()]  [调用refundReportsService中queryrefundReports()方法，获得报表数据]");
        return "/report/refundReports.ftl";
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
                                           StringBuffer html, YcPage<RefundReportInfo> page_list)
    {
        LOGGER.debug("[设置利润报表响应数据][refundReportsController: setModelToProfltReports()]");
        model.addAttribute("reportTermList",
            ReportTool.getReportTermList(reportTypeService, rtype));
        model.addAttribute("reportTypeList",
            ReportTool.getReportTypeList(reportTypeService, Constant.ReportType.PROFIT_REPORTS));
        model.addAttribute("provinces", ReportTool.getProvince(provinceService));
        model.addAttribute("refunds", html);
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
        model.addAttribute("refundList", page_list.getList());
        model.addAttribute("page", pageNumber);
        model.addAttribute("counttotal", page_list.getCountTotal() + "");
        model.addAttribute("pagetotal", page_list.getPageTotal() + "");
    }

    /**
     * 测试
     */
    @RequestMapping(value = "/testrefund")
    public String testrefund(@RequestParam(value = "beginTime", defaultValue = "")
    String beginTime, @RequestParam(value = "endTime", defaultValue = "")
    String endTime, Model model, ServletRequest request)
    {
        refundReportService.testReport(beginTime, endTime);
        return null;
    }
}
