package com.yuecheng.hops.mportal.web.injection;


import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.lang3.SerializationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.alibaba.dubbo.rpc.RpcException;
import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.security.CertFile;
import com.yuecheng.hops.gateway.service.CertFileService;
import com.yuecheng.hops.mportal.constant.PageConstant;


@Controller
@RequestMapping(value = "/certfile")
public class CertFileControl
{
    @Autowired
    private CertFileService certFileService;

    @RequestMapping(value = "/certfilelist")
    public String certfilelist(@RequestParam(value = "alias", defaultValue = "") String alias,
                               ModelMap model)
    {
        List<CertFile> certFileList = certFileService.getAllAliasFromCertFile();
        CertFile targetCertFile = null;
        boolean flag = false;
        for (CertFile certFile : certFileList)
        {
            if (certFile.getAlias().equalsIgnoreCase(alias))
            {
                flag = true;
                targetCertFile = certFile;
                break;
            }
        }
        if (flag)
        {
            certFileList.clear();
            certFileList.add(targetCertFile);
        }
        model.addAttribute("certFileList", certFileList);
        return "interface/certfileupload/certFileList";
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
    @RequestMapping(value = "/addCertFile")
    public String AddSupplyOrderList(ModelMap model)
    {
        model.addAttribute("certfile", Constant.SecurityCredential.UPLOAD_CERTFILE_NAME);
        return "interface/certfileupload/addCertFile";
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
    public String uploadFile(@RequestParam(value = "alias", defaultValue = "") String alias,
                             @RequestParam(value = "page", defaultValue = "1") int pageNumber,
                             @RequestParam(value = "pageSize", defaultValue = PageConstant.LIST_PAGE_SIZE) int pageSize,
                             @RequestParam(value = "sortType", defaultValue = PageConstant.LIST_ORDER_SORT) String sortType,
                             ModelMap model, HttpServletRequest request)
    {
        try
        {
            if (request instanceof MultipartHttpServletRequest)
            {
                MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
                MultipartFile file = multipartRequest.getFiles(Constant.SecurityCredential.UPLOAD_CERTFILE_NAME).get(0);
                CommonsMultipartFile cf = (CommonsMultipartFile)file;
                DiskFileItem fi = (DiskFileItem)cf.getFileItem();
                File certFile = fi.getStoreLocation();
                file.transferTo(certFile);
                byte[] byteFile = SerializationUtils.serialize(certFile);
                certFileService.uploadCertFile(byteFile, alias);
                model.put("message", "操作成功");
                model.put("canback", false);
                model.put("next_url", "certfile/certfilelist");
                model.put("next_msg", "证书别名列表");
            }
        }
        catch (Exception e)
        {
            model.put("message", "操作失败[" + e.getMessage() + "]");
            model.put("canback", true);
        }

        return PageConstant.PAGE_COMMON_NOTIFY;
    }

    @RequestMapping(value = "/deleteCertFile")
    public String deleteCertFile(@RequestParam(value = "alias", defaultValue = "") String alias,
                                 ModelMap model)
    {
        try
        {
            certFileService.deleteCertFileByAlias(alias);
            model.put("message", "操作成功");
            model.put("canback", false);
            model.put("next_url", "certfile/certfilelist");
            model.put("next_msg", "证书别名列表");
        }
        catch (RpcException e)
        {
            model.put("message", "操作失败[" + e.getMessage() + "]");
            model.put("canback", true);
        }
        return PageConstant.PAGE_COMMON_NOTIFY;

    }
}
