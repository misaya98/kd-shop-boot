package com.shop.service.impl;

import com.shop.dao.CommentsMapper;
import com.shop.pojo.Comments;
import com.shop.service.CommentsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service("commentsService")
public class CommentsServiceImpl implements CommentsService{

    @Resource
    private CommentsMapper commentsMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return commentsMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(Comments record) {
        return commentsMapper.insert(record);
    }

    @Override
    public Comments selectByPrimaryKey(Integer id) {
        return commentsMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Comments> selectAll() {
        return commentsMapper.selectAll();
    }

    @Override
    public int updateByPrimaryKey(Comments record) {
        return commentsMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<Comments> selectByUserKeyWithContent(Integer uid) {
        return commentsMapper.selectByUserKeyWithContent(uid);
    }

    @Override
    public List<Comments> selectByGoodsId(Integer goodsId) {
        return commentsMapper.selectByGoodsId(goodsId);
    }

    @Override
    public List<Comments> searchCommentsByContent(String content) {
        return commentsMapper.searchCommentsByContent(content);
    }
}
