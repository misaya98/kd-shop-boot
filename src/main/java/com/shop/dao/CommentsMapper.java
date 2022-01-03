package com.shop.dao;


import com.shop.pojo.Comments;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommentsMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table comments
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table comments
     *
     * @mbggenerated
     */
    int insert(Comments record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table comments
     *
     * @mbggenerated
     */
    Comments selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table comments
     *
     * @mbggenerated
     */
    List<Comments> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table comments
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(Comments record);

    List<Comments> selectByUserKeyWithContent(@Param("uid") Integer uid);

    List<Comments> selectByGoodsId(@Param("gid") Integer goodsId);

    List<Comments> searchCommentsByContent(@Param("content") String content);
}