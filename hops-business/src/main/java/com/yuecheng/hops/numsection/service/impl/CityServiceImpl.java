package com.yuecheng.hops.numsection.service.impl;
/**
 * 城市逻辑层
 * @author Jinger
 * @date：2013-10-18
 *
 */
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springside.modules.persistence.SearchFilter;

import com.yuecheng.hops.common.query.BSort;
import com.yuecheng.hops.common.query.PageUtil;
import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.numsection.entity.City;
import com.yuecheng.hops.numsection.repository.CityDao;
import com.yuecheng.hops.numsection.service.CityService;

@Service("cityService")
public class CityServiceImpl implements CityService{
	@Autowired
	private CityDao cityDao;

	private static Logger logger = LoggerFactory.getLogger(CityServiceImpl.class);
	
	@Override
	public City saveCity(City city) {
		logger.info("[CityServiceImpl:saveCity("+city!=null?city.toString():null+")]");
		if(city!=null)
			city= cityDao.save(city);
		return city;
	}

	@Override
	public void deleteCity(String cityId) {
		logger.info("[CityServiceImpl:deleteCity("+cityId+")]");
		cityDao.delete(cityId);	
	}

	@Override
	public void updateCity(String cityId, String status) {
//		return 0;
	}

	@Override
	public City findOne(String cityId) {
		logger.info("[CityServiceImpl:findOne("+cityId+")]");
		return cityDao.findOne(cityId);
	}

	@Override
	public List<City> selectAll() {
		logger.info("[CityServiceImpl:selectAll()]");
		return cityDao.selectAll();
	}

	@Override
	public YcPage<City> queryCity(Map<String, Object> searchParams,
			int pageNumber, int pageSize, BSort bsort) {
		logger.info("[CityServiceImpl:queryCity("+searchParams!=null?searchParams.toString():null+","+pageNumber+","+pageSize+")]");
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		String orderCloumn = bsort==null?"id":bsort.getCloumn();
		String orderDirect = bsort==null?"DESC":bsort.getDirect().toString();
	    Sort sort = new Sort(Direction.valueOf(Direction.class,orderDirect),orderCloumn);
		Page<City> page = PageUtil.queryPage(cityDao, filters, pageNumber, pageSize,sort,City.class);
		YcPage<City> ycPage = new YcPage<City>();
		ycPage.setList(page.getContent());
		ycPage.setPageTotal(page.getTotalPages());
		ycPage.setCountTotal((int)page.getTotalElements());
		return ycPage;
	}

	@Override
	public List<City> getCityByProvince(String provinceId) {
		logger.info("[CityServiceImpl:getCityByProvince("+provinceId+")]");
		return cityDao.getCityList(provinceId);
	}

	@Override
	public City getByCityName(String cityName) {
		logger.info("[CityServiceImpl:getByCityName("+cityName+")]");
		return cityDao.getByCityName(cityName);
	}

}
