package com.yuecheng.hops.mportal.web.security;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.rpc.RpcException;
import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.EntityConstant;
import com.yuecheng.hops.common.enump.IdentityType;
import com.yuecheng.hops.common.enump.MerchantType;
import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.common.security.MD5Util;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.common.utils.DateUtil;
import com.yuecheng.hops.common.utils.StringUtil;
import com.yuecheng.hops.identity.entity.customer.Customer;
import com.yuecheng.hops.identity.entity.merchant.Merchant;
import com.yuecheng.hops.identity.entity.operator.Operator;
import com.yuecheng.hops.identity.entity.sp.SP;
import com.yuecheng.hops.identity.service.IdentityService;
import com.yuecheng.hops.identity.service.customer.CustomerService;
import com.yuecheng.hops.identity.service.merchant.MerchantService;
import com.yuecheng.hops.identity.service.operator.OperatorService;
import com.yuecheng.hops.mportal.constant.PageConstant;
import com.yuecheng.hops.mportal.web.BaseControl;
import com.yuecheng.hops.security.entity.SecurityCredential;
import com.yuecheng.hops.security.entity.SecurityCredentialType;
import com.yuecheng.hops.security.entity.vo.SecurityCredentialVo;
import com.yuecheng.hops.security.service.SecurityCredentialManagerService;
import com.yuecheng.hops.security.service.SecurityCredentialService;
import com.yuecheng.hops.security.service.SecurityRuleService;
import com.yuecheng.hops.security.service.SecurityTypeService;


/**
 * 密钥服务
 * 
 * @author Administrator
 * @version 2014年10月12日
 * @see SecurityCredentialController
 * @since
 */
@Controller
@RequestMapping(value = "/security")
public class SecurityCredentialController extends BaseControl
{
    private static final String PAGE_SIZE = "10";

    @Autowired
    private SecurityCredentialService securityCredentialService;

    @Autowired
    private SecurityTypeService securityTypeService;

    @Autowired
    private SecurityRuleService securityRuleService;

    @Autowired
    private SecurityCredentialManagerService securityCredentialManagerService;

    @Autowired
    private MerchantService merchantService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private OperatorService operatorService;

    @Autowired
    private HttpSession session;

    private static final Logger logger = LoggerFactory.getLogger(SecurityCredentialController.class);

    @Autowired
    private IdentityService identityService;

    /**
     * 密钥展示列表
     * 
     * @param identityId
     * @param identityType
     * @param status
     * @param securityTypeId
     * @param pageNumber
     * @param pageSize
     * @param sortType
     * @param model
     * @param request
     * @return
     * @see
     */
    @RequestMapping(value = "/securityCredentialList")
    public String securityCredentialList(@RequestParam(value = "identityName", defaultValue = "")
    String identityName, @RequestParam(value = "identityType", defaultValue = "")
    String identityType, @RequestParam(value = "status", defaultValue = "")
    String status, @RequestParam(value = "securityTypeId", defaultValue = "")
    String securityTypeId, @RequestParam(value = "page", defaultValue = "1")
    int pageNumber, @RequestParam(value = "pageSize", defaultValue = PAGE_SIZE)
    int pageSize, @RequestParam(value = "sortType", defaultValue = "auto")
    String sortType, ModelMap model, ServletRequest request)
    {
        try
        {
            Map<String, Object> searchParams = new HashMap<String, Object>();

            searchParams.put(EntityConstant.SecurityCredential.IDENTITY_NAME, identityName);
            searchParams.put(EntityConstant.SecurityCredential.IDENTITY_TYPE, identityType);
            searchParams.put(EntityConstant.SecurityCredential.SECURITY_TYPE, securityTypeId);
            searchParams.put(EntityConstant.SecurityCredential.STATUS, status);
            YcPage<SecurityCredentialVo> pagelist = new YcPage<SecurityCredentialVo>();
            if (StringUtil.isNotBlank(identityType))
            {
                pagelist = securityCredentialService.queryPageSecurityCredential(searchParams,
                    pageNumber, pageSize);

            }
            List<SecurityCredentialType> securityTypes = securityTypeService.querySecurityTypeBystatus(Constant.SecurityType.OPEN_STATUS);

            model.addAttribute("mlist", pagelist.getList());
            model.addAttribute("counttotal", pagelist.getCountTotal() + "");
            model.addAttribute("pagetotal", pagelist.getPageTotal() + "");
            model.addAttribute("securityTypes", securityTypes);
            model.addAttribute("identityType", identityType);
            model.addAttribute("status", status);
            model.addAttribute("securityTypeId", securityTypeId);
            model.addAttribute("identityName", identityName);

            model.addAttribute("page", pageNumber);
            model.addAttribute("pageSize", pageSize);

            return "security/securityCredentialList";
        }
        catch (RpcException e)
        {
            logger.error("[SecurityCredentialController: securityCredentialList()]"
                         + e.getMessage());
            model.put("message", "操作失败");
            model.put("canback", true);
        }
        catch (Exception e)
        {
            logger.error("[SecurityCredentialController: securityCredentialList()]"
                         + e.getMessage());
            model.put("message", "操作失败[" + e.getMessage() + "]");
            model.put("canback", true);

        }
        return PageConstant.PAGE_COMMON_NOTIFY;
    }

    /**
     * 开启密钥
     * 
     * @param securityId
     * @param model
     * @param request
     * @return
     * @see
     */
    @RequestMapping(value = "/openSecurityCredential")
    public String openSecurityCredential(@RequestParam(value = "securityId", defaultValue = "")
    String securityId, ModelMap model, ServletRequest request)
    {
        try
        {
            if (StringUtil.isNotBlank(securityId))
            {
                com.yuecheng.hops.identity.entity.operator.Operator loginPerson = getLoginUser();
                securityCredentialManagerService.updateSecurityCredentialStatus(
                    Long.valueOf(securityId), Constant.SecurityCredentialStatus.ENABLE_STATUS,
                    loginPerson.getOperatorName());
            }
            else
            {
                model.put("message", "启用失败!");
                model.put("canback", true);
                return PageConstant.PAGE_COMMON_NOTIFY;
            }
            model.put("message", "操作成功");
            model.put("canback", false);
            model.put("next_url", "security/securityCredentialList");
            model.put("next_msg", "密钥管理");
        }
        catch (RpcException e)
        {
            logger.debug("[SecurityCredentialController:updateSecurityCredential()]"
                         + e.getMessage());
            model.put("message", "操作失败[" + e.getMessage() + "]");
            model.put("canback", true);
        }
        return PageConstant.PAGE_COMMON_NOTIFY;
    }

    /**
     * 关闭密钥
     * 
     * @param securityId
     * @param model
     * @param request
     * @return
     * @see
     */
    @RequestMapping(value = "/closeSecurityCredential")
    public String closeSecurityCredential(@RequestParam(value = "securityId", defaultValue = "")
    String securityId, ModelMap model, ServletRequest request)
    {
        try
        {
            if (StringUtil.isNotBlank(securityId))
            {
                com.yuecheng.hops.identity.entity.operator.Operator loginPerson = getLoginUser();
                securityCredentialManagerService.updateSecurityCredentialStatus(
                    Long.valueOf(securityId), Constant.SecurityCredentialStatus.DISABLE_STATUS,
                    loginPerson.getOperatorName());
            }
            else
            {
                model.put("message", "禁用失败!");
                model.put("canback", true);
                return PageConstant.PAGE_COMMON_NOTIFY;
            }
            model.put("message", "操作成功");
            model.put("canback", false);
            model.put("next_url", "security/securityCredentialList");
            model.put("next_msg", "密钥管理");
        }
        catch (RpcException e)
        {
            logger.debug("[SecurityCredentialController:updateSecurityCredential()]"
                         + e.getMessage());
            model.put("message", "操作失败[" + e.getMessage() + "]");
            model.put("canback", true);
        }
        return PageConstant.PAGE_COMMON_NOTIFY;
    }

    /**
     * 删除密钥
     * 
     * @param securityId
     * @param model
     * @param request
     * @return
     * @see
     */
    @RequestMapping(value = "/deleteSecurityCredential")
    public String deleteSecurityCredential(@RequestParam(value = "securityId", defaultValue = "")
    String securityId, ModelMap model, ServletRequest request)
    {
        try
        {
            if (StringUtil.isNotBlank(securityId))
            {
                securityCredentialService.deleteSecurityCredential(Long.valueOf(securityId));
            }
            else
            {
                logger.error("[SecurityCredentialController:deleteSecurityCredential(securityId:"
                             + securityId + ")][密钥为空]");
                model.put("message", "密钥为空");
                model.put("canback", true);
            }
            model.put("message", "操作成功");
            model.put("canback", false);
            model.put("next_url", "security/securityCredentialList");
            model.put("next_msg", "密钥管理列表");
        }
        catch (RpcException e)
        {
            logger.error("[SecurityCredentialController:deleteSecurityCredential(securityId:"
                         + securityId + ")]" + e.getMessage());
            model.put("message", "操作失败[" + e.getMessage() + "!]");
            model.put("canback", true);
        }
        return PageConstant.PAGE_COMMON_NOTIFY;
    }

    /**
     * 查看密钥
     * 
     * @param securityId
     * @param model
     * @param request
     * @return
     * @see
     */
    @RequestMapping(value = "/securityCredentialDetail")
    public String securityCredentialDetail(@RequestParam(value = "securityId", defaultValue = "")
    String securityId, ModelMap model, ServletRequest request)
    {
        String securityValue = "";
        String identityName = "";
        try
        {
            SecurityCredential securityCredential = new SecurityCredential();
            try
            {
                securityCredential = securityCredentialService.querySecurityCredentialById(Long.valueOf(securityId));
                if (securityCredential.getSecurityType().getEncryptType().equals(
                    Constant.EncryptType.ENCRYPT_TYPE_RSA))
                {
                    securityValue = securityCredentialManagerService.decrypt(
                        securityCredential.getSecurityValue(),
                        Constant.EncryptType.ENCRYPT_TYPE_RSA);
                }
                else if (securityCredential.getSecurityType().getEncryptType().equals(
                    Constant.EncryptType.ENCRYPT_TYPE_3DES))
                {
                    securityValue = securityCredentialManagerService.decrypt(
                        securityCredential.getSecurityValue(),
                        Constant.EncryptType.ENCRYPT_TYPE_3DES);
                }
                else
                {
                    securityValue = securityCredential.getSecurityValue();
                }
                securityCredential.setSecurityValue(securityValue);
                identityName = getIdentityName(securityCredential.getIdentityId(),
                    securityCredential.getIdentityType());
            }
            catch (Exception e)
            {
                model.put("message", "未找到此密钥属性[" + securityId + "]");
                model.put("canback", true);
                return PageConstant.PAGE_COMMON_NOTIFY;
            }
            model.addAttribute("identityName", identityName);
            model.addAttribute("securityValue", securityValue);
            model.addAttribute("securityCredential", securityCredential);
            return "security/securityCredentialDetail";
        }
        catch (RpcException e)
        {
            logger.debug("[SecurityCredentialController:securityCredentialDetail()]"
                         + e.getMessage());
            model.put("message", "操作失败");
            model.put("canback", true);
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
    }

    /**
     * 增加密钥
     * 
     * @param model
     * @param request
     * @return
     * @see
     */
    @RequestMapping(value = "/securityCredentialAdd")
    public String securityCredentialAdd(Model model, ServletRequest request)
    {
        List<SecurityCredentialType> securityTypeList = securityTypeService.querySecurityTypeBystatus(Constant.SecurityType.OPEN_STATUS);
        List<SecurityCredentialType> securityTypes = new ArrayList<SecurityCredentialType>();
        for (SecurityCredentialType securityCredentialType : securityTypeList)
        {
            if (BeanUtils.isNotNull(securityCredentialType)
                && securityCredentialType.getIdentityType() != null
                && securityCredentialType.getIdentityType().indexOf(
                    Constant.SecurityCredentialType.SUPPLYMERCHANT) >= 0)
            {
                securityTypes.add(securityCredentialType);
            }
        }

        List<Merchant> mList = merchantService.queryAllMerchant(MerchantType.SUPPLY,
            Constant.MerchantStatus.ENABLE);
        model.addAttribute("merchants", mList);
        model.addAttribute("securityTypes", securityTypes);
        return "security/securityCredentialAdd";
    }

    /**
     * 保存密钥
     * 
     * @param securityCredential
     * @param identityId
     * @param identityType
     * @param securityName
     * @param securityTypeId
     * @param securityValue
     * @param status
     * @param model
     * @param request
     * @return
     * @see
     */
    @RequestMapping(value = "/saveSecurityCredential")
    public String saveSecurityCredential(SecurityCredential securityCredential,
                                         @RequestParam(value = "identityId", defaultValue = "")
                                         String identityId,
                                         @RequestParam(value = "identityType", defaultValue = "")
                                         String identityType,
                                         @RequestParam(value = "securityName", defaultValue = "")
                                         String securityName,
                                         @RequestParam(value = "securityTypeId", defaultValue = "")
                                         String securityTypeId,
                                         @RequestParam(value = "securityValue", defaultValue = "")
                                         String securityValue,
                                         @RequestParam(value = "status", defaultValue = "")
                                         String status, ModelMap model, ServletRequest request)
    {
        com.yuecheng.hops.identity.entity.operator.Operator loginPerson = getLoginUser();
        try
        {
            boolean bl = securityCredentialService.checkIsExistSecurity(Long.valueOf(identityId),
                IdentityType.valueOf(identityType), Long.valueOf(securityTypeId));
            if (bl)
            {
                model.put("message", "操作失败[该密钥已存在]");
                model.put("canback", true);
                return PageConstant.PAGE_COMMON_NOTIFY;
            }

            securityCredential.setIdentityId(Long.valueOf(identityId));
            SecurityCredentialType securityType = null;
            if (StringUtil.isNotBlank(securityTypeId))
            {
                securityType = securityTypeService.getSecurityType(Long.valueOf(securityTypeId));
                securityCredential.setSecurityType(securityType);
            }
            securityCredential.setIdentityType(IdentityType.valueOf(identityType));
            if (StringUtil.isNotBlank(securityValue))
            {
                if (securityType.getEncryptType().equals(Constant.EncryptType.ENCRYPT_TYPE_RSA))
                {
                    securityValue = securityCredentialManagerService.encrypt(securityValue,
                        Constant.EncryptType.ENCRYPT_TYPE_RSA);
                }
                else if (securityType.getEncryptType().equals(
                    Constant.EncryptType.ENCRYPT_TYPE_3DES))
                {
                    securityValue = securityCredentialManagerService.encrypt(securityValue,
                        Constant.EncryptType.ENCRYPT_TYPE_3DES);
                }
                else if (securityType.getEncryptType().equals(
                    Constant.EncryptType.ENCRYPT_TYPE_MD5))
                {
                    securityValue = securityCredentialManagerService.encrypt(securityValue,
                        Constant.EncryptType.ENCRYPT_TYPE_MD5);
                }
            }

            securityCredential.setSecurityValue(securityValue);
            securityCredential.setSecurityName(securityName);
            securityCredential.setCreateDate(new Date());
            securityCredential.setCreateUser(loginPerson.getOperatorName());
            securityCredential.setUpdateDate(new Date());
            securityCredential.setUpdateUser(loginPerson.getOperatorName());

            if (securityType != null)
            {
                if (securityType.getValidity() == null
                    || securityType.getValidity().intValue() == 0)
                {
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    securityCredential.setValidityDate(format.parse(Constant.SecurityCredential.DEFULT_VALIDITY_DATE));
                }
                else
                {
                    securityCredential.setValidityDate(DateUtil.addTime(
                        Constant.DateUnit.TIME_UNIT_DAY, securityType.getValidity().intValue()));
                }
            }

            securityCredential.setStatus(status);
            securityCredentialService.saveSecurityCredential(securityCredential);
            model.put("message", "操作成功");
            model.put("canback", false);
            model.put("next_url", "security/securityCredentialList");
            model.put("next_msg", "密钥管理列表");
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
        catch (RpcException e)
        {
            logger.error("[SecurityCredentialController:saveSecurityCredential()]"
                         + e.getMessage());
            model.put("message", "操作失败[" + e.getMessage() + "]");
            model.put("canback", true);
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
        catch (ParseException e)
        {
            logger.error("[SecurityCredentialController:saveSecurityCredential()]"
                         + e.getMessage());
            model.put("message", "操作失败:时间转换异常");
            model.put("canback", true);
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
        catch (Exception e)
        {
            logger.error("[SecurityCredentialController:saveSecurityCredential()]"
                         + e.getMessage());
            model.put("message", "操作失败");
            model.put("canback", true);
            return PageConstant.PAGE_COMMON_NOTIFY;
        }
    }

    /**
     * 展示密钥
     * 
     * @param securityId
     * @param securityValue
     * @param securitytypeid
     * @param securitytypename
     * @param model
     * @param request
     * @return
     * @see
     */
    @RequestMapping(value = "/showSecretKey")
    public String showSecretKey(@RequestParam(value = "securityId", defaultValue = "")
    String securityId, @RequestParam(value = "securityValue", defaultValue = "")
    String securityValue, @RequestParam(value = "securitytypeid", defaultValue = "")
    String securitytypeid, @RequestParam(value = "securitytypename", defaultValue = "")
    String securitytypename, Model model, ServletRequest request)
    {
        model.addAttribute("securityValue", securityValue);
        model.addAttribute("securityId", securityId);
        return "security/updateSecretKeyView";
    }

    /**
     * 更新密钥
     * 
     * @param securityId
     * @param securityValue
     * @param model
     * @param request
     * @return
     * @see
     */
    @RequestMapping(value = "/updateSecretKey")
    public String updateSecretKey(@RequestParam(value = "securityId", defaultValue = "")
    String securityId, @RequestParam(value = "securityValue", defaultValue = "")
    String securityValue, ModelMap model, ServletRequest request)
    {
        com.yuecheng.hops.identity.entity.operator.Operator loginPerson = getLoginUser();
        SecurityCredential securityCredential = securityCredentialService.querySecurityCredentialById(Long.valueOf(securityId));
        securityValue = securityCredentialManagerService.encrypt(securityValue,
            Constant.EncryptType.ENCRYPT_TYPE_3DES);
        securityCredential.setSecurityValue(securityValue);
        securityCredential.setStatus(Constant.SecurityCredentialStatus.DISABLE_STATUS);
        securityCredential.setUpdateDate(new Date());
        securityCredential.setUpdateUser(loginPerson.getOperatorName());

        Date validityDate = new Date();
        if (securityCredential.getSecurityType() != null)
        {
            if (securityCredential.getSecurityType().getValidity() == null
                || securityCredential.getSecurityType().getValidity().intValue() == 0)
            {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try
                {
                    validityDate = format.parse(Constant.SecurityCredential.DEFULT_VALIDITY_DATE);
                }
                catch (ParseException e)
                {
                    logger.error("[SecurityCredentialController: updateSecretKey()][报错:"
                                 + e.getMessage() + "]");
                }
            }
            else
            {
                validityDate = DateUtil.addTime(Constant.DateUnit.TIME_UNIT_DAY,
                    securityCredential.getSecurityType().getValidity().intValue());
            }
        }
        securityCredential.setValidityDate(validityDate);
        securityCredentialService.saveSecurityCredential(securityCredential);
        return "redirect:/security/securityCredentialDetail?securityId=" + securityId;
    }

    /**
     * 初始化登录密码
     * 
     * @param securityId
     * @param model
     * @param request
     * @return
     * @see
     */
    @RequestMapping(value = "/initializationCode")
    public String initializationCode(@RequestParam(value = "securityId", defaultValue = "")
    String securityId, ModelMap model, ServletRequest request)
    {
        try
        {
            com.yuecheng.hops.identity.entity.operator.Operator loginPerson = getLoginUser();
            if (StringUtil.isNotBlank(securityId))
            {

                SecurityCredential securityCredential = securityCredentialService.querySecurityCredentialById(Long.valueOf(securityId));
                if (Constant.SecurityCredentialType.PASSWORD.equals(securityCredential.getSecurityType().securityTypeName))
                {

                    String securityValue = securityCredentialManagerService.getLoginEncryptKey(
                        Constant.SecurityCredential.DEFULT_PWD, securityCredential.getIdentityId());
                    securityCredential.setSecurityValue(securityValue);
                    securityCredential.setUpdateDate(new Date());
                    securityCredential.setUpdateUser(loginPerson.getOperatorName());
                    securityCredential.setStatus(Constant.SecurityCredentialStatus.ENABLE_STATUS);

                    Date validityDate = new Date();
                    if (securityCredential.getSecurityType() != null)
                    {
                        if (securityCredential.getSecurityType().getValidity() == null
                            || securityCredential.getSecurityType().getValidity().intValue() == 0)
                        {
                            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            try
                            {
                                validityDate = format.parse(Constant.SecurityCredential.DEFULT_VALIDITY_DATE);
                            }
                            catch (ParseException e)
                            {
                                logger.error("[SecurityCredentialController: updateSecretKey()][报错:"
                                             + e.getMessage() + "]");
                            }
                        }
                        else
                        {
                            validityDate = DateUtil.addTime(Constant.DateUnit.TIME_UNIT_DAY,
                                securityCredential.getSecurityType().getValidity().intValue());
                        }
                    }
                    securityCredential.setValidityDate(validityDate);
                    securityCredentialService.saveSecurityCredential(securityCredential);
                }
            }
            else
            {
                model.put("message", "未找到此密钥[" + securityId + "]");
                model.put("canback", true);
                return PageConstant.PAGE_COMMON_NOTIFY;
            }

            model.put("message", "操作成功");
            model.put("canback", false);
            model.put("next_url", "security/securityCredentialList");
            model.put("next_msg", "密钥管理列表");
        }
        catch (RpcException e)
        {
            logger.debug("[SecurityCredentialController:deleteSecurityCredential()]"
                         + e.getMessage());
            model.put("message", "操作失败[" + e.getMessage() + "]");
            model.put("canback", true);
        }
        return PageConstant.PAGE_COMMON_NOTIFY;
    }

    /**
     * 自动更新密钥
     * 
     * @param securityId
     * @param model
     * @param request
     * @param response
     * @return
     * @see
     */
    @RequestMapping(value = "/autoUpdateSecurity")
    @ResponseBody
    public String autoUpdateSecurity(@RequestParam(value = "securityId", defaultValue = "")
    String securityId, Model model, ServletRequest request, ServletResponse response)
    {
        // 随机字符串
        String securityValue = UUID.randomUUID().toString();
        securityValue = MD5Util.getMD5Sign(securityValue);
        return securityValue;
    }

    /*
     * 获得identityName
     */
    private String getIdentityName(Long identityId, IdentityType identityType)
    {
        if (IdentityType.MERCHANT.toString().equalsIgnoreCase(identityType.toString()))
        {
            Merchant mt = (Merchant)identityService.findIdentityByIdentityId(identityId,
                identityType);
            return mt != null ? mt.getMerchantName() : "";
        }
        else if (IdentityType.CUSTOMER.toString().equalsIgnoreCase(identityType.toString()))
        {
            Customer customer = (Customer)identityService.findIdentityByIdentityId(identityId,
                identityType);
            return customer != null ? customer.getCustomerName() : "";
        }
        else if (IdentityType.OPERATOR.toString().equalsIgnoreCase(identityType.toString()))
        {
            Operator operator = (Operator)identityService.findIdentityByIdentityId(identityId,
                identityType);
            return operator != null ? operator.getOperatorName() : "";
        }
        else if (IdentityType.SP.toString().equalsIgnoreCase(identityType.toString()))
        {
            SP sp = (SP)identityService.findIdentityByIdentityId(identityId, identityType);
            return sp != null ? sp.getSpName() : "";
        }
        return "";
    }
}