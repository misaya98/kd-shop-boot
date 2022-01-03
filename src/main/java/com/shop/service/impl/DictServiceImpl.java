package com.shop.service.impl;

import com.shop.dao.DictMapper;
import com.shop.pojo.Area;
import com.shop.pojo.City;
import com.shop.pojo.Province;
import com.shop.service.DictService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("dictService")
public class DictServiceImpl implements DictService {

	@Resource
	private DictMapper dictMapper;

	@Override
	public List<Province> getProvinces() {
		return dictMapper.getProvinces();
	}
	@Override
	public List<City> getCities(String provinceCode) {
		return dictMapper.getCities(provinceCode);
	}
	@Override
	public List<Area> getAreas(String cityCode) {
		return dictMapper.getAreas(cityCode);
	}

}
