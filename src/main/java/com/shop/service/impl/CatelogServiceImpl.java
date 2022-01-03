package com.shop.service.impl;


import com.shop.dao.CatelogMapper;
import com.shop.pojo.Catelog;
import com.shop.service.CatelogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("catelogService")
public class CatelogServiceImpl implements CatelogService {

    @Resource
    private CatelogMapper catelogMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return catelogMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(Catelog record) {
        return catelogMapper.insert(record);
    }

    @Override
    public Catelog selectByPrimaryKey(Integer id) {
        return catelogMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Catelog> selectAll() {
        return catelogMapper.selectAll();
    }

    @Override
    public int updateByPrimaryKey(Catelog record) {
        return catelogMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<Catelog> getAllCatelogByStatus(Byte status) {
        return catelogMapper.getAllCatelogByStatus(status);
    }

    @Override
    public int getCount(Catelog catelog) {
        return catelogMapper.getCount(catelog);
    }

    @Override
    public int updateCatelogNum(Integer id, Integer number) {
        return catelogMapper.updateCatelogNum(id,number);
    }

    @Override
    public List<Catelog> searchCatelogByName(String name) {
        return catelogMapper.searchCatelogByName(name);
    }
}
