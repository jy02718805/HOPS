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
import com.yuecheng.hops.mportal.vo.report.TransactionReportVo;
import com.yuecheng.hops.numsection.service.CarrierInfoService;
import com.yuecheng.hops.numsection.service.CityService;
import com.yuecheng.hops.numsection.service.ProvinceService;
import com.yuecheng.hops.report.entity.ReportProperty;
import com.yuecheng.hops.report.entity.ReportType;
import com.yuecheng.hops.report.entity.TransactionReportInfo;
import com.yuecheng.hops.report.entity.po.TransactionReportPo;
import com.yuecheng.hops.report.service.ReportPropertyService;
import com.yuecheng.hops.report.service.ReportTypeService;
import com.yuecheng.hops.report.service.TransactionReportService;


/**
 * 交易报表
 * 
 * @author Administrator
 * @version 2014年10月12日
 * @see TransactionReportController
 * @since
 */
@Controller
@RequestMapping(value = "/report")
public class TransactionReportController
{
    @Autowired
    private ProvinceService provinceService;

    @Autowired
    private TransactionReportService transactionReportService;

    @Autowired
    private ReportTypeService reportTypeService;

    @Autowired
    private ReportPropertyService reportPropertyService;

    @Autowired
    private CityService cityService;

    @Autowired
    private CarrierInfoService carrierInfoService;

    private static final String PAGE_SIZE = "10";

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionReportController.class);

    /**
     * 交易报表列表
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
    @RequestMapping(value = "/transactionReports")
    public String transactionReports(TransactionReportVo transactionReportVo,
                                     @RequestParam(value = "page", defaultValue = "1")
                                     int pageNumber,
                                     @RequestParam(value = "pageSize", defaultValue = PAGE_SIZE)
                                     int pageSize, Model model, ServletRequest request)
    {

        LOGGER.debug(" [进入查询交易报表方法] [TransactionReportsController:transactionListReports()] ");

        if (StringUtil.isBlank(transactionReportVo.getBeginTime())
            && StringUtil.isBlank(transactionReportVo.getEndTime()))
        {
            transactionReportVo.setBeginTime(ReportTool.getDefaultDate());
            transactionReportVo.setEndTime(ReportTool.getDefaultDate());
        }
        YcPage<TransactionReportPo> pagelist = new YcPage<TransactionReportPo>();
        StringBuffer html = new StringBuffer();
        ReportType rtype = new ReportType();
        try
        {
            if (StringUtil.isNotBlank(transactionReportVo.getReportType()))
            {

                rtype = reportTypeService.getReportType(Long.valueOf(transactionReportVo.getReportType()));
                List<ReportProperty> rpList = reportPropertyService.queryReportPropertysByTypeId(Long.valueOf(transactionReportVo.getReportType()));
                Map<String, Object> searchParams = BeanUtils.transBean2Map(transactionReportVo);
                pagelist = transactionReportService.queryTransactionReports(searchParams, rpList,
                    transactionReportVo.getBeginTime(), transactionReportVo.getEndTime(),
                    pageNumber, pageSize, EntityConstant.TransactionReports.BEGIN_TIME);
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
            LOGGER.error("[查询交易报表报错][TransactionReportsController:transactionListReports()] "
                         + e.getMessage());
        }

        model.addAttribute("transactionHtml", html);
        model.addAttribute("transactionReports", pagelist.getList());
        model.addAttribute("reportTermList",
            ReportTool.getReportTermList(reportTypeService, rtype));
        model.addAttribute("reportTypeList", ReportTool.getReportTypeList(reportTypeService,
            Constant.ReportType.TRANSACTION_REPORTS));

        model.addAttribute("provinces", ReportTool.getProvince(provinceService));
        model.addAttribute("citys", ReportTool.getcitys(cityService));
        model.addAttribute("carrierInfos", ReportTool.getcarrierInfos(carrierInfoService));

        model.addAttribute("pageSize", pageSize);
        model.addAttribute("transactionReportVo", transactionReportVo);
        model.addAttribute("page", pageNumber);
        model.addAttribute("counttotal", pagelist.getCountTotal() + "");
        model.addAttribute("pagetotal", pagelist.getPageTotal() + "");

        LOGGER.debug(" [结束查询交易报表方法] [TransactionReportsController:transactionListReports()] ");
        return "/report/transactionReports.ftl";
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
     * @param page_list
     * @param html
     * @param rtype
     * @see
     */
    protected void setModelToTransactionReports(String province, String merchantName,
                                                String merchantType, String reportType,
                                                String parValue, String city, String carrierInfo,
                                                String reportsStatus, String beginTime,
                                                String endTime, int pageNumber, Model model,
                                                YcPage<TransactionReportInfo> page_list,
                                                StringBuffer html, ReportType rtype)
    {
        LOGGER.debug(" [设置交易报表响应方法] [TransactionReportsController: setModelToTransactionReports()] ");
        model.addAttribute("transactionHtml", html);
        model.addAttribute("transactionReports", page_list.getList());
        model.addAttribute("reportTermList",
            ReportTool.getReportTermList(reportTypeService, rtype));
        model.addAttribute("reportTypeList", ReportTool.getReportTypeList(reportTypeService,
            Constant.ReportType.TRANSACTION_REPORTS));
        model.addAttribute("reportType", reportType);
        model.addAttribute("reportsStatus", reportsStatus);

        model.addAttribute("provinces", ReportTool.getProvince(provinceService));
        model.addAttribute("citys", ReportTool.getcitys(cityService));
        model.addAttribute("carrierInfos", ReportTool.getcarrierInfos(carrierInfoService));
        model.addAttribute("province", province);
        model.addAttribute("carrierInfo", carrierInfo);
        model.addAttribute("city", city);

        model.addAttribute("merchantName", merchantName);
        model.addAttribute("merchantType", merchantType);
        model.addAttribute("parValue", parValue);
        model.addAttribute("reportType", reportType);
        model.addAttribute("beginTime", beginTime);
        model.addAttribute("endTime", endTime);
        model.addAttribute("page", pageNumber);
        model.addAttribute("counttotal", page_list.getCountTotal() + "");
        model.addAttribute("pagetotal", page_list.getPageTotal() + "");
    }

    @RequestMapping(value = "/testTransaction")
    public void testTransaction2(@RequestParam(value = "beginTime", defaultValue = "")
    String beginTime, @RequestParam(value = "endTime", defaultValue = "")
    String endTime, Model model, ServletRequest request)
    {
        transactionReportService.testTransactionReport(beginTime, endTime);
    }

}
