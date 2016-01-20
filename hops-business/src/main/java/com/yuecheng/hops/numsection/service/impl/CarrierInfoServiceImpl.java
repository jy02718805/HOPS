package com.yuecheng.hops.numsection.service.impl;
/**
 * 运营商逻辑层
 * @author Jinger
 * @date：2013-10-18
 *
 */
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuecheng.hops.numsection.entity.CarrierInfo;
import com.yuecheng.hops.numsection.repository.CarrierInfoDao;
import com.yuecheng.hops.numsection.service.CarrierInfoService;

@Service("carrierInfoService")
public class CarrierInfoServiceImpl implements CarrierInfoService{
	@Autowired
	private CarrierInfoDao carrierInfoDao;

	private static Logger logger = LoggerFactory.getLogger(CarrierInfoServiceImpl.class);
	
	@Override
	public CarrierInfo saveCarrierInfo(CarrierInfo carrierInfo) {
		logger.info("[CarrierInfoServiceImpl:saveCarrierInfo("+carrierInfo!=null?carrierInfo.toString():null+")]");
		if(carrierInfo!=null)
			carrierInfo= carrierInfoDao.save(carrierInfo);
		return carrierInfo;
	}

	@Override
	public CarrierInfo findOne(String carrierInfoId) {
		logger.info("[CarrierInfoServiceImpl:findOne("+carrierInfoId+")]");
		return carrierInfoDao.findOne(carrierInfoId);
	}

	@Override
	public List<CarrierInfo> getAllCarrierInfo() {
		logger.info("[CarrierInfoServiceImpl:getAllCarrierInfo()]");
		return carrierInfoDao.selectAll();
	}

	@Override
	public CarrierInfo getByCarrierName(String carrierName) {
		logger.info("[CarrierInfoServiceImpl:getByCarrierName("+carrierName+")]");
		return carrierInfoDao.getByCityName(carrierName);
	}

}
