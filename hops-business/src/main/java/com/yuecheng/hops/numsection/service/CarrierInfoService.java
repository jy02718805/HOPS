package com.yuecheng.hops.numsection.service;


/**
 * 运营商逻辑层
 * 
 * @author Jinger
 * @date：2013-10-18
 */
import java.util.List;

import com.yuecheng.hops.numsection.entity.CarrierInfo;


public interface CarrierInfoService
{
    public CarrierInfo saveCarrierInfo(CarrierInfo carrierInfo);

    public CarrierInfo findOne(String carrierInfoId);

    public List<CarrierInfo> getAllCarrierInfo();

    public CarrierInfo getByCarrierName(String carrierName);


}
