package com.shop.service.impl;


import com.shop.dao.WantedMapper;
import com.shop.pojo.Wanted;
import com.shop.service.WantedService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service("wantedService")
public class WantedServiceImpl implements WantedService {

    @Resource
    private WantedMapper wantedMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return wantedMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(Wanted wanted) {
        return wantedMapper.insert(wanted);
    }

    @Override
    public Wanted selectByPrimaryKey(Integer id) {
        return wantedMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Wanted> selectAll() {
        return wantedMapper.selectAll();
    }

    @Override
    public int updateByPrimaryKey(Wanted wanted) {
        return wantedMapper.updateByPrimaryKey(wanted);
    }

    @Override
    public Wanted selectWant(Integer uid, Integer gid) {
        return wantedMapper.selectWant(uid,gid);
    }

    @Override
    public List<Wanted> selectWantByUserId(Integer uid) {
        return wantedMapper.selectWantByUserId(uid);
    }

    @Override
    public int deleteWantedByGoodsId(Integer gid) {
        return wantedMapper.deleteWantedByGoodsId(gid);
    }


}
