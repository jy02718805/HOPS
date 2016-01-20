package com.yuecheng.hops.mportal.web.report;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springside.modules.persistence.SearchFilter.Operator;

import com.alibaba.dubbo.rpc.RpcException;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.EntityConstant;
import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.common.utils.StringUtil;
import com.yuecheng.hops.mportal.constant.PageConstant;
import com.yuecheng.hops.report.entity.ReportMetadata;
import com.yuecheng.hops.report.entity.ReportProperty;
import com.yuecheng.hops.report.entity.ReportTerm;
import com.yuecheng.hops.report.entity.ReportType;
import com.yuecheng.hops.report.service.ReportPropertyService;
import com.yuecheng.hops.report.service.ReportTypeService;


/**
 * 报表类型配置
 * 
 * @author Administrator
 * @version 2014年10月12日
 * @see ReportTypeController
 * @since
 */
@Controller
@RequestMapping(value = "/report")
public class ReportTypeController
{
    private static final Logger LOGGER = LoggerFactory.getLogger(ReportTypeController.class);

    private static final String PAGE_SIZE = "10";

    @Autowired
    private ReportTypeService reportTypeService;

    @Autowired
    private ReportPropertyService reportPropertyService;

    /**
     * 报表类型配置列表
     * 
     * @param reportFileName
     * @param pageNumber
     * @param pageSize
     * @param model
     * @param request
     * @return
     * @see
     */
    @RequestMapping(value = "/reportTypeList")
    public String reportTypeList(@RequestParam(value = "reportFileName", defaultValue = "")
    String reportFileName, @RequestParam(value = "page", defaultValue = "1")
    int pageNumber, @RequestParam(value = "pageSize", defaultValue = PAGE_SIZE)
    int pageSize, Model model, ServletRequest request)
    {
        LOGGER.debug("[进入报表类型查询方法][ReportTypeController:reportTypeList()] ");

        Map<String, Object> searchParams = new HashMap<String, Object>();
        if (!StringUtil.isNullOrEmpty(reportFileName))
        {
            searchParams.put(Operator.LIKE + "_" + EntityConstant.ReportType.REPORT_FILE_NAME,
                reportFileName);
        }

        YcPage<ReportType> pagelist = new YcPage<ReportType>();
        try
        {
            pagelist = reportTypeService.queryReportTypes(searchParams, pageNumber, pageSize,
                EntityConstant.ReportType.REPORT_TYPE_ID);
        }
        catch (Exception e)
        {
            LOGGER.error("[报表类型查询错误][ReportTypeController:reportTypeList()] " + e.getMessage());
        }
        model.addAttribute("reportFileName", reportFileName);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("reportTypetList", pagelist.getList());
        model.addAttribute("page", pageNumber);
        model.addAttribute("counttotal", pagelist.getCountTotal() + "");
        model.addAttribute("pagetotal", pagelist.getPageTotal() + "");
        LOGGER.debug("[结束报表类型查询方法] [ReportTypeController:reportTypeList()] ");
        return "/report/reportConfigureList.ftl";
    }

    /**
     * 报表类型
     * 
     * @param reportTypeId
     * @param reportMetadataType
     * @param reportTypeName
     * @param reportMetadata
     * @param reportMetadataTypeName
     * @param resetReportMetadata
     * @param model
     * @param request
     * @return
     * @see
     */
    @RequestMapping(value = "/showReportType")
    public String showReportType(@RequestParam(value = "reportTypeId", defaultValue = "")
    String reportTypeId, @RequestParam(value = "reportMetadataType", defaultValue = "")
    String reportMetadataType, @RequestParam(value = "reportTypeName", defaultValue = "")
    String reportTypeName, @RequestParam(value = "reportMetadata", defaultValue = "")
    String reportMetadata, @RequestParam(value = "reportMetadataTypeName", defaultValue = "")
    String reportMetadataTypeName, @RequestParam(value = "resetReportMetadata", defaultValue = "")
    String resetReportMetadata, ModelMap model, ServletRequest request)
    {
        LOGGER.debug("[进入报表类型与属性管理页面][ReportTypeController:showReportType(reportTypeId:"
                     + reportTypeId + ",reportMetadataType:" + reportMetadataType + ""
                     + ",reportTypeName:" + reportTypeName + ",reportMetadata:" + reportMetadata
                     + ",reportMetadataTypeName:" + reportMetadataTypeName
                     + ",resetReportMetadata:" + resetReportMetadata + ")] ");
        List<ReportProperty> reportPropertyList = new ArrayList<ReportProperty>();
        List<ReportMetadata> rmList = new ArrayList<ReportMetadata>();
        List<ReportTerm> reportTermList = new ArrayList<ReportTerm>();
        List<ReportMetadata> rmSt = new ArrayList<ReportMetadata>();
        ReportType rt = new ReportType();
        try
        {
            if (StringUtil.isNotBlank(reportTypeId))
            {
                Long id = Long.valueOf(reportTypeId);
                rt = reportTypeService.getReportType(id);
            }
            if (ReportTool.reportTypeisNotNull(rt))
            {
                reportPropertyList = reportPropertyService.queryReportPropertysByTypeId(Long.valueOf(rt.getReportTypeId()));
                reportMetadataType = rt.getReportMetadataType();
                reportMetadataTypeName = rt.getReportMetadataTypeName();
                reportTermList = reportTypeService.getReportTermsByReportTypeId(rt.getReportTypeId());
            }
            rmList = reportTypeService.getmetadataByType(reportMetadataType);
            rmSt = reportTypeService.getAllmetadataByType(reportMetadataType);
        }
        catch (RpcException e)
        {
            LOGGER.error("[报表类型与属性管理展示报错][ReportTypeController:showReportType()] "
                         + e.getMessage());
            model.put("message", "操作失败[" + e.getMessage() + "]");
            model.put("canback", true);
            return PageConstant.PAGE_COMMON_NOTIFY;
        }

        model.addAttribute("rmSt", rmSt);
        model.addAttribute("reportMetadataType", reportMetadataType);
        model.addAttribute("reportMetadataTypeName", reportMetadataTypeName);
        model.addAttribute("rt", rt);
        model.addAttribute("rmList", rmList);
        model.addAttribute("reportTermList", reportTermList);
        model.addAttribute("rpList", reportPropertyList);
        LOGGER.debug("[结束报表类型与属性管理][ReportTypeController:showReportType()] ");
        return "/report/saveReportConfigure.ftl";
    }

    /**
     * 保存类型
     * 
     * @param reportTypeId
     * @param reportPropertyStr
     * @param reportFileName
     * @param reportTypeName
     * @param reportMetadataType
     * @param reportMetadataTypeName
     * @param reportMetadata
     * @param resetReportMetadata
     * @param model
     * @param request
     * @return
     * @see
     */
    @RequestMapping(value = "/saveReportType")
    public String saveReportType(@RequestParam(value = "reportTypeId", defaultValue = "")
    String reportTypeId, @RequestParam(value = "reportPropertyStr", defaultValue = "")
    String reportPropertyStr, @RequestParam(value = "reportFileName", defaultValue = "")
    String reportFileName, @RequestParam(value = "reportTypeName", defaultValue = "")
    String reportTypeName, @RequestParam(value = "reportMetadataType", defaultValue = "")
    String reportMetadataType, @RequestParam(value = "reportMetadataTypeName", defaultValue = "")
    String reportMetadataTypeName, @RequestParam(value = "reportMetadata", defaultValue = "")
    String reportMetadata, @RequestParam(value = "resetReportMetadata", defaultValue = "")
    String resetReportMetadata, ModelMap model, ServletRequest request)
    {
        LOGGER.debug("[进入保存报表类型与属性管理页面][ReportTypeController:showReportType(reportTypeId:"
                     + reportTypeId + ",reportPropertyStr:" + reportPropertyStr
                     + ",reportMetadataType:" + reportMetadataType + "" + ",reportTypeName:"
                     + reportTypeName + ",reportMetadata:" + reportMetadata
                     + ",reportMetadataTypeName:" + reportMetadataTypeName
                     + ",resetReportMetadata:" + resetReportMetadata + ")] ");
        boolean bl = false;
        ReportType rt = new ReportType();
        if (StringUtil.isNotBlank(reportTypeId))
        {
            rt.setReportTypeId(Long.valueOf(reportTypeId));
        }
        else
        {
            bl = reportTypeService.reportNameIsRepeat(reportFileName, reportMetadataType);
            if (bl)
            {
                model.put("message", "操作失败 [报表名称重复!]");
                model.put("canback", true);
                return PageConstant.PAGE_COMMON_NOTIFY;
            }
        }

        if (StringUtil.isNotBlank(reportFileName))
        {
            rt.setReportFileName(reportFileName);
        }

        if (StringUtil.isNotBlank(reportTypeName))
        {
            rt.setReportTypeName(reportTypeName.trim());
        }

        rt.setReportMetadataType(reportMetadataType);
        rt.setReportMetadataTypeName(reportMetadataTypeName);
        Long typeId;
        try
        {
            LOGGER.debug("[保存报表类型] [ReportTypeController:saveReportType(" + rt.toString() + ")]");
            ReportType reportType = reportTypeService.saveReportType(rt);

            if (reportTypeId.isEmpty())
            {
                typeId = reportType.getReportTypeId();
            }
            else
            {
                typeId = Long.valueOf(reportTypeId);
            }
            ReportTerm term = new ReportTerm();
            String[] reportMetadatas = reportMetadata.split("_");
            String[] resetReportMetadatas = resetReportMetadata.split("_");
            LOGGER.debug("[进入保存报表类型方法] [ReportTypeController:saveReportType()]");
            saveCheckedReportTerms(typeId, term, reportMetadatas);

            saveNotCheckReportTerms(typeId, term, resetReportMetadatas);

            saveReportProperty(reportPropertyStr, reportType);

            model.put("message", "操作成功");
            model.put("canback", false);
            model.put("next_url", "report/reportTypeList");
            model.put("next_msg", "报表类型配置");
        }
        catch (RpcException e)
        {
            LOGGER.error("[保存报表类型,报表条件，报表属性报错]" + "[ReportTypeController:saveReportType()] "
                         + e.getMessage());
            model.put("message", "操作失败 [" + e.getMessage() + "]");
            model.put("canback", true);
        }
        catch (Exception e)
        {
            model.put("message", "操作失败 [" + e.getMessage() + "]");
            model.put("canback", true);
        }

        return PageConstant.PAGE_COMMON_NOTIFY;
    }

    private void saveReportProperty(String reportPropertyStr, ReportType reportType)
        throws Exception
    {
        LOGGER.debug("[进入保存报表属性] [ReportTypeController: saveReportProperty(reportPropertyStr: "
                     + reportPropertyStr + ")]");

        if (reportType != null && reportType.getReportTypeId() != null)
        {
            Gson gson = new Gson();
            List<ReportProperty> rps = gson.fromJson(reportPropertyStr,
                new TypeToken<List<ReportProperty>>()
                {}.getType());

            for (int i = 0; i < rps.size() - 1; i++ )
            {
                ReportProperty rProperty = rps.get(i);
                if (BeanUtils.isNull(rProperty))
                {
                    break;
                }
                for (int j = i + 1; j < rps.size(); j++ )
                {
                    if (rps.get(j) != null
                        && rProperty.getReportPropertyFieldName().equals(
                            rps.get(j).getReportPropertyFieldName()))
                    {
                        throw new Exception("报表属性不能重复!");

                    }
                }
            }

            for (ReportProperty rp : rps)
            {
                rp.setReportTypeId(reportType.getReportTypeId());
                reportPropertyService.saveReportProperty(rp);

            }

        }

        LOGGER.debug("[结束保存报表属性] [ReportTypeController: saveReportProperty()]");
    }

    private void saveNotCheckReportTerms(Long reportTypeIdlong, ReportTerm term,
                                         String[] resetReportMetadatas)
    {
        LOGGER.debug("[进入保存未选中的报表查询条件方法] [ReportTypeController: saveNotCheckReportTerms(reportTypeIdlong："
                     + reportTypeIdlong
                     + ",term:"
                     + term
                     + ",reportMetadatas:"
                     + Arrays.toString(resetReportMetadatas) + ")]");

        for (String resetReportMetadataid : resetReportMetadatas)
        {
            if (resetReportMetadataid.isEmpty())
            {
                continue;
            }
            ReportMetadata rmdata = reportTypeService.getmetadataById(Long.valueOf(resetReportMetadataid));
            term = reportTypeService.getReportTermsByMetadataId(rmdata.getReportMetadataId(),
                reportTypeIdlong);
            if (BeanUtils.isNotNull(term))
            {
                term.setReportTermStatus(Constant.ReportType.REPORT_TERM_STATUS_CLOSE);
                reportTypeService.saveReportTerms(term);
            }
        }
        LOGGER.debug("[结束保存未选中的报表查询条件方法] [ReportTypeController: saveNotCheckReportTerms()]");
    }

    private void saveCheckedReportTerms(Long reportTypeIdlong, ReportTerm term,
                                        String[] reportMetadatas)
    {
        LOGGER.debug("[进入保存选中的报表查询条件方法] [ReportTypeController: saveCheckedReportTerms(reportTypeIdlong："
                     + reportTypeIdlong
                     + ",term:"
                     + term
                     + ",reportMetadatas:"
                     + Arrays.toString(reportMetadatas) + ")]");

        for (String reportMetadataid : reportMetadatas)
        {
            if (reportMetadataid.isEmpty())
            {
                continue;
            }
            ReportMetadata rmdata = reportTypeService.getmetadataById(Long.valueOf(reportMetadataid));

            if (BeanUtils.isNotNull(rmdata))
            {
                term = reportTypeService.getReportTermsByMetadataId(rmdata.getReportMetadataId(),
                    reportTypeIdlong);
            }
            if (BeanUtils.isNull(term))
            {
                term = new ReportTerm();
            }
            term.setReportMetadataId(Long.valueOf(reportMetadataid));
            term.setReportTypeId(reportTypeIdlong);
            term.setReportTermStatus(Constant.ReportType.REPORT_TERM_STATUS_OPEN);
            reportTypeService.saveReportTerms(term);
        }

        LOGGER.debug("[结束保存选中的报表查询条件方法] [ReportTypeController: saveCheckedReportTerms(term:"
                     + term.toString() + ")]");
    }

    /**
     * 删除类型
     * 
     * @param reportPropertyId
     * @param reportTypeId
     * @param model
     * @param request
     * @return
     * @see
     */
    @RequestMapping(value = "/toDelReportProperty")
    public String delReportProperty(@RequestParam(value = "reportPropertyId", defaultValue = "")
    String reportPropertyId, @RequestParam(value = "reportTypeId", defaultValue = "")
    String reportTypeId, Model model, ServletRequest request)
    {
        LOGGER.debug("[进入删除报表属性方法] [ReportTypeController:delReportProperty(reportTypeId:"
                     + reportTypeId + ",reportPropertyId:" + reportPropertyId + ")] ");

        ReportProperty rp = new ReportProperty();
        if (!reportPropertyId.isEmpty())
        {
            rp.setReportPropertyId(Long.valueOf(reportPropertyId));
        }
        try
        {
            reportPropertyService.delReportProperty(rp);
        }
        catch (Exception e)
        {
            LOGGER.error("[删除报表属性方法报错] [ReportTypeController:delReportProperty()] "
                         + e.getMessage());

        }
        LOGGER.debug("[结束删除报表属性方法] [ReportTypeController:delReportProperty()] ");

        model.addAttribute("reportTypeId", reportTypeId);
        return "redirect:/report/showReportType";
    }

}
