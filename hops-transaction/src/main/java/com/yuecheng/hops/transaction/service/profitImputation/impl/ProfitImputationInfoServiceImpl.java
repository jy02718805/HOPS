package com.yuecheng.hops.transaction.service.profitImputation.impl;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextException;
import org.springframework.stereotype.Service;

import com.yuecheng.hops.account.entity.AccountType;
import com.yuecheng.hops.account.service.AccountTypeService;
import com.yuecheng.hops.account.service.IdentityAccountRoleService;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.identity.service.sp.SpService;
import com.yuecheng.hops.transaction.config.entify.profitImputation.ProfitImputationInfo;
import com.yuecheng.hops.transaction.config.entify.profitImputation.vo.ProfitImputationVo;
import com.yuecheng.hops.transaction.config.repository.ProfitImputationInfoDao;
import com.yuecheng.hops.transaction.config.repository.jpa.ProfitImputationInfoJpaDao;
import com.yuecheng.hops.transaction.execution.account.AirtimeAccountingTransaction;
import com.yuecheng.hops.transaction.execution.imputation.ProfitImputationService;
import com.yuecheng.hops.transaction.service.profitImputation.ProfitImputationInfoService;


@Service("profitImputationInfoService")
public class ProfitImputationInfoServiceImpl implements ProfitImputationInfoService
{

    private static final Logger LOGGER = LoggerFactory.getLogger(ProfitImputationInfoServiceImpl.class);

    @Autowired
    private ProfitImputationInfoJpaDao profitImputationJpaDao;

    @Autowired
    private ProfitImputationInfoDao profitImputationInfoDao;

    @Autowired
    private AirtimeAccountingTransaction airtimeAccountingTransaction;

    @Autowired
    private ProfitImputationService profitImputationService;

    @Autowired
    private AccountTypeService accountTypeService;

    @Autowired
    private SpService spService;

    @Autowired
    private IdentityAccountRoleService identityAccountRoleService;

    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public YcPage<ProfitImputationVo> queryProfitImputationInfos(Map<String, Object> searchParams,
                                                                 int pageNumber, int pageSize)
    {
        LOGGER.debug("begin [ProfitImputationInfoServiceImpl.queryTransactionReports()]  参数  :[searchParams:"
                     + searchParams + ",pageNumber:" + pageNumber + ",pageSize:" + pageSize + "]");
        YcPage<ProfitImputationVo> ycPage = new YcPage<ProfitImputationVo>();
        YcPage<ProfitImputationInfo> page = profitImputationInfoDao.queryProfitImputationInfos(
            searchParams, pageNumber, pageSize);
        List<ProfitImputationVo> profitImputationVos = new ArrayList<ProfitImputationVo>();
        try
        {
            for (ProfitImputationInfo profitImputationInfo : page.getList())
            {
                ProfitImputationVo profitImputationVo = new ProfitImputationVo();
                BeanUtils.copyProperties(profitImputationVo, profitImputationInfo);
                profitImputationVo.setProfitImputationId(profitImputationInfo.getProfitImputationId());
                AccountType accountType = accountTypeService.queryAccountTypeById(profitImputationVo.getProfitAccountTypeId());
                profitImputationVo.setImputationBeginDate(profitImputationInfo.getImputationBeginDate());
                profitImputationVo.setImputationEndDate(profitImputationInfo.getImputationEndDate());
                profitImputationVo.setProfitAccountType(accountType);
                profitImputationVos.add(profitImputationVo);
            }
            ycPage.setList(profitImputationVos);
            ycPage.setPageTotal(page.getPageTotal());
            ycPage.setCountTotal(page.getCountTotal());
        }
        catch (Exception e)
        {
            LOGGER.error("[ProfitImputationInfoServiceImpl. queryProfitImputationInfos()] [报错:"
                         + ExceptionUtil.getStackTraceAsString(e) + "]");
            throw new ApplicationContextException(ExceptionUtil.getStackTraceAsString(e), e);
        }

        return ycPage;
    }

    @Override
    public ProfitImputationInfo saveProfitImputation(ProfitImputationInfo profitImputation)
    {
        // TODO Auto-generated method stub
        LOGGER.debug("begin [ProfitImputationInfoServiceImpl.saveProfitImputation(ProfitImputationInfo:"
                     + profitImputation + ")]");
        ProfitImputationInfo pi = new ProfitImputationInfo();
        try
        {
            pi = profitImputationJpaDao.save(profitImputation);
        }
        catch (Exception e)
        {
            LOGGER.error("[ProfitImputationInfoServiceImpl. saveProfitImputation(ProfitImputationInfo profitImputation)] [异常 :"
                         + ExceptionUtil.getStackTraceAsString(e) + "]");
            throw new ApplicationContextException(ExceptionUtil.getStackTraceAsString(e), e);
        }
        LOGGER.debug("end [ProfitImputationInfoServiceImpl.saveProfitImputation()]");
        return pi;
    }

    @Override
    public ProfitImputationInfo getProfitImputationInfoById(Long profitImputationId)
    {
        LOGGER.debug("begin [ProfitImputationInfoServiceImpl.getProfitImputationInfoById(profitImputationId:"
                     + profitImputationId + ")]");
        ProfitImputationInfo profitImputationInfo = null;
        try
        {
            profitImputationInfo = profitImputationJpaDao.findOne(profitImputationId);
        }
        catch (Exception e)
        {
            LOGGER.error("[ProfitImputationInfoServiceImpl. getProfitImputationInfoById(profitImputationId profitImputationId)] [异常 :"
                         + ExceptionUtil.getStackTraceAsString(e) + "]");
        }
        return profitImputationInfo;
    }

    public Date getFormatBeginTime(String time)
    {
        Date dateFormat = null;
        try
        {
            dateFormat = format.parse(time.trim() + " 00:00:00");
        }
        catch (ParseException e)
        {
            LOGGER.error("[ProfitImputationInfoServiceImpl. getFormatBeginTime(time:" + time
                         + ")][异常: " + e.getMessage() + "]");
        }
        return dateFormat;
    }

    public Date getFormatEndTime(String time)
    {
        Date dateFormat = null;
        try
        {
            dateFormat = format.parse(time.trim() + " 23:59:59");
        }
        catch (ParseException e)
        {
            LOGGER.error("[ProfitImputationInfoServiceImpl. getFormatEndTime(time:" + time
                         + ")][异常: " + e.getMessage() + "]");
        }
        return dateFormat;
    }

    @Override
    public boolean isAccountImputation(ProfitImputationInfo profitImputationInfo)
    {
        return profitImputationService.isAccountImputation(profitImputationInfo);
    }

    @Override
    public List<ProfitImputationInfo> saveProfitImputations(List<ProfitImputationInfo> profitImputations)
    {

        try
        {
            profitImputations = (List<ProfitImputationInfo>)profitImputationJpaDao.save(profitImputations);
        }
        catch (Exception e)
        {
            LOGGER.error("[ProfitImputationInfoServiceImpl. saveProfitImputations(ProfitImputationInfo profitImputation)] [异常 :"
                         + ExceptionUtil.getStackTraceAsString(e) + "]");
            throw new ApplicationContextException(ExceptionUtil.getStackTraceAsString(e), e);
        }
        return profitImputations;
    }
}
