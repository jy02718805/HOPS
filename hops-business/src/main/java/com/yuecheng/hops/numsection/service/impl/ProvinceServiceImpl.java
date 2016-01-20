package com.yuecheng.hops.numsection.service.impl;
/**
 * 省份逻辑层
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
import com.yuecheng.hops.numsection.entity.Province;
import com.yuecheng.hops.numsection.repository.ProvinceDao;
import com.yuecheng.hops.numsection.service.ProvinceService;

@Service("provinceService")
public class ProvinceServiceImpl implements ProvinceService{
	@Autowired
	private ProvinceDao provinceDao;

	private static Logger logger = LoggerFactory.getLogger(ProvinceServiceImpl.class);
	
	@Override
	public Province saveProvince(Province province) {
		logger.info("[ProvinceServiceImpl:saveProvince("+province!=null?province.toString():null+")]");
		if(province!=null)
			province= provinceDao.save(province);
		return province;
	}

	@Override
	public void deleteProvince(String provinceId) {
		logger.info("[ProvinceServiceImpl:deleteProvince("+provinceId+")]");
		provinceDao.delete(provinceId);
	}

	@Override
	public void updateProvince(String provinceId, String status) {		
//		return 0;
	}

	@Override
	public Province findOne(String provinceId) {
		logger.info("[ProvinceServiceImpl:findOne("+provinceId+")]");
		return provinceDao.findOne(provinceId);
	}

	@Override
	public List<Province> getAllProvince() {
		logger.info("[ProvinceServiceImpl:getAllProvince()]");
		return provinceDao.selectAll();
	}

	@Override
	public YcPage<Province> queryProvince(Map<String, Object> searchParams,
			int pageNumber, int pageSize, BSort bsort) {
		logger.info("[ProvinceServiceImpl:queryProvince("+searchParams!=null?searchParams.toString():null+","+pageNumber+","+pageSize+")]");
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		String orderCloumn = bsort==null?"id":bsort.getCloumn();
		String orderDirect = bsort==null?"DESC":bsort.getDirect().toString();
	    Sort sort = new Sort(Direction.valueOf(Direction.class,orderDirect),orderCloumn);
		Page<Province> page = PageUtil.queryPage(provinceDao, filters, pageNumber, pageSize,sort,Province.class);
		YcPage<Province> ycPage = new YcPage<Province>();
		ycPage.setList(page.getContent());
		ycPage.setPageTotal(page.getTotalPages());
		ycPage.setCountTotal((int)page.getTotalElements());
		return ycPage;
	}

}
