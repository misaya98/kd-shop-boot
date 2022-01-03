package com.shop.service.impl;

import com.shop.dao.ImageMapper;
import com.shop.pojo.Image;
import com.shop.service.ImageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("imageService")
public class ImageServiceImpl implements ImageService{

    @Resource
    private ImageMapper imageMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return imageMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(Image record) {
        return imageMapper.insert(record);
    }

    @Override
    public Image selectByPrimaryKey(Integer id) {
        return imageMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Image> selectAll() {
        return imageMapper.selectAll();
    }

    @Override
    public int updateByPrimaryKey(Image record) {
        return imageMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<Image> selectByGoodsPrimaryKey(Integer goods_id) {
        return imageMapper.selectByGoodsPrimaryKey(goods_id);
    }

    @Override
    public int deleteImagesByGoodsPrimaryKey(Integer goods_id) {
        return imageMapper.deleteImagesByGoodsPrimaryKey(goods_id);
    }
}
