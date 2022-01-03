package com.shop.controller;


import com.shop.pojo.Area;
import com.shop.pojo.City;
import com.shop.pojo.Province;
import com.shop.pojo.ResponseResult;
import com.shop.service.DictService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;


@Controller
@RequestMapping("/dict")
public class DictController {
	@Resource
	private DictService dictService;

	@RequestMapping("/showProvince")
	@ResponseBody
	public ResponseResult<List<Province>>
		showProvince(){
		ResponseResult<List<Province>> rr;
		List<Province> listp = 
				dictService.getProvinces();
		rr = new ResponseResult<List<Province>>();
		rr.setState(1);
		rr.setMessage("获取数据成功");
		rr.setData(listp);
		return rr;
	}
	
	@RequestMapping("/showCity")
	@ResponseBody
	public ResponseResult<List<City>> showCity(@RequestParam("provinceCode") String provinceCode){
		ResponseResult<List<City>> rr;
		List<City> listC = dictService.getCities(provinceCode);
		rr = new ResponseResult<List<City>>();
		rr.setState(1);
		rr.setMessage("获取数据成功");
		rr.setData(listC);
		return rr;
	}
	@RequestMapping("/showArea")
	@ResponseBody
	public ResponseResult<List<Area>> showArea(@RequestParam("cityCode")  String cityCode){
		ResponseResult<List<Area>> rr;
		List<Area> listA = dictService.getAreas(cityCode);
		
		rr = new ResponseResult<>();
		rr.setData(listA);
		rr.setState(1);
		rr.setMessage("获取数据成功");
		return rr;
	}
	

}









