package com.yuecheng.hops.aportal.web.report;


import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springside.modules.web.Servlets;

import com.yuecheng.hops.account.entity.vo.AccountHistoryAssistVo;
import com.yuecheng.hops.account.service.CurrencyAccountBalanceHistoryService;
import com.yuecheng.hops.account.service.TransactionHistoryService;
import com.yuecheng.hops.aportal.constant.PageConstant;
import com.yuecheng.hops.aportal.vo.order.OrderVO;
import com.yuecheng.hops.aportal.web.BaseControl;
import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.EntityConstant;
import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.common.utils.DateUtil;
import com.yuecheng.hops.common.utils.StringUtil;
import com.yuecheng.hops.identity.entity.operator.Operator;
import com.yuecheng.hops.identity.service.merchant.MerchantService;
import com.yuecheng.hops.numsection.service.CarrierInfoService;
import com.yuecheng.hops.numsection.service.CityService;
import com.yuecheng.hops.numsection.service.ProvinceService;
import com.yuecheng.hops.product.service.ProductPageQuery;
import com.yuecheng.hops.report.entity.ReportProperty;
import com.yuecheng.hops.report.entity.ReportType;
import com.yuecheng.hops.report.service.ReportPropertyService;
import com.yuecheng.hops.report.service.ReportTypeService;
import com.yuecheng.hops.transaction.basic.entity.vo.OrderExportVo;
import com.yuecheng.hops.transaction.service.order.OrderManagement;
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
public class ExportExcelController extends BaseControl
{
    @Autowired
    private CurrencyAccountBalanceHistoryService currencyAccountBalanceHistoryService;

    @Autowired
    private OrderManagement orderManagement;

    @Autowired
    private OrderPageQuery orderPageQuery;

    @Autowired
    private ProvinceService provinceService;

    @Autowired
    private CarrierInfoService carrierInfoService;

    @Autowired
    private TransactionHistoryService transactionHistoryService;

    @Autowired
    private ReportTypeService reportTypeService;

    @Autowired
    private ReportPropertyService reportPropertyService;

    @Autowired
    private MerchantService merchantService;

    @Autowired
    private CityService cityService;

    @Autowired
    private OrderManagement orderService;

    @Autowired
    private ProductPageQuery productPageQuery;

    private static final Logger LOGGER = LoggerFactory.getLogger(ExportExcelController.class);

    @Autowired
    private HttpSession session;

    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    private static final int PAGE_MAX_SIZE = 200000;

    private static final int PAGE_SIZE = 10000;

    // 变动时间、日期、变动类型、订单编号
    @RequestMapping(value = "/fundchangeExport")
    public String fundchangelist(@RequestParam(value = "logType", defaultValue = "1")
    String logType, @RequestParam(value = "accountChangeType", defaultValue = "")
    String accountChangeType, @RequestParam(value = "transactionChangeType", defaultValue = "")
    String transactionChangeType, @RequestParam(value = "changeDate", defaultValue = "0")
    String changeDate, @RequestParam(value = "orderNo", defaultValue = "")
    String orderNo, @RequestParam(value = "beginDate", defaultValue = "")
    String beginDate, @RequestParam(value = "endDate", defaultValue = "")
    String endDate, @RequestParam(value = "countTotal")
    Integer countTotal, ModelMap model, ServletRequest request, HttpServletResponse response)
    {
        LOGGER.debug("[ExportExcelController: fundchangelist()]");
        Map<String, Object> searchParams = new HashMap<String, Object>();

        if (Constant.AccountFundChange.LOGTYPE_TRANSACTION.equals(logType))
        {

            if (StringUtil.isBlank(transactionChangeType))
            {
                searchParams.put(EntityConstant.AccountFundChange.TRANSACTION_TYPE, null);
            }
            else
            {
                searchParams.put(EntityConstant.AccountFundChange.TRANSACTION_TYPE,
                    transactionChangeType);
            }

            if (StringUtil.isBlank(orderNo))
            {
                searchParams.put(EntityConstant.AccountFundChange.TRANSACTION_NO, null);
            }
            else
            {
                searchParams.put(EntityConstant.AccountFundChange.TRANSACTION_NO, orderNo.trim());
            }
        }
        else
        {
            searchParams.put(EntityConstant.AccountFundChange.TRANSACTION_TYPE, accountChangeType);

        }

        if (StringUtil.isNullOrEmpty(changeDate))
        {
            changeDate = format.format(new Date());
            if (StringUtil.isBlank(beginDate))
            {
                beginDate = DateUtil.formatDateTime(DateUtil.getDateStart(new Date()));
            }
            if (StringUtil.isBlank(endDate))
            {
                endDate = DateUtil.formatDateTime(new Date());
            }
        }
        searchParams.put(EntityConstant.AccountFundChange.BEGIN_DATE, beginDate);
        searchParams.put(EntityConstant.AccountFundChange.END_DATE, endDate);

        YcPage<AccountHistoryAssistVo> pageList = new YcPage<AccountHistoryAssistVo>();
        List<AccountHistoryAssistVo> assisVoList = new ArrayList<AccountHistoryAssistVo>();
        try
        {
            Operator loginPerson = getLoginUser();
            Long identityId = null;
            if (loginPerson != null)
            {
                identityId = loginPerson.getOwnerIdentityId();
            }
            if (identityId != null)
            {
                searchParams.put(EntityConstant.AccountFundChange.LOGTYPE, logType);
                searchParams.put(EntityConstant.AccountFundChange.IDENTITY_ID, identityId);
                searchParams.put(EntityConstant.AccountFundChange.ACCOUNT_TYPE,
                    Constant.AccountType.MERCHANT_DEBIT);
                searchParams.put(EntityConstant.AccountFundChange.RELATION,
                    Constant.Account.ACCOUNT_RELATION_TYPE_OWN);
                searchParams.put(EntityConstant.AccountFundChange.IDENTITY_ID, identityId);
                searchParams.put(EntityConstant.Account.ACCOUNT_ID,
                    session.getAttribute(EntityConstant.Account.ACCOUNT_ID));

                if (countTotal > PAGE_MAX_SIZE)
                {
                    countTotal = 200000;
                }

                for (int i = 0; i < (countTotal % PAGE_SIZE == 0 ? countTotal / PAGE_SIZE : countTotal
                                                                                            / PAGE_SIZE
                                                                                            + 1); i++ )
                {
                    pageList = currencyAccountBalanceHistoryService.queryAccountFundsChange(
                        searchParams, i + 1, PAGE_SIZE);
                    assisVoList.addAll(pageList.getList());
                }
            }
            else
            {
                LOGGER.error("[ ExportExcelController :fundchangelist()] [identityId:"
                             + identityId + "]");
            }

        }
        catch (Exception e)
        {
            LOGGER.error("[ExportExcelController :fundchangelist()] " + e.getMessage());
        }

        try
        {
            if (StringUtil.isNotBlank(beginDate))
            {
                beginDate = getFomartTime(beginDate);
            }

            if (StringUtil.isNotBlank(endDate))
            {
                endDate = getFomartTime(endDate);
            }
            List<ReportType> reportTypeList = reportTypeService.getReportTypeByType(Constant.ReportType.CCY_ACCOUNT_BALANCE_HISTORY_REPORTS);
            List<ReportProperty> rpList = reportPropertyService.queryReportPropertysByTypeId(reportTypeList.get(
                0).getReportTypeId());
            if (BeanUtils.isNull(pageList))
            {
                pageList = new YcPage<AccountHistoryAssistVo>();
            }
            exportExcel(reportTypeList.get(0).getReportFileName(), request, response, rpList,
                assisVoList, beginDate, endDate, assisVoList.size(), logType);
            return null;
        }
        catch (Exception e)
        {
            LOGGER.error("[ExportExcelController: fundchangeExport()] " + e.getMessage());
            model.put("message", "操作失败:[没有配置报表]");
            model.put("canback", true);
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
    }

    // private void setAccountFundsChange(List<AccountHistoryAssistVo> list)
    // {
    // Long orderno = 0l;
    // try
    // {
    // for (AccountHistoryAssistVo accountHistoryAssistVo : list)
    // {
    //
    // if (BeanUtils.isNotNull(accountHistoryAssistVo)
    // && StringUtil.isNotBlank(accountHistoryAssistVo.getTRANSACTIONNO()))
    // {
    // orderno = Long.valueOf(accountHistoryAssistVo.getTRANSACTIONNO());
    // Order order = orderManagement.findOne(orderno);
    //
    // accountHistoryAssistVo.setMERCHANTORDERNO(order.getMerchantOrderNo());
    // accountHistoryAssistVo.setUSERCODE(order.getUserCode());
    // accountHistoryAssistVo.setPRODUCTFACE(order.getProductFace().toString());
    //
    // AirtimeProduct airtimeProduct =
    // productPageQuery.queryAirtimeProductById(order.getProductId());
    // accountHistoryAssistVo.setPRODUCTNO(airtimeProduct.getProductName());
    // }
    // }
    //
    // }
    // catch (Exception e)
    // {
    // LOGGER.error("[ExportExcelController: setAccountFundsChange(transactionNo:" + orderno
    // + ")] [报错：" + e.getMessage() + "]");
    // }
    //
    // }

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
            com.yuecheng.hops.identity.entity.operator.Operator loginPerson = getLoginUser();
            if (loginPerson != null)
            {
                order.setDownMerchant(loginPerson.getOwnerIdentityId().toString());
                order.setPreSuccessStatus(null);
            }

            int totalNum = order.getCountTotal();
            if (order.getCountTotal() > PAGE_MAX_SIZE)
            {
                totalNum = 200000;
            }

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
                orderExportList, beginDate, endDate, orderExportList.size(), null);
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
                            String endTime, int totalNum, String logType)
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
            response.addHeader("Content-Disposition", "attachment;filename=" + fileStr + "["
                                                      + beginTime + "~" + endTime + "]" + ".xls");
            OutputStream out = response.getOutputStream();
            LOGGER.debug("[导出报表][ExportExcelController:exportExcel()] [报表名称:" + filename
                         + ",WritableSheet:创建]");
            WritableWorkbook wb = Workbook.createWorkbook(out);
            WritableSheet wsheet = null;// wb.createSheet(filename, 0);

            WritableFont font1 = new WritableFont(WritableFont.ARIAL, fontSize, WritableFont.BOLD);
            WritableCellFormat format1 = new WritableCellFormat(font1);
            int columnssize = headers.size();
            int rowsize = content.size();

            for (int x = 0; x < (rowsize % PAGE_SIZE == 0 ? rowsize / PAGE_SIZE : rowsize
                                                                                  / PAGE_SIZE + 1); x++ )
            {

                wsheet = wb.createSheet(filename + "-" + (x + 1), x);
                for (int j = 0; j < columnssize; j++ )
                {
                    ReportProperty rp = (ReportProperty)headers.get(j);
                    wsheet.addCell(new Label(j, 0, rp.getReportPropertyName(), format1));
                }
                for (int i = x * PAGE_SIZE; i < (rowsize / PAGE_SIZE > x ? PAGE_SIZE : rowsize
                                                                                       % PAGE_SIZE)
                                                + x * PAGE_SIZE; i++ )
                {
                    Object object = content.get(i);
                    BeanWrapper bw = new BeanWrapperImpl(object);
                    for (int k = 0; k < columnssize; k++ )
                    {
                        ReportProperty rp = (ReportProperty)headers.get(k);

                        Object propertyValue = bw.getPropertyValue(rp.getReportPropertyFieldName());
                        String propertyStr = "";

                        if (propertyValue == null)
                        {
                            propertyStr = "";
                        }
                        else
                        {
                            propertyStr = propertyValue + "";
                        }

                        if (rp.getReportPropertyFieldName().equals(
                            EntityConstant.Order.ORDER_STATUS.toUpperCase()))
                        {
                            propertyStr = getOrderStatusToString(propertyValue, logType);
                        }

                        if (rp.getReportPropertyFieldName().equalsIgnoreCase(
                            EntityConstant.CurrencyAccountBalanceHistory.TYPE))
                        {
                            propertyStr = getTransactType(propertyValue, logType);

                        }

                        if (BeanUtils.isNotNull(propertyValue)
                            && propertyValue.getClass().getName().equals(
                                Constant.ReportType.REPORT_PROPERTY_DATE))
                        {
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                            propertyStr = sdf.format(propertyValue);
                        }
                        wsheet.addCell(new Label(k, i % PAGE_SIZE + 1, propertyStr));

                    }
                }
            }
            LOGGER.debug("[导出报表][ExportExcelController:exportExcel()] [报表名称:" + filename + "]");

            wsheet.addCell(new Label(0,
                rowsize % PAGE_SIZE == 0 ? PAGE_SIZE + 1 : rowsize % PAGE_SIZE + 1, "总条数："));
            wsheet.addCell(new Label(1,
                rowsize % PAGE_SIZE == 0 ? PAGE_SIZE + 1 : rowsize % PAGE_SIZE + 1,
                (content.size()) + ""));

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

    private String getOrderStatusToString(Object o, String logType)
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

    private String getTransactType(Object o, String logType)
    {
        try
        {
            String transactionType = "";
            if (BeanUtils.isNotNull(o))
            {
                transactionType = o.toString();
            }
            // if (BeanUtils.isNotNull(transactionType))
            // {
            // transactionType = type.toString();
            // }

            if (Constant.AccountFundChange.LOGTYPE_TRANSACTION.equals(logType))
            {
                if (Constant.TransferType.TRANSFER_AGENT_ORDERED.equals(transactionType))
                {
                    return "下单";
                }
                else if (Constant.TransferType.TRANSFER_UN_AGENT_ORDERED.equals(transactionType))
                {
                    return "退款";
                }
                else if (Constant.TransferType.TRANSFER_REBATE_AMT_CONFIRM.equals(transactionType))
                {
                    return "返佣";
                }
            }
            else if (Constant.AccountFundChange.LOGTYPE_ACCOUNT.equals(logType))
            {
                if (Constant.TransferType.TRANSFER_ADD_CASH.equals(transactionType))
                {
                    return "加款";
                }
                else if (Constant.TransferType.TRANSFER_SUB_CASH.equals(transactionType))
                {
                    return "减款";
                }
                else if (Constant.AccountBalanceOperationType.ACT_BAL_OPR_ADD_CREDIT.equals(transactionType))
                {
                    return "授信加款";
                }
                else if (Constant.AccountBalanceOperationType.ACT_BAL_OPR_SUB_CREDIT.equals(transactionType))
                {
                    return "授信加款";
                }
            }
        }
        catch (Exception e)
        {
            LOGGER.error("[ExportExcelController: getOrderStatusToString(Object o:" + o + ")]");
            return "";
        }
        return "";
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

}
