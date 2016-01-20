package com.yuecheng.hops.mportal.web.transaction;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springside.modules.persistence.SearchFilter.Operator;

import com.yuecheng.hops.common.EntityConstant;
import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.mportal.constant.PageConstant;
import com.yuecheng.hops.parameter.entity.ParameterConfiguration;
import com.yuecheng.hops.parameter.service.ParameterConfigurationService;


@Controller
@RequestMapping(value = "/transaction")
public class ParameterConfigurationControl
{
    private static final String PAGE_SIZE = "10";

    @Autowired
    private ParameterConfigurationService parameterConfigurationService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ParameterConfigurationControl.class);

    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @RequestMapping(value = "/queryParameterConfiguration")
    public String queryParameterConfiguration(@RequestParam(value = "constantValue", defaultValue = "")
                                              String constantValue,
                                              @RequestParam(value = "page", defaultValue = "1")
                                              int pageNumber,
                                              @RequestParam(value = "pageSize", defaultValue = PAGE_SIZE)
                                              int pageSize, Model model, ServletRequest request)
    {
        LOGGER.debug("[ParameterConfigurationControl:queryParameterConfiguration]");

        Map<String, Object> searchParams = new HashMap<String, Object>();
        if (!constantValue.isEmpty())
        {
            searchParams.put(Operator.EQ + "_"
                             + EntityConstant.ParameterConfiguration.CONSTANT_VALUE, constantValue);
        }

        YcPage<ParameterConfiguration> page_list = new YcPage<ParameterConfiguration>();
        try
        {
            page_list = parameterConfigurationService.queryParameterConfiguration(searchParams,
                pageNumber, pageSize, EntityConstant.ParameterConfiguration.ID);
        }
        catch (Exception e)
        {
            LOGGER.error("[ParameterConfigurationControl:queryParameterConfiguration] "
                         + e.getMessage());

        }

        model.addAttribute("pageSize", pageSize);
        model.addAttribute("hctList", page_list.getList());
        model.addAttribute("page", pageNumber);
        model.addAttribute("counttotal", page_list.getCountTotal() + "");
        model.addAttribute("pagetotal", page_list.getPageTotal() + "");
        return PageConstant.PAGE_PARAMETERCONFIGURATION_LIST;
    }

    @RequestMapping(value = "/showParameterConfiguration")
    public String showParameterConfiguration(@RequestParam(value = "id", defaultValue = "")
    String id, Model model, ServletRequest request)
    {
        LOGGER.debug("[" + format.format(new Date())
                     + "] [ParameterConfigurationControl:showParameterConfiguration]");
        ParameterConfiguration hc = new ParameterConfiguration();
        if (!id.isEmpty())
        {
            hc = parameterConfigurationService.getParameterConfiguration(Long.valueOf(id));
        }
        model.addAttribute("hc", hc);
        return PageConstant.PAGE_PARAMETERCONFIGURATION_EDIT;
    }

    @RequestMapping(value = "/addParameterConfiguration")
    public String addParameterConfiguration(@RequestParam(value = "constantValue", defaultValue = "")
                                            String constantValue,
                                            @RequestParam(value = "constantName", defaultValue = "")
                                            String constantName,
                                            @RequestParam(value = "constantUnitValue", defaultValue = "")
                                            String constantUnitValue,
                                            @RequestParam(value = "constantUnitName", defaultValue = "")
                                            String constantUnitName,
                                            @RequestParam(value = "ext1", defaultValue = "")
                                            String ext1,
                                            @RequestParam(value = "ext2", defaultValue = "")
                                            String ext2, Model model, ServletRequest request)
    {
        LOGGER.debug("[" + format.format(new Date())
                     + "] [ParameterConfigurationControl:addParameterConfiguration]");
        ParameterConfiguration ParameterConfiguration = new ParameterConfiguration();
        if (!constantValue.isEmpty())
        {
            ParameterConfiguration.setConstantValue(constantValue);
        }

        if (!constantName.isEmpty())
        {
            ParameterConfiguration.setConstantName(constantName);
        }

        if (!constantValue.isEmpty())
        {
            ParameterConfiguration.setConstantUnitValue(constantUnitValue);
        }

        if (!constantUnitName.isEmpty())
        {
            ParameterConfiguration.setConstantUnitName(constantUnitName);
        }

        if (!ext1.isEmpty())
        {
            ParameterConfiguration.setExt1(ext1);
        }
        if (!ext2.isEmpty())
        {
            ParameterConfiguration.setExt2(ext2);
        }
        try
        {
            parameterConfigurationService.addParameterConfiguration(ParameterConfiguration);
        }
        catch (Exception e)
        {
            // TODO: handle exception
            LOGGER.error("[ParameterConfigurationControl:addParameterConfiguration] "
                         + e.getMessage());
        }

        return PageConstant.PAGE_PARAMETERCONFIGURATION_LIST;
    }

    @RequestMapping(value = "/updateParameterConfiguration")
    public String updateParameterConfiguration(@RequestParam(value = "id", defaultValue = "")
    String id, @RequestParam(value = "constantValue", defaultValue = "")
    String constantValue, @RequestParam(value = "constantName", defaultValue = "")
    String constantName, @RequestParam(value = "constantUnitValue", defaultValue = "")
    String constantUnitValue, @RequestParam(value = "constantUnitName", defaultValue = "")
    String constantUnitName, @RequestParam(value = "ext1", defaultValue = "")
    String ext1, @RequestParam(value = "ext2", defaultValue = "")
    String ext2, Model model, ServletRequest request)
    {
        LOGGER.debug("[" + format.format(new Date())
                     + "] [ParameterConfigurationControl:updateParameterConfiguration]");
        ParameterConfiguration ParameterConfiguration = new ParameterConfiguration();
        if (!id.isEmpty())
        {
            ParameterConfiguration.setId(Long.valueOf(id));
        }

        if (!constantValue.isEmpty())
        {
            ParameterConfiguration.setConstantValue(constantValue);
        }
        if (!constantName.isEmpty())
        {
            ParameterConfiguration.setConstantName(constantName);
        }

        if (!constantValue.isEmpty())
        {
            ParameterConfiguration.setConstantUnitValue(constantUnitValue);
        }

        if (!constantUnitName.isEmpty())
        {
            ParameterConfiguration.setConstantUnitName(constantUnitName);
        }
        if (!ext1.isEmpty())
        {
            ParameterConfiguration.setExt1(ext1);
        }
        if (!ext2.isEmpty())
        {
            ParameterConfiguration.setExt2(ext2);
        }
        try
        {
            parameterConfigurationService.updateParameterConfiguration(ParameterConfiguration);
        }
        catch (Exception e)
        {
            // TODO: handle exception
            LOGGER.error("[ParameterConfigurationControl:updateParameterConfiguration] "
                         + e.getMessage());
        }

        return PageConstant.PAGE_PARAMETERCONFIGURATION_REDIRECT_LIST;
    }

}
