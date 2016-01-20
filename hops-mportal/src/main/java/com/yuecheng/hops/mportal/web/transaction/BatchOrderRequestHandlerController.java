/**
 * @Title: BatchOrderRequestHandlerController.java
 * @Package com.yuecheng.hops.mportal.web.transaction
 * @Description: TODO Copyright: Copyright (c) 2011 Company:湖南跃程网络科技有限公司
 * @author 肖进
 * @date 2014年9月2日 下午4:06:21
 * @version V1.0
 */

package com.yuecheng.hops.mportal.web.transaction;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.yuecheng.hops.batch.entity.BatchJob;
import com.yuecheng.hops.batch.entity.BatchJobDetail;
import com.yuecheng.hops.batch.entity.BatchOrderRequestHandler;
import com.yuecheng.hops.batch.service.order.BatchJobDetailService;
import com.yuecheng.hops.batch.service.order.BatchJobService;
import com.yuecheng.hops.batch.service.order.BatchOrderRequestHandlerManagement;
import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.EntityConstant;
import com.yuecheng.hops.common.enump.MerchantType;
import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.common.utils.StringUtil;
import com.yuecheng.hops.identity.entity.merchant.Merchant;
import com.yuecheng.hops.identity.service.IdentityService;
import com.yuecheng.hops.identity.service.merchant.MerchantService;
import com.yuecheng.hops.mportal.constant.PageConstant;
import com.yuecheng.hops.mportal.thread.AatchOrderListThread;
import com.yuecheng.hops.mportal.thread.TxtDataToDBThread;
import com.yuecheng.hops.mportal.vo.batch.BatchJobDetailVo;
import com.yuecheng.hops.mportal.vo.batch.BatchJobVo;
import com.yuecheng.hops.transaction.config.entify.query.SupplyQueryTactics;
import com.yuecheng.hops.transaction.service.order.OrderService;


/**
 * @ClassName: BatchOrderRequestHandlerController
 * @Description: TODO
 * @author 肖进
 * @param <T>
 * @date 2014年9月2日 下午4:06:21
 */
@Controller
@RequestMapping(value = "/Batch")
public class BatchOrderRequestHandlerController<T>
{
    private static Logger logger = LoggerFactory.getLogger(BatchOrderRequestHandlerController.class);

    @Autowired
    private BatchOrderRequestHandlerManagement batchOrderRequestHandlerManagement;

    @Autowired
    private MerchantService merchantService;

    @Autowired
    IdentityService identityService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Autowired
    private BatchJobService batchJobService;

    @Autowired
    private BatchJobDetailService batchJobDetailService;

    private static final Logger LOGGER = LoggerFactory.getLogger(BatchOrderRequestHandlerController.class);

    private static final String PAGE_SIZE = "10";

    private static final int batchPageSize = 1000;

    /**
     * @Title: batchOrderreQuestHandlerList
     * @Description: 进入批量手工补单页面
     * @param @param pageNumber
     * @param @param pageSize
     * @param @param sortType
     * @param @param model
     * @param @param request
     * @param @return 设定文件
     * @return String 返回类型
     * @throws
     */
    @RequestMapping(value = "/orderrequesthandlerlist")
    public String batchOrderreQuestHandlerList(@RequestParam(value = "upfile", defaultValue = "")
    String upfile, @RequestParam(value = "orderStatus", defaultValue = "")
    String orderStatus, @RequestParam(value = "page", defaultValue = "1")
    int pageNumber, @RequestParam(value = "pageSize", defaultValue = PageConstant.LIST_PAGE_SIZE)
    int pageSize, @RequestParam(value = "sortType", defaultValue = PageConstant.LIST_ORDER_SORT)
    String sortType, ModelMap model)
    {
        // try
        // {
        // upfile = new String(upfile.getBytes("iso8859-1"), "utf-8");
        // }
        // catch (UnsupportedEncodingException e)
        // {
        // // TODO Auto-generated catch block
        // e.printStackTrace();
        // }
        List<String> upfileList = batchOrderRequestHandlerManagement.getUpfileAll();
        Map<String, Object> orderStatusMap = new HashMap<String, Object>();
        orderStatusMap.put(String.valueOf(Constant.BatchOrderStatus.WAITING_CHECK), "待审核");
        orderStatusMap.put(String.valueOf(Constant.BatchOrderStatus.WAITING_RECHARGE), "待充值");
        orderStatusMap.put(String.valueOf(Constant.BatchOrderStatus.RECHARGE_SUCCESS), "下单成功");
        orderStatusMap.put(String.valueOf(Constant.BatchOrderStatus.RECHARGE_FAIL), "下单失败");
        if ("".equals(orderStatus)) orderStatus = "3";
        YcPage<BatchOrderRequestHandler> page_list = batchOrderRequestHandlerManagement.queryBatchOrderRequestHandlerBySQL(
            pageNumber, pageSize, upfile, Long.valueOf(Long.parseLong(orderStatus)));
        List<BatchOrderRequestHandler> list = page_list.getList();
        String pagetotal = (new StringBuilder(String.valueOf(page_list.getPageTotal()))).toString();
        String countTotal = (new StringBuilder(String.valueOf(page_list.getCountTotal()))).toString();
        model.addAttribute("upfileList", upfileList);
        model.addAttribute("upfile", upfile);
        model.addAttribute("orderStatusMap", orderStatusMap);
        model.addAttribute("orderStatus", orderStatus);
        model.addAttribute("counttotal", countTotal);
        model.addAttribute("mlist", list);
        model.addAttribute("returnstr", " ");
        model.addAttribute("page", Integer.valueOf(pageNumber));
        model.addAttribute("pageSize", Integer.valueOf(pageSize));
        model.addAttribute("pagetotal", pagetotal);
        return "transaction/batchorderrequesthandler/batchorderrequesthandler_list.ftl";
    }

    /**
     * 跳转文件上传的页面
     * 
     * @Title: AddSupplyOrderList
     * @Description: 跳转批量提交的页面
     * @param @return 设定文件
     * @return String 返回类型PAGE_ADD_SUPPLY_ORDER_LIST
     * @throws
     */
    @RequestMapping(value = "/addsupplyorderlist")
    public String AddSupplyOrderList(ModelMap model)
    {
        model.addAttribute("returnstr", "");
        return "transaction/batchorderrequesthandler/addsupplyorderlist.ftl";
    }

    /**
     * 文件上传
     * 
     * @Title: AddSupplyOrderList
     * @Description: 文件上传
     * @param @param model
     * @param @param request
     * @param @return 设定文件
     * @return String 返回类型
     * @throws
     */
    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    public String uploadFile(@RequestParam(value = "displayName", defaultValue = "")
    String displayName, @RequestParam(value = "upfile", defaultValue = "")
    String upfile, ModelMap model, HttpServletRequest request)
    {
        String myappPath = request.getSession().getServletContext().getRealPath("/").toString();
        try
        {
            if (request instanceof MultipartHttpServletRequest)
            {
                MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
                MultipartFile file = multipartRequest.getFiles("userfile1").get(0);
                long size = file.getSize();
                byte data[] = new byte[(int)size];
                InputStream input = file.getInputStream();
                input.read(data);

                // 判断上传文件夹是否存在
                File filedir = new File(myappPath + "/upload");
                if (!filedir.exists())
                {
                    filedir.mkdir();
                }
                File outFile = new File(
                    (new StringBuilder(String.valueOf(myappPath))).append("/upload/").append(
                        File.separator).append(file.getOriginalFilename()).toString());
                upfile = file.getOriginalFilename();
                if (BeanUtils.isNotNull(upfile))
                {
                    List<BatchOrderRequestHandler> supplyOrders = batchOrderRequestHandlerManagement.queryBatchOrderRequestHandlerByUpFile(upfile);
                    if (BeanUtils.isNotNull(supplyOrders) && supplyOrders.size() > 0)
                    {
                        model.put("message", "文件名重复请重新命名再试");
                        model.put("canback", true);
                    }
                    else
                    {
                        if (!outFile.exists())
                        {
                            outFile.createNewFile();
                        }
                        FileOutputStream outStream = new FileOutputStream(outFile);
                        outStream.write(data);
                        outStream.close();
                        input.close();
                        getTxtDataToDB(displayName, outFile.getAbsolutePath(), upfile);
                        model.put("message", "文件上传成功");
                        model.put("canback", false);
                        model.put("next_url", "Batch/orderrequesthandlerlist");
                        // 进入权限树界面
                        model.put("next_msg", "进入批量手工补单");
                    }
                }
                else
                {
                    model.put("message", "请选择文件后再试");
                    model.put("canback", true);
                }
            }
        }
        catch (Exception e)
        {
            logger.error("BatchOrderRequestHandlerController[uploadFile][" + e.getMessage() + "]");
        }

        return PageConstant.PAGE_COMMON_NOTIFY;
    }

    /**
     * 跳转审核数据页面
     * 
     * @Title: auditDataByUpFile
     * @Description: 跳转审核数据页面
     * @param @param upfile
     * @param @param model
     * @param @param request
     * @param @return 设定文件
     * @return String 返回类型
     * @throws
     */
    @RequestMapping(value = "/auditdatapage")
    public String auditDataPageByUpFile(ModelMap model, ServletRequest request)
    {
        List<String> upfileList = batchOrderRequestHandlerManagement.getUpfileAll();
        model.addAttribute("upfileList", upfileList);
        return "transaction/batchorderrequesthandler/auditdatapage.ftl";
    }

    @ResponseBody
    @RequestMapping(value = "/getAuditnum")
    public int getAuditnum(@RequestParam(value = "upfile", defaultValue = "")
    String upfile, @RequestParam(value = "status", defaultValue = "")
    String status, ModelMap model)
    {

        try
        {
            upfile = URLDecoder.decode(upfile, "UTF-8");
            upfile = URLDecoder.decode(upfile, "UTF-8");
        }
        catch (UnsupportedEncodingException e)
        {
            LOGGER.error("[BatchOrderRequestHandlerController.getAuditnum(upfile:" + upfile
                         + ",status:" + status + ")][异常:" + e.getMessage() + "]");
        }
        List<BatchOrderRequestHandler> borhList = batchOrderRequestHandlerManagement.queryBatchOrderRequestHandler(
            upfile, new Long(status));
        if (borhList == null)
            return 0;
        else
            return borhList.size();
    }

    /**
     * 审核数据
     * 
     * @Title: auditDataByUpFile
     * @Description: TODO
     * @param @param upfile
     * @param @param auditnum
     * @param @param model
     * @param @param request
     * @param @return 设定文件
     * @return String 返回类型
     * @throws
     */
    @RequestMapping(value = "/auditdata")
    public String auditDataByUpFile(@RequestParam(value = "upfile", defaultValue = "")
    String upfile, @RequestParam(value = "auditnum", defaultValue = "0")
    String auditnum, @RequestParam(value = "borhnum", defaultValue = "0")
    String borhnum, ModelMap model, ServletRequest request)
    {
        try
        {
            batchOrderRequestHandlerManagement.updateAuditByUpFile(
                Constant.BatchOrderStatus.WAITING_RECHARGE, upfile, new Long(auditnum.trim()));
            model.put("message", "审核数据成功");
            model.put("canback", false);
            model.put("next_url", "Batch/orderrequesthandlerlist?upfile=" + upfile
                                  + "&orderStatus=" + Constant.BatchOrderStatus.WAITING_RECHARGE);
            // 进入权限树界面
            model.put("next_msg", "进入批量手工补单");
        }
        catch (Exception e)
        {
            logger.debug("审核数据失败：" + e.getMessage());
            model.put("message", "审核失败");
            model.put("canback", true);
        }
        return PageConstant.PAGE_COMMON_NOTIFY;
    }

    /**
     * 跳转批量充值页面
     * 
     * @Title: batchOrderListPage
     * @Description: TODO
     * @param @param model
     * @param @param request
     * @param @param response
     * @param @return 设定文件
     * @return String 返回类型
     * @throws
     */
    @RequestMapping(value = "/batchorderlistpage")
    public String batchOrderListPage(ModelMap model, ServletRequest request)
    {
        List<String> upfileList = batchOrderRequestHandlerManagement.getUpfileAll();
        model.addAttribute("upfileList", upfileList);
        List<Merchant> downMerchants = merchantService.queryAllMerchant(MerchantType.AGENT, "0");
        model.addAttribute("downMerchants", downMerchants);
        return "transaction/batchorderrequesthandler/batchorderlistpage.ftl";
    }

    /**
     * 批量充值
     * 
     * @Title: batchOrderListByUpFile
     * @Description: 批量充值
     * @param @param upfile
     * @param @param merchantid
     * @param @param auditnum
     * @param @param orderStatus
     * @param @param pageNumber
     * @param @param pageSize
     * @param @param sortType
     * @param @param model
     * @param @param request
     * @param @param response
     * @param @return 设定文件
     * @return String 返回类型
     * @throws
     */
    @RequestMapping(value = "/batchorderlist")
    public String batchOrderListByUpFile(@RequestParam(value = "upfile", defaultValue = "")
    String upfile, @RequestParam(value = "merchantid", defaultValue = "")
    String merchantid, @RequestParam(value = "allnum", defaultValue = "0")
    String allnum, @RequestParam(value = "auditnum", defaultValue = "0")
    String auditnum, ModelMap model, ServletRequest request)
    {
        try
        {
            threadPoolTaskExecutor.execute(new AatchOrderListThread(new Long(merchantid), upfile,
                new Long(auditnum)));

            model.put("message", "批量充值请求成功,订单正在充值，请稍后查询结果。。。");
        }
        catch (Exception e)
        {
            logger.debug("批量充值失败：" + e.getMessage());
            model.put("message", "批量充值失败");
            model.put("canback", true);
        }

        model.put("canback", false);
        model.put("next_url", "Batch/orderrequesthandlerlist?upfile=" + upfile + "&orderStatus="
                              + Constant.BatchOrderStatus.WAITING_RECHARGE);
        // 进入权限树界面
        model.put("next_msg", "进入批量手工补单");

        return PageConstant.PAGE_COMMON_NOTIFY;
    }

    /**
     * @Title: deleteUpLoadFile
     * @Description: 删除导入文件数据
     * @param @param upfile
     * @param @param orderStatus
     * @param @param pageNumber
     * @param @param pageSize
     * @param @param sortType
     * @param @param model
     * @param @param request
     * @param @param response
     * @param @return 设定文件
     * @return String 返回类型
     * @throws
     */
    @RequestMapping(value = "/deleteupfile")
    public String deleteUpFileByUpFile(@RequestParam(value = "upfile", defaultValue = "")
    String upfile, ModelMap model, ServletRequest request)
    {
        try
        {
            upfile = new String(upfile.getBytes("iso8859-1"), "utf-8");
            batchOrderRequestHandlerManagement.updateByUpFile(Constant.BatchOrderStatus.DELETE,
                getNowTime(), upfile);

            model.put("message", "删除导入文件数据成功");
            model.put("canback", false);
            model.put("next_url", "Batch/orderrequesthandlerlist");
            // 进入权限树界面
            model.put("next_msg", "进入批量手工补单");
        }
        catch (Exception e)
        {
            model.put("message", "删除导入文件数据失败");
            model.put("canback", false);
            model.put("next_url", "Batch/orderrequesthandlerlist");
            // 进入权限树界面
            model.put("next_msg", "进入批量手工补单");
        }

        return PageConstant.PAGE_COMMON_NOTIFY;
    }

    /**
     * @Title: batchOrderreQuestHandlerResultsList
     * @Description: 进入批量手工补单结果页面
     * @param @param pageNumber
     * @param @param pageSize
     * @param @param sortType
     * @param @param model
     * @param @param request
     * @param @return 设定文件
     * @return String 返回类型
     * @throws
     */
    @RequestMapping(value = "/orderrequesthandlerresultslist")
    public String batchOrderreQuestHandlerResultsList(@RequestParam(value = "page", defaultValue = "1")
                                                      int pageNumber,
                                                      @RequestParam(value = "pageSize", defaultValue = PageConstant.LIST_PAGE_SIZE)
                                                      int pageSize,
                                                      @RequestParam(value = "sortType", defaultValue = PageConstant.LIST_ORDER_SORT)
                                                      String sortType, ModelMap model,
                                                      ServletRequest request)
    {
        return "transaction/batchorderrequesthandler/batchorderrequesthandler_results_list.ftl";
    }

    public boolean getTxtDataToDB(String displayName, String filepath, String filename)
    {
        threadPoolTaskExecutor.execute(new TxtDataToDBThread(displayName, filepath, filename,
            batchOrderRequestHandlerManagement));
        return false;
    }

    public Date getNowTime()
    {
        GregorianCalendar now = new GregorianCalendar();
        SimpleDateFormat fmtrq = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss|SSSS", Locale.CHINA);
        String nowDate = fmtrq.format(now.getTime());
        Date nowdate = null;
        try
        {
            nowdate = fmtrq.parse(nowDate);
            logger.info(fmtrq.format(nowdate));
        }
        catch (ParseException e)
        {
            logger.error("BatchOrderRequestHandlerController[getNowTime-ParseException]["
                         + e.getMessage() + "]");
        }
        return nowdate;
    }

    @RequestMapping(value = "/batchJobDetail")
    public String batchJobDetailList(@RequestParam(value = "page", defaultValue = "1")
    int pageNumber, @RequestParam(value = "page.size", defaultValue = PAGE_SIZE)
    int pageSize, BatchJobDetailVo batchJobDetailVo, Model model, ServletRequest request)
    {

        YcPage<SupplyQueryTactics> page_list = new YcPage<SupplyQueryTactics>();
        List<Merchant> upMerchants = new ArrayList<Merchant>();
        try
        {

        }
        catch (Exception e)
        {
            // TODO: handle exception
            if (LOGGER.isDebugEnabled())
            {
                LOGGER.debug("[SupplyQueryTacticsController:setRuleList()] " + e.getMessage());
            }
        }

        model.addAttribute("upMerchants", upMerchants);
        model.addAttribute("uqtList", page_list.getList());
        model.addAttribute("page", pageNumber);
        model.addAttribute("counttotal", page_list.getCountTotal() + "");
        model.addAttribute("pagetotal", page_list.getPageTotal() + "");
        return "/batch/batchJobDetail.ftl";
    }

    /**
     * 跳转文件上传的页面
     * 
     * @Title: AddSupplyOrderList
     * @Description: 跳转批量提交的页面
     * @param @return 设定文件
     * @return String 返回类型PAGE_ADD_SUPPLY_ORDER_LIST
     * @throws
     */
    @RequestMapping(value = "/uploadBacthFileInfo")
    public String uploadBacthFileInfo(ModelMap model)
    {

        List<Merchant> merchants = merchantService.queryAllMerchant(MerchantType.AGENT, null);
        model.addAttribute("returnstr", "");
        model.addAttribute("merchants", merchants);
        return "transaction/batch/uploadBacthFileInfo.ftl";
    }

    /**
     * 文件上传
     * 
     * @Description: 文件上传
     * @param @param model
     * @param @param request
     * @param @return 设定文件
     * @return String 返回类型
     * @throws
     */
    @RequestMapping(value = "/uploadBacthFile", method = RequestMethod.POST)
    public String uploadBacthFile(@RequestParam(value = "displayName", defaultValue = "")
    String displayName, @RequestParam(value = "upfile", defaultValue = "")
    String upfile, @RequestParam(value = "identityId", defaultValue = "")
    String identityId, ModelMap model, HttpServletRequest request)
    {
        String myappPath = request.getSession().getServletContext().getRealPath("/").toString();
        try
        {
            if (request instanceof MultipartHttpServletRequest)
            {
                MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
                MultipartFile file = multipartRequest.getFiles("userfile1").get(0);
                long size = file.getSize();
                byte data[] = new byte[(int)size];
                InputStream input = file.getInputStream();
                input.read(data);

                // 判断上传文件夹是否存在
                File filedir = new File(myappPath + "/upload");
                if (!filedir.exists())
                {
                    filedir.mkdir();
                }
                File outFile = new File(
                    (new StringBuilder(String.valueOf(myappPath))).append("/upload/").append(
                        File.separator).append(file.getOriginalFilename()).toString());
                upfile = file.getOriginalFilename();

                if (BeanUtils.isNotNull(upfile))
                {

                    List<BatchJob> bjList = batchJobService.queryBatchJobByFileName(upfile);
                    if (BeanUtils.isNotNull(bjList) && bjList.size() > 0)
                    {
                        model.put("message", "文件名重复请重新命名再试");
                        model.put("canback", true);
                    }
                    else
                    {
                        if (!outFile.exists())
                        {
                            outFile.createNewFile();
                        }
                        FileOutputStream outStream = new FileOutputStream(outFile);
                        outStream.write(data);
                        // !!!!
                        outStream.close();
                        input.close();

                        List<BatchJobDetail> batchJobDetails = getBatchJobDetails(
                            outFile.getAbsolutePath(), upfile);

                        if (batchJobDetails.size() > 100000)
                        {
                            model.put("message", "批充号码超过10万条数,请重新导入");
                            model.put("canback", true);
                            return PageConstant.PAGE_COMMON_NOTIFY;
                        }

                        BatchJob batchJob = new BatchJob();
                        batchJob.setIdentityId(Long.valueOf(identityId));
                        batchJob.setFileName(upfile);
                        batchJob.setOperatorName(displayName);
                        batchJob.setStatus(Constant.Batch.PENDING_AUDIT);
                        batchJob.setTotalNum(batchJobDetails.size());
                        batchJob.setCreatedTime(new Date());

                        BigDecimal totalAmt = new BigDecimal(0);
                        for (Iterator<BatchJobDetail> iterator = batchJobDetails.iterator(); iterator.hasNext();)
                        {
                            BatchJobDetail batchJobDetail = (BatchJobDetail)iterator.next();
                            totalAmt = totalAmt.add(batchJobDetail.getFaceValue());
                        }
                        batchJob.setTotalAmt(totalAmt);
                        batchJob = batchJobService.saveBatchJob(batchJob);
                        Assert.isTrue(BeanUtils.isNotNull(batchJob.getBatchId()));

                        // List<BatchJobDetail> batchJobDetails
                        for (BatchJobDetail batchJobDetail : batchJobDetails)
                        {
                            batchJobDetail.setStatus(Constant.BatchDetail.INITIAL);
                            batchJobDetail.setBatchId(batchJob.getBatchId());
                        }
                        batchJobDetailService.saveBatchJobDetails(batchJobDetails);
                        // boolean bl = saveBatchJobDetails(batchJobDetails);
                        //
                        // if (!bl)
                        // {
                        // batchJobService.updateAuditBatchJob(Constant.Batch.CANCELED,
                        // batchJob.getBatchId(), new Date());
                        // }

                        model.put("message", "文件上传成功");
                        model.put("canback", false);
                        model.put("next_url", "Batch/batchJoblist");
                        // 进入权限树界面
                        model.put("next_msg", "进入批量手工补单");
                    }
                }
                else
                {
                    model.put("message", "请选择文件后再试");
                    model.put("canback", true);
                }
            }
        }
        catch (Exception e)
        {
            logger.error("BatchOrderRequestHandlerController[uploadFile][" + e.getMessage() + "]");
            model.put("message", "文件格式有误，请选择文件后再试");
            model.put("canback", true);
        }

        return PageConstant.PAGE_COMMON_NOTIFY;
    }

    private boolean saveBatchJobDetails(List<BatchJobDetail> batchJobDetails)
    {
        int listSize = batchJobDetails.size();
        int page = (listSize + (batchPageSize - 1)) / batchPageSize;
        try
        {
            for (int i = 0; i < page; i++ )
            { // 按照数组大小遍历
                List<BatchJobDetail> subList = new ArrayList<BatchJobDetail>();
                for (int j = 0; j < listSize; j++ )
                { // 遍历待分割的list
                    int pageIndex = ((j + 1) + (batchPageSize - 1)) / batchPageSize;
                    if (pageIndex == (i + 1))
                    {
                        subList.add(batchJobDetails.get(j));
                    }

                    if ((j + 1) == ((j + 1) * batchPageSize))
                    {
                        break;
                    }
                }
                batchJobDetailService.saveBatchJobDetails(subList);
            }
        }
        catch (Exception e)
        {
            return false;
        }
        return true;
    }

    public List<BatchJobDetail> getBatchJobDetails(String filepath, String filename)
        throws Exception
    {
        List<BatchJobDetail> BatchJobDetails = new ArrayList<BatchJobDetail>();
        FileReader fr = null;
        BufferedReader br = null;
        try
        {
            fr = new FileReader(filepath);
            br = new BufferedReader(fr);
            String read = StringUtil.initString();
            int i = 0;
            while ((read = br.readLine()) != null)
            {
                BatchJobDetail borh = new BatchJobDetail();
                String str[] = read.toString().split(",");
                Assert.isTrue(str.length == 2);
                Assert.isTrue(str[0].trim().length() == 11 && isNumeric(str[0]));
                Assert.isTrue(isNumeric(str[1]));
                borh.setPhoneNum(str[0].trim());
                BigDecimal bd = new BigDecimal(str[1]);
                borh.setFaceValue(bd);
                borh.setSerialNum(++i);
                borh.setStatus(Constant.BatchDetail.INITIAL);
                BatchJobDetails.add(borh);
            }
        }
        catch (FileNotFoundException e)
        {
            logger.error("getBatchJobDetails:[run-FileNotFoundException][" + e.getMessage() + "]");
            throw e;
        }
        catch (IOException e)
        {
            logger.error("getBatchJobDetails:[run-IOException][" + e.getMessage() + "]");
            throw e;
        }
        catch (IllegalArgumentException e)
        {
            logger.error("getBatchJobDetails:[run-IOException][" + e.getMessage() + "]");
            throw e;
        }
        finally
        {
            br.close();
            fr.close();
        }
        return BatchJobDetails;
    }

    @RequestMapping(value = "/batchJoblist")
    public String batchJoblist(BatchJobVo batchJob,
                               @RequestParam(value = "page", defaultValue = "1")
                               int pageNumber,
                               @RequestParam(value = "pageSize", defaultValue = PageConstant.LIST_PAGE_SIZE)
                               int pageSize, ModelMap model)
    {
        Map<String, Object> statusMap = new HashMap<String, Object>();
        Map<String, Object> searchParams = new HashMap<String, Object>();
        searchParams.put(EntityConstant.BatchJob.FILENAME,
            batchJob.getFileName() == null ? "" : batchJob.getFileName());
        searchParams.put(EntityConstant.BatchJob.STATUS, batchJob.getStatus());
        YcPage<BatchJob> page_list = batchJobService.queryPageBatchJob(searchParams, pageNumber,
            pageSize);

        String pagetotal = (new StringBuilder(String.valueOf(page_list.getPageTotal()))).toString();
        String countTotal = (new StringBuilder(String.valueOf(page_list.getCountTotal()))).toString();
        model.addAttribute("batchJob", batchJob);
        model.addAttribute("statusMap", statusMap);
        model.addAttribute("counttotal", countTotal);
        model.addAttribute("mlist", page_list.getList());
        model.addAttribute("returnstr", " ");
        model.addAttribute("page", Integer.valueOf(pageNumber));
        model.addAttribute("pageSize", Integer.valueOf(pageSize));
        model.addAttribute("pagetotal", pagetotal);
        return "transaction/batch/batchJobList.ftl";
    }

    @RequestMapping(value = "/auditBatchJobInfo")
    public String auditBatchJobInfo(@RequestParam(value = "batchId", defaultValue = "")
    String batchId, ModelMap model, ServletRequest request)
    {
        BatchJob batchJob = batchJobService.getBatchJob(Long.valueOf(batchId));
        model.addAttribute("batchJob", batchJob);
        return "transaction/batch/auditBatchJob.ftl";
    }

    /**
     * 审核数据 功能描述: <br> 参数说明: <br>
     */
    @RequestMapping(value = "/auditBatchJob")
    @ResponseBody
    public Integer auditBatchJob(@RequestParam(value = "status", defaultValue = "")
    String status, @RequestParam(value = "batchId", defaultValue = "")
    String batchId, ModelMap model, ServletRequest request)
    {
        Integer i = 0;
        try
        {
            i = batchJobService.updateAuditBatchJob(Integer.parseInt(status),
                Long.valueOf(batchId), new Date());
        }
        catch (Exception e)
        {
            logger.error("auditBatchJob：" + e.getMessage());
        }
        return i;
    }

    @RequestMapping(value = "/batchJobDetailList")
    public String batchJobDetailList(BatchJobDetailVo batchJobDetailVo,
                                     @RequestParam(value = "page", defaultValue = "1")
                                     int pageNumber,
                                     @RequestParam(value = "pageSize", defaultValue = PageConstant.LIST_PAGE_SIZE)
                                     int pageSize, ModelMap model)
    {
        Map<String, Object> searchParams = new HashMap<String, Object>();
        searchParams.put(EntityConstant.BatchJobDetail.BATCH_ID,
            batchJobDetailVo.getBatchId() == null ? "" : batchJobDetailVo.getBatchId());

        searchParams.put(EntityConstant.BatchJobDetail.SERIAL_NUM,
            batchJobDetailVo.getSerialNum() == null ? "" : batchJobDetailVo.getSerialNum());

        searchParams.put(EntityConstant.BatchJobDetail.STATUS,
            batchJobDetailVo.getStatus() == null ? "" : batchJobDetailVo.getStatus());

        searchParams.put(EntityConstant.BatchJobDetail.ORDER_NO,
            batchJobDetailVo.getOrderNo() == null ? "" : batchJobDetailVo.getOrderNo());
        YcPage<BatchJobDetail> page_list = batchJobDetailService.queryPageBatchJobDetail(
            searchParams, pageNumber, pageSize);
        String pagetotal = (new StringBuilder(String.valueOf(page_list.getPageTotal()))).toString();
        String countTotal = (new StringBuilder(String.valueOf(page_list.getCountTotal()))).toString();
        model.addAttribute("batchJobDetailVo", batchJobDetailVo);
        model.addAttribute("counttotal", countTotal);
        model.addAttribute("mlist", page_list.getList());
        model.addAttribute("returnstr", " ");
        model.addAttribute("page", Integer.valueOf(pageNumber));
        model.addAttribute("pageSize", Integer.valueOf(pageSize));
        model.addAttribute("pagetotal", pagetotal);
        return "transaction/batch/batchJobDetailList.ftl";
    }

    /**
     * 开启 功能描述: <br> 参数说明: <br>
     */
    @RequestMapping(value = "/startBatchJob")
    @ResponseBody
    public Integer startBatchJob(@RequestParam(value = "status", defaultValue = "")
    String status, @RequestParam(value = "batchId", defaultValue = "")
    String batchId, ModelMap model, ServletRequest request)
    {
        Integer i = 0;
        try
        {
            List<BatchJob> bjList = batchJobService.queryBatchJobByStatus(Constant.Batch.STARTED);
            if (BeanUtils.isNotNull(bjList) && bjList.size() > 0)
            {
                i = 3;
            }
            else
            {
                i = batchJobService.updateStartBatchJob(Integer.parseInt(status),
                    Long.valueOf(batchId), new Date());
            }

        }
        catch (Exception e)
        {
            logger.error("startBatchJob：" + e.getMessage());
        }
        return i;
    }

    /**
     * 暂停 功能描述: <br> 参数说明: <br>
     */
    @RequestMapping(value = "/pasuseBatchJob")
    @ResponseBody
    public Integer pasuseBatchJob(@RequestParam(value = "status", defaultValue = "")
    String status, @RequestParam(value = "batchId", defaultValue = "")
    String batchId, ModelMap model, ServletRequest request)
    {
        Integer i = 0;
        try
        {
            i = batchJobService.updatePasuseBatchJob(Integer.parseInt(status),
                Long.valueOf(batchId), new Date());
        }
        catch (Exception e)
        {
            logger.error("pasuseBatchJob：" + e.getMessage());
        }
        return i;
    }

    public boolean isNumeric(String str)
    {
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }

}
