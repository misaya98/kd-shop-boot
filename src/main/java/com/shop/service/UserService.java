package com.shop.service;


import com.shop.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserService {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user
     *
     * @mbggenerated
     */
    int insert(User record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user
     *
     * @mbggenerated
     */
    User selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user
     *
     * @mbggenerated
     */
    List<User> selectAll();

    int updateByPrimaryKey(User record);

    int updateLastLoginByPrimaryKey(User record);

    int updateByPrimaryKeySelective(User record);

    User getUserByPhone(String phone);

    List<User> getUserList();

    int updateGoodsNum(Integer id, Integer goodsNum);

    int updateImgUrl(Integer id, String url);

    Integer checkUserByPhone(String phone);

    int updatePasswordByPrimaryKey(Integer uid, String password);

    int updatePowerByPrimaryKey(Integer uid, Integer power);

    List<User> searchUserByNameOrPhone(String name, String phone);
}
