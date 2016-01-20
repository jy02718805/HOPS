package com.yuecheng.hops.identity.service.merchant.impl;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.persistence.DynamicSpecifications;
import org.springside.modules.persistence.SearchFilter;
import org.springside.modules.persistence.SearchFilter.Operator;

import com.alibaba.dubbo.rpc.RpcException;
import com.yuecheng.hops.account.entity.Account;
import com.yuecheng.hops.account.entity.AccountType;
import com.yuecheng.hops.account.service.AccountTypeService;
import com.yuecheng.hops.account.service.CCYAccountService;
import com.yuecheng.hops.account.service.CardAccountService;
import com.yuecheng.hops.account.service.IdentityAccountRoleService;
import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.Constant.IdentityConstants;
import com.yuecheng.hops.common.EntityConstant;
import com.yuecheng.hops.common.enump.IdentityType;
import com.yuecheng.hops.common.enump.MerchantLevel;
import com.yuecheng.hops.common.enump.MerchantType;
import com.yuecheng.hops.common.exception.ApplicationException;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.common.utils.Collections3;
import com.yuecheng.hops.common.utils.DateUtil;
import com.yuecheng.hops.common.utils.StringUtil;
import com.yuecheng.hops.identity.entity.IdentityStatus;
import com.yuecheng.hops.identity.entity.merchant.Merchant;
import com.yuecheng.hops.identity.entity.sp.SP;
import com.yuecheng.hops.identity.repository.MerchantDao;
import com.yuecheng.hops.identity.service.impl.IdentityServiceImpl;
import com.yuecheng.hops.identity.service.merchant.MerchantService;
import com.yuecheng.hops.identity.service.sp.SpService;
import com.yuecheng.hops.security.entity.SecurityCredential;
import com.yuecheng.hops.security.entity.SecurityCredentialType;
import com.yuecheng.hops.security.service.SecurityCredentialManagerService;
import com.yuecheng.hops.security.service.SecurityCredentialService;
import com.yuecheng.hops.security.service.SecurityTypeService;


/**
 * 商户identity服务实现类
 * 
 * @author xwj
 */
@Service("merchantService")
public class MerchantServiceImpl extends IdentityServiceImpl implements MerchantService
{
    @Autowired
    private MerchantDao merchantDao;

    @Autowired
    private AccountTypeService accountTypeService;

    @Autowired
    private CCYAccountService ccyAccountService;

    @Autowired
    private CardAccountService cardAccountService;

    @Autowired
    private SpService spService;

    @Autowired
    private IdentityAccountRoleService identityAccountRoleService;

    @Autowired
    private SecurityCredentialService securityCredentialService;

    @Autowired
    private SecurityCredentialManagerService securityCredentialManagerService;

    @Autowired
    private SecurityTypeService securityTypeService;

    private static Logger logger = LoggerFactory.getLogger(MerchantServiceImpl.class);

    /*
     * 添加商户
     * @see
     * com.yuecheng.hops.identity.service.MerchantService#saveMerchant(com.yuecheng.hops.identity
     * .entity.merchant.Merchant)
     */
    @Override
    @Transactional
    public Merchant saveMerchant(Merchant merchant, String updateUser)
    {
        try
        {
            logger.debug("MerchantServiceImpl:saveMerchant("
                         + (BeanUtils.isNotNull(merchant) ? merchant.toString() : "") + ","
                         + updateUser + ")");
            Map<String, SearchFilter> filters = new HashMap<String, SearchFilter>();
            filters.put(EntityConstant.Merchant.MERCHANT_CODE, new SearchFilter(
                EntityConstant.Merchant.MERCHANT_CODE, Operator.EQ, merchant.getMerchantCode()));
            Specification<Merchant> spec = DynamicSpecifications.bySearchFilter(filters.values(),
                Merchant.class);

            List<Merchant> checkMerchants = merchantDao.findAll(spec);
            if (checkMerchants.size() > 0)
            {
                logger.error("MerchantServiceImpl:saveMerchant(商户编号已存在："
                             + merchant.getMerchantCode() + ")");
                String[] msgParams = new String[] {merchant.toString(), updateUser};
                ApplicationException ae = new ApplicationException("identity000027", msgParams);
                throw ExceptionUtil.throwException(ae);
            }
            // 初始化商户状态为待初始化
            merchant.setIdentityStatus(new IdentityStatus(IdentityConstants.MERCHANT_INIT));
            // 设置Indentity类型为商户
            merchant.setIdentityType(IdentityType.MERCHANT);
            // 设置商户创建时间
            merchant.setCreateTime(new Date());
            // 持久化
            merchant = merchantDao.save(merchant);
            createMerchantAccount(merchant);
            // 若为销售商 分配密钥
            if (MerchantType.AGENT.equals(merchant.getMerchantType()))
            {
                String key = SecurityCredential.updateSecurityPropertyValue();
                String desKey = securityCredentialManagerService.encrypt(key,
                    Constant.EncryptType.ENCRYPT_TYPE_3DES);
                setSercurityCredential(merchant, updateUser, desKey);
            }

            logger.debug("MerchantServiceImpl:saveMerchant("
                         + (BeanUtils.isNotNull(merchant) ? merchant.toString() : "") + ")[返回信息]");
            return merchant;
        }
        catch (RpcException e)
        {
            throw ExceptionUtil.throwException(e);
        }
        catch (Exception e)
        {
            logger.error("[MerchantServiceImpl:saveMerchant("
                         + ExceptionUtil.getStackTraceAsString(e) + ")]");
            String[] msgParams = new String[] {"saveMerchant"};
            ApplicationException ae = new ApplicationException("identity001020", msgParams);
            throw ExceptionUtil.throwException(ae);
        }
    }

    private void setSercurityCredential(Merchant merchant, String updateUser, String desKey)
    {
        try
        {
            SecurityCredential credential = new SecurityCredential();
            credential.setIdentityId(merchant.getId());
            credential.setIdentityType(IdentityType.MERCHANT);
            credential.setSecurityName(merchant.getMerchantName()
                                       + Constant.SecurityCredential.SECURITYNAME_MD5KEY);
            credential.setUpdateDate(new Date());
            credential.setStatus(Constant.MerchantStatus.ENABLE);
            credential.setUpdateUser(updateUser);
            credential.setCreateUser(updateUser);
            credential.setCreateDate(new Date());
            credential.setSecurityValue(desKey);
            SecurityCredentialType securityType = securityTypeService.querySecurityTypeByTypeName(Constant.SecurityCredentialType.AGENTMD5KEY);
            credential.setSecurityType(securityType);

            Date validityDate = new Date();
            if (securityType != null)
            {
                if (securityType.getValidity() == null
                    || securityType.getValidity().intValue() == 0)
                {
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    validityDate = format.parse(Constant.SecurityCredential.DEFULT_VALIDITY_DATE);
                }
                else
                {
                    validityDate = DateUtil.addTime(Constant.DateUnit.TIME_UNIT_DAY,
                        securityType.getValidity().intValue());
                }
            }
            credential.setValidityDate(validityDate);
            securityCredentialService.saveSecurityCredential(credential);
        }
        catch (Exception e)
        {
            throw new ApplicationException("identity101070",
                new String[] {ExceptionUtil.getStackTraceAsString(e)}, e);
        }

    }

    @Transactional
    public void createMerchantAccount(Merchant mer)
    {
        try
        {
            logger.debug("MerchantServiceImpl:createMerchantAccount("
                         + (BeanUtils.isNotNull(mer) ? mer.toString() : "") + ")");
            Account account = null;
            SP sp = spService.getSP();
            if (mer.getMerchantType().equals(MerchantType.AGENT))
            {
                AccountType accountType = accountTypeService.queryAccountTypeById(Constant.AccountType.MERCHANT_DEBIT);
                account = identityAccountRoleService.saveAccount(mer, accountType,
                    Constant.Account.ACCOUNT_STATUS_LOCKED, Constant.Merchant.MERCHANT_REMARK,
                    Constant.Account.ACCOUNT_RELATION_TYPE_OWN, null);

                accountType = accountTypeService.queryAccountTypeById(Constant.AccountType.SYSTEM_DEBIT);
                for (int i = 0; i < accountType.getSubNumber(); i++ )
                {
                    account = identityAccountRoleService.saveAccount(sp, accountType,
                        Constant.Account.ACCOUNT_STATUS_UNLOCK, Constant.Merchant.MERCHANT_REMARK,
                        Constant.Account.ACCOUNT_RELATION_TYPE_OWN, new Long(i));
                    identityAccountRoleService.saveIdentityAccountRole(mer.getId(),
                        mer.getIdentityType().toString(), account.getAccountId(),
                        account.getAccountType().getAccountTypeId(),
                        Constant.Account.ACCOUNT_RELATION_TYPE_USE, Long.valueOf(i));
                }

                accountType = accountTypeService.queryAccountTypeById(Constant.AccountType.SYSTEM_PROFIT);
                account = identityAccountRoleService.saveAccount(sp, accountType,
                    Constant.Account.ACCOUNT_STATUS_UNLOCK, Constant.Merchant.MERCHANT_REMARK,
                    Constant.Account.ACCOUNT_RELATION_TYPE_OWN, null);
                identityAccountRoleService.saveIdentityAccountRole(mer.getId(),
                    mer.getIdentityType().toString(), account.getAccountId(),
                    account.getAccountType().getAccountTypeId(),
                    Constant.Account.ACCOUNT_RELATION_TYPE_USE, null);

            }
            else if (mer.getMerchantType().equals(MerchantType.SUPPLY))
            {
                AccountType accountType = accountTypeService.queryAccountTypeById(Constant.AccountType.MERCHANT_CREDIT);
                account = identityAccountRoleService.saveAccount(mer, accountType,
                    Constant.Account.ACCOUNT_STATUS_LOCKED, Constant.Merchant.MERCHANT_REMARK,
                    Constant.Account.ACCOUNT_RELATION_TYPE_OWN, null);
            }
        }
        catch (RpcException e)
        {
            throw ExceptionUtil.throwException(e);
        }
        catch (Exception e)
        {
            logger.error("[MerchantServiceImpl:createMerchantAccount("
                         + ExceptionUtil.getStackTraceAsString(e) + ")]");
            String[] msgParams = new String[] {"createMerchantAccount"};
            ApplicationException ae = new ApplicationException("identity001021", msgParams);
            throw ExceptionUtil.throwException(ae);
        }
    }

    public List<Merchant> queryAllMerchant(MerchantType type, String status)
    {
        logger.debug("MerchantServiceImpl:queryAllMerchant("
                     + (BeanUtils.isNotNull(type) ? type.toString() : "") + "," + status + ")");
        Map<String, SearchFilter> filters = new HashMap<String, SearchFilter>();
        if (BeanUtils.isNotNull(type))
        {
            filters.put(EntityConstant.Merchant.MERCHANT_TYPE, new SearchFilter(
                EntityConstant.Merchant.MERCHANT_TYPE, Operator.EQ, type));
        }
        if (StringUtil.isNotBlank(status))
        {
            filters.put(EntityConstant.Merchant.STATUS, new SearchFilter(
                EntityConstant.Merchant.STATUS, Operator.EQ, status));
        }
        Specification<Merchant> spec = DynamicSpecifications.bySearchFilter(filters.values(),
            Merchant.class);
        List<Merchant> list = merchantDao.findAll(spec);
        logger.debug("MerchantServiceImpl:queryAllMerchant("
                     + (BeanUtils.isNotNull(list) ? Collections3.convertToString(list,
                         Constant.Common.SEPARATOR) : "") + ")[返回信息]");
        return list;
    }

    public Merchant queryMerchantByMerchantCode(String merchantCode)
    {
        logger.debug("MerchantServiceImpl:queryMerchantByMerchantCode(" + merchantCode + ")");
        Map<String, SearchFilter> filters = new HashMap<String, SearchFilter>();
        filters.put(EntityConstant.Merchant.MERCHANT_CODE_CODE, new SearchFilter(
            EntityConstant.Merchant.MERCHANT_CODE_CODE, Operator.EQ, merchantCode));
        Specification<Merchant> spec = DynamicSpecifications.bySearchFilter(filters.values(),
            Merchant.class);
        Merchant merchant = merchantDao.findOne(spec);
        logger.debug("MerchantServiceImpl:queryMerchantByMerchantCode("
                     + (BeanUtils.isNotNull(merchant) ? merchant.toString() : "") + ")[返回信息]");
        return merchant;
    }

    public List<Merchant> queryMerchantByColumn(Object[] columnAndValue)
    {
        logger.debug("MerchantServiceImpl:queryMerchantByColumn()");
        if (columnAndValue == null || columnAndValue.length != 2)
        {
            logger.debug("MerchantServiceImpl:queryMerchantByColumn(商户编号检查失败，条件参数为空)");
            ApplicationException ae = new ApplicationException("identity000031");
            throw ExceptionUtil.throwException(ae);
        }
        Map<String, SearchFilter> filters = new HashMap<String, SearchFilter>();
        filters.put((String)columnAndValue[0], new SearchFilter((String)columnAndValue[0],
            Operator.EQ, columnAndValue[1]));
        Specification<Merchant> spec = DynamicSpecifications.bySearchFilter(filters.values(),
            Merchant.class);
        List<Merchant> merList = merchantDao.findAll(spec);
        logger.debug("MerchantServiceImpl:queryMerchantByColumn("
                     + (BeanUtils.isNotNull(merList) ? Collections3.convertToString(merList,
                         Constant.Common.SEPARATOR) : "") + ")[返回信息]");
        return merList;
    }

    @Override
    public List<Merchant> queryMerchantByMerchantNameFuzzy(String merchantName,
                                                           MerchantType merchantType,
                                                           MerchantLevel level)
    {
        logger.debug("MerchantServiceImpl:queryMerchantByMerchantNameFuzzy(" + merchantName + ","
                     + (BeanUtils.isNotNull(merchantType) ? merchantType.toString() : "") + ","
                     + (BeanUtils.isNotNull(level) ? level.toString() : "") + ")");
        if (BeanUtils.isNull(merchantType))
        {
            String[] msgParams = new String[] {"queryMerchantByMerchantNameFuzzy"};
            ApplicationException ae = new ApplicationException("identity001113", msgParams);
            throw ExceptionUtil.throwException(ae);
        }
        List<Merchant> merchantList = new ArrayList<Merchant>();
        if (BeanUtils.isNotNull(level))
        {
            merchantList = merchantDao.getPMerchantByMerchantName(merchantName, merchantType,
                level);
        }
        else
        {
            merchantList = merchantDao.getMerchantByMerchantName(merchantName, merchantType);
        }
        logger.debug("MerchantServiceImpl:queryMe)MerchantNameFuzzy("
                     + (BeanUtils.isNotNull(merchantList) ? Collections3.convertToString(
                         merchantList, Constant.Common.SEPARATOR) : "") + ")[返回信息]");
        return merchantList;
    }

    @Override
    public List<Merchant> queryParentMerchantByMerchantId(Long merchantId)
    {
        logger.debug("MerchantServiceImpl:queryParentMerchantByMerchantId(" + merchantId + ")");
        List<Merchant> merchantList = new ArrayList<Merchant>();
        merchantList = merchantDao.queryParentMerchantTreeListByPId(merchantId);
        logger.debug("MerchantServiceImpl:queryParentMerchantByMerchantId("
                     + (BeanUtils.isNotNull(merchantList) ? Collections3.convertToString(
                         merchantList, Constant.Common.SEPARATOR) : "") + ")[返回信息]");
        return merchantList;
    }

    @Override
    public List<Merchant> queryChildMerchantByMerchantId(Long merchantId)
    {
        try
        {
            logger.debug("MerchantServiceImpl:queryChildMerchantByMerchantId(" + merchantId + ")");
            List<Merchant> merchantList = new ArrayList<Merchant>();
            merchantList = merchantDao.queryChildMerchantTreeListById(merchantId);
            logger.debug("MerchantServiceImpl:queryChildMerchantByMerchantId("
                         + (BeanUtils.isNotNull(merchantList) ? Collections3.convertToString(
                             merchantList, Constant.Common.SEPARATOR) : "") + ")[返回信息]");
            return merchantList;
        }
        catch (Exception e)
        {
            logger.error("[MerchantServiceImpl:queryChildMerchantByMerchantId("
                         + ExceptionUtil.getStackTraceAsString(e) + ")]");
            String[] msgParams = new String[] {"queryChildMerchantByMerchantId"};
            ApplicationException ae = new ApplicationException("identity001027", msgParams);
            throw ExceptionUtil.throwException(ae);
        }
    }

    @Override
    public List<Merchant> queryMerchantList(MerchantLevel merchantLevel, String status)
    {
        logger.debug("MerchantServiceImpl:queryMerchantList(" + merchantLevel.toString() + ","
                     + status + ")");
        List<Merchant> merchantList = new ArrayList<Merchant>();
        merchantList = merchantDao.selectList(merchantLevel, status);
        logger.debug("MerchantServiceImpl:queryMerchantList("
                     + (BeanUtils.isNotNull(merchantList) ? Collections3.convertToString(
                         merchantList, Constant.Common.SEPARATOR) : "") + ")[返回信息]");
        return merchantList;
    }

    @Override
    public List<Merchant> queryMerchantList(Map<String, Object> searchParams)
    {
        logger.debug("MerchantServiceImpl:queryMerchantList("
                     + (BeanUtils.isNotNull(searchParams) ? searchParams.toString() : "") + ")");
        Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
        Specification<Merchant> spec_Merchant = DynamicSpecifications.bySearchFilter(
            filters.values(), Merchant.class);
        List<Merchant> merchants = merchantDao.findAll(spec_Merchant);
        logger.debug("MerchantServiceImpl:queryMerchantList("
                     + (BeanUtils.isNotNull(merchants) ? Collections3.convertToString(merchants,
                         Constant.Common.SEPARATOR) : "") + ")[返回信息]");
        return merchants;
    }

    @Override
    public Merchant queryMerchantById(Long merchantId)
    {
        try
        {
            logger.debug("MerchantServiceImpl:queryMerchantById(" + merchantId + ")");
            Merchant merchant = merchantDao.findOne(merchantId);
            logger.debug("MerchantServiceImpl:queryMerchantById("+ (BeanUtils.isNotNull(merchant) ? merchant.toString() : "") + ")[返回信息]");
            return merchant;
        }
        catch (Exception e)
        {
            throw ExceptionUtil.throwException(new ApplicationException("identity101085", new String[]{merchantId.toString()},e));
        }
        
    }

    @Override
    public List<Merchant> queryAllMerchantByMerchantNameFuzzy(String merchantName,
                                                              MerchantType merchantType)
    {
        logger.debug("MerchantServiceImpl:queryAllMerchantByMerchantNameFuzzy(" + merchantName
                     + ")");
        List<Merchant> merchants = new ArrayList<Merchant>();
        if (BeanUtils.isNotNull(merchantType))
        {
            merchants = merchantDao.getMerchantByMerchantName(merchantName, merchantType);
        }
        else
        {
            merchants = merchantDao.getAllMerchantByMerchantNameFuzzy(merchantName);
        }
        logger.debug("MerchantServiceImpl:queryAllMerchantByMerchantNameFuzzy("
                     + (BeanUtils.isNotNull(merchants) ? Collections3.convertToString(merchants,
                         Constant.Common.SEPARATOR) : "") + ")[返回信息]");
        return merchants;
    }

    @Override
    public List<Merchant> queryMerchantByOrganizationId(Long organizationId,
                                                        MerchantType merchantType)
    {
        logger.debug("MerchantServiceImpl:queryMerchantByOrganizationId(" + organizationId + ","
                     + (BeanUtils.isNotNull(merchantType) ? merchantType.toString() : "")
                     + ")[返回信息]");
        Map<String, SearchFilter> filters = new HashMap<String, SearchFilter>();
        if (BeanUtils.isNotNull(organizationId))
        {
            filters.put(EntityConstant.Merchant.ORGANIZATION_ID, new SearchFilter(
                EntityConstant.Merchant.ORGANIZATION_ID, Operator.EQ, organizationId));
        }
        if (BeanUtils.isNotNull(merchantType))
        {
            filters.put(EntityConstant.Merchant.MERCHANT_TYPE, new SearchFilter(
                EntityConstant.Merchant.MERCHANT_TYPE, Operator.EQ, merchantType));
        }
        Specification<Merchant> spec_Merchants = DynamicSpecifications.bySearchFilter(
            filters.values(), Merchant.class);
        List<Merchant> merchants = merchantDao.findAll(spec_Merchants, new Sort(Direction.DESC,
            EntityConstant.Merchant.MERCHANT_TYPE));
        logger.debug("MerchantServiceImpl:queryMerchantByOrganizationId("
                     + (BeanUtils.isNotNull(merchants) ? Collections3.convertToString(merchants,
                         Constant.Common.SEPARATOR) : "") + ")[返回信息]");
        return merchants;
    }

    @Override
    public List<Merchant> queryMerchantByMerchantName(String merchantName,
                                                      MerchantType merchantType, String status)
    {
        logger.debug("MerchantServiceImpl:queryMerchantByMerchantName(" + merchantName + ","
                     + (BeanUtils.isNotNull(merchantType) ? merchantType.toString() : "") + ","
                     + status + ")");
        Map<String, SearchFilter> filters = new HashMap<String, SearchFilter>();
        if (StringUtil.isNotBlank(merchantName))
        {
            filters.put(EntityConstant.Merchant.MERCHANT_NAME, new SearchFilter(
                EntityConstant.Merchant.MERCHANT_NAME, Operator.EQ, merchantName));
        }
        if (BeanUtils.isNotNull(merchantType))
        {
            filters.put(EntityConstant.Merchant.MERCHANT_TYPE, new SearchFilter(
                EntityConstant.Merchant.MERCHANT_TYPE, Operator.EQ, merchantType));
        }
        if (StringUtil.isNotBlank(status))
        {
            filters.put(EntityConstant.Merchant.STATUS, new SearchFilter(
                EntityConstant.Merchant.STATUS, Operator.EQ, status));
        }
        Specification<Merchant> spec = DynamicSpecifications.bySearchFilter(filters.values(),
            Merchant.class);
        List<Merchant> list = merchantDao.findAll(spec);
        logger.debug("MerchantServiceImpl:queryMerchantByMerchantName("
                     + (BeanUtils.isNotNull(list) ? Collections3.convertToString(list,
                         Constant.Common.SEPARATOR) : "") + ")[返回信息]");
        return list;
    }

    @Override
    public List<Merchant> queryMerchantsByName(String merchantName)
    {
        // TODO Auto-generated method stub
        List<Merchant> merchants = merchantDao.queryMerchantsByName(merchantName);
        return merchants;
    }

}
