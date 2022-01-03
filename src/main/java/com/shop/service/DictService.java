package com.shop.service;



import com.shop.pojo.Area;
import com.shop.pojo.City;
import com.shop.pojo.Province;

import java.util.List;

public interface DictService {
	List<Province> getProvinces();
	List<City> getCities(String provinceCode);
	List<Area> getAreas(String cityCode);

}







