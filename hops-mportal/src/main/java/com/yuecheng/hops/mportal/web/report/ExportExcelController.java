package com.yuecheng.hops.mportal.web.report;


import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springside.modules.web.Servlets;

import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.EntityConstant;
import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.common.utils.StringUtil;
import com.yuecheng.hops.mportal.constant.PageConstant;
import com.yuecheng.hops.mportal.vo.order.OrderVO;
import com.yuecheng.hops.mportal.vo.report.AccountReportVo;
import com.yuecheng.hops.mportal.vo.report.ProfitReportVo;
import com.yuecheng.hops.mportal.vo.report.RefundReportVo;
import com.yuecheng.hops.mportal.vo.report.TransactionReportVo;
import com.yuecheng.hops.report.entity.ReportProperty;
import com.yuecheng.hops.report.entity.ReportType;
import com.yuecheng.hops.report.entity.po.AccountReportPo;
import com.yuecheng.hops.report.entity.po.AgentTransactionReportPo;
import com.yuecheng.hops.report.entity.po.ProfitReportPo;
import com.yuecheng.hops.report.entity.po.RefundReportPo;
import com.yuecheng.hops.report.entity.po.SupplyTransactionReportPo;
import com.yuecheng.hops.report.entity.po.TransactionReportPo;
import com.yuecheng.hops.report.service.AccountReportService;
import com.yuecheng.hops.report.service.AgentTransactionReportService;
import com.yuecheng.hops.report.service.ProfitReportService;
import com.yuecheng.hops.report.service.RefundReportService;
import com.yuecheng.hops.report.service.ReportPropertyService;
import com.yuecheng.hops.report.service.ReportTypeService;
import com.yuecheng.hops.report.service.SupplyTransactionReportService;
import com.yuecheng.hops.report.service.TransactionReportService;
import com.yuecheng.hops.transaction.basic.entity.vo.OrderExportVo;
import com.yuecheng.hops.transaction.basic.entity.vo.OrderParameterVo;
import com.yuecheng.hops.transaction.service.order.OrderPageQuery;


/**
 * 报表导出
 * 
 * @author Administrator
 * @version 2014年10月11日
 * @see ExportExcelController
 * @since
 */
@Controller
@RequestMapping(value = "/report")
public class ExportExcelController
{
    @Autowired
    private TransactionReportService transactionReportService;

    @Autowired
    private ReportTypeService reportTypeService;

    @Autowired
    private ReportPropertyService reportPropertyService;

    @Autowired
    private ProfitReportService profitReportsService;

    @Autowired
    private AccountReportService accountReportsService;

    @Autowired
    private AgentTransactionReportService agentTransactionReportService;

    @Autowired
    private SupplyTransactionReportService supplyTransactionReportService;

    @Autowired
    private RefundReportService refundReportService;

    @Autowired
    private OrderPageQuery orderPageQuery;

    private static final Logger LOGGER = LoggerFactory.getLogger(ExportExcelController.class);

    private static final int RECORD_MAX_SIZE_TOTAL = 20000;

    private static final int RECORD_MAX_SIZE = 10000;

    // private static final int PAGE_MAX_SIZE = 200000;

    private static final int PAGE_SIZE = 50000;

    /**
     * //交易量
     * 
     * @param province
     * @param merchantName
     * @param merchantType
     * @param reportType
     * @param productName
     * @param city
     * @param carrierInfo
     * @param reportsStatus
     * @param beginTime
     * @param endTime
     * @param model
     * @param request
     * @param response
     * @return
     * @see
     */
    @RequestMapping(value = "/transactionExport")
    public void transactionExport(TransactionReportVo transactionReportVo, Model model,
                                  HttpServletRequest request, HttpServletResponse response)
    {
        Map<String, Object> searchParams = BeanUtils.transBean2Map(transactionReportVo);
        LOGGER.debug("[进入导出交易报表数据方法][ExportExcelController: transactionExport()] ");

        try
        {
            Long reportTypeId = 0l;
            if (StringUtil.isNotBlank(transactionReportVo.getReportType()))
            {
                LOGGER.debug("[导出交易报表数据方法][ExportExcelController: transactionExport()] [参数:reportTypeId:"
                             + transactionReportVo.getReportType() + "]");
                reportTypeId = Long.valueOf(transactionReportVo.getReportType());
            }

            ReportType rt = reportTypeService.getReportType(reportTypeId);
            LOGGER.debug("[导出交易报表数据方法][ExportExcelController: transactionExport()] [获得报表类型:reportTypeService.getReportType(reportTypeId:"
                         + reportTypeId + ")][ReportType:" + rt.toString() + "]");
            List<ReportProperty> rpList = reportPropertyService.queryReportPropertysByTypeId(reportTypeId);
            LOGGER.debug("[导出交易报表数据方法][ExportExcelController: transactionExport()] [获得报表属性:reportPropertyService.queryReportPropertysByTypeId(reportTypeId:"
                         + reportTypeId + ")][rpList。size:" + rpList.size() + "]");
            //
            // YcPage<TransactionReportPo> pagelist2 =
            // transactionReportService.queryTransactionReports(
            // searchParams, rpList, transactionReportVo.getBeginTime(),
            // transactionReportVo.getEndTime(), 1, 1,
            // EntityConstant.TransactionReports.BEGIN_TIME);
            int count = transactionReportVo.getCountTotal();
            if (count > 300000)
            {
                count = 300000;
            }
            YcPage<TransactionReportPo> pagelist = null;
            YcPage<TransactionReportPo> pagelist3 = null;
            for (int i = 0; i < (count % RECORD_MAX_SIZE_TOTAL == 0 ? count
                                                                      / RECORD_MAX_SIZE_TOTAL : count
                                                                                                / RECORD_MAX_SIZE_TOTAL
                                                                                                + 1); i++ )
            {
                pagelist3 = transactionReportService.queryTransactionReports(searchParams, rpList,
                    transactionReportVo.getBeginTime(), transactionReportVo.getEndTime(), i + 1,
                    RECORD_MAX_SIZE_TOTAL, EntityConstant.TransactionReports.BEGIN_TIME);
                LOGGER.debug("[查询出导出交易量数据内容][ExportExcelController:transactionExport()]"
                             + " [调用transactionReportsService中queryTransactionReports()方法，获得报表数据]");
                if (i != 0)
                {
                    pagelist.getList().addAll(pagelist3.getList());
                }
                else
                {
                    pagelist = pagelist3;
                }
            }

            exportExcel(rt.getReportFileName(), request, response, rpList, pagelist.getList(),
                transactionReportVo.getBeginTime(), transactionReportVo.getEndTime(),
                pagelist.getCountTotal());
        }
        catch (Exception e)
        {
            LOGGER.error("[导出交易量报表数据报错][ExportExcelController:transactionExport()] "
                         + e.getMessage());
        }
        LOGGER.debug("[结束导出交易报表数据方法][ExportExcelController: transactionExport()] ");
    }

    /**
     * 利润导出
     * 
     * @param province
     * @param merchantName
     * @param merchantType
     * @param reportType
     * @param productName
     * @param city
     * @param carrierInfo
     * @param reportsStatus
     * @param beginTime
     * @param endTime
     * @param model
     * @param request
     * @param response
     * @return
     * @see
     */
    @RequestMapping(value = "/profitExport")
    public void profitExport(ProfitReportVo profitReportVo, String endTime, Model model,
                             ServletRequest request, HttpServletResponse response)
    {
        LOGGER.debug("[进入导出利润报表数据方法][ExportExcelController:profitReport()] ");
        Map<String, Object> searchParams = BeanUtils.transBean2Map(profitReportVo);

        try
        {
            Long reportTypeId = 0l;
            if (StringUtil.isNotBlank(profitReportVo.getReportType()))
            {
                LOGGER.debug("[导出利润报表数据方法][ExportExcelController: profitExport()] [参数:reportTypeId:"
                             + profitReportVo.getReportType() + "]");
                reportTypeId = Long.valueOf(profitReportVo.getReportType());
            }
            ReportType rt = reportTypeService.getReportType(reportTypeId);
            LOGGER.debug("[导出利润报表数据方法][ExportExcelController: profitExport()] [参数:reportTypeId:"
                         + profitReportVo.getReportType() + "] [查询出报表类型:ReportType"
                         + rt.toString() + "]");
            List<ReportProperty> rpList = reportPropertyService.queryReportPropertysByTypeId(reportTypeId);
            LOGGER.debug("[导出利润报表数据方法][ExportExcelController: profitExport()] [参数:reportTypeId:"
                         + profitReportVo.getReportType() + "] [查询出报表属性:ReportType.size"
                         + rpList.size() + "]");
            YcPage<ProfitReportPo> pagelist2 = profitReportsService.queryProfitReports(
                searchParams, rpList, profitReportVo.getBeginTime(), profitReportVo.getEndTime(),
                1, 1, EntityConstant.TransactionReports.BEGIN_TIME);
            int count = pagelist2.getCountTotal();
            if (count > 300000)
            {
                count = 300000;
            }
            YcPage<ProfitReportPo> pagelist = null;
            YcPage<ProfitReportPo> pagelist3 = null;
            for (int i = 0; i < (count % RECORD_MAX_SIZE_TOTAL == 0 ? count
                                                                      / RECORD_MAX_SIZE_TOTAL : count
                                                                                                / RECORD_MAX_SIZE_TOTAL
                                                                                                + 1); i++ )
            {
                pagelist3 = profitReportsService.queryProfitReports(searchParams, rpList,
                    profitReportVo.getBeginTime(), profitReportVo.getEndTime(), i + 1,
                    RECORD_MAX_SIZE_TOTAL, EntityConstant.TransactionReports.BEGIN_TIME);
                LOGGER.debug("[导出利润报表数据方法][ExportExcelController:profitReport()]"
                             + " [调用profitReportsService中queryProfitReports()方法，获得报表数据]");
                if (i != 0)
                {
                    pagelist.getList().addAll(pagelist3.getList());
                }
                else
                {
                    pagelist = pagelist3;
                }
            }

            exportExcel(rt.getReportFileName(), request, response, rpList, pagelist.getList(),
                profitReportVo.getBeginTime(), profitReportVo.getEndTime(),
                pagelist.getCountTotal());
        }
        catch (Exception e)
        {
            LOGGER.error("[导出利润报表数据方法报错][ExportExcelController:profitReport()] " + e.getMessage());
        }

        LOGGER.debug("[结束导出利润报表数据方法][ExportExcelController:profitReport()] ");
    }

    /**
     * 账户统计导出
     * 
     * @param merchantName
     * @param accountId
     * @param merchantType
     * @param accountTypeId
     * @param reportType
     * @param beginTime
     * @param endTime
     * @param model
     * @param request
     * @param response
     * @return
     * @see
     */
    @RequestMapping(value = "/accountExport")
    public void accountExport(AccountReportVo accountReportVo, Model model,
                              ServletRequest request, HttpServletResponse response)
    {
        LOGGER.debug("[进入导出账户报表数据方法][ExportExcelController:accoutExport()] ");
        Map<String, Object> searchParams = BeanUtils.transBean2Map(accountReportVo);

        try
        {
            Long reportTypeid = 0l;
            if (StringUtil.isNotBlank(accountReportVo.getReportType()))
            {
                LOGGER.debug("[导出账户报表数据方法][ExportExcelController:accoutExport()][reportId:"
                             + accountReportVo.getReportType() + "]");
                reportTypeid = Long.valueOf(accountReportVo.getReportType());
            }
            ReportType rt = reportTypeService.getReportType(reportTypeid);
            LOGGER.debug("[导出账户报表数据方法][ExportExcelController:accoutExport()][reportId:"
                         + reportTypeid + ",[查询出报表类型:ReportType" + rt.toString() + "]");
            List<ReportProperty> rpList = reportPropertyService.queryReportPropertysByTypeId(reportTypeid);
            YcPage<AccountReportPo> pagelist2 = accountReportsService.queryAccountReports(
                searchParams, rpList, accountReportVo.getBeginTime(),
                accountReportVo.getEndTime(), 1, 1, EntityConstant.AccountReport.BEGIN_TIME);
            int count = pagelist2.getCountTotal();
            if (count > 300000)
            {
                count = 300000;
            }
            YcPage<AccountReportPo> pagelist = null;
            YcPage<AccountReportPo> pagelist3 = null;
            for (int i = 0; i < (count % RECORD_MAX_SIZE_TOTAL == 0 ? count
                                                                      / RECORD_MAX_SIZE_TOTAL : count
                                                                                                / RECORD_MAX_SIZE_TOTAL
                                                                                                + 1); i++ )
            {
                pagelist3 = accountReportsService.queryAccountReports(searchParams, rpList,
                    accountReportVo.getBeginTime(), accountReportVo.getEndTime(), i + 1,
                    RECORD_MAX_SIZE_TOTAL, EntityConstant.AccountReport.BEGIN_TIME);

                LOGGER.debug("导出账户报表数据方法][ExportExcelController:accoutExport()]"
                             + " [调用profitReportsService中queryAccountReports()方法，获得报表数据]");
                if (i != 0)
                {
                    pagelist.getList().addAll(pagelist3.getList());
                }
                else
                {
                    pagelist = pagelist3;
                }
            }

            exportExcel(rt.getReportFileName(), request, response, rpList, pagelist.getList(),
                accountReportVo.getBeginTime(), accountReportVo.getEndTime(),
                pagelist.getCountTotal());
        }
        catch (Exception e)
        {
            LOGGER.error("[导出账户报表数据方法报错][ExportExcelController:accoutExport()] " + e.getMessage());
        }
    }

    /**
     * //交易量
     * 
     * @param province
     * @param merchantName
     * @param merchantType
     * @param reportType
     * @param productName
     * @param city
     * @param carrierInfo
     * @param reportsStatus
     * @param beginTime
     * @param endTime
     * @param model
     * @param request
     * @param response
     * @return
     * @see
     */
    @RequestMapping(value = "/agentTransactionExport")
    public void agentTransactionExport(TransactionReportVo transactionReportVo, Model model,
                                       HttpServletRequest request, HttpServletResponse response)
    {

        LOGGER.debug("[进入导出代理商交易报表数据方法][ExportExcelController: agentTransactionExport()] ");

        try
        {
            Map<String, Object> searchParams = BeanUtils.transBean2Map(transactionReportVo);
            Long reportTypeId = 0l;
            if (StringUtil.isNotBlank(transactionReportVo.getReportType()))
            {
                LOGGER.debug("[导出交易报表数据方法][ExportExcelController: agentTransactionExport()] [参数:reportTypeId:"
                             + transactionReportVo.getReportType() + "]");
                reportTypeId = Long.valueOf(transactionReportVo.getReportType());
            }
            else
            {
                LOGGER.debug("[导出交易报表数据方法][ExportExcelController: agentTransactionExport()] [参数:reportTypeId:"
                             + transactionReportVo.getReportType() + "]");
            }
            ReportType rt = reportTypeService.getReportType(reportTypeId);
            LOGGER.debug("[导出交易报表数据方法][ExportExcelController: agentTransactionExport()] [获得报表类型:reportTypeService.getReportType(reportTypeId:"
                         + reportTypeId + ")][ReportType:" + rt.toString() + "]");
            List<ReportProperty> rpList = reportPropertyService.queryReportPropertysByTypeId(reportTypeId);
            LOGGER.debug("[导出交易报表数据方法][ExportExcelController: agentTransactionExport()] [获得报表属性:reportPropertyService.queryReportPropertysByTypeId(reportTypeId:"
                         + reportTypeId + ")][rpList。size:" + rpList.size() + "]");

            YcPage<AgentTransactionReportPo> pagelist2 = agentTransactionReportService.queryAgentTransactionReports(
                searchParams, rpList, transactionReportVo.getBeginTime(),
                transactionReportVo.getEndTime(), 1, 1,
                EntityConstant.AgentTransactionReport.BEGIN_TIME);
            int count = pagelist2.getCountTotal();
            if (count > 300000)
            {
                count = 300000;
            }
            YcPage<AgentTransactionReportPo> pagelist = null;
            YcPage<AgentTransactionReportPo> pagelist3 = null;
            for (int i = 0; i < (count % RECORD_MAX_SIZE_TOTAL == 0 ? count
                                                                      / RECORD_MAX_SIZE_TOTAL : count
                                                                                                / RECORD_MAX_SIZE_TOTAL
                                                                                                + 1); i++ )
            {
                pagelist3 = agentTransactionReportService.queryAgentTransactionReports(
                    searchParams, rpList, transactionReportVo.getBeginTime(),
                    transactionReportVo.getEndTime(), i + 1, RECORD_MAX_SIZE_TOTAL,
                    EntityConstant.AgentTransactionReport.BEGIN_TIME);

                LOGGER.debug("[查询出导出交易量数据内容][ExportExcelController: agentTransactionExport()]"
                             + " [调用agentTransactionReportsService中queryAgentTransactionReports()方法，获得报表数据]");
                if (i != 0)
                {
                    pagelist.getList().addAll(pagelist3.getList());
                }
                else
                {
                    pagelist = pagelist3;
                }
            }

            exportExcel(rt.getReportFileName(), request, response, rpList, pagelist.getList(),
                transactionReportVo.getBeginTime(), transactionReportVo.getEndTime(),
                pagelist.getCountTotal());
        }
        catch (Exception e)
        {
            LOGGER.error("[导出交易量报表数据报错][ExportExcelController: agentTransactionExport()] "
                         + e.getMessage());
        }
        LOGGER.debug("[结束导出交易报表数据方法][ExportExcelController: agentTransactionExport()] ");
    }

    /**
     * 供货商交易量
     * 
     * @param province
     * @param merchantName
     * @param merchantType
     * @param reportType
     * @param productName
     * @param city
     * @param carrierInfo
     * @param reportsStatus
     * @param beginTime
     * @param endTime
     * @param model
     * @param request
     * @param response
     * @return
     * @see
     */
    @RequestMapping(value = "/supplyTransactionExport")
    public void supplyTransactionExport(TransactionReportVo transactionReportVo, Model model,
                                        HttpServletRequest request, HttpServletResponse response)
    {

        LOGGER.debug("[进入导出交易报表数据方法][ExportExcelController: supplyTransactionExport()] ");

        try
        {
            Map<String, Object> searchParams = BeanUtils.transBean2Map(transactionReportVo);
            Long reportTypeId = 0l;
            if (StringUtil.isNotBlank(transactionReportVo.getReportType()))
            {
                LOGGER.debug("[导出交易报表数据方法][ExportExcelController: supplyTransactionExport()] [参数:reportTypeId:"
                             + transactionReportVo.getReportType() + "]");
                reportTypeId = Long.valueOf(transactionReportVo.getReportType());
            }
            else
            {
                LOGGER.debug("[导出交易报表数据方法][ExportExcelController: supplyTransactionExport()] [参数:reportTypeId:"
                             + transactionReportVo.getReportType() + "]");
            }
            ReportType rt = reportTypeService.getReportType(reportTypeId);
            LOGGER.debug("[导出交易报表数据方法][ExportExcelController: supplyTransactionExport()] [获得报表类型:reportTypeService.getReportType(reportTypeId:"
                         + reportTypeId + ")][ReportType:" + rt.toString() + "]");
            List<ReportProperty> rpList = reportPropertyService.queryReportPropertysByTypeId(reportTypeId);
            LOGGER.debug("[导出交易报表数据方法][ExportExcelController: supplyTransactionExport()] [获得报表属性:reportPropertyService.queryReportPropertysByTypeId(reportTypeId:"
                         + reportTypeId + ")][rpList。size:" + rpList.size() + "]");

            YcPage<SupplyTransactionReportPo> pagelist2 = supplyTransactionReportService.querySupplyTransactionReports(
                searchParams, rpList, transactionReportVo.getBeginTime(),
                transactionReportVo.getEndTime(), 1, 1,
                EntityConstant.SupplyTransactionReport.BEGIN_TIME);
            int count = pagelist2.getCountTotal();
            if (count > 300000)
            {
                count = 300000;
            }
            YcPage<SupplyTransactionReportPo> pagelist = null;
            YcPage<SupplyTransactionReportPo> pagelist3 = null;
            for (int i = 0; i < (count % RECORD_MAX_SIZE_TOTAL == 0 ? count
                                                                      / RECORD_MAX_SIZE_TOTAL : count
                                                                                                / RECORD_MAX_SIZE_TOTAL
                                                                                                + 1); i++ )
            {
                pagelist3 = supplyTransactionReportService.querySupplyTransactionReports(
                    searchParams, rpList, transactionReportVo.getBeginTime(),
                    transactionReportVo.getEndTime(), i + 1, RECORD_MAX_SIZE_TOTAL,
                    EntityConstant.SupplyTransactionReport.BEGIN_TIME);
                LOGGER.debug("[查询出导出交易量数据内容][ExportExcelController: supplyTransactionExport()]"
                             + " [调用supplyTransactionReportsService中querySupplyTransactionReports()方法，获得报表数据]");
                if (i != 0)
                {
                    pagelist.getList().addAll(pagelist3.getList());
                }
                else
                {
                    pagelist = pagelist3;
                }
            }

            exportExcel(rt.getReportFileName(), request, response, rpList, pagelist.getList(),
                transactionReportVo.getBeginTime(), transactionReportVo.getEndTime(),
                pagelist.getCountTotal());
        }
        catch (Exception e)
        {
            LOGGER.error("[导出交易量报表数据报错][ExportExcelController: supplyTransactionExport()] "
                         + e.getMessage());
        }
        LOGGER.debug("[结束导出交易报表数据方法][ExportExcelController: supplyTransactionExport()] ");
    }

    /**
     * 退款导出
     * 
     * @param province
     * @param merchantName
     * @param merchantType
     * @param reportType
     * @param productName
     * @param city
     * @param carrierInfo
     * @param reportsStatus
     * @param beginTime
     * @param endTime
     * @param model
     * @param request
     * @param response
     * @return
     * @see
     */
    @RequestMapping(value = "/refundExport")
    public void refundExport(RefundReportVo refundReportVo, String endTime, Model model,
                             ServletRequest request, HttpServletResponse response)
    {
        LOGGER.debug("[进入导出退款报表数据方法][ExportExcelController: refundExport()] ");
        Map<String, Object> searchParams = BeanUtils.transBean2Map(refundReportVo);

        try
        {
            Long reportTypeId = 0l;
            if (StringUtil.isNotBlank(refundReportVo.getReportType()))
            {
                LOGGER.debug("[导出退款报表数据方法][ExportExcelController: refundExport()] [参数:reportTypeId:"
                             + refundReportVo.getReportType() + "]");
                reportTypeId = Long.valueOf(refundReportVo.getReportType());
            }
            ReportType rt = reportTypeService.getReportType(reportTypeId);
            LOGGER.debug("[导出退款报表数据方法][ExportExcelController: refundExport()] [参数:reportTypeId:"
                         + refundReportVo.getReportType() + "] [查询出报表类型:ReportType"
                         + rt.toString() + "]");
            List<ReportProperty> rpList = reportPropertyService.queryReportPropertysByTypeId(reportTypeId);
            LOGGER.debug("[导出退款报表数据方法][ExportExcelController: refundExport()] [参数:reportTypeId:"
                         + refundReportVo.getReportType() + "] [查询出报表属性:ReportType.size"
                         + rpList.size() + "]");
            YcPage<RefundReportPo> pagelist2 = refundReportService.queryRefundReports(
                searchParams, rpList, refundReportVo.getBeginTime(), refundReportVo.getEndTime(),
                1, 1, EntityConstant.TransactionReports.BEGIN_TIME);
            int count = pagelist2.getCountTotal();
            if (count > 300000)
            {
                count = 300000;
            }
            YcPage<RefundReportPo> pagelist = null;
            YcPage<RefundReportPo> pagelist3 = null;
            for (int i = 0; i < (count % RECORD_MAX_SIZE_TOTAL == 0 ? count
                                                                      / RECORD_MAX_SIZE_TOTAL : count
                                                                                                / RECORD_MAX_SIZE_TOTAL
                                                                                                + 1); i++ )
            {
                pagelist3 = refundReportService.queryRefundReports(searchParams, rpList,
                    refundReportVo.getBeginTime(), refundReportVo.getEndTime(), i + 1,
                    RECORD_MAX_SIZE_TOTAL, EntityConstant.TransactionReports.BEGIN_TIME);
                LOGGER.debug("[导出退款报表数据方法][ExportExcelController: refundExport()]"
                             + " [调用profitReportsService中queryProfitReports()方法，获得报表数据]");
                if (i != 0)
                {
                    pagelist.getList().addAll(pagelist3.getList());
                }
                else
                {
                    pagelist = pagelist3;
                }
            }

            exportExcel(rt.getReportFileName(), request, response, rpList, pagelist.getList(),
                refundReportVo.getBeginTime(), refundReportVo.getEndTime(),
                pagelist.getCountTotal());
        }
        catch (Exception e)
        {
            LOGGER.error("[导出退款报表数据方法报错][ExportExcelController:profitReport()] " + e.getMessage());
        }

        LOGGER.debug("[结束导出退款报表数据方法][ExportExcelController:profitReport()] ");
    }

    /**
     * 导出
     * 
     * @param filename
     * @param response
     * @param headers
     * @param content
     * @throws RowsExceededException
     * @throws WriteException
     * @throws IOException
     */
    public void exportExcel(String filename, ServletRequest request, HttpServletResponse response,
                            List<ReportProperty> headers, List<?> content, String beginTime,
                            String endTime, int totalNum)
    {
        LOGGER.debug("[导出报表开始][ExportExcelController:exportExcel()] [报表名称:" + filename + "]");
        int fontSize = 10;
        try
        {
            // ie 11 处理
            String fileStr = "";
            String agent = ((HttpServletRequest)request).getHeader("User-Agent");
            if (agent != null && agent.toLowerCase().indexOf("firefox") != -1)
            {
                fileStr = new String(filename.getBytes(), "iso-8859-1");
            }
            else
            {
                fileStr = URLEncoder.encode(filename, "UTF-8");
            }
            request.setCharacterEncoding("utf-8");
            response.reset();
            response.setCharacterEncoding("utf-8");
            response.setHeader("pragma", "no-cache");
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.addHeader("Content-Disposition", "attachment;filename=" + fileStr + beginTime
                                                      + "~" + endTime + ".csv");
            OutputStream out = response.getOutputStream();
            LOGGER.debug("[导出报表][ExportExcelController:exportExcel()] [报表名称:" + filename
                         + ",WritableSheet:创建]");

            WritableWorkbook wb = Workbook.createWorkbook(out);
            WritableSheet wsheet = null;// wb.createSheet(filename, 0);

            WritableFont font1 = new WritableFont(WritableFont.ARIAL, fontSize, WritableFont.BOLD);
            WritableCellFormat format1 = new WritableCellFormat(font1);
            int columnssize = headers.size();
            int rowsize = content.size();

            for (int x = 0; x < (rowsize % RECORD_MAX_SIZE == 0 ? rowsize / RECORD_MAX_SIZE : rowsize
                                                                                              / RECORD_MAX_SIZE
                                                                                              + 1); x++ )
            {

                wsheet = wb.createSheet(filename + "-" + (x + 1), x);
                for (int j = 0; j < columnssize; j++ )
                {
                    ReportProperty rp = (ReportProperty)headers.get(j);
                    wsheet.addCell(new Label(j, 0, rp.getReportPropertyName(), format1));
                }
                for (int i = x * RECORD_MAX_SIZE; i < (rowsize / RECORD_MAX_SIZE > x ? RECORD_MAX_SIZE : rowsize
                                                                                                         % RECORD_MAX_SIZE)
                                                      + x * RECORD_MAX_SIZE; i++ )
                {
                    Object object = content.get(i);
                    BeanWrapper bw = new BeanWrapperImpl(object);
                    for (int k = 0; k < columnssize; k++ )
                    {
                        ReportProperty rp = (ReportProperty)headers.get(k);

                        Object propertyValue = bw.getPropertyValue(rp.getReportPropertyFieldName());
                        if (propertyValue == null)
                        {
                            propertyValue = "";
                        }
                        if (propertyValue.getClass().getName().equals(
                            Constant.ReportType.REPORT_PROPERTY_DATE))
                        {
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                            propertyValue = sdf.format(propertyValue);
                        }

                        if (rp.getReportPropertyFieldName().equals(
                            EntityConstant.Order.ORDER_STATUS.toUpperCase()))
                        {
                            propertyValue = getOrderStatusToString(propertyValue);
                        }

                        if ("BUSINESSTYPE".equals(rp.getReportPropertyFieldName()))
                        {
                            if (Constant.BusinessType.BUSINESS_TYPE_HF.equals(propertyValue + ""))
                            {
                                propertyValue = "话费";
                            }
                            else if (Constant.BusinessType.BUSINESS_TYPE_FLOW.equals(propertyValue
                                                                                     + ""))
                            {
                                propertyValue = "流量";
                            }

                        }
                        wsheet.addCell(new Label(k, i % RECORD_MAX_SIZE + 1,
                            propertyValue.toString()));

                    }
                }
            }

            // list
            LOGGER.debug("[导出报表][ExportExcelController:exportExcel()] [报表名称:" + filename + "]");

            wsheet.addCell(new Label(0,
                rowsize % RECORD_MAX_SIZE == 0 ? RECORD_MAX_SIZE + 1 : rowsize % RECORD_MAX_SIZE
                                                                       + 1, "总条数："));
            wsheet.addCell(new Label(1,
                rowsize % RECORD_MAX_SIZE == 0 ? RECORD_MAX_SIZE + 1 : rowsize % RECORD_MAX_SIZE
                                                                       + 1, rowsize + ""));

            wb.write();
            wb.close();

            out.close();
            out.flush();
        }
        catch (Exception e)
        {
            LOGGER.error("[导出报表报错][ExportExcelController:exportExcel()] 异常:" + e.getMessage());
        }
        LOGGER.debug("结束导出报表][ExportExcelController:exportExcel()] [报表名称:" + filename);
    }

    /**
     * 按号码导出交易量
     * 
     * @param transactionReportVo
     * @param model
     * @param request
     * @param response
     */
    @RequestMapping(value = "/transactionExportByNum")
    public void transactionExportByNum(TransactionReportVo transactionReportVo, Model model,
                                       HttpServletRequest request, HttpServletResponse response)
    {
        LOGGER.debug("[进入导出按号码交易报表数据方法][ExportExcelController: transactionExportByNum()] ");

        try
        {
            Map<String, Object> searchParams = BeanUtils.transBean2Map(transactionReportVo);
            Long reportTypeId = 0l;
            if (StringUtil.isNotBlank(transactionReportVo.getReportType()))
            {
                LOGGER.debug("[导出按号码交易报表数据方法][ExportExcelController: transactionExportByNum()] [参数:reportTypeId:"
                             + transactionReportVo.getReportType() + "]");
                reportTypeId = Long.valueOf(transactionReportVo.getReportType());
            }
            else
            {
                LOGGER.debug("[导出按号码交易报表数据方法][ExportExcelController: transactionExportByNum()] [参数:reportTypeId:"
                             + transactionReportVo.getReportType() + "]");
            }
            ReportType rt = reportTypeService.getReportType(reportTypeId);
            LOGGER.debug("[导出按号码交易报表数据方法][ExportExcelController: transactionExportByNum()] [获得报表类型:reportTypeService.getReportType(reportTypeId:"
                         + reportTypeId + ")][ReportType:" + rt.toString() + "]");
            List<ReportProperty> rpList = reportPropertyService.queryReportPropertysByTypeId(reportTypeId);
            LOGGER.debug("[导出按号码交易报表数据方法][ExportExcelController: transactionExportByNum()] [获得报表属性:reportPropertyService.queryReportPropertysByTypeId(reportTypeId:"
                         + reportTypeId + ")][rpList。size:" + rpList.size() + "]");
            YcPage<TransactionReportPo> pagelist2 = transactionReportService.queryTransactionReports(
                searchParams, rpList, transactionReportVo.getBeginTime(),
                transactionReportVo.getEndTime(), 1, 1,
                EntityConstant.TransactionReports.BEGIN_TIME);
            int count = pagelist2.getCountTotal();
            if (count > 300000)
            {
                count = 300000;
            }
            YcPage<TransactionReportPo> pagelist = null;
            YcPage<TransactionReportPo> pagelist3 = null;
            String filename = null;
            for (int i = 0; i < (count % RECORD_MAX_SIZE_TOTAL == 0 ? count
                                                                      / RECORD_MAX_SIZE_TOTAL : count
                                                                                                / RECORD_MAX_SIZE_TOTAL
                                                                                                + 1); i++ )
            {
                pagelist3 = transactionReportService.queryTransactionReports(searchParams, rpList,
                    transactionReportVo.getBeginTime(), transactionReportVo.getEndTime(), i + 1,
                    RECORD_MAX_SIZE_TOTAL, EntityConstant.TransactionReports.BEGIN_TIME);

                LOGGER.debug("[查询出导出按号码交易量数据内容][ExportExcelController: transactionExportByNum()]"
                             + " [调用transactionReportService中queryTransactionReports()方法，获得报表数据]");
                filename = rt.getReportFileName()
                           + (transactionReportVo.getMerchantName() == null
                              || transactionReportVo.getMerchantName() == "" ? "" : ("-" + transactionReportVo.getMerchantName()));
                if (i != 0)
                {
                    pagelist.getList().addAll(pagelist3.getList());
                }
                else
                {
                    pagelist = pagelist3;
                }

            }
            exportExcel(filename, request, response, rpList, pagelist.getList(),
                transactionReportVo.getBeginTime(), transactionReportVo.getEndTime(),
                pagelist.getCountTotal());

        }
        catch (Exception e)
        {
            LOGGER.error("[导出按号码交易量报表数据报错][ExportExcelController: transactionExportByNum()] "
                         + e.getMessage());
        }
        LOGGER.debug("[结束导出按号码交易报表数据方法][ExportExcelController: transactionExportByNum()] ");
    }

    // 导出订单
    @RequestMapping(value = "/orderExport")
    public String orderExport(@RequestParam(value = "page", defaultValue = "1")
    int pageNumber, @RequestParam(value = "pageSize", defaultValue = "")
    int pageSize, @RequestParam(value = "sortType", defaultValue = "auto")
    String sortType, OrderVO order, ModelMap model, ServletRequest request,
                              HttpServletResponse response)
    {
        LOGGER.debug("[ExportExcelController:orderExport()]");
        YcPage<OrderExportVo> orderlist = new YcPage<OrderExportVo>();
        List<OrderExportVo> orderExportList = new ArrayList<OrderExportVo>();
        try
        {
            String userCode = order.getUsercode();
            String merchantOrderNo = order.getMerchantOrderNo();
            userCode = new String(
                (StringUtils.isNotBlank(userCode) ? userCode : "").getBytes("ISO-8859-1"), "UTF-8");
            merchantOrderNo = new String(
                (StringUtils.isNotBlank(merchantOrderNo) ? merchantOrderNo : "").getBytes("ISO-8859-1"),
                "UTF-8");
            order.setUsercode(userCode);
            order.setMerchantOrderNo(merchantOrderNo);
            Map<String, Object> searchParams = Servlets.getParametersStartingWith(request,
                "search_");
            order.setPreSuccessStatus(null);

            int totalNum = order.getCountTotal();
            // if (order.getCountTotal() > PAGE_MAX_SIZE)
            // {
            // totalNum = 200000;
            // }

            for (int i = 0; i < (totalNum % PAGE_SIZE == 0 ? totalNum / PAGE_SIZE : totalNum
                                                                                    / PAGE_SIZE
                                                                                    + 1); i++ )
            {
                orderlist = orderPageQuery.queryOrderForExport(searchParams, i + 1, PAGE_SIZE,
                    order.getCarrierInfo(), order.getProvince(), order.getParValue(),
                    order.getDownMerchant(), order.getStatus(), EntityConstant.Order.ORDER_NO2,
                    order.getBeginDate(), order.getEndDate(), order.getNotifyStatus(),
                    order.getPreSuccessStatus(), order.getUsercode(), order.getOrderNo(),
                    order.getMerchantOrderNo(), order.getBusinessType(), false);
                orderExportList.addAll(orderlist.getList());
            }

        }
        catch (Exception e)
        {
            LOGGER.error("[ExportExcelController:orderExport()] " + e.getMessage());
        }
        try
        {
            String beginDate = getFomartTime(order.getBeginDate());
            String endDate = getFomartTime(order.getEndDate());
            List<ReportType> reportTypeList = reportTypeService.getReportTypeByType(Constant.ReportType.ORDER_REPORTS);
            List<ReportProperty> rpList = reportPropertyService.queryReportPropertysByTypeId(reportTypeList.get(
                0).getReportTypeId());
            exportExcel(reportTypeList.get(0).getReportFileName(), request, response, rpList,
                orderExportList, beginDate, endDate, orderExportList.size());
            return null;
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            LOGGER.error("[ExportExcelController:orderExport()] " + e.getMessage());
            model.put("message", "操作失败:[没有配置报表]");
            model.put("canback", true);
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
    }

    private String getFomartTime(String date)
    {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try
        {
            Date d = formatDate.parse(date);
            date = format.format(d);
        }
        catch (ParseException e)
        {
            LOGGER.error("[ExportExcelController: getFomartTime()] 异常:" + e.getMessage());
        }
        return date;
    }

    public Date getLastMonth(Date date)
    {
        Calendar currentDate = Calendar.getInstance();
        currentDate.setTime(date);
        currentDate.add(Calendar.MONTH, -1);
        return (Date)currentDate.getTime().clone();
    }

    private String getOrderStatusToString(Object o)
    {
        try
        {
            int status = Integer.parseInt(o.toString());
            if (status == Constant.OrderStatus.WAIT_PAY)
            {
                return "待付款";
            }
            else if (status == Constant.OrderStatus.WAIT_RECHARGE)
            {
                return "待发货";
            }
            else if (status == Constant.OrderStatus.RECHARGING)
            {
                return "处理中";
            }
            else if (status == Constant.OrderStatus.SUCCESS)
            {
                return "成功";
            }
            else if (status == Constant.OrderStatus.FAILURE_ALL)
            {
                return "失败";
            }
            else if (status == Constant.OrderStatus.SUCCESS_PART)
            {
                return "部分成功";
            }
            else if (status == Constant.OrderStatus.FAILURE_PART)
            {
                return "部分失败";
            }
            else
            {
                return "";
            }
        }
        catch (Exception e)
        {
            LOGGER.error("[ExportExcelController: getOrderStatusToString(Object o:" + o + ")]");
            return "";
        }

    }
}
