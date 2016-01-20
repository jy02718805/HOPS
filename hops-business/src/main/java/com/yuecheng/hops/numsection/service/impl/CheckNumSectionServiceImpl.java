package com.yuecheng.hops.numsection.service.impl;


import java.util.Date;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.transaction.annotation.Transactional;

import com.yuecheng.hops.common.exception.ApplicationException;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.common.utils.NumberUtil;
import com.yuecheng.hops.common.utils.StringUtil;
import com.yuecheng.hops.numsection.entity.CarrierInfo;
import com.yuecheng.hops.numsection.entity.City;
import com.yuecheng.hops.numsection.entity.NumSection;
import com.yuecheng.hops.numsection.entity.Province;
import com.yuecheng.hops.numsection.service.CarrierInfoService;
import com.yuecheng.hops.numsection.service.CheckNumSectionService;
import com.yuecheng.hops.numsection.service.CityService;
import com.yuecheng.hops.numsection.service.NumSectionService;
import com.yuecheng.hops.numsection.service.ProvinceService;


@Service("checkNumSectionService")
@Transactional
public class CheckNumSectionServiceImpl implements CheckNumSectionService
{

    @Autowired
    private NumSectionService numSectionService;

    @Autowired
    private ProvinceService provinceService;

    @Autowired
    private CityService cityService;

    @Autowired
    private CarrierInfoService carrierInfoService;

    private static Logger logger = LoggerFactory.getLogger(CheckNumSectionServiceImpl.class);

    /**
     * 手机归属地查询 本地查询没有则调用第三方接口查询 Author：Jinger 2014-01-23
     */
    @Override
    public NumSection checkNum(String phone)
    {
        try
        {
            logger.info("[CheckNumSectionServiceImpl:checkNum(" + phone + ")]");
            if (phone != null && !phone.isEmpty())
            {
                if(NumberUtil.isFixedPhone(phone))
                {
                    String zipCode = NumberUtil.getZipFromHomephone(phone);
                    NumSection num = numSectionService.findOne(zipCode);
                    return num;
                }
                else
                {
                    String numSection = phone.substring(0, 7);
                    NumSection num = numSectionService.findOne(numSection);
                    if (num == null)
                    {
                        num = new NumSection();
                        try
                        {
    
                            Document doc = Jsoup.connect(
                                "http://www.ip138.com:8080/search.asp?mobile=" + phone + "&action=mobile").get();
                            String cityCode = doc.getElementsContainingOwnText("区 号").first().nextElementSibling().text().trim();
                            if(cityCode.length() < 4){
                                cityCode = cityCode + "_";
                            }
                            City city = cityService.findOne(cityCode);
                            if (city != null)
                            {
                                Province province = provinceService.findOne(city.getProvinceId());
                                num.setSectionId(numSection);
                                num.setCity(city);
                                num.setProvince(province);
                                logger.debug("找到城市，城市信息：" + city.toString() + ",号段信息：" + num.toString());
                            }
                            else
                            {
                                return null;
                            }
    
                            Element table = doc.select("table").get(1);
                            Element phoneType_yd = table.getElementsContainingText("移动").first();
                            Element phoneType_lt = table.getElementsContainingText("联通").first();
                            Element phoneType_dx = table.getElementsContainingText("电信").first();
                            
                            Element phoneType = doc.getElementsContainingOwnText("型").first();
                            
                            String phoneTypeStr = BeanUtils.isNotNull(phoneType)?phoneType.nextElementSibling().text():StringUtil.initString();
                            
                            CarrierInfo carrierInfo = null;
                            if (BeanUtils.isNotNull(phoneType_yd))
                            {
                                carrierInfo = carrierInfoService.getByCarrierName("移动");
                            }
                            else if (BeanUtils.isNotNull(phoneType_lt))
                            {
                                carrierInfo = carrierInfoService.getByCarrierName("联通");
                            }
                            else if (BeanUtils.isNotNull(phoneType_dx))
                            {
                                carrierInfo = carrierInfoService.getByCarrierName("电信");
                            }
                            else
                            {
                                return null;
                            }
                            if (carrierInfo != null && carrierInfo.getCarrierNo() != null)
                            {
                                num.setCarrierInfo(carrierInfo);
                                num.setMobileType(phoneTypeStr);
                                num.setCreateTime(new Date());
                                logger.debug("找到运营商，运营商信息：" + carrierInfo.toString() + ",号段信息："
                                             + num.toString());
                                num = numSectionService.saveNumSection(num);
                                logger.debug("保存号段信息成功！");
                            }
                            else
                            {
                                return null;
                            }
                            logger.debug("返回号段信息：" + num.toString());
                            return num;
                        }
                        catch (Exception ex)
                        {
                            logger.debug("[CheckNumSectionServiceImpl:checkNum(异常)]" + ex.getMessage());
                            String[] msgParams = new String[] {phone};
                            String[] viewParams = new String[] {};
                            ApplicationException ae = new ApplicationException("businesss000018",
                                msgParams, viewParams);
                            throw ExceptionUtil.throwException(ae);
                        }
                    }
                    logger.debug("数据库已保存，返回号段信息：" + num.toString());
                    return num;
                }
                
            }
            logger.debug("调用查询号码为空，返回 null");
        }
        catch(CannotCreateTransactionException e){
            throw e;
        }
        return null;
    }
}
