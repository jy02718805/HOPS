package com.yuecheng.hops.rebate.service.impl;


import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.utils.Collections3;

import com.alibaba.dubbo.rpc.RpcException;
import com.yuecheng.hops.account.entity.role.IdentityAccountRole;
import com.yuecheng.hops.account.service.IdentityAccountRoleService;
import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.enump.IdentityType;
import com.yuecheng.hops.common.enump.MerchantType;
import com.yuecheng.hops.common.exception.ApplicationException;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.exception.HopsException;
import com.yuecheng.hops.common.query.BSort;
import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.common.utils.DateUtil;
import com.yuecheng.hops.common.utils.StringUtil;
import com.yuecheng.hops.identity.entity.merchant.Merchant;
import com.yuecheng.hops.identity.service.IdentityService;
import com.yuecheng.hops.identity.service.merchant.MerchantStatusManagement;
import com.yuecheng.hops.identity.service.sp.SpService;
import com.yuecheng.hops.rebate.entity.RebateRecord;
import com.yuecheng.hops.rebate.entity.RebateRule;
import com.yuecheng.hops.rebate.entity.RebateTaskControl;
import com.yuecheng.hops.rebate.entity.RebateTradingVolume;
import com.yuecheng.hops.rebate.entity.assist.RebateProductAssist;
import com.yuecheng.hops.rebate.entity.assist.RebateRecordAssist;
import com.yuecheng.hops.rebate.entity.assist.RebateRuleAssist;
import com.yuecheng.hops.rebate.repository.RebateRecordDao;
import com.yuecheng.hops.rebate.service.RebateProductQueryManager;
import com.yuecheng.hops.rebate.service.RebateRecordService;
import com.yuecheng.hops.rebate.service.RebateRuleQueryManager;
import com.yuecheng.hops.rebate.service.RebateRuleService;
import com.yuecheng.hops.rebate.service.RebateTaskControlService;
import com.yuecheng.hops.rebate.service.RebateTradingVolumeService;
import com.yuecheng.hops.report.service.AgentTransactionReportService;
import com.yuecheng.hops.report.service.ProfitReportService;
import com.yuecheng.hops.report.service.SupplyTransactionReportService;
import com.yuecheng.hops.transaction.execution.account.AirtimeAccountingTransaction;


@Service("rebateRecordService")
public class RebateRecordServiceImpl implements RebateRecordService
{
    @Autowired
    private RebateRecordDao rebateRecordDao;

    @Autowired
    private RebateRuleService rebateRuleService;
    @Autowired
    private RebateRuleQueryManager rebateRuleQueryManager;

    @Autowired
    private ProfitReportService profitReportsService;

    @Autowired
    private RebateProductQueryManager rebateProductService;

    @Autowired
    private MerchantStatusManagement merchantService;

    @Autowired
    private RebateTradingVolumeService rebateTradingVolumeService;

    @Autowired
    private IdentityAccountRoleService identityAccountRoleService;

    @Autowired
    private AirtimeAccountingTransaction airtimeAccountingTransaction;

    @Autowired
    private AgentTransactionReportService agentTransactionReportsService;

    @Autowired
    private SupplyTransactionReportService supplyTransactionReportsService;

    @Autowired
    private RebateTaskControlService rebateTaskControlService;

    @Autowired
    private SpService spService;

    @Autowired
    private IdentityService identityService;

    private static Logger logger = LoggerFactory.getLogger(RebateRecordServiceImpl.class);
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Override
    @Transactional
    public void deleteRebateRecord(Long id)
    {
        logger.debug("[RebateRecordServiceImpl:deleteRebateRecord(" + id + ")]");
        RebateRecord rebateRecord = rebateRecordDao.findOne(id);
        if (!rebateRecord.getStatus().equalsIgnoreCase(Constant.RebateRecordStatus.WAIT_REBATE))
        {
            String[] msgParams = new String[] {id.toString()};
            ApplicationException ae = new ApplicationException("transaction000007", msgParams);
            throw ExceptionUtil.throwException(ae);
        }
        else
        {
            rebateRecordDao.delete(rebateRecord);
        }
    }

    @Override
    public List<RebateRecordAssist> getAllRebateRecord()
    {
        logger.debug("[RebateRecordServiceImpl:getAllRebateRecord()]");
        List<RebateRecord> rebateRecords = (List<RebateRecord>)rebateRecordDao.findAll();
        List<RebateRecordAssist> rebateRecordAssists = getAllName(rebateRecords);
        logger.debug("[RebateRecordServiceImpl:getAllRebateRecord("+(BeanUtils.isNotNull(rebateRecordAssists)?Collections3.convertToString(rebateRecordAssists, Constant.Common.SEPARATOR):StringUtil.initString())+")][返回信息]");
        return rebateRecordAssists;
    }

    @Override
    @Transactional
    public RebateRecordAssist updateRebateRecord(RebateRecord rebateRecord)
    {
        logger.debug("[RebateRecordServiceImpl:updateRebateRecord(" + (BeanUtils.isNotNull(rebateRecord) ? rebateRecord.toString() :StringUtil.initString())
                                                                                                                          + ")]");
        rebateRecord = rebateRecordDao.save(rebateRecord);
        RebateRecordAssist rebateRecordAssist = getAllName(rebateRecord);
        logger.debug("[RebateRecordServiceImpl:updateRebateRecord(" + (BeanUtils.isNotNull(rebateRecordAssist) ? rebateRecordAssist.toString() :StringUtil.initString())
            + ")][返回信息]");
        return rebateRecordAssist;
    }

    @Override
    @Transactional
    public void createRebateRecords(Date createTime)
    {
        try
        {
            logger.debug("[RebateRecordServiceImpl:createRebateRecords(" + (BeanUtils.isNotNull(createTime) ? createTime.toString() :StringUtil.initString())
                                                                                                                           + ")]");
            Date rebateTime = DateUtil.addDate(Constant.DateUnit.TIME_UNIT_DAY, -1, createTime);
            rebateTime=DateUtil.getDateStart(rebateTime);
            
            //判断是否有任务
            RebateTaskControl rebateTaskControl=rebateTaskControlService.queryRebateTaskControlByRebateDate(rebateTime);
            if(BeanUtils.isNull(rebateTaskControl))
            {
            	//任务不存在重新创建一条
            	logger.debug("[RebateRecordServiceImpl:createRebateRecords()]:数据库不存在任务数据，需新建一条");
            	rebateTaskControl=rebateTaskControlService.createrebateTaskControl(rebateTime,"数据库未获取查询，重新生成一条");
            }
            if(BeanUtils.isNotNull(rebateTaskControl)&&Constant.RebateStatus.DISABLE.equals(rebateTaskControl.getStatus()))
        	{

            	logger.debug("[RebateRecordServiceImpl:createRebateRecords()]:即将进入返佣清算，开始更新任务状态");
                //修改任务状态
                rebateTaskControl.setStatus(Constant.RebateStatus.ENABLE);
                rebateTaskControlService.updateRebateTaskControl(rebateTaskControl);
            	logger.debug("[RebateRecordServiceImpl:createRebateRecords()]:即将进入返佣清算，更新任务状态完成");
            	
        		List<Merchant> rebateMerchants = merchantService.queryMerchantByIsRebate(true);// 返佣商户集合
                for (Merchant rebateMerchant : rebateMerchants)
                {
                    List<RebateRuleAssist> rebateRuleAssists = rebateRuleQueryManager.queryRebateRuleByParams(
                        rebateMerchant.getId(), Constant.RebateStatus.ENABLE);
                    // 根据现在时间、返佣时间类型。获取时间间隔区间。
                    for (RebateRuleAssist rebateRuleAssist : rebateRuleAssists)
                    {
                        RebateRule rebateRule=rebateRuleAssist.getRebateRule();
                        Date beginTime = getTime(rebateRule.getRebateTimeType(),rebateTime,true);
                        Date endTime = getTime(rebateRule.getRebateTimeType(),rebateTime,false);
                        
                        List<RebateRecord> rebateRecords=rebateRecordDao.queryRebateRecordByParams(rebateRule.getMerchantId(), rebateRule.getRebateMerchantId(), rebateTime, rebateRule.getRebateProductId(),rebateRule.getRebateRuleId());
                        Long rebateRecordId=0l;
                        if(BeanUtils.isNotNull(rebateRecords)&&rebateRecords.size()>0)
                        {
                            rebateRecordId=rebateRecords.get(0).getId();
                        }
                        createRebateRecord(rebateMerchant.getId(), rebateMerchant.getMerchantType(),
                            rebateRule.getRebateRuleId(), beginTime, endTime, rebateRecordId);
                    }
                }

            	logger.debug("[RebateRecordServiceImpl:createRebateRecords()]:清算完毕后，开始新建一条返佣任务");
                //新建下一次任务
                Date rebateDate=DateUtil.getDateStart(createTime);
                rebateTaskControlService.createrebateTaskControl(rebateDate,"新建下一次任务"+rebateDate);

            	logger.debug("[RebateRecordServiceImpl:createRebateRecords()]:清算完毕且返佣任务已经生成");
        	}
            
        }catch(RpcException e){
            throw ExceptionUtil.throwException(e);
        }
        catch (Exception e)
        {
            logger.error("[RebateRecordServiceImpl:createRebateRecords(" + ExceptionUtil.getStackTraceAsString(e)
                             + ")]");
            String[] msgParams = new String[] {"createRebateRecords"};
            ApplicationException ae = new ApplicationException("transaction001008", msgParams);
            throw ExceptionUtil.throwException(ae);
        }
    }
    

    @Override
    @Transactional
    public RebateRecordAssist createRebateRecord(Long merchantId, MerchantType merchantType,
                                           Long rebate_rule_id, Date beginTime, Date endTime,
                                           Long rebateRecordId)
    {
        try
        {
            logger.debug("[RebateRecordServiceImpl:createRebateRecord(" + merchantId + ","
                            + rebate_rule_id + "," + beginTime + "," + endTime + ","
                            + rebateRecordId + ")]");
            //获取返佣规则
            RebateRuleAssist rebateRuleAssist = rebateRuleService.queryRebateRuleById(rebate_rule_id);
            RebateRule rebateRule=rebateRuleAssist.getRebateRule();
            // 计算交易量
            List<RebateProductAssist> rebateProducts = rebateProductService.queryProductsByRProductId(rebateRule.getRebateProductId());
            //总交易量
            BigDecimal transactionTotalVolume = new BigDecimal(0);
            for (RebateProductAssist rebateProductAssist : rebateProducts)
            {
                BigDecimal productTransactionVolume = new BigDecimal(0);
                if (merchantType.equals(MerchantType.AGENT))
                {
                    // 代交理商交易量
                    productTransactionVolume = agentTransactionReportsService.getAgentReportTransaction(
                        merchantId, rebateProductAssist.getRebateProduct().getProductId(), beginTime, endTime);
                }
                else
                {
                    // 供货商交易量
                    productTransactionVolume = supplyTransactionReportsService.getSupplyReportTransaction(
                        merchantId, rebateProductAssist.getRebateProduct().getProductId(), beginTime, endTime);
                }
                productTransactionVolume=BeanUtils.isNotNull(productTransactionVolume)?productTransactionVolume:new BigDecimal(0);
                transactionTotalVolume=transactionTotalVolume.add(productTransactionVolume);
            }
            logger.debug(merchantType.toString()+"商户："+merchantId+",交易量："+transactionTotalVolume+",时间段："+beginTime.toString()+"-"+endTime.toString());
            //总返佣金额
            BigDecimal rebateAmt = new BigDecimal(0);
            RebateTradingVolume rtv = rebateTradingVolumeService.queryRebateTradingVolumeByParams(
                rebateRule.getRebateRuleId(), transactionTotalVolume.longValue());
            // 将交易量装换成钱
            BigDecimal discount=BeanUtils.isNotNull(rtv)?rtv.getDiscount():new BigDecimal(0); 
            rebateAmt = getRebateAmt(rebateRule.getRebateType(),discount,transactionTotalVolume);
            logger.debug("返佣金额："+rebateAmt);
            // 组装返佣记录
            RebateRecord rebateRecord = new RebateRecord();
            rebateRecord.setId(rebateRecordId);
            rebateRecord.setUpdateDate(new Date());
            rebateRecord.setUpdateUser(Constant.Common.SYSTEM);
            rebateRecord.setMerchantId(merchantId);
            rebateRecord.setRebateDate(beginTime);
            rebateRecord.setMerchantType(rebateRule.getMerchantType());
            rebateRecord.setRebateAmt(rebateAmt);
            rebateRecord.setRebateProductId(rebateRule.getRebateProductId());
            rebateRecord.setRebateRuleId(rebateRule.getRebateRuleId());
            rebateRecord.setStatus(Constant.RebateStatus.DISABLE);
            rebateRecord.setTransactionVolume(transactionTotalVolume.longValue());
            rebateRecord.setRebateMerchantId(rebateRule.getRebateMerchantId());
            rebateRecord.setRebateType(rebateRule.getRebateType());
            //保存返佣记录
            rebateRecord =saveOrUpdateRebateRecord(rebateRecord);
            //更新或保存
            rebateRecord = rebateRecordDao.save(rebateRecord);
            RebateRecordAssist rebateRecordAssist=getAllName(rebateRecord);
            logger.debug("[RebateRecordServiceImpl:createRebateRecord(" + (BeanUtils.isNotNull(rebateRecordAssist) ? rebateRecordAssist.toString() :StringUtil.initString())
                + ")][返回信息]");
            return rebateRecordAssist;
        }catch(RpcException e){
            throw ExceptionUtil.throwException(e);
        }
        catch (Exception e)
        {
            logger.error("[RebateRecordServiceImpl:createRebateRecord(" + ExceptionUtil.getStackTraceAsString(e)
                             + ")]");
            String[] msgParams = new String[] {"createRebateRecord"};
            ApplicationException ae = new ApplicationException("transaction001009", msgParams);
            throw ExceptionUtil.throwException(ae);
        }
    }
    public BigDecimal getRebateAmt(Long rebateType,BigDecimal discount,BigDecimal transactionTotalVolume)
    {
        BigDecimal rebateAmt=new BigDecimal(0);
        if (rebateType.compareTo(Constant.RebateType.FIXED_VALUE) == 0)
        {
            rebateAmt = discount;
        }
        else if (rebateType.compareTo(Constant.RebateType.PROPORTION) == 0)
        {
            rebateAmt = transactionTotalVolume.multiply(discount.multiply(
                new BigDecimal(Constant.RebateTransactionVolume.ONE_HUNDRED_PERCENT)));
        }
        else
        {
            rebateAmt = new BigDecimal(0);
        }
        return rebateAmt;
    }
    public RebateRecord saveOrUpdateRebateRecord(RebateRecord rebateRecord)
    {
        try
        {
            logger.debug("[RebateRecordServiceImpl:saveOrUpdateRebateRecord(" + (BeanUtils.isNotNull(rebateRecord)?rebateRecord.toString():StringUtil.initString())
                            + ")]");
            if(BeanUtils.isNotNull(rebateRecord))
            {
                if (rebateRecord.getId().compareTo(0l) == 0)
                {
                    // 保存  先查询数据库中是否有数据，有将直接修改，没有则新创建
                    List<RebateRecord> rebateRecords = rebateRecordDao.queryRebateRecordByParams(rebateRecord.getMerchantId(), rebateRecord.getRebateMerchantId(), rebateRecord.getRebateDate(), rebateRecord.getRebateProductId(),null);
                    if (rebateRecords.size() > 0)
                    {
                        RebateRecord rebateRecordTemp = rebateRecords.get(0);
                        rebateRecord.setId(rebateRecordTemp.getId());
                        rebateRecord.setCreateDate(rebateRecordTemp.getCreateDate());
                        rebateRecord.setCreateUser(rebateRecordTemp.getCreateUser());
                    }
                }
//                rebateRecordAssist = getAllName(rebateRecord);
                logger.debug("[RebateRecordServiceImpl:saveOrUpdateRebateRecord(" + (BeanUtils.isNotNull(rebateRecord) ? rebateRecord.toString() :StringUtil.initString())
                    + ")][返回信息]");
                }
            return rebateRecord;
        }catch(RpcException e){
            throw ExceptionUtil.throwException(e);
        }
        catch (Exception e)
        {
            logger.error("[RebateRecordServiceImpl:saveOrUpdateRebateRecord(" + ExceptionUtil.getStackTraceAsString(e)
                             + ")]");
            String[] msgParams = new String[] {"saveOrUpdateRebateRecord"};
            ApplicationException ae = new ApplicationException("transaction001010", msgParams);
            throw ExceptionUtil.throwException(ae);
        }
    }

    
    @Override
    public YcPage<RebateRecordAssist> queryPageRebateRecord(Map<String, Object> searchParams,
                                                  Date beginDate, Date endDate, int pageNumber,
                                                  int pageSize, BSort bsort)
    {
        logger.debug("[RebateRecordServiceImpl:queryPageRebateRecord(" + (BeanUtils.isNotNull(searchParams) ? searchParams.toString() :StringUtil.initString())
                                                                                                                  + ")]");
        YcPage<RebateRecord> ycPage =rebateRecordDao.queryPageRebateRecord(searchParams, beginDate, endDate, pageNumber, pageSize, bsort);
        List<RebateRecord> rebateRecords = ycPage.getList();
        
        List<RebateRecordAssist> rebateRecordAssists= getAllName(rebateRecords);
        YcPage<RebateRecordAssist> ycPageAssist=new YcPage<RebateRecordAssist>(rebateRecordAssists, ycPage.getPageTotal(), ycPage.getCountTotal());
        logger.debug("[RebateRecordServiceImpl:queryPageRebateRecord(" + (BeanUtils.isNotNull(rebateRecordAssists) ? Collections3.convertToString(rebateRecordAssists, Constant.Common.SEPARATOR) :StringUtil.initString())
            + ")][返回信息]");
        return ycPageAssist;
    }

    @Override
    public List<Merchant> queryRebateMerchantsByMerchantId(Long merchantId)
    {
        try
        {
            logger.debug("[RebateRecordServiceImpl:queryRebateMerchantsByMerchantId("
                            + merchantId + ")]");
            List<Merchant> result = new ArrayList<Merchant>();
            List<RebateRuleAssist> rebateRuleAssists = rebateRuleQueryManager.queryRebateRuleByParams(merchantId,
                Constant.RebateStatus.ENABLE);
            for (RebateRuleAssist rebateRuleAssist : rebateRuleAssists)
            {
                Merchant merchant = (Merchant)identityService.findIdentityByIdentityId(
                    rebateRuleAssist.getRebateRule().getMerchantId(), IdentityType.MERCHANT);
                result.add(merchant);
            }
            logger.debug("[RebateRecordServiceImpl:queryRebateMerchantsByMerchantId(" + (BeanUtils.isNotNull(result) ? Collections3.convertToString(result, Constant.Common.SEPARATOR) :StringUtil.initString())
                + ")][返回信息]");
            return result;
        }catch(RpcException e){
            throw ExceptionUtil.throwException(e);
        }
        catch (Exception e)
        {
            logger.error("[RebateRecordServiceImpl:queryRebateMerchantsByMerchantId("
                             + ExceptionUtil.getStackTraceAsString(e) + ")]");
            String[] msgParams = new String[] {"queryRebateMerchantsByMerchantId"};
            ApplicationException ae = new ApplicationException("transaction001012", msgParams);
            throw ExceptionUtil.throwException(ae);
        }
    }

    @Override
    public RebateRecordAssist queryRebateRecordById(Long id)
    {
        try
        {
            logger.debug("[RebateRecordServiceImpl:queryRebateRecordById(" + id + ")]");
            RebateRecord rebateRecord = rebateRecordDao.findOne(id);
            RebateRuleAssist rebateRuleAssist = rebateRuleService.queryRebateRuleById(rebateRecord.getRebateRuleId());
            RebateRule rebateRule=rebateRuleAssist.getRebateRule();
            Date createTime = rebateRecord.getCreateDate();
            Date beginTime = getTime(rebateRule.getRebateTimeType(),createTime,true);
            Date endTime = getTime(rebateRule.getRebateTimeType(),createTime,false);
            RebateRecordAssist rebateRecordAssist=getAllName(rebateRecord);
            rebateRecordAssist.setBeginTime(beginTime);
            rebateRecordAssist.setEndTime(endTime);
            logger.debug("[RebateRecordServiceImpl:queryRebateRecordById(" + (BeanUtils.isNotNull(rebateRecordAssist) ? rebateRecordAssist.toString() :StringUtil.initString())
                + ")][返回信息]");
            return rebateRecordAssist;
        }catch(RpcException e){
            throw ExceptionUtil.throwException(e);
        }
        catch (Exception e)
        {
            logger.error("[RebateRecordServiceImpl:queryRebateRecordById(" + ExceptionUtil.getStackTraceAsString(e) + ")]");
            String[] msgParams = new String[] {"queryRebateRecordById"};
            ApplicationException ae = new ApplicationException("transaction001013", msgParams);
            throw ExceptionUtil.throwException(ae);
        }
    }
    
    public Date getTime(Long rebateTimeType,Date createTime,boolean isBegin)
    {
        try{
            Date beginTime = null;
            Date endTime = null;
            if (rebateTimeType.compareTo(Constant.RebateTimeType.MONTH) == 0)
            {
                //按月返得到当月的起止日期
                beginTime = format.parse(DateUtil.getFirstDay(createTime));
                endTime = format.parse(DateUtil.getLastDay(createTime));
            }
            else if (rebateTimeType.compareTo(
                Constant.RebateTimeType.QUARTER) == 0)
            {
              //按季返得到当季的起止日期
                beginTime = DateUtil.getQuarterFirstDay(createTime);
                endTime = DateUtil.getQuarterLastDay(createTime);
            }
            else if (rebateTimeType.compareTo(Constant.RebateTimeType.YEAR) == 0)
            {
              //按年返得到当年的起止日期
                beginTime = DateUtil.getYearFirstDay(createTime);
                endTime = DateUtil.getYearLastDay(createTime);
            }
            else if (rebateTimeType.compareTo(Constant.RebateTimeType.DAY) == 0)
            {
                // 按天计算得到当天的起止时间点
                beginTime = DateUtil.getDateStart(createTime);
                endTime = DateUtil.getDateEnd(createTime);
            }
            if(isBegin)
            {
                return beginTime;
            }else{
                return endTime;
            }
        }catch(Exception e)
        {
            logger.error("[RebateRecordServiceImpl:getTime("
                + ExceptionUtil.getStackTraceAsString(e) + ")]");
            String[] msgParams = new String[] {"queryRebateRecordsByMerchantIdAndDate"};
            ApplicationException ae = new ApplicationException("transaction002002", msgParams);
            throw ExceptionUtil.throwException(ae);
        }
    }

    @Override
    public List<RebateRecordAssist> queryRebateRecordsByMerchantIdAndDate(Long merchantId, Date beginDate,
                                                                  Date endDate)
    {
        try
        {
            logger.debug("[RebateRecordServiceImpl:queryRebateRecordsByMerchantIdAndDate("
                            + merchantId + "," + beginDate + "," + endDate + ")]");
            List<RebateRecord> rebateRecords = rebateRecordDao.getRebateRecordByParams(merchantId, beginDate, endDate);
            List<RebateRecordAssist> rebateRecordAssists = getAllName(rebateRecords);
            logger.debug("[RebateRecordServiceImpl:queryRebateRecordsByMerchantIdAndDate(" + (BeanUtils.isNotNull(rebateRecordAssists) ? Collections3.convertToString(rebateRecordAssists, Constant.Common.SEPARATOR) :StringUtil.initString())
                + ")][返回信息]");
            return rebateRecordAssists;
        }catch(RpcException e){
            throw ExceptionUtil.throwException(e);
        }
        catch (Exception e)
        {
            logger.error("[RebateRecordServiceImpl:queryRebateRecordsByMerchantIdAndDate("
                             + ExceptionUtil.getStackTraceAsString(e) + ")]");
            String[] msgParams = new String[] {"queryRebateRecordsByMerchantIdAndDate"};
            ApplicationException ae = new ApplicationException("transaction001014", msgParams);
            throw ExceptionUtil.throwException(ae);
        }
    }

    public List<RebateRecordAssist> getAllName(List<RebateRecord> listRebateRecord)
    {
        logger.debug("[RebateRecordServiceImpl:getAllName(" + (BeanUtils.isNotNull(listRebateRecord) ? Collections3.convertToString(listRebateRecord, Constant.Common.SEPARATOR) :StringUtil.initString())
                                                                                                                          + ")]");
        List<RebateRecordAssist> rebateRecordAssists=new ArrayList<RebateRecordAssist>();
        for (RebateRecord rebateRecord : listRebateRecord)
        {
            RebateRecordAssist rebateRecordAssist = getAllName(rebateRecord);
            rebateRecordAssists.add(rebateRecordAssist);
        }
        logger.debug("[RebateRecordServiceImpl:getAllName(" + (BeanUtils.isNotNull(rebateRecordAssists) ? Collections3.convertToString(rebateRecordAssists, Constant.Common.SEPARATOR) :StringUtil.initString())
            + ")][返回信息]");
        return rebateRecordAssists;
    }

    public RebateRecordAssist getAllName(RebateRecord rebateRecord)
    {
        try
        {
            logger.debug("[RebateRecordServiceImpl:getAllName(" + (BeanUtils.isNotNull(rebateRecord) ? rebateRecord.toString() :StringUtil.initString())
                                                                                                                      + ")]");
            RebateRecordAssist rebateRecordAssist=new RebateRecordAssist();
            if (BeanUtils.isNotNull(rebateRecord))
            {
                Merchant merchant = (Merchant)identityService.findIdentityByIdentityId(
                    rebateRecord.getMerchantId(), IdentityType.MERCHANT);
                Merchant rebateMerchant = (Merchant)identityService.findIdentityByIdentityId(
                    rebateRecord.getRebateMerchantId(), IdentityType.MERCHANT);
                if (BeanUtils.isNotNull(merchant))
                {
                    rebateRecordAssist.setMerchantName(merchant.getMerchantName());
                }
                if (BeanUtils.isNotNull(rebateMerchant))
                {
                    rebateRecordAssist.setRebateMerchantName(rebateMerchant.getMerchantName());
                }
                List<RebateProductAssist> rebateProducts = rebateProductService.queryProductsByRProductId(rebateRecord.getRebateProductId());
                rebateRecordAssist.setRebateProducts(rebateProducts);
                String productNames = StringUtil.initString();
                String productNamesAlt = StringUtil.initString();
                for (int i = 0; i < rebateProducts.size(); i++ )
                {
                    RebateProductAssist rebateProduct = rebateProducts.get(i);
                    if (i <= 2)
                    {
                        productNames = rebateProduct.getProductName();
                    }
                    if(StringUtil.isBlank(productNamesAlt))
                    {
                    	productNamesAlt = rebateProduct.getProductName();
                    }else{
    	                //设置返佣产品全称
    	                productNamesAlt = productNamesAlt + "," + rebateProduct.getProductName();
                    }
                }
                rebateRecordAssist.setProductNames(productNames);
                rebateRecordAssist.setProductNamesAlt(productNamesAlt);
                rebateRecordAssist.setRebateRecord(rebateRecord);
            }
            logger.debug("[RebateRecordServiceImpl:getAllName(" + (BeanUtils.isNotNull(rebateRecord) ? rebateRecord.toString() :StringUtil.initString())
                + ")][返回信息]");
            return rebateRecordAssist;
        }catch(RpcException e){
            throw ExceptionUtil.throwException(e);
        }
        catch (Exception e)
        {
            logger.error("[RebateRecordServiceImpl:getAllName(" + ExceptionUtil.getStackTraceAsString(e) + ")]");
            String[] msgParams = new String[] {"getAllName"};
            ApplicationException ae = new ApplicationException("transaction001016", msgParams);
            throw ExceptionUtil.throwException(ae);
        }
    }

    @Override
    @Transactional
    public void doRebateTransfer(Long transactionNo,Long payerIdentityId, String payerIdentityType,
                              Long payerAccountType, Long payeeIdentityId,
                              String payeeIdentityType, Long payeeAccountType, BigDecimal amt,
                              String type,String desc)
    {
        try
        {
            logger.debug("[RebateRecordServiceImpl:doRebateTransfer(" + payerIdentityId + ","
                            + payerAccountType + "," + payeeIdentityId + "," + payeeAccountType
                            + "," + amt + ")]");
            
            //获取付款方账户信息
            IdentityAccountRole payerAccount = identityAccountRoleService.getIdentityAccountRoleByParams(
                payerAccountType, payerIdentityId, payerIdentityType,
                Constant.Account.ACCOUNT_RELATION_TYPE_OWN, null);
            //获取收款方账户信息
            IdentityAccountRole payeeAccount = identityAccountRoleService.getIdentityAccountRoleByParams(
                payeeAccountType, payeeIdentityId, payeeIdentityType,
                Constant.Account.ACCOUNT_RELATION_TYPE_OWN, null);
            Long payerAccountId = payerAccount.getAccountId();
            Long payeeAccountId = payeeAccount.getAccountId();
            //执行转账操作
            airtimeAccountingTransaction.transfer(
                payerAccountId, 
                payerAccount.getAccountType(),
                payeeAccountId, 
                payeeAccount.getAccountType(),
                amt, desc,type, transactionNo);
        }catch(RpcException e){
            throw ExceptionUtil.throwException(e);
        }catch(HopsException e){
            throw ExceptionUtil.throwException(e);
        }
        catch (Exception e)
        {
            logger.error("[RebateRecordServiceImpl:doRebateTransfer(" + ExceptionUtil.getStackTraceAsString(e) + ")]");
            String[] msgParams = new String[] {"doRebateTransfer"};
            ApplicationException ae = new ApplicationException("transaction001011", msgParams);
            throw ExceptionUtil.throwException(ae);
        }
    }
}
