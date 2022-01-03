package com.shop.dao;


import com.shop.pojo.Area;
import com.shop.pojo.City;
import com.shop.pojo.Province;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface DictMapper {

	List<Province> getProvinces();
	List<City> getCities(String provinceCode);
	List<Area> getAreas(String cityCode);
	String  getProvinceByCode(String code);
	String  getCityByCode(String code);
	String  getAreaByCode(String code);
}









